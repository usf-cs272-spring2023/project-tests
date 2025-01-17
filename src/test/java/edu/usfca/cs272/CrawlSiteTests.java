package edu.usfca.cs272;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

import edu.usfca.cs272.ThreadBuildTests.Threads;

/**
 * A test suite for project v4.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CrawlSiteTests extends ProjectBenchmarks {
	// ███████╗████████╗ ██████╗ ██████╗
	// ██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗
	// ███████╗   ██║   ██║   ██║██████╔╝
	// ╚════██║   ██║   ██║   ██║██╔═══╝
	// ███████║   ██║   ╚██████╔╝██║
	// ╚══════╝   ╚═╝    ╚═════╝ ╚═╝

	/*
	 * ...and read this! Please do not spam web servers by rapidly re-running all of
	 * these tests over and over again. You risk being blocked by the web server if
	 * you make making too many requests in too short of a time period!
	 *
	 * Focus on one test or one group of tests at a time instead. If you do that,
	 * you will not have anything to worry about!
	 */

	/**
	 * Tests the output of this project on simple web sites.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class InitialTests {
		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(1)
		public void testSimpleCounts() throws MalformedURLException {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir;
			testCrawl(seed, subdir, id, crawl, List.of(ProjectFlag.COUNTS));
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(2)
		public void testSimpleIndex() throws MalformedURLException {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir;
			testCrawl(seed, subdir, id, crawl, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(3)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testSimpleSearch() throws MalformedURLException {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir;
			ProjectPath query = ProjectPath.QUERY_SIMPLE;
			testPartial(seed, subdir, id, crawl, query, List.of(ProjectFlag.RESULTS));
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(4)
		public void testBirdsCounts() throws MalformedURLException {
			int crawl = 50;
			String seed = "input/birds/";
			String subdir = "birds";
			String id = subdir;
			testCrawl(seed, subdir, id, crawl, List.of(ProjectFlag.COUNTS));
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(5)
		public void testBirdsIndex() throws MalformedURLException {
			int crawl = 50;
			String seed = "input/birds/";
			String subdir = "birds";
			String id = subdir;
			testCrawl(seed, subdir, id, crawl, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(6)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testBirdsSearch() throws MalformedURLException {
			int crawl = 50;
			String seed = "input/birds/";
			String subdir = "birds";
			String id = subdir;
			ProjectPath query = ProjectPath.QUERY_LETTERS;
			testPartial(seed, subdir, id, crawl, query, List.of(ProjectFlag.RESULTS));
		}

		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the output of this project on more complex web sites.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class ComplexTests {
		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(1)
		public void testRFCsBuild() throws MalformedURLException {
			int crawl = 7;
			String seed = "input/rfcs/";
			String subdir = "rfcs";
			String id = subdir;
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(2)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testRFCsSearch() throws MalformedURLException {
			int crawl = 7;
			String seed = "input/rfcs/";
			String subdir = "rfcs";
			String id = subdir;
			ProjectPath query = ProjectPath.QUERY_LETTERS;
			List<ProjectFlag> output = List.of(ProjectFlag.RESULTS);
			testPartial(seed, subdir, id, crawl, query, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(3)
		public void testGutenBuild() throws MalformedURLException {
			int crawl = 7;
			String seed = "input/guten/";
			String subdir = "guten";
			String id = subdir;
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(4)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testGutenSearch() throws MalformedURLException {
			int crawl = 7;
			String seed = "input/guten/";
			String subdir = "guten";
			String id = subdir;
			ProjectPath query = ProjectPath.QUERY_COMPLEX;
			List<ProjectFlag> output = List.of(ProjectFlag.RESULTS);
			testPartial(seed, subdir, id, crawl, query, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(5)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testJavaBuild() throws MalformedURLException {
			int crawl = 50;
			String seed = "docs/api/allclasses-index.html";
			String subdir = "java";
			String id = subdir;
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(6)
		@Tag("test-v4.1")
		@Tag("test-v4.x")
		public void testJavaSearch() throws MalformedURLException {
			int crawl = 50;
			String seed = "docs/api/allclasses-index.html";
			String subdir = "java";
			String id = subdir;
			ProjectPath query = ProjectPath.QUERY_LETTERS;
			List<ProjectFlag> output = List.of(ProjectFlag.RESULTS);
			testPartial(seed, subdir, id, crawl, query, output);
		}

		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the output of this project on special web sites.
	 */
	@Nested
	@Order(3)
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("past-v5")
	@TestMethodOrder(OrderAnnotation.class)
	public class SpecialTests {
		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(1)
		public void testRecurse() throws MalformedURLException {
			int crawl = 100;
			String seed = "https://www.cs.usfca.edu/~cs272/recurse/link01.html";
			String subdir = "special";
			String id = "recurse";
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(2)
		public void testRedirect() throws MalformedURLException {
			int crawl = 10;
			String seed = "https://www.cs.usfca.edu/~cs272/redirect/index.html";
			String subdir = "special";
			String id = "redirect";
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Tests the project output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(3)
		public void testLocal() throws MalformedURLException {
			int crawl = 200;
			String seed = "https://www.cs.usfca.edu/~cs272/local.html";
			String subdir = "special";
			String id = "local";
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, crawl, output);
		}

		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@Order(4)
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("past-v5")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {
		/**
		 * Tests that exceptions are not thrown.
		 *
		 * @throws Exception if an error occurs
		 */
		@Test
		@Order(1)
		public void testMissingLimit() throws Exception {
			Map<ProjectFlag, String> config = new LinkedHashMap<>();
			config.put(ProjectFlag.MAX, null);
			config.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);

			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir;

			CrawlPageTests.testCrawl(seed, subdir, id, config, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests that exceptions are not thrown.
		 *
		 * @throws Exception if an error occurs
		 */
		@Test
		@Order(2)
		public void testInvalidLimit() throws Exception {
			Map<ProjectFlag, String> config = new LinkedHashMap<>();
			config.put(ProjectFlag.MAX, "hello");
			config.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);

			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir;

			CrawlPageTests.testCrawl(seed, subdir, id, config, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests that exceptions are not thrown.
		 *
		 * @throws Exception if an error occurs
		 */
		@Test
		@Order(3)
		public void testMissingThreads() throws Exception {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir + "-" + crawl;

			Map<ProjectFlag, String> config = new LinkedHashMap<>();
			config.put(ProjectFlag.MAX, Integer.toString(crawl));

			CrawlPageTests.testCrawl(seed, subdir, id, config, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests that exceptions are not thrown.
		 *
		 * @throws Exception if an error occurs
		 */
		@Test
		@Order(4)
		public void testInvalidThreads() throws Exception {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir + "-" + crawl;

			Map<ProjectFlag, String> config = new LinkedHashMap<>();
			config.put(ProjectFlag.MAX, Integer.toString(crawl));
			config.put(ProjectFlag.THREADS, "hello");

			CrawlPageTests.testCrawl(seed, subdir, id, config, List.of(ProjectFlag.INDEX));
		}

		/**
		 * Tests that exceptions are not thrown.
		 *
		 * @throws Exception if an error occurs
		 */
		@Test
		@Order(5)
		public void testNoOutput() throws Exception {
			int crawl = 15;
			String seed = "input/simple/";
			String subdir = "simple";
			String id = subdir + "-" + crawl;

			Map<ProjectFlag, String> config = new LinkedHashMap<>();
			config.put(ProjectFlag.MAX, Integer.toString(crawl));
			config.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);

			CrawlPageTests.testCrawl(seed, subdir, id, config, Collections.emptyList());
		}

		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the runtime of this project.
	 */
	@Nested
	@Order(5)
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("past-v5")
	@TestMethodOrder(OrderAnnotation.class)
	public class RuntimeTests {
		/**
		 * Tests consistency of output.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Order(1)
		@RepeatedTest(3)
		public void testConsistency() throws MalformedURLException {
			new ComplexTests().testGutenSearch();
		}

		/**
		 * Tests build speedup.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(2)
		public void testBuild() throws MalformedURLException {
			String seed = "docs/api/allclasses-index.html";
			URL url = new URL(new URL(CrawlPageTests.GITHUB), seed);
			int crawl = 25; // smaller to speed up benchmark

			Map<ProjectFlag, String> config1 = new LinkedHashMap<>();
			Map<ProjectFlag, String> config2 = new LinkedHashMap<>();

			config1.put(ProjectFlag.HTML, url.toString());
			config1.put(ProjectFlag.MAX, Integer.toString(crawl));
			config1.put(ProjectFlag.THREADS, Threads.ONE.text);

			config2.putAll(config1);
			config2.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);

			// convert to arguments
			String[] args1 = args(config1);
			String[] args2 = args(config2);

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			String format = ThreadBenchTests.format;
			double target = ProjectBenchmarks.FAST_SPEEDUP;

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Build", "1 Worker", args1, BENCH_WORKERS.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_WORKERS.num, result, target, "1 worker");
				assertTrue(result >= target, debug);
			});
		}

		/**
		 * Tests search speedup.
		 *
		 * @throws MalformedURLException if unable to convert seed to URL
		 */
		@Test
		@Order(3)
		public void testSearch() throws MalformedURLException {
			String seed = "docs/api/allclasses-index.html";
			URL url = new URL(new URL(CrawlPageTests.GITHUB), seed);
			int crawl = 25; // smaller to speed up benchmark

			Map<ProjectFlag, String> config1 = new LinkedHashMap<>();
			Map<ProjectFlag, String> config2 = new LinkedHashMap<>();

			config1.put(ProjectFlag.HTML, url.toString());
			config1.put(ProjectFlag.MAX, Integer.toString(crawl));
			config1.put(ProjectFlag.QUERY, ProjectPath.QUERY_LETTERS.text);
			config1.put(ProjectFlag.PARTIAL, null);
			config1.put(ProjectFlag.THREADS, Threads.ONE.text);

			config2.putAll(config1);
			config2.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);

			// convert to arguments
			String[] args1 = args(config1);
			String[] args2 = args(config2);

			// make sure code runs without exceptions before testing
			testNoExceptions(args1, SHORT_TIMEOUT);
			testNoExceptions(args2, SHORT_TIMEOUT);

			String format = ThreadBenchTests.format;
			double target = ProjectBenchmarks.FAST_SPEEDUP;

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = compare("Search", "1 Worker", args1, BENCH_WORKERS.text + " Workers", args2);
				Supplier<String> debug = () -> String.format(format, BENCH_WORKERS.num, result, target, "1 worker");
				assertTrue(result >= target, debug);
			});
		}

		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Calls {@link CrawlPageTests#testCrawl(String, String, String, Map, List)}
	 * with values for the crawl limit and number of threads.
	 *
	 * @param seed the seed link
	 * @param subdir the expected output subdir
	 * @param id the test id
	 * @param crawl the crawl limit
	 * @param output the output flags to use
	 * @throws MalformedURLException if unable to convert seed to URL
	 */
	public static void testCrawl(String seed, String subdir, String id, int crawl, List<ProjectFlag> output) throws MalformedURLException {
		Map<ProjectFlag, String> config = new LinkedHashMap<>();
		config.put(ProjectFlag.MAX, Integer.toString(crawl));
		config.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);
		CrawlPageTests.testCrawl(seed, subdir, id + "-" + crawl, config, output);
	}

	/**
	 * Calls {@link CrawlPageTests#testCrawl(String, String, String, Map, List)}
	 * with values for the crawl limit and number of threads.
	 *
	 * @param seed the seed link
	 * @param subdir the expected output subdir
	 * @param id the test id
	 * @param crawl the crawl limit
	 * @param query the query file
	 * @param output the output flags to use
	 * @throws MalformedURLException if unable to convert seed to URL
	 */
	public static void testPartial(String seed, String subdir, String id, int crawl, ProjectPath query, List<ProjectFlag> output) throws MalformedURLException {
		Map<ProjectFlag, String> config = new LinkedHashMap<>();
		config.put(ProjectFlag.QUERY, query.text);
		config.put(ProjectFlag.PARTIAL, null);
		config.put(ProjectFlag.MAX, Integer.toString(crawl));
		config.put(ProjectFlag.THREADS, ThreadBenchTests.BENCH_WORKERS.text);
		CrawlPageTests.testCrawl(seed, subdir, id + "-" + crawl, config, output);
	}
}
