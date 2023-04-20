package edu.usfca.cs272;

import java.nio.file.Path;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;

/**
 * A test suite for project v4.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CrawlSiteTests {


	/** Base URL for the GitHub test website. */
	public static final String GITHUB = CrawlPageTests.GITHUB;

	/** Base directory for crawl output. */
	public static final Path CRAWL = CrawlPageTests.CRAWL;
}
