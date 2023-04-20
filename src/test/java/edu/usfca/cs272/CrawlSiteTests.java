package edu.usfca.cs272;

import java.net.MalformedURLException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
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
		@Order(1)
		public void testSimpleCounts() {
			int crawl = 15;
		}

		@Order(2)
		public void testSimpleIndex() {
			int crawl = 15;
		}

		@Order(3)
		public void testSimpleSearch() {
			int crawl = 15;
		}

		@Order(4)
		public void testBirdsCounts() {
			int crawl = 50;
		}

		@Order(5)
		public void testBirdsIndex() {
			int crawl = 50;
		}

		@Order(6)
		public void testBirdsSearch() {
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
	 * Tests the output of this project on more complex web sites.
	 */
	@Nested
	@Order(2)
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@TestMethodOrder(OrderAnnotation.class)
	public class ComplexTests {
		@Order(1)
		public void testRFCs() {
			int crawl = 7;
		}

		@Order(2)
		public void testGuten() {
			int crawl = 7;
		}

		@Order(3)
		public void testJava() {
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
		public void testRecurse() {
			int crawl = 100;
		}

		@Order(2)
		public void testRedirect() {
			int crawl = 10;
		}

		@Order(3)
		public void testLocal() {
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

	/** Base URL for the GitHub test website. */
	public static final String GITHUB = CrawlPageTests.GITHUB;

	/** Base directory for crawl output. */
	public static final Path CRAWL = CrawlPageTests.CRAWL;
}
