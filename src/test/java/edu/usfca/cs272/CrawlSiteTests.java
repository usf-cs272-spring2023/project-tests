package edu.usfca.cs272;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

/**
 * A test suite for project v4.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CrawlSiteTests {
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
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@TestMethodOrder(OrderAnnotation.class)
	public class ComplexTests {
		@Order(1)
		public void testRFCs() throws MalformedURLException {
			int crawl = 7;
		}

		@Order(2)
		public void testGuten() throws MalformedURLException {
			int crawl = 7;
		}

		@Order(3)
		public void testJava() throws MalformedURLException {
			int crawl = 50;
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
	@TestMethodOrder(OrderAnnotation.class)
	public class SpecialTests {
		@Order(1)
		public void testRecurse() throws MalformedURLException {
			int crawl = 100;
		}

		@Order(2)
		public void testRedirect() throws MalformedURLException {
			int crawl = 10;
		}

		@Order(3)
		public void testLocal() throws MalformedURLException {
			int crawl = 200;
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
		@Order(1)
		public void testMissingLimit() throws Exception {

		}

		@Order(2)
		public void testInvalidLimit() throws Exception {

		}

		@Order(3)
		public void testMissingThreads() throws Exception {

		}

		@Order(4)
		public void testInvalidThreads() throws Exception {

		}

		@Order(5)
		public void testNoOutput() throws Exception {

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
		@Order(1)
		@RepeatedTest(3)
		public void testConsistency() throws MalformedURLException {

		}

		@Order(2)
		public void testBuild() throws MalformedURLException {

		}

		@Order(3)
		public void testSearch() throws MalformedURLException {

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
