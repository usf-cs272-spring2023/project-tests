package edu.usfca.cs272;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;

/**
 * A test suite for project v2.1.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class SearchPartialTests extends SearchExactTests {
	/**
	 * Sets up the tests before running.
	 */
	@Override
	@BeforeEach
	public void setup() {
		super.partial = true;
	}
}
