package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlag.COUNTS;
import static edu.usfca.cs272.ProjectFlag.INDEX;
import static edu.usfca.cs272.ProjectFlag.TEXT;
import static edu.usfca.cs272.ProjectPath.ACTUAL;
import static edu.usfca.cs272.ProjectPath.EXPECTED;
import static edu.usfca.cs272.ProjectPath.HELLO;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.checkOutput;
import static edu.usfca.cs272.ProjectTests.testNoExceptions;
import static org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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
 * A test suite for project v1.1.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class BuildIndexTests {
	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class FileTests {
		/**
		 * See the JUnit output for test details.
		 *
		 * @param filename the filename
		 */
		@ParameterizedTest
		@Order(1)
		@ValueSource(strings = { "hello.txt", "animals.text", "capitals.txt", "capital_extension.TXT", "digits.tXt",
				"position.teXt", "empty.txt", "symbols.txt", "words.tExT" })
		public void testSimple(String filename) {
			Path input = ProjectPath.SIMPLE.resolve(filename);
			testOutput("simple", input, ProjectPath.id(input));
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param filename the filename
		 */
		@ParameterizedTest
		@Order(2)
		@ValueSource(strings = { "no_extension", "sentences.md", "double_extension.txt.html", "wrong_extension.html" })
		public void testOther(String filename) {
			Path input = ProjectPath.SIMPLE.resolve(filename);
			testOutput("simple", input, ProjectPath.id(input));
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param path the path
		 */
		@ParameterizedTest
		@Order(3)
		@EnumSource(names = { "STEMS_IN", "STEMS_OUT" })
		public void testStems(ProjectPath path) {
			testOutput("stems", path);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param path the path
		 */
		@ParameterizedTest
		@Order(4)
		@EnumSource(mode = MATCH_ALL, names = "^RFCS_.+")
		public void testRfcs(ProjectPath path) {
			testOutput("rfcs", path);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param path the path
		 */
		@ParameterizedTest
		@Order(5)
		@EnumSource(mode = MATCH_ALL, names = "^GUTEN_.+")
		public void testGuten(ProjectPath path) {
			testOutput("guten", path);
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
		@Test
		@Order(1)
		public void testSimple() {
			testOutput("simple", ProjectPath.SIMPLE);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(2)
		public void testStems() {
			testOutput("stems", ProjectPath.STEMS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(3)
		public void testRfcs() {
			testOutput("rfcs", ProjectPath.RFCS);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(4)
		public void testGuten() {
			testOutput("guten", ProjectPath.GUTEN);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(5)
		public void testText() {
			testOutput(".", ProjectPath.TEXT);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(6)
		public void testCountsIndex() {
			ProjectPath input = ProjectPath.TEXT;
			String indexName = String.format("index-%s.json", input.id);
			String countsName = String.format("counts-%s.json", input.id);

			Path indexActual = ACTUAL.resolve(indexName);
			Path countsActual = ACTUAL.resolve(countsName);

			String[] args = {
					TEXT.flag, input.text,
					INDEX.flag, indexActual.toString(),
					COUNTS.flag, countsActual.toString()
			};

			Map<Path, Path> files = Map.of(
					ACTUAL.resolve(countsName), EXPECTED.resolve("counts").resolve(countsName),
					ACTUAL.resolve(indexName), EXPECTED.resolve("index").resolve(indexName)
			);

			Executable test = () -> ProjectTests.checkOutput(args, files);
			Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, test);
		}
	}

	/**
	 * Tests the index exception handling of this project.
	 */
	@Nested
	@Order(3)
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {
		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(1)
		public void testDefaultIndex() throws IOException {
			String[] args = { TEXT.flag, HELLO.text, INDEX.flag };

			Files.deleteIfExists(COUNTS.path);
			Files.deleteIfExists(INDEX.path);
			testNoExceptions(args, SHORT_TIMEOUT);

			Assertions.assertAll(
					() -> Assertions.assertTrue(Files.exists(INDEX.path)),
					() -> Assertions.assertFalse(Files.exists(COUNTS.path))
			);
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(2)
		public void testOnlyIndex() throws IOException {
			String[] args = { INDEX.flag };
			Files.deleteIfExists(INDEX.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(INDEX.path));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Order(3)
		@Test
		public void testSwitched() throws IOException {
			String[] args = { INDEX.flag, TEXT.flag, HELLO.text };
			Files.deleteIfExists(INDEX.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertTrue(Files.exists(INDEX.path));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(1)
		public void testNoCounts() throws IOException {
			String[] args = { TEXT.flag, HELLO.text, INDEX.flag };
			Files.deleteIfExists(COUNTS.path);
			testNoExceptions(args, SHORT_TIMEOUT);
			Assertions.assertFalse(Files.exists(COUNTS.path));
		}

		/**
		 * Tests no exceptions are thrown with the provided arguments.
		 *
		 * @throws IOException if IO error occurs
		 */
		@Test
		@Order(6)
		public void testNoOutput() throws IOException {
			String[] args = { TEXT.flag, HELLO.text };

			Files.deleteIfExists(COUNTS.path);
			Files.deleteIfExists(INDEX.path);
			testNoExceptions(args, SHORT_TIMEOUT);

			Assertions.assertAll(
					() -> Assertions.assertFalse(Files.exists(COUNTS.path)),
					() -> Assertions.assertFalse(Files.exists(INDEX.path))
			);
			;
		}
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param subdir the subdir
	 * @param input the input
	 * @param id the id
	 */
	public static void testOutput(String subdir, Path input, String id) {
		String filename = String.format("index-%s.json", id);
		Path actual = ACTUAL.resolve(filename).normalize();
		Path expected = EXPECTED.resolve("index").resolve(subdir).resolve(filename).normalize();
		String[] args = { TEXT.flag, input.toString(), INDEX.flag, actual.toString() };
		Executable test = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, test);
	}

	/**
	 * Calls {@link BuildIndexTests#testOutput(String, Path, String)} using the enum.
	 *
	 * @param subdir the subdir
	 * @param path the path
	 * @see BuildCountTests#testOutput(Path, String)
	 */
	public static void testOutput(String subdir, ProjectPath path) {
		testOutput(subdir, path.path, path.id);
	}
}
