package edu.usfca.cs272;

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
public class CrawlPageTests {
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
	 * Tests the output of this project on smaller web pages.
	 */
	@Nested
	@Order(1)
	@TestMethodOrder(OrderAnnotation.class)
	public class InitialTests {

	}

	/**
	 * Tests the output of this project on complex web pages.
	 */
	@Nested
	@Order(2)
	@TestMethodOrder(OrderAnnotation.class)
	public class ComplexTests {

	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@Order(3)
	@Tag("test-v4.0")
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("test-v5.0")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {

	}
}
