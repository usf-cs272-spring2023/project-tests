package edu.usfca.cs272;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A test suite for project 3. During development, run individual tests instead
 * of this entire test suite!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
@TestMethodOrder(MethodName.class)
public class Project3aTest extends ProjectTests {
	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_ThreadBuildTests {
		/**
		 * Tests the index output for hello.txt in the simple subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(1)
		@Tag("test3a")
		@Tag("past3b")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testSimpleHello(int threads) {
			Path input = TEXT_PATH.resolve("simple").resolve("hello.txt");
			testIndex("index-simple", input, threads);
		}

		/**
		 * Tests the index output for the simple subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(2)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testSimpleDirectory(int threads) {
			Path input = TEXT_PATH.resolve("simple");
			testIndex("index-simple", input, threads);
		}

		/**
		 * Tests the index output for the stems subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(3)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testStemsDirectory(int threads) {
			Path input = TEXT_PATH.resolve("stems");
			testIndex("index-stems", input, threads);
		}

		/**
		 * Tests the index output for the rfcs subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(4)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testRfcDirectory(int threads) {
			Path input = TEXT_PATH.resolve("rfcs");
			testIndex("index-rfcs", input, threads);
		}

		/**
		 * Tests the index output for 1400-0.txt in the guten subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(5)
		@Tag("test3a")
		@Tag("past3b")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testGuten1400(int threads) {
			Path input = TEXT_PATH.resolve("guten").resolve("1400-0.txt");
			testIndex("index-guten", input, threads);
		}

		/**
		 * Tests the index output for the guten subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(6)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testGutenDirectory(int threads) {
			Path input = TEXT_PATH.resolve("guten");
			testIndex("index-guten", input, threads);
		}

		/**
		 * Tests the index output for all of the text files.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(7)
		@Tag("test3a")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testText(int threads) {
			Path input = TEXT_PATH;
			testIndex(".", input, threads);
		}

		/**
		 * Tests the word counts functionality of the inverted index on the entire
		 * input directory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(8)
		@Tag("test3a")
		@Tag("past4")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testCounts(int threads) {
			String filename = "counts-text.json";
			String threaded = String.format("counts-text-%d.json", threads);

			Path actual = ACTUAL_PATH.resolve(threaded);
			Path expected = EXPECTED_PATH.resolve("counts").resolve(filename);

			String[] args = {
					TEXT_FLAG, TEXT_PATH.normalize().toString(), COUNTS_FLAG, actual.normalize().toString(), THREADS_FLAG,
					Integer.toString(threads)
			};

			Executable debug = () -> checkOutput(args, actual, expected);
			Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_ExactSearchTests {
		/** The default subdir for this nested class. */
		public String subdir;

		/** The default search mode for this nested class. */
		public boolean exact;

		/**
		 * Sets up the tests before running.
		 */
		@BeforeEach
		public void setup() {
			subdir = "exact";
			exact = true;
		}

		/**
		 * Tests the search result output for the simple directory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(1)
		@Tag("test3a")
		@Tag("past3b")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testSimpleDirectory(int threads) {
			Path input = TEXT_PATH.resolve("simple");
			String query = "simple.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for the stems directory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(2)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testStemsDirectory(int threads) {
			Path input = TEXT_PATH.resolve("stems");
			String query = "words.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for the rfcs subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(3)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testRfcDirectory(int threads) {
			Path input = TEXT_PATH.resolve("rfcs");
			String query = "letters.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for files in the guten subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(4)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testGutenFiles(int threads) {
			Path input = TEXT_PATH.resolve("guten").resolve("1400-0.txt");
			String query = "complex.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for the guten subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(5)
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testGutenDirectory(int threads) {
			Path input = TEXT_PATH.resolve("guten");
			String query = "complex.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(6)
		@Tag("test3a")
		@Tag("past3b")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testTextRespect(int threads) {
			Path input = TEXT_PATH;
			String query = "respect.txt";
			testSearch(subdir, input, query, exact, threads);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 *
		 * @param threads the number of worker threads to use
		 */
		@Order(7)
		@Tag("test3a")
		@Tag("past4")
		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {
				1, 2, 4
		})
		public void testTextDirectory(int threads) {
			Path input = TEXT_PATH;
			String query = "complex.txt";
			testSearch(subdir, input, query, exact, threads);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_PartialSearchTests extends B_ExactSearchTests {
		/**
		 * Initialize the subdir and search type before each test.
		 */
		@Override
		@BeforeEach
		public void setup() {
			subdir = "partial";
			exact = false;
		}
	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@Tag("test3a")
	@Tag("past3b")
	@Tag("past4")
	@TestMethodOrder(OrderAnnotation.class)
	public class D_ExceptionTests {
		/** Path to the hello.txt file. */
		public final String HELLO = TEXT_PATH.resolve("simple").resolve("hello.txt").toString();

		/** Path to the simple query file. */
		public final String SIMPLE = QUERY_PATH.resolve("simple.txt").toString();

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(1)
		@Test
		public void testNegativeThreads() {
			String[] args = {
					TEXT_FLAG, HELLO, QUERY_FLAG, SIMPLE, THREADS_FLAG, "-1"
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(2)
		@Test
		public void testZeroThreads() {
			String[] args = {
					TEXT_FLAG, HELLO, QUERY_FLAG, SIMPLE, THREADS_FLAG, "0"
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(3)
		@Test
		public void testFractionThreads() {
			String[] args = {
					TEXT_FLAG, HELLO, QUERY_FLAG, SIMPLE, THREADS_FLAG, "3.14"
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(4)
		@Test
		public void testWordThreads() {
			String[] args = {
					TEXT_FLAG, HELLO, QUERY_FLAG, SIMPLE, THREADS_FLAG, "fox"
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(5)
		@Test
		public void testDefaultThreads() {
			String[] args = {
					TEXT_FLAG, HELLO, QUERY_FLAG, SIMPLE, THREADS_FLAG
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(6)
		@Test
		public void testNoOutputBuild() {
			String[] args = {
					TEXT_FLAG, TEXT_PATH.toString(), THREADS_FLAG, String.valueOf(3)
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(7)
		@Test
		public void testNoOutputSearch() {
			String[] args = {
					TEXT_FLAG, TEXT_PATH.toString(), QUERY_FLAG, QUERY_PATH.resolve("words.txt").toString(),
					THREADS_FLAG, String.valueOf(3)
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}
	}

	/**
	 * Tests the approach of this project.
	 *
	 * THESE ARE SLOW TESTS. AVOID RUNNING UNLESS REALLY NEEDED.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	@Tag("test3a")
	public class E_ApproachTests {
		/**
		 * Tests that multithreading is actually occurring for building.
		 */
		@Test
		@Order(1)
		public void testMultithreadedBuild() {
			Runnable index = () -> {
				new A_ThreadBuildTests().testText(APROACH_THREADS);
				System.out.println("Random: " + Math.random());
			};

			testMultithreaded(index);
		}

		/**
		 * Tests that multithreading is actually occurring for searching.
		 */
		@Test
		@Order(2)
		public void testMultithreadedSearch() {
			Runnable search = () -> {
				var test = new C_PartialSearchTests();
				test.setup();
				test.testTextDirectory(APROACH_THREADS);
				System.out.println("Random: " + Math.random());
			};

			testMultithreaded(search);
		}
	}

	/** The default number of threads to use in mutlithreading approach tests. */
	public static final int APROACH_THREADS = 2;

	/**
	 * Attempts to test that multiple threads are being used in this code.
	 *
	 * @param action the action to run
	 */
	public static void testMultithreaded(Runnable action) {
		// time the action run
		Instant start = Instant.now();
		action.run();
		Duration elapsed = Duration.between(start, Instant.now());

		// get how long to pause when checking for multithreading
		Duration pause = elapsed.dividedBy(3);

		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
			// get the non-worker threads that are running this test code
			List<String> before = activeThreads();

			// start up the Driver thread
			Thread driver = new Thread(action);
			driver.setPriority(Thread.MAX_PRIORITY);
			driver.start();

			// pause this thread for a bit (this is where things can go wrong)
			// this gives Driver a chance to start up its worker threads
			Thread.sleep(pause.toMillis());

			// get the threads (ideally Driver should be up and running by this point)
			String error = "Something went wrong with the test code; see instructor. Elapsed: %d, Pause: %d";
			Assertions.assertTrue(driver.isAlive(), error.formatted(elapsed.toMillis(), pause.toMillis()));
			List<String> finish = activeThreads();

			// wait for Driver to finish up
			driver.join();

			// try to figure out which ones are worker threads
			List<String> workers = new ArrayList<>(finish);
			workers.removeAll(before); // remove threads running this code
			workers.remove(driver.getName()); // remove the driver thread
			workers.removeIf(name -> name.startsWith("junit")); // remove junit timeout threads
			workers.removeIf(name -> name.startsWith("ForkJoinPool")); // remove other junit threads

			String debug = "\nThreads Before: %s\nThreads After: %s\nWorker Threads: %s\n";
			Assertions.assertTrue(workers.size() > 0, debug.formatted(before, finish, workers));
		});
	}

	/**
	 * Returns a list of the active thread names (approximate).
	 *
	 * @return list of active thread names
	 */
	public static List<String> activeThreads() {
		int active = Thread.activeCount(); // only an estimate
		Thread[] threads = new Thread[active * 2]; // make sure large enough
		Thread.enumerate(threads);

		return Arrays.stream(threads).filter(t -> t != null).map(t -> t.getName()).collect(Collectors.toList());
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be
	 * used inside a JUnit test.
	 *
	 * @param subdir the output subdirectory to use
	 * @param input the input path to use
	 * @param threads the number of worker threads to use
	 */
	public static void testIndex(String subdir, Path input, int threads) {
		String filename = outputFileName("index", input);
		String threaded = outputFileName("index", input, threads);

		Path actual = ACTUAL_PATH.resolve(threaded);
		Path expected = EXPECTED_PATH.resolve("index").resolve(subdir).resolve(filename);

		String[] args = {
				TEXT_FLAG, input.normalize().toString(), INDEX_FLAG, actual.normalize().toString(), THREADS_FLAG,
				Integer.toString(threads)
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}

	/**
	 * Generates the arguments to use for this test case. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param subdir the output subdirectory to use
	 * @param input the input path to use
	 * @param query the query file to use for search
	 * @param exact whether to perform exact or partial search
	 * @param threads the number of worker threads to use
	 */
	public static void testSearch(String subdir, Path input, String query, boolean exact, int threads) {
		String type = exact ? "exact" : "partial";
		String prefix = "search-" + type + "-" + query.split("\\.")[0];
		String filename = outputFileName(prefix, input);
		String threaded = outputFileName(prefix, input, threads);

		Path actual = ACTUAL_PATH.resolve(threaded);
		Path expected = EXPECTED_PATH.resolve("search").resolve("search-" + subdir).resolve(filename);

		String[] args = {
				TEXT_FLAG, input.normalize().toString(), QUERY_FLAG, QUERY_PATH.resolve(query).toString(), RESULTS_FLAG,
				actual.normalize().toString(), THREADS_FLAG, Integer.toString(threads), exact ? EXACT_FLAG : ""
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}
}
