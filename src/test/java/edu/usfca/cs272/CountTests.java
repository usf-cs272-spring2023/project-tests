package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlags.COUNTS;
import static edu.usfca.cs272.ProjectFlags.TEXT;
import static edu.usfca.cs272.ProjectPaths.HELLO;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.checkOutput;
import static edu.usfca.cs272.ProjectTests.errorMessage;
import static edu.usfca.cs272.ProjectTests.testNoExceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;

/**
 * A test suite for project v1.0. During development, run individual tests instead
 * of this entire test suite!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CountTests {
	/**
	 * Tests the counts output of this project.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class FileTests {
		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(1)
		@Test
		public void testHelloFile() {
			testCountOutput(ProjectPaths.HELLO);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(2)
		@Test
		@Tag("test_v1.0")
		@Tag("test_v1.1")
		@Tag("test_v1.x")
		public void testSentencesFile() {
			Path input = ProjectPaths.SIMPLE.resolve("sentences.md");
			String id = ProjectPaths.id(input);
			testCountOutput(input, id);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(3)
		@Test
		public void testStemFile() {
			testCountOutput(ProjectPaths.STEMS_IN);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(4)
		@Test
		public void testRFCFile() {
			testCountOutput(ProjectPaths.RFCS_HTTP);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(4)
		@Test
		public void testGutenFile() {
			testCountOutput(ProjectPaths.GUTEN_GREAT);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(5)
		@Test
		@Tag("test_v1.0")
		@Tag("test_v1.1")
		@Tag("test_v1.x")
		public void testEmptyFile() {
			testCountOutput(ProjectPaths.EMPTY);
		}
	}

	/**
	 * Tests the counts output of this project.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class DirectoryTests {
		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(1)
		@Test
		public void testSimple() {
			testCountOutput(ProjectPaths.SIMPLE);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(2)
		@Test
		public void testRFCs() {
			testCountOutput(ProjectPaths.RFCS);
		}

		/**
		 * @see CountTests#testCountOutput(Path, String)
		 */
		@Order(3)
		@Test
		public void testGuten() {
			testCountOutput(ProjectPaths.GUTEN);
		}

		/**
		 *  @see CountTests#testCountOutput(Path, String)
		 */
		@Order(4)
		@Test
		@Tag("test_v1.0")
		@Tag("test_v1.1")
		@Tag("test_v1.x")
		public void testText() {
			testCountOutput(ProjectPaths.TEXT);
		}
	}

	/**
	 * Tests the counts exception handling of this project.
	 */
	@Nested
	@Order(3)
	@TestMethodOrder(OrderAnnotation.class)
	@Tag("test_v1.0")
	@Tag("test_v1.1")
	@Tag("test_v1.x")
	public class ExceptionTests {
		/**
		 * Tests no exceptions are thrown with no arguments.
		 */
		@Order(1)
		@Test
		public void testNoArguments() {
			String[] args = {};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with invalid arguments.
		 */
		@Order(2)
		@Test
		public void testBadArguments() {
			String[] args = { "hello", "world" };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with a missing path value.
		 */
		@Order(3)
		@Test
		public void testMissingPath() {
			String[] args = { "-text" };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with an invalid path value.
		 */
		@Order(4)
		@Test
		public void testInvalidPath() {
			// generates a random path name
			String path = Long.toHexString(Double.doubleToLongBits(Math.random()));
			String[] args = { TEXT.flag, path };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with no count output.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Order(5)
		@Test
		public void testNoOutput() throws IOException {
			Path output = COUNTS.path;
			String[] args = { TEXT.flag, HELLO.text };

			// make sure to delete old output file if it exists
			Files.deleteIfExists(output);

			// make sure a new output file was not created
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertFalse(Files.exists(output));
		}

		/**
		 * Tests no exceptions are thrown with a default output value.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Order(6)
		@Test
		public void testDefaultOutput() throws IOException {
			String[] args = { TEXT.flag, HELLO.text, COUNTS.flag };

			// make sure to delete old index.json if it exists
			Files.deleteIfExists(COUNTS.path);

			// make sure a new index.json was created
			testNoExceptions(args, SHORT_TIMEOUT);

			// generate debug output
			Supplier<String> debug = () -> {
				String message = "Make sure to use the default value for this flag.";
				return errorMessage(args, COUNTS.path, COUNTS.path, message);
			};

			Assertions.assertTrue(Files.exists(COUNTS.path), debug);
		}

		/**
		 * Tests no exceptions are thrown with only output (no input path).
		 *
		 * @throws IOException if IO error occurs
		 */
		@Order(7)
		@Test
		public void testEmptyOutput() throws IOException {
			String[] args = { COUNTS.flag };

			// make sure to delete old index.json if it exists
			Files.deleteIfExists(COUNTS.path);

			// make sure a new index.json was created
			testNoExceptions(args, SHORT_TIMEOUT);

			// generate debug output
			Supplier<String> debug = () -> {
				String message = "Make sure to always produce file output if this flag exists.";
				return errorMessage(args, COUNTS.path, COUNTS.path, message);
			};

			Assertions.assertTrue(Files.exists(COUNTS.path), debug);
		}

		/**
		 * Tests no exceptions are thrown with arguments in a different order.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Order(8)
		@Test
		public void testSwitchedOrder() throws IOException {
			String[] args = { COUNTS.flag, TEXT.flag, HELLO.text };

			// make sure to delete old index.json if it exists
			Files.deleteIfExists(COUNTS.path);

			// make sure a new index.json was created
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(COUNTS.path));
		}
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param input the input path to use
	 * @param id the id to use in the output filename
	 */
	public static void testCountOutput(Path input, String id) {
		String filename = String.format("counts-%s.json", id);
		Path actual = ProjectPaths.ACTUAL.resolve(filename);
		Path expected = ProjectPaths.EXPECTED.resolve("counts").resolve(filename);

		String[] args = {
				ProjectFlags.TEXT.flag, input.normalize().toString(),
				ProjectFlags.COUNTS.flag, actual.normalize().toString()
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}

	/**
	 * Calls {@link CountTests#testCountOutput(Path, String)} using the enum.
	 *
	 * @param path the project path to use
	 * @see CountTests#testCountOutput(Path, String)
	 */
	public static void testCountOutput(ProjectPaths path) {
		testCountOutput(path.path, path.id);
	}
}
