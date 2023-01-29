package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlag.INDEX;
import static edu.usfca.cs272.ProjectFlag.TEXT;
import static edu.usfca.cs272.ProjectPath.ACTUAL;
import static edu.usfca.cs272.ProjectPath.EXPECTED;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A test suite for project v1.1. During development, run individual tests
 * instead of this entire test suite!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class IndexTests {
	/**
	 * Tests the index output of this project.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class FileTests {
		/**
		 * Tests the index output for files in the simple subdirectory.
		 *
		 * @param filename filename of a text file in the simple subdirectory
		 */
		@ParameterizedTest
		@Order(1)
		@ValueSource(strings = { "hello.txt", "animals.text", "capitals.txt", "capital_extension.TXT", "digits.tXt",
				"position.teXt", "empty.txt", "symbols.txt", "words.tExT" })
		public void testSimpleFiles(String filename) {
			Path input = ProjectPath.SIMPLE.resolve(filename);
			testIndexOutput("simple", input, ProjectPath.id(input));
		}

		/**
		 * Tests the index output for files in the simple subdirectory.
		 *
		 * @param filename filename of a non-text file in the simple subdirectory
		 */
		@ParameterizedTest
		@Order(2)
		@ValueSource(strings = { "no_extension", "sentences.md", "double_extension.txt.html", "wrong_extension.html" })
		public void testOtherFiles(String filename) {
			Path input = ProjectPath.SIMPLE.resolve(filename);
			testIndexOutput("simple", input, ProjectPath.id(input));
		}

		/**
		 * Tests the index output for files in the stems subdirectory.
		 *
		 * @param path the project path to use
		 */
		@ParameterizedTest
		@Order(3)
		@EnumSource(names = { "STEMS_IN", "STEMS_OUT" })
		public void testStemsFiles(ProjectPath path) {
			testIndexOutput("stems", path);
		}

		/**
		 * Tests the index output for files in the RFCs subdirectory.
		 *
		 * @param path the project path to use
		 */
		@ParameterizedTest
		@Order(4)
		@EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^RFCS_.+")
		public void testRfcsFiles(ProjectPath path) {
			testIndexOutput("rfcs", path);
		}

		/**
		 * Tests the index output for files in the guten subdirectory.
		 *
		 * @param path the project path to use
		 */
		@ParameterizedTest
		@Order(5)
		@EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^GUTEN_.+")
		public void testGutenFiles(ProjectPath path) {
			testIndexOutput("guten", path);
		}
	}

	/**
	 * Tests the index output of this project.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class DirectoryTests {
		/**
		 * Tests the index output for the simple subdirectory.
		 */
		@Test
		@Order(1)
		public void testSimpleDirectory() {
			testIndexOutput("simple", ProjectPath.SIMPLE);
		}

		/**
		 * Tests the index output for the stems subdirectory.
		 */
		@Test
		@Order(2)
		public void testStemsDirectory() {
			testIndexOutput("stems", ProjectPath.STEMS);
		}

		/**
		 * Tests the index output for the rfcs subdirectory.
		 */
		@Test
		@Order(3)
		public void testRfcDirectory() {
			testIndexOutput("rfcs", ProjectPath.RFCS);
		}

		/**
		 * Tests the index output for the guten subdirectory.
		 */
		@Test
		@Order(4)
		public void testGutenDirectory() {
			testIndexOutput("guten", ProjectPath.GUTEN);
		}

		/**
		 * Tests the index output for all of the text files.
		 */
		@Test
		@Order(5)
		public void testText() {
			testIndexOutput(".", ProjectPath.TEXT);
		}
	}

	/**
	 * Tests the index exception handling of this project.
	 */
	@Nested
	@Order(3)
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {

	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param subdir the output subdirectory to use
	 * @param input the input path to use
	 * @param id the id to use in the output filename
	 */
	public static void testIndexOutput(String subdir, Path input, String id) {
		String filename = String.format("index-%s.json", id);
		Path actual = ACTUAL.resolve(filename);
		Path expected = EXPECTED.resolve("index").resolve(subdir).resolve(filename);

		String[] args = { TEXT.flag, input.normalize().toString(), INDEX.flag, actual.normalize().toString() };

		Executable test = () -> ProjectTests.checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, test);
	}

	/**
	 * Calls {@link IndexTests#testIndexOutput(String, Path, String)} using the
	 * enum.
	 *
	 * @param subdir the output subdirectory to use
	 * @param path the project path to use
	 * @see CountTests#testCountOutput(Path, String)
	 */
	public static void testIndexOutput(String subdir, ProjectPath path) {
		testIndexOutput(subdir, path.path, path.id);
	}
}
