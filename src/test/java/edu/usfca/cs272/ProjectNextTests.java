package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectFlag.INDEX;
import static edu.usfca.cs272.ProjectFlag.QUERY;
import static edu.usfca.cs272.ProjectFlag.RESULTS;
import static edu.usfca.cs272.ProjectFlag.TEXT;
import static edu.usfca.cs272.ProjectFlag.THREADS;
import static edu.usfca.cs272.ProjectPath.GUTEN;
import static edu.usfca.cs272.ProjectPath.HELLO;
import static edu.usfca.cs272.ProjectPath.QUERY_SIMPLE;
import static edu.usfca.cs272.ProjectTests.LONG_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.SHORT_TIMEOUT;
import static edu.usfca.cs272.ProjectTests.testMultithreaded;
import static edu.usfca.cs272.ProjectTests.testNoExceptions;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * Tests that next project code is not in the current project. This class should
 * not be run directly; it is run by GitHub Actions only.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class ProjectNextTests {
	/** Message output when a test fails. */
	public static final String debug = "You must place functionality for the next project in a separate branch. It should not be in the current version of your project in the main branch!";

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next-v1.0")
	public void testIndexOutput() throws IOException {
		String[] args = { TEXT.flag, HELLO.text, QUERY.flag, QUERY_SIMPLE.text, INDEX.flag };
		Files.deleteIfExists(INDEX.path);
		testNoExceptions(args, SHORT_TIMEOUT);
		Assertions.assertFalse(Files.exists(INDEX.path), debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	public void testSearchOutput() throws IOException {
		String[] args = { TEXT.flag, HELLO.text, QUERY.flag, QUERY_SIMPLE.text, RESULTS.flag };
		Files.deleteIfExists(RESULTS.path);
		testNoExceptions(args, SHORT_TIMEOUT);
		Assertions.assertFalse(Files.exists(RESULTS.path), debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next-v1.0")
	@Tag("next-v1.1")
	@Tag("next-v1.x")
	@Tag("next-v2.0")
	@Tag("next-v2.1")
	@Tag("next-v2.x")
	public void testThreadBuild() throws IOException {
		String[] args = { TEXT.flag, GUTEN.text, THREADS.flag, "2" };

		try {
			// should fail and throw an error
			testMultithreaded(() -> testNoExceptions(args, LONG_TIMEOUT));
		}
		catch (AssertionFailedError e) {
			Assertions.assertNotNull(e);
			return;
		}

		// should return before here
		fail(debug);
	}
}
