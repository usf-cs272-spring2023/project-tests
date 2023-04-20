package edu.usfca.cs272;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
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
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@TestMethodOrder(OrderAnnotation.class)
	public class InitialTests {
		public void testSimpleCounts() {

		}

		public void testSimpleIndex() {

		}

		public void testSimpleSearch() {

		}

		public void testBirdsCounts() {

		}

		public void testBirdsIndex() {

		}

		public void testBirdsSearch() {

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
		public void testRFCs() {

		}

		public void testGuten() {

		}

		public void testInput() {

		}

		public void testJava() {

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
		public void testRecurse() {

		}

		public void testRedirect() {

		}

		public void testLocal() {

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
		public void testMissingLimit() throws Exception {

		}

		public void testInvalidLimit() throws Exception {

		}

		public void testMissingThreads() throws Exception {

		}

		public void testInvalidThreads() throws Exception {

		}

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


		/**
		 * Free up memory before running.
		 */
		@BeforeAll
		public static void freeMemory() {
			ProjectTests.freeMemory();
		}
	}

	/** Base URL for the GitHub test website. */
	public static final String GITHUB = CrawlPageTests.GITHUB;

	/** Base directory for crawl output. */
	public static final Path CRAWL = CrawlPageTests.CRAWL;
}
