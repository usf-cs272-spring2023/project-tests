package edu.usfca.cs272;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * A test suite for project 3. During development, run individual tests instead
 * of this entire test suite!
 *
 * THIS IS THE RUNTIME SUITE OF TESTS. IT TAKES VERY LONG TO RUN. DO NOT RUN
 * UNLESS YOU ARE PASSING THE OUTPUT TESTS FIRST.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestMethodOrder(MethodName.class)
public class Project3bTest extends ProjectTests {
	/**
	 * Tests the output consistency of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_ConsistencyTest {
		/**
		 * Tests that the inverted index output remains consistent when repeated.
		 */
		@Order(1)
		@RepeatedTest(3)
		public void testIndexConsistency() {
			new Project3aTest().new A_ThreadBuildTests().testText(BENCH_THREADS);
		}

		/**
		 * Tests that the inverted index output remains consistent when repeated.
		 */
		@Order(2)
		@RepeatedTest(3)
		@Tag("test3b")
		public void testCountConsistency() {
			new Project3aTest().new A_ThreadBuildTests().testCounts(BENCH_THREADS);
		}

		/**
		 * Tests that the inverted index output remains consistent when repeated.
		 */
		@Order(3)
		@RepeatedTest(3)
		public void testExactSearchConsistency() {
			var test = new Project3aTest().new B_ExactSearchTests();
			test.setup();
			test.testTextDirectory(BENCH_THREADS);
		}

		/**
		 * Tests that the inverted index output remains consistent when repeated.
		 */
		@Order(4)
		@RepeatedTest(3)
		@Tag("test3b")
		public void testPartialSearchConsistency() {
			var test = new Project3aTest().new C_PartialSearchTests();
			test.setup();
			test.testTextDirectory(BENCH_THREADS);
		}
	}

	/**
	 * Tests the runtime for building of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	@Tag("test3b")
	public class B_BuildRuntimeTest {
		/**
		 * Tests that code runs faster with {@value #BENCH_THREADS} threads is 1.1x
		 * faster versus just 1 worker thread.
		 */
		@Order(1)
		@Test
		public void testIndexMultithreaded() {
			String[] args1 = {
					TEXT_FLAG, TEXT_PATH.toString(), THREADS_FLAG, String.valueOf(1)
			};

			String[] args2 = {
					TEXT_FLAG, TEXT_PATH.toString(), THREADS_FLAG, String.valueOf(BENCH_THREADS)
			};

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Build", "1 Worker", args1, String.valueOf(BENCH_THREADS) + " Workers", args2);

				assertTrue(result >= 1.1,
						() -> String.format("%d workers has a %.2fx speedup (less than the 1.1x required) compareed to %s.",
								BENCH_THREADS, result, "1 worker"));
			});
		}

		/**
		 * Tests that code runs faster with {@value #BENCH_THREADS} threads is 1.1x
		 * faster versus no worker threads.
		 */
		@Order(2)
		@Test
		public void testIndexSingleMulti() {
			String[] args1 = {
					TEXT_FLAG, TEXT_PATH.toString()
			};

			String[] args2 = {
					TEXT_FLAG, TEXT_PATH.toString(), THREADS_FLAG, String.valueOf(BENCH_THREADS)
			};

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Build", "Single", args1, String.valueOf(BENCH_THREADS) + " Workers", args2);

				assertTrue(result >= 1.1,
						() -> String.format("%d workers has a %.2fx speedup (less than the 1.1x required) compareed to %s.",
								BENCH_THREADS, result, "no workers"));
			});
		}
	}

	/**
	 * Tests the runtime for searching of this project. It is harder to detect
	 * issues with search runtime since the overall runtime is dominated by the
	 * building, so these tests may not catch runtime issues.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	@Tag("test3b")
	public class C_SeachRuntimeTest {
		/**
		 * Tests that code runs faster with {@value #BENCH_THREADS} threads is 1.1x
		 * faster versus just 1 worker thread.
		 */
		@Order(1)
		@Test
		@Tag("past4")
		public void testSearchMultithreaded() {
			String[] args1 = {
					TEXT_FLAG, TEXT_PATH.toString(), QUERY_FLAG, QUERY_PATH.resolve("complex.txt").toString(),
					THREADS_FLAG, String.valueOf(1)
			};

			String[] args2 = {
					TEXT_FLAG, TEXT_PATH.toString(), QUERY_FLAG, QUERY_PATH.resolve("complex.txt").toString(),
					THREADS_FLAG, String.valueOf(BENCH_THREADS)
			};

			System.out.println();
			System.out.printf("## Testing Search 1 vs %d Workers...%n", BENCH_THREADS);

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Search", "1 Worker", args1, String.valueOf(BENCH_THREADS) + " Workers", args2);

				assertTrue(result >= 1.1,
						() -> String.format("%d workers has a %.2fx speedup (less than the 1.1x required) compareed to %s.",
								BENCH_THREADS, result, "1 worker"));
			});
		}

		/**
		 * Tests that code runs faster with {@value #BENCH_THREADS} threads is 1.1x
		 * faster versus no worker threads.
		 */
		@Order(2)
		@Test
		public void testSearchSingleMulti() {
			String[] args1 = {
					TEXT_FLAG, TEXT_PATH.toString(), QUERY_FLAG, QUERY_PATH.resolve("complex.txt").toString()
			};

			String[] args2 = {
					TEXT_FLAG, TEXT_PATH.toString(), QUERY_FLAG, QUERY_PATH.resolve("complex.txt").toString(),
					THREADS_FLAG, String.valueOf(BENCH_THREADS)
			};

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Search", "Single", args1, String.valueOf(BENCH_THREADS) + " Workers", args2);

				assertTrue(result >= 1.1,
						() -> String.format("%d workers has a %.2fx speedup (less than the 1.1x required) compareed to %s.",
								BENCH_THREADS, result, "1 worker"));
			});
		}
	}

	/** The number of warmup runs when benchmarking. */
	public static final int WARM_RUNS = 4;

	/** The number of timed runs when benchmarking. */
	public static final int TIME_RUNS = 6;

	/** The default number of threads to use when benchmarking. */
	public static final int BENCH_THREADS = 4;

	/** The timeout for long runs (involving multiple runs of Driver). */
	public static final Duration LONG_TIMEOUT = Duration.ofMinutes(5);

	/**
	 * Compares the runtime using two different sets of arguments. Outputs the
	 * runtimes to the console just in case there are any anomalies.
	 *
	 * @param file the file name to use to save output
	 * @param label1 the label of the first argument set
	 * @param args1 the first argument set
	 * @param label2 the label of the second argument set
	 * @param args2 the second argument set
	 * @return the runtime difference between the first and second set of arguments
	 * @throws IOException if an I/O error occurs
	 */
	public static double compare(String file, String label1, String[] args1, String label2, String[] args2)
			throws IOException {
		return compare(file, label1, args1, label2, args2, WARM_RUNS, TIME_RUNS);
	}

	/**
	 * Compares the runtime using two different sets of arguments. Outputs the
	 * runtimes to the console just in case there are any anomalies.
	 *
	 * @param file the file name to use to save output
	 * @param label1 the label of the first argument set
	 * @param args1 the first argument set
	 * @param label2 the label of the second argument set
	 * @param args2 the second argument set
	 * @param warmRuns the number of warmup runs to use
	 * @param timeRuns the number of timed runs to use
	 * @return the runtime difference between the first and second set of arguments
	 * @throws IOException if an I/O error occurs
	 */
	public static double compare(String file, String label1, String[] args1, String label2, String[] args2, int warmRuns,
			int timeRuns) throws IOException {
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.OFF);

		// free up memory before benchmarking
		ProjectTests.freeMemory();

		// begin benchmarking
		long[] runs1 = benchmark(args1, warmRuns, timeRuns);
		long[] runs2 = benchmark(args2, warmRuns, timeRuns);

		long total1 = 0;
		long total2 = 0;

		long min1 = Long.MAX_VALUE;
		long min2 = Long.MAX_VALUE;

		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);

		out.printf("%n## Testing %s - %s versus %s%n", file, label1, label2);

		String labelFormat = "%-6s    %10s    %10s%n";
		String valueFormat = "%-6d    %10.6f    %10.6f%n";

		out.printf("%n```%n");
		out.printf(labelFormat, "Warmup", label1, label2);

		for (int i = 0; i < warmRuns; i++) {
			min1 = Math.min(min1, runs1[i]);
			min2 = Math.min(min2, runs2[i]);

			out.printf(valueFormat, i + 1,
					(double) runs1[i] / Duration.ofSeconds(1).toMillis(),
					(double) runs2[i] / Duration.ofSeconds(1).toMillis());
		}

		out.println();
		out.printf(labelFormat, "Timed", label1, label2);

		for (int i = warmRuns; i < warmRuns + timeRuns; i++) {
			total1 += runs1[i];
			total2 += runs2[i];

			min1 = Math.min(min1, runs1[i]);
			min2 = Math.min(min2, runs2[i]);

			out.printf(valueFormat, i + 1,
					(double) runs1[i] / Duration.ofSeconds(1).toMillis(),
					(double) runs2[i] / Duration.ofSeconds(1).toMillis());
		}

		double average1 = (double) total1 / timeRuns;
		double average2 = (double) total2 / timeRuns;

		out.println();
		out.printf("%10s:  %10.6f seconds average%n",   label1, average1 / Duration.ofSeconds(1).toMillis());
		out.printf("%10s:  %10.6f seconds average%n%n", label2, average2 / Duration.ofSeconds(1).toMillis());
		out.printf("%10s:  %10.6f seconds minimum%n",   label1, (double) min1 / Duration.ofSeconds(1).toMillis());
		out.printf("%10s:  %10.6f seconds minimum%n%n", label2, (double) min2 / Duration.ofSeconds(1).toMillis());

		double speedup = (double) min1 / min2;
		out.printf("%10s: x%10.6f %n", "Speedup", speedup);
		out.printf("```%n%n");
		out.flush();

		// output to console and to file
		String results = writer.toString();
		System.out.print(results);

		String test = label1.equals("Single") ? "single" : "multi";
		String format = "bench-%s-%s.txt";
		String filename = String.format(format, file.toLowerCase(), test);
		Files.writeString(ACTUAL_PATH.resolve(filename), results);

		return speedup;
	}

	/**
	 * Benchmarks the {@link Driver#main(String[])} method with the provided
	 * arguments. Tracks the timing of every run to allow of visual inspection.
	 *
	 * @param args the arguments to run
	 * @param warmRuns the number of warmup runs to use
	 * @param timeRuns the number of timed runs to use
	 * @return an array of all the runtimes, including warmup runs and timed runs
	 */
	public static long[] benchmark(String[] args, int warmRuns, int timeRuns) {
		long[] runs = new long[warmRuns + timeRuns];

		Instant start;
		Duration elapsed;

		// suppress all console output for the warmup and timed runs
		PrintStream systemOut = System.out;
		PrintStream systemErr = System.err;

		PrintStream nullStream = new PrintStream(OutputStream.nullOutputStream());
		System.setOut(nullStream);
		System.setErr(nullStream);

		systemOut.print("Benchmarking");

		try {
			for (int i = 0; i < warmRuns; i++) {
				start = Instant.now();
				Driver.main(args);
				elapsed = Duration.between(start, Instant.now());
				runs[i] = elapsed.toMillis();
				systemOut.print(".");
			}

			for (int i = 0; i < timeRuns; i++) {
				start = Instant.now();
				Driver.main(args);
				elapsed = Duration.between(start, Instant.now());
				runs[i + warmRuns] = elapsed.toMillis();
				systemOut.print(".");
			}
		}
		catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));

			String format = "%nArguments:%n    [%s]%nException:%n    %s%n";
			String debug = String.format(format, String.join(" ", args), writer.toString());
			fail(debug);
		}
		finally {
			systemOut.println("done.");

			// restore console output
			System.setOut(systemOut);
			System.setErr(systemErr);
		}

		return runs;
	}
}
