package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlag.COUNTS;
import static edu.usfca.cs272.ProjectFlag.INDEX;
import static edu.usfca.cs272.ProjectFlag.TEXT;
import static edu.usfca.cs272.ProjectFlag.THREADS;
import static edu.usfca.cs272.ProjectPath.ACTUAL;
import static edu.usfca.cs272.ProjectPath.EXPECTED;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.checkOutput;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
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

/**
 * A test suite for project v3.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class ThreadBuildTests {
	/**
	 * Tests that threads are being used for this project. These tests are slow and
	 * should only be run when needed. The tests are also imperfect and may not
	 * reliably pass unless on the GitHub Actions environment.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class ApproachTests {
		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(1)
		public void testIndex() {
			Runnable test = () -> {
				new ComplexTests().testTextIndex(Threads.TWO);
				System.out.println("Random: " + Math.random());
			};

			ProjectTests.testMultithreaded(test);
		}

		/**
		 * See the JUnit output for test details.
		 */
		@Test
		@Order(2)
		public void testCount() {
			Runnable test = () -> {
				new ComplexTests().testTextCounts(Threads.TWO);
				System.out.println("Random: " + Math.random());
			};

			ProjectTests.testMultithreaded(test);
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class InitialTests {
		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(1)
		@EnumSource()
		public void testHello(Threads threads) {
			testIndex("simple", ProjectPath.HELLO, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(2)
		@EnumSource()
		public void testSimpleIndex(Threads threads) {
			testIndex("simple", ProjectPath.SIMPLE, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(3)
		@EnumSource()
		public void testSimpleCounts(Threads threads) {
			testCount(ProjectPath.SIMPLE, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(4)
		@EnumSource()
		public void testStems(Threads threads) {
			testIndex("stems", ProjectPath.STEMS, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(5)
		@EnumSource()
		public void testRFCs(Threads threads) {
			testIndex("rfcs", ProjectPath.RFCS, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(6)
		@EnumSource()
		public void testGutenGreat(Threads threads) {
			testIndex("guten", ProjectPath.GUTEN_GREAT, threads);
		}

		/**
		 * Free up memory after running --- useful for following tests.
		 */
		@AfterAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/**
	 * Tests the output of this project.
	 */
	@Nested
	@Order(3)
	@TestMethodOrder(OrderAnnotation.class)
	public class ComplexTests {
		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(1)
		@EnumSource()
		public void testGuten(Threads threads) {
			testIndex("guten", ProjectPath.GUTEN, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(2)
		@EnumSource()
		public void testTextIndex(Threads threads) {
			testIndex(".", ProjectPath.TEXT, threads);
		}

		/**
		 * See the JUnit output for test details.
		 *
		 * @param threads the threads
		 */
		@ParameterizedTest
		@Order(3)
		@EnumSource()
		public void testTextCounts(Threads threads) {
			testCount(ProjectPath.TEXT, threads);
		}

		/**
		 * Free up memory after running --- useful for following tests.
		 */
		@AfterAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/** The number of threads to use in testing. */
	public static enum Threads {
		/** One thread */
		ONE(1),

		/** Two threads */
		TWO(2),

		/** Five threads */
		FIVE(5);

		/** The number of threads as an int. */
		public final int num;

		/** The number of threads as text. */
		public final String text;

		/**
		 * Initializes this thread count.
		 * @param num the number of threads
		 */
		private Threads(int num) {
			this.num = num;
			this.text = Integer.toString(num);
		}
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param subdir the subdir
	 * @param input the input
	 * @param id the id
	 * @param threads the threads
	 */
	public static void testIndex(String subdir, Path input, String id, Threads threads) {
		String single = String.format("index-%s.json", id);
		String threaded = String.format("index-%s-%s.json", id, threads.text);
		Path actual = ACTUAL.resolve(threaded).normalize();
		Path expected = EXPECTED.resolve("index").resolve(subdir).resolve(single).normalize();
		String[] args = { TEXT.flag, input.toString(), INDEX.flag, actual.toString(), THREADS.flag, threads.text };
		Executable test = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, test);
	}

	/**
	 * Calls {@link ThreadBuildTests#testIndex(String, Path, String, Threads)} using the enum.
	 *
	 * @param subdir the subdir
	 * @param path the path
	 * @param threads the threads
	 * @see ThreadBuildTests#testIndex(String, Path, String, Threads)
	 */
	public static void testIndex(String subdir, ProjectPath path, Threads threads) {
		testIndex(subdir, path.path, path.id, threads);
	}

	/**
	 * Generates the arguments to use for the output test cases. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param input the input
	 * @param id the id
	 * @param threads the threads
	 */
	public static void testCount(Path input, String id, Threads threads) {
		String single = String.format("counts-%s.json", id);
		String threaded = String.format("counts-%s-%s.json", id, threads.text);
		Path actual = ACTUAL.resolve(threaded).normalize();
		Path expected = EXPECTED.resolve("counts").resolve(single).normalize();
		String[] args = { TEXT.flag, input.toString(), COUNTS.flag, actual.toString(), THREADS.flag, threads.text };
		Executable debug = () -> checkOutput(args, actual, expected);
		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, debug);
	}

	/**
	 * Calls {@link ThreadBuildTests#testCount(Path, String, Threads)} using the enum.
	 *
	 * @param path the path
	 * @param threads the threads
	 * @see ThreadBuildTests#testCount(Path, String, Threads)
	 */
	public static void testCount(ProjectPath path, Threads threads) {
		testCount(path.path, path.id, threads);
	}
}
