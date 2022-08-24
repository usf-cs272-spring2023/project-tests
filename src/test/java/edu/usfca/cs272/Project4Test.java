package edu.usfca.cs272;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A test suite for project 4. During development, run individual tests instead
 * of this entire test suite!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
@TestMethodOrder(MethodName.class)
public class Project4Test extends ProjectTests {
	// ███████╗████████╗ ██████╗ ██████╗
	// ██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗
	// ███████╗   ██║   ██║   ██║██████╔╝
	// ╚════██║   ██║   ██║   ██║██╔═══╝
	// ███████║   ██║   ╚██████╔╝██║
	// ╚══════╝   ╚═╝    ╚═════╝ ╚═╝

	/*
	 * TODO ...and read this! Please do not spam web servers by rapidly re-running
	 * all of these tests over and over again. You risk being blocked by the web
	 * server if you make making too many requests in too short of a time period!
	 *
	 * Focus on one test or one group of tests at a time instead. If you do that,
	 * you will not have anything to worry about!
	 */

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_SimpleTests {
		/** The base URL to use for all tests. */
		public String baseUrl;

		/** The base resource to use for all tests. */
		public String baseResource;

		/** The base subdir to use for all tests. */
		public String baseSubdir;

		/** The base query file to use for all tests. */
		public String baseQuery;

		/** The base limit to use for all tests. */
		public int baseLimit;

		/** Setup for the tests in this class. */
		@BeforeEach
		public void setup() {
			baseUrl = REMOTE;
			baseResource = "input/simple/";
			baseSubdir = "simple";
			baseQuery = "simple.txt";
			baseLimit = 15;
		}

		/**
		 * The individual files to test.
		 *
		 * @return the files to test
		 */
		public static Stream<String> getFiles() {
			return Stream.of("index.html", "a/b/c/subdir.html", "capital_extension.HTML", "empty.html", "hello.html",
					"mixed_case.htm", "position.html", "stems.html", "symbols.html", "dir.txt");
		}

		/**
		 * Tests the inverted index output for individual web pages with a crawl
		 * limit of only 1 web page.
		 *
		 * @param file the name or path to the file
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(1)
		@ParameterizedTest
		@MethodSource("getFiles")
		public void testCrawlFiles(String file) throws MalformedURLException {
			testIndex(baseUrl, baseResource + file, baseSubdir, 1);
		}

		/**
		 * Tests the inverted index output with a crawl limit greater than 1.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(2)
		public void testCrawlIndex() throws MalformedURLException {
			testIndex(baseUrl, baseResource, baseSubdir, baseLimit);
		}

		/**
		 * Tests the inverted index count output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(3)
		public void testCrawlCounts() throws MalformedURLException {
			testCounts(baseUrl, baseResource, baseSubdir, baseLimit);
		}

		/**
		 * Tests the search results output.
		 *
		 * @param exact whether to conduct exact or partial search
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(4)
		@ParameterizedTest
		@ValueSource(booleans = {
				true, false
		})
		public void testCrawlResults(boolean exact) throws MalformedURLException {
			testSearch(baseUrl, baseResource, baseSubdir, baseLimit, exact, baseQuery);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_BirdsTests extends A_SimpleTests {
		@Override
		@BeforeEach
		public void setup() {
			baseUrl = REMOTE;
			baseResource = "input/birds/";
			baseSubdir = "birds";
			baseQuery = "letters.txt";
			baseLimit = 50;
		}

		/**
		 * The individual web pages to test.
		 *
		 * @return a stream of web pages to test
		 */
		public static Stream<String> getFiles() {
			return Stream.of("index.html", "falcon.html", "yellowthroat.html");
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_RfcTests extends A_SimpleTests {
		@Override
		@BeforeEach
		public void setup() {
			baseUrl = REMOTE;
			baseResource = "input/rfcs/";
			baseSubdir = "rfcs";
			baseQuery = "letters.txt";
			baseLimit = 7;
		}

		/**
		 * The individual web pages to test.
		 *
		 * @return a stream of web pages to test
		 */
		public static Stream<String> getFiles() {
			return Stream.of("index.html", "rfc3629.html", "rfc475.html", "rfc5646.html", "rfc6797.html", "rfc6805.html",
					"rfc6838.html", "rfc7231.html");
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class D_GutenTests extends A_SimpleTests {
		@Override
		@BeforeEach
		public void setup() {
			baseUrl = REMOTE;
			baseResource = "input/guten/";
			baseSubdir = "guten";
			baseQuery = "complex.txt";
			baseLimit = 7;
		}

		/**
		 * The individual web pages to test.
		 *
		 * @return a stream of web pages to test
		 */
		public static Stream<String> getFiles() {
			return Stream.of("index.html", "1228-h/1228-h.htm", "1322-h/1322-h.htm", "1400-h/1400-h.htm", "1661-h/1661-h.htm",
					"22577-h/22577-h.htm", "2701-h/2701-h.htm", "37134-h/37134-h.htm", "50468-h/50468-h.htm");
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class E_JavaTests extends A_SimpleTests {
		@Override
		@BeforeEach
		public void setup() {
			baseUrl = REMOTE;
			baseResource = "docs/api/allclasses-index.html";
			baseSubdir = "java";
			baseQuery = "letters.txt";
			baseLimit = 50;
		}

		/**
		 * The individual web pages to test.
		 *
		 * @return a stream of web pages to test
		 */
		public static Stream<String> getFiles() {
			return Stream.of("java.base/java/util/AbstractCollection.html",
					"java.compiler/javax/lang/model/SourceVersion.html", "java.desktop/java/awt/desktop/AboutHandler.html",
					"java.prefs/java/util/prefs/AbstractPreferences.html", "overview-tree.html", "allclasses-index.html");
		}

		@Override
		@Order(1)
		@ParameterizedTest
		@MethodSource("getFiles")
		public void testCrawlFiles(String file) throws MalformedURLException {
			baseResource = "docs/api/"; // reset for file cases
			super.testCrawlFiles(file);
		}
	}

	/**
	 * These are the same tests as above, except tagged so they are picked up
	 * by GitHub Actions when testing remotely. There is no need to run tests
	 * tests locally!
	 */
	@Nested
	@EnabledIfEnvironmentVariable(named = "USER_PATH", matches = "project-main")
	@Tag("test4")
	@TestMethodOrder(OrderAnnotation.class)
	public class F_RemoteTests {
		/**
		 * Runs one test from the simple test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(1)
		@Tag("test4")
		public void testSimple() throws MalformedURLException {
			var testClass = new A_SimpleTests();
			testClass.setup();
			testClass.testCrawlIndex();
		}

		/**
		 * Runs one test from the birds test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(2)
		@Tag("test4")
		public void testBirds() throws MalformedURLException {
			var testClass = new B_BirdsTests();
			testClass.setup();
			testClass.testCrawlCounts();
		}

		/**
		 * Runs one test from the RFCs test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(3)
		@Tag("test4")
		public void testRfcs() throws MalformedURLException {
			var testClass = new C_RfcTests();
			testClass.setup();
			testClass.testCrawlResults(true);
		}

		/**
		 * Runs one test from the guten test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(4)
		@Tag("test4")
		public void testGuten() throws MalformedURLException {
			var testClass = new D_GutenTests();
			testClass.setup();
			testClass.testCrawlResults(false);
		}

		/**
		 * Runs one test from the Java test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(5)
		@Tag("test4")
		public void testJavaCounts() throws MalformedURLException {
			var testClass = new E_JavaTests();
			testClass.setup();
			testClass.testCrawlCounts();
		}

		/**
		 * Runs one test from the Java test cases.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Test
		@Order(6)
		@Tag("test4")
		public void testJavaPartial() throws MalformedURLException {
			var testClass = new E_JavaTests();
			testClass.setup();
			testClass.testCrawlResults(false);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Tag("test4")
	@TestMethodOrder(OrderAnnotation.class)
	public class G_StargateTests {
		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(1)
		@Test
		public void testRecurseIndex() throws MalformedURLException {
			testIndex(STARGATE, "recurse/link01.html", "stargate", 100);
		}

		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(2)
		@Test
		public void testRecurseCounts() throws MalformedURLException {
			testCounts(STARGATE, "recurse/link01.html", "stargate", 100);
		}

		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(3)
		@Test
		public void testRedirectIndex() throws MalformedURLException {
			testIndex(STARGATE, "redirect/index.html", "stargate", 10);
		}

		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(4)
		@Test
		public void testRedirectCounts() throws MalformedURLException {
			testCounts(STARGATE, "redirect/index.html", "stargate", 10);
		}

		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(5)
		@Test
		public void testLocalIndex() throws MalformedURLException {
			testIndex(STARGATE, "local.html", "stargate", 200);
		}

		/**
		 * Tests project output.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(6)
		@Test
		public void testLocalCounts() throws MalformedURLException {
			testCounts(STARGATE, "local.html", "stargate", 200);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Tag("test4")
	@TestMethodOrder(OrderAnnotation.class)
	public class H_ExceptionTests {
		/** Default arguments used by the exception tests. */
		public static final Map<String, String> DEFAULT_INPUT = Map.of(HTML_FLAG, REMOTE + "input/birds/falcon.html",
				MAX_FLAG, "1", THREADS_FLAG, Integer.toString(BENCH_THREADS), QUERY_FLAG,
				QUERY_PATH.resolve("letters.txt").toString(), EXACT_FLAG, "");

		/** Default arguments used by the exception tests. */
		public static final Map<String, String> DEFAULT_OUTPUT = Map.of(INDEX_FLAG,
				ACTUAL_PATH.resolve("crawl-index.json").toString(), RESULTS_FLAG,
				ACTUAL_PATH.resolve("crawl-results.json").toString(), COUNTS_FLAG,
				ACTUAL_PATH.resolve("crawl-counts.json").toString());

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testMissingLimit() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);
			map.remove(MAX_FLAG);
			map.put(COUNTS_FLAG, DEFAULT_OUTPUT.get(COUNTS_FLAG));

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG)));
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG))));
		}

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testInvalidLimit() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);
			map.replace(MAX_FLAG, "-12");
			map.put(COUNTS_FLAG, DEFAULT_OUTPUT.get(COUNTS_FLAG));

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG)));
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG))));
		}

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testNoThreads() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);
			map.remove(THREADS_FLAG);
			map.put(COUNTS_FLAG, DEFAULT_OUTPUT.get(COUNTS_FLAG));

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG)));
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG))));
		}

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testMissingSeedValue() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);
			map.replace(HTML_FLAG, "");
			map.put(COUNTS_FLAG, DEFAULT_OUTPUT.get(COUNTS_FLAG));

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testNoOutput() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown.
		 *
		 * @throws Exception if something goes wrong
		 */
		@Test
		public void testAllOutput() throws Exception {
			Map<String, String> map = new HashMap<>(DEFAULT_INPUT);
			map.putAll(DEFAULT_OUTPUT);

			String[] args = map.entrySet()
					.stream()
					.flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
					.filter(Predicate.not(String::isBlank))
					.toArray(String[]::new);

			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(INDEX_FLAG)));
			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG)));
			Files.deleteIfExists(Path.of(DEFAULT_OUTPUT.get(RESULTS_FLAG)));

			testNoExceptions(args, SHORT_TIMEOUT);

			Assertions.assertAll(() -> Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(INDEX_FLAG)))),
					() -> Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(COUNTS_FLAG)))),
					() -> Assertions.assertTrue(Files.exists(Path.of(DEFAULT_OUTPUT.get(RESULTS_FLAG)))));
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Tag("test4")
	@TestMethodOrder(OrderAnnotation.class)
	public class I_RuntimeTests {
		/**
		 * Tests that the inverted index output remains consistent when repeated.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(1)
		@RepeatedTest(3)
		public void testIndexConsistency() throws MalformedURLException {
			// free up memory before re-running
			Runtime.getRuntime().gc();

			// run test
			var testClass = new E_JavaTests();
			testClass.setup();
			testClass.testCrawlCounts();
		}

		/**
		 * Tests that building the inverted index with {@link #BENCH_THREADS} is
		 * faster than building with only 1 thread.
		 *
		 * @throws MalformedURLException if unable to create seed URL
		 */
		@Order(2)
		@Test
		public void testIndexMultithreaded() throws MalformedURLException {
			URL base = new URL(REMOTE);
			URL url = new URL(base, "docs/api/allclasses-index.html");

			int limit = 30; // smaller to speed up benchmark

			String[] args1 = {
					HTML_FLAG, url.toString(), MAX_FLAG, Integer.toString(limit), THREADS_FLAG, String.valueOf(1)
			};

			String[] args2 = {
					HTML_FLAG, url.toString(), MAX_FLAG, Integer.toString(limit), THREADS_FLAG, String.valueOf(BENCH_THREADS)
			};

			System.out.println();
			System.out.printf("### Testing Build 1 vs %d Workers...%n", BENCH_THREADS);

			// make sure code runs without exceptions before testing
			testNoExceptions(args2, SHORT_TIMEOUT);

			// then test the timing
			assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
				double result = Project3bTest.compare("bench-crawl-multi.txt", "1 Worker", args1,
						String.valueOf(BENCH_THREADS) + " Workers", args2);

				assertTrue(result >= 1.5,
						() -> String.format("%d workers has a %.2fx speedup (less than the 1.5x required) compareed to %s.",
								BENCH_THREADS, result, "1 worker"));
			});
		}
	}

	/** Primary location of the remote tests. */
	public static final String REMOTE = "https://usf-cs272-fall2022.github.io/project-web/";

	/** Location of remote tests hosted by stargate. */
	public static final String STARGATE = "https://www.cs.usfca.edu/~cs272/";

	/** Where to locate expected files for web crawling. */
	public static final Path EXPECTED_CRAWL = EXPECTED_PATH.resolve("crawl");

	/** The default number of threads to use when benchmarking. */
	public static final int BENCH_THREADS = 5;

	/**
	 * Runs an individual test of the web crawler inverted index output.
	 *
	 * @param absolute the base URL in absolute form
	 * @param relative the URL to fetch from the base in relative form
	 * @param subdir the subdir to use for expected output
	 * @param limit the crawl limit to use
	 * @throws MalformedURLException if unable to create seed URL
	 */
	public static void testIndex(String absolute, String relative, String subdir, int limit)
			throws MalformedURLException {
		String name = getTestName(relative);
		name = limit > 1 ? name + "-" + limit : name;

		String actualName = String.format("index-%s-%s.json", subdir, name);
		Path actualPath = ACTUAL_PATH.resolve(actualName);

		String expectedName = String.format("index-%s.json", name);
		Path expectedPath = EXPECTED_CRAWL.resolve(subdir).resolve(expectedName);

		URL base = new URL(absolute);
		URL url = new URL(base, relative);

		String[] args = {
				HTML_FLAG, url.toString(), MAX_FLAG, Integer.toString(limit), THREADS_FLAG, Integer.toString(THREADS_DEFAULT),
				INDEX_FLAG, actualPath.normalize().toString()
		};

		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT,
				() -> { ProjectTests.checkOutput(args, actualPath, expectedPath); });
	}

	/**
	 * Runs an individual test of the web crawler inverted index output.
	 *
	 * @param absolute the base URL in absolute form
	 * @param relative the URL to fetch from the base in relative form
	 * @param subdir the directory for expected files
	 * @param limit the crawl limit to use
	 * @throws MalformedURLException if unable to create seed URL
	 */
	public static void testCounts(String absolute, String relative, String subdir, int limit)
			throws MalformedURLException {
		String name = getTestName(relative);
		name = limit > 1 ? name + "-" + limit : name;

		String file = String.format("counts-%s.json", name);
		Path actual = ACTUAL_PATH.resolve(file);
		Path expected = EXPECTED_CRAWL.resolve(subdir).resolve(file);

		URL base = new URL(absolute);
		URL url = new URL(base, relative);

		String[] args = {
				HTML_FLAG, url.toString(), MAX_FLAG, Integer.toString(limit), THREADS_FLAG, Integer.toString(THREADS_DEFAULT),
				COUNTS_FLAG, actual.normalize().toString()
		};

		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, () -> { ProjectTests.checkOutput(args, actual, expected); });
	}

	/**
	 * Runs an individual test of the web crawler search output.
	 *
	 * @param absolute the base URL in absolute form
	 * @param relative the URL to fetch from the base in relative form
	 * @param subdir the directory for expected files
	 * @param limit the crawl limit to use
	 * @param exact whether it is an exact search
	 * @param query the query file to use
	 * @throws MalformedURLException if unable to create seed URL
	 */
	public static void testSearch(String absolute, String relative, String subdir, int limit, boolean exact, String query)
			throws MalformedURLException {
		String type = exact ? "exact" : "partial";
		String name = getTestName(relative) + "-" + query.split("[.]")[0];

		String file = String.format("results-%s-%s.json", type, name);
		Path actual = ACTUAL_PATH.resolve(file);
		Path expected = EXPECTED_CRAWL.resolve(subdir).resolve(file);

		URL base = new URL(absolute);
		URL url = new URL(base, relative);

		String[] args = {
				HTML_FLAG, url.toString(), MAX_FLAG, Integer.toString(limit), THREADS_FLAG, Integer.toString(THREADS_DEFAULT),
				QUERY_FLAG, QUERY_PATH.resolve(query).toString(), RESULTS_FLAG, actual.normalize().toString()
		};

		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, () -> { ProjectTests.checkOutput(args, actual, expected); });
	}

	/**
	 * Gets the name to use for test output files based on the link.
	 *
	 * @param link the link to test
	 * @return the name to use for test output
	 */
	public static String getTestName(String link) {
		String[] paths = link.split("/");
		String last = paths[paths.length - 1];

		String[] names = last.split("[.#-]");
		String name = names[0];

		return name;
	}
}
