package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
 * A test suite for project 2. During development, run individual tests instead
 * of this entire test suite!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestMethodOrder(MethodName.class)
public class Project2Test extends ProjectTests {
	/**
	 * Tests the exact search output of this project.
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
		 */
		@Order(1)
		@Test
		public void testSimpleDirectory() {
			Path input = TEXT_PATH.resolve("simple");
			String query = "simple.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the stems directory.
		 */
		@Order(2)
		@Test
		public void testStemsWords() {
			Path input = TEXT_PATH.resolve("stems");
			String query = "words.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 */
		@Order(3)
		@Test
		public void testStemsRespect() {
			Path input = TEXT_PATH.resolve("stems");
			String query = "respect.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the stems directory.
		 */
		@Order(4)
		@Test
		public void testStemsLetters() {
			Path input = TEXT_PATH.resolve("stems");
			String query = "letters.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the rfcs subdirectory.
		 */
		@Order(5)
		@Test
		public void testRfcDirectory() {
			Path input = TEXT_PATH.resolve("rfcs");
			String query = "letters.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for files in the guten subdirectory.
		 *
		 * @param filename filename of a text file in the guten subdirectory
		 */
		@Order(6)
		@ParameterizedTest
		@ValueSource(strings = {
				"pg37134.txt", // The Elements of Style by William Strunk
				"pg22577.txt", // Practical Grammar and Composition by Thomas Wood
				"pg1661.txt",  // Adventures of Sherlock Holmes by Arthur Conan Doyle
				"pg1322.txt",  // Leaves of Grass by Walt Whitman
				"50468-0.txt", // ALGOL Compiler by L. L. Bumgarner
				"2701-0.txt",  // Moby Dick by Herman Melville
				"1400-0.txt"   // Great Expectations by Charles Dickens
		})
		public void testGutenFiles(String filename) {
			Path input = TEXT_PATH.resolve("guten").resolve(filename);
			String query = "complex.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the guten subdirectory.
		 */
		@Order(7)
		@Test
		public void testGutenDirectory() {
			Path input = TEXT_PATH.resolve("guten");
			String query = "complex.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 */
		@Order(8)
		@Test
		@Tag("test2")
		public void testTextWords() {
			Path input = TEXT_PATH;
			String query = "words.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 */
		@Order(9)
		@Test
		@Tag("test2")
		@Tag("past3a")
		public void testTextRespect() {
			Path input = TEXT_PATH;
			String query = "respect.txt";
			testSearch(subdir, input, query, exact);
		}

		/**
		 * Tests the search result output for the text subdirectory.
		 */
		@Order(10)
		@Test
		@Tag("test2")
		@Tag("past3a")
		public void testTextDirectory() {
			Path input = TEXT_PATH;
			String query = "complex.txt";
			testSearch(subdir, input, query, exact);
		}
	}

	/**
	 * Tests the partial search output of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_PartialSearchTests extends B_ExactSearchTests {
		@Override
		@BeforeEach
		public void setup() {
			subdir = "partial";
			exact = false;
		}

		/*
		 * NOTE: The tests in A_ExactSearchTest are inherited automatically here.
		 * However, they don't show up in the Package Explorer or Outline views in
		 * Eclipse. To run individual tests, try the "Type Hierarchy" view instead.
		 * It has an option to show all inherited members and methods.
		 */
	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	@Tag("test2")
	@Tag("past3a")
	@Tag("past3b")
	@Tag("past4")
	public class D_ExceptionTests {
		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(1)
		@Test
		public void testMissingQueryPath() {
			String[] args = {
					TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(2)
		@Test
		public void testInvalidQueryPath() {
			String query = Long.toHexString(Double.doubleToLongBits(Math.random()));
			String[] args = {
					TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, query
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 */
		@Order(3)
		@Test
		public void testInvalidExactPath() {
			String query = Long.toHexString(Double.doubleToLongBits(Math.random()));
			String[] args = {
					TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, query, EXACT_FLAG
			};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@Order(4)
		@Test
		public void testNoOutput() throws IOException {
			String[] args = {
					TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, SIMPLE
			};

			// make sure to delete old index.json and results.json if it exists
			Files.deleteIfExists(INDEX_DEFAULT);
			Files.deleteIfExists(RESULTS_DEFAULT);

			testNoExceptions(args, SHORT_TIMEOUT);

			// make sure a new index.json and results.json were not created
			Assertions.assertFalse(Files.exists(INDEX_DEFAULT) || Files.exists(RESULTS_DEFAULT));
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@Order(5)
		@Test
		public void testDefaultOutput() throws IOException {
			String[] args = {
					TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, SIMPLE, RESULTS_FLAG
			};

			// make sure to delete old index.json and results.json if it exists
			Files.deleteIfExists(INDEX_DEFAULT);
			Files.deleteIfExists(RESULTS_DEFAULT);

			testNoExceptions(args, SHORT_TIMEOUT);

			// make sure a new results.json was created (but index.json was not)
			Assertions.assertTrue(Files.exists(RESULTS_DEFAULT) && !Files.exists(INDEX_DEFAULT));
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@Order(6)
		@Test
		public void testEmptyIndex() throws IOException {
			String[] args = {
					QUERY_FLAG, SIMPLE, RESULTS_FLAG
			};

			// make sure to delete old index.json and results.json if it exists
			Files.deleteIfExists(INDEX_DEFAULT);
			Files.deleteIfExists(RESULTS_DEFAULT);

			testNoExceptions(args, SHORT_TIMEOUT);

			// make sure a new results.json was created (but index.json was not)
			Assertions.assertTrue(Files.exists(RESULTS_DEFAULT) && !Files.exists(INDEX_DEFAULT));
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@Order(7)
		@Test
		public void testEmptyQuery() throws IOException {
			String[] args = {
					RESULTS_FLAG
			};

			// make sure to delete old index.json and results.json if it exists
			Files.deleteIfExists(INDEX_DEFAULT);
			Files.deleteIfExists(RESULTS_DEFAULT);

			testNoExceptions(args, SHORT_TIMEOUT);

			// make sure a new results.json was created (but index.json was not)
			Assertions.assertTrue(Files.exists(RESULTS_DEFAULT) && !Files.exists(INDEX_DEFAULT));
		}

		/**
		 * Tests that no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@Order(8)
		@Test
		public void testSwitchedOrder() throws IOException {
			String[] args = {
					QUERY_FLAG, SIMPLE, RESULTS_FLAG, TEXT_FLAG, Project1Test.HELLO, EXACT_FLAG
			};

			// make sure to delete old index.json and results.json if it exists
			Files.deleteIfExists(INDEX_DEFAULT);
			Files.deleteIfExists(RESULTS_DEFAULT);

			testNoExceptions(args, SHORT_TIMEOUT);

			// make sure a new (but empty) results.json was created (but index.json was not)
			Assertions.assertTrue(Files.exists(RESULTS_DEFAULT) && !Files.exists(INDEX_DEFAULT));
		}
	}

	/*
	 * Members and methods to help configure these tests.
	 */

	/** Path to the simple.txt file used for testing. */
	public static final String SIMPLE = QUERY_PATH.resolve("simple.txt").toString();

	/**
	 * Generates the arguments to use for this test case. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param subdir the output subdirectory to use
	 * @param input the input path to use
	 * @param query the query file to use for search
	 * @param exact whether to perform exact or partial search
	 */
	public static void testSearch(String subdir, Path input, String query, boolean exact) {
		String type = exact ? "exact" : "partial";
		String prefix = "search-" + type + "-" + query.split("\\.")[0];
		String filename = outputFileName(prefix, input);

		Path actual = ACTUAL_PATH.resolve(filename).normalize();
		Path expected = EXPECTED_PATH.resolve("search").resolve("search-" + subdir).resolve(filename).normalize();

		String[] args = {
				TEXT_FLAG, input.normalize().toString(), QUERY_FLAG, QUERY_PATH.resolve(query).toString(), RESULTS_FLAG,
				actual.normalize().toString(), exact ? EXACT_FLAG : ""
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}
}
