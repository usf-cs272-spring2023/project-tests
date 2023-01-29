package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlag.COUNTS;
import static edu.usfca.cs272.ProjectFlag.TEXT;
import static edu.usfca.cs272.ProjectPath.HELLO;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.checkOutput;
import static edu.usfca.cs272.ProjectTests.testNoExceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
 * A test suite for project v1.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class BuildCountTests {
	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class FileTests {
		/**
		 * See the JUnit output for test details.
		 */
		@Order(1)
		@Test
		public void testHello() {
			testOutput(ProjectPath.HELLO);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(2)
		@Test
		public void testSentences() {
			Path input = ProjectPath.SIMPLE.resolve("sentences.md");
			String id = ProjectPath.id(input);
			testOutput(input, id);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(3)
		@Test
		public void testStemIn() {
			testOutput(ProjectPath.STEMS_IN);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(4)
		@Test
		public void testRfcHttp() {
			testOutput(ProjectPath.RFCS_HTTP);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(4)
		@Test
		public void testGutenGreat() {
			testOutput(ProjectPath.GUTEN_GREAT);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(5)
		@Test
		@Tag("test_v1.0")
		@Tag("test_v1.1")
		@Tag("test_v1.x")
		public void testEmpty() {
			testOutput(ProjectPath.EMPTY);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class DirectoryTests {
		/**
		 * See the JUnit output for test details.
		 */
		@Order(1)
		@Test
		public void testSimple() {
			testOutput(ProjectPath.SIMPLE);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(2)
		@Test
		public void testRfcs() {
			testOutput(ProjectPath.RFCS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(3)
		@Test
		public void testGuten() {
			testOutput(ProjectPath.GUTEN);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Order(4)
		@Test
		public void testText() {
			testOutput(ProjectPath.TEXT);
		}
	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@Order(3)
	@Tag("test-v1.0") @Tag("test-v1.1") @Tag("test-v1.x")
	@Tag("past-v1.1") @Tag("past-v1.x")
	@Tag("past-v2.0") @Tag("past-v2.1") @Tag("past-v2.x")
	@Tag("past-v3.0") @Tag("past-v3.1") @Tag("past-v3.x")
	@Tag("past-v4.0") @Tag("past-v4.1") @Tag("past-v4.x")
	@Tag("past-v5.0")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {
		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 */
		@Test
		@Order(1)
		public void testNoArguments() {
			String[] args = {};
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 */
		@Test
		@Order(2)
		public void testInvalidFlag() {
			String[] args = { "-hello", "world" };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 */
		@Test
		@Order(3)
		public void testOnlyValues() {
			String[] args = { "hello", "world" };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 */
		@Test
		@Order(4)
		public void testOnlyText() {
			String[] args = { "-text" };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 */
		@Test
		@Order(5)
		public void testInvalidPath() {
			// generates a random path name
			String path = Long.toHexString(Double.doubleToLongBits(Math.random()));
			String[] args = { TEXT.flag, path };
			testNoExceptions(args, SHORT_TIMEOUT);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(6)
		public void testNoCounts() throws IOException {
			Path output = COUNTS.path;
			String[] args = { TEXT.flag, HELLO.text };
			Files.deleteIfExists(output);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertFalse(Files.exists(output));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(7)
		public void testDefaultCounts() throws IOException {
			String[] args = { TEXT.flag, HELLO.text, COUNTS.flag };
			Files.deleteIfExists(COUNTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(COUNTS.path));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(8)
		public void testOnlyCounts() throws IOException {
			String[] args = { COUNTS.flag };
			Files.deleteIfExists(COUNTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(COUNTS.path));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(9)
		public void testSwitched() throws IOException {
			String[] args = { COUNTS.flag, TEXT.flag, HELLO.text };
			Files.deleteIfExists(COUNTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(COUNTS.path));
		}
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param input the input
	 * @param id the id
	 */
	public static void testOutput(Path input, String id) {
		String filename = String.format("counts-%s.json", id);
		Path actual = ProjectPath.ACTUAL.resolve(filename);
		Path expected = ProjectPath.EXPECTED.resolve("counts").resolve(filename);

		String[] args = {
				ProjectFlag.TEXT.flag, input.normalize().toString(),
				ProjectFlag.COUNTS.flag, actual.normalize().toString()
		};

		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}

	/**
	 * Calls {@link BuildCountTests#testOutput(Path, String)} using the enum.
	 *
	 * @param path the path
	 * @see BuildCountTests#testOutput(Path, String)
	 */
	public static void testOutput(ProjectPath path) {
		testOutput(path.path, path.id);
	}
}
