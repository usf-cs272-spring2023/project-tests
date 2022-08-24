package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * Tests that next project code is not in the current project. This class should
 * not be run directly; it is run by GitHub Actions only.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class ProjectNextTests extends ProjectTests {
	/**
	 * Message output when a test fails.
	 */
	public static final String debug = "You must place functionality for the next project in a separate branch. It should not be in the current version of your project in the main branch!";

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next1")
	public void testSearchOutput() throws IOException {
		String[] args = {
				TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, Project2Test.SIMPLE, RESULTS_FLAG
		};

		// make sure to delete old index.json and results.json if it exists
		Files.deleteIfExists(INDEX_DEFAULT);
		Files.deleteIfExists(RESULTS_DEFAULT);

		testNoExceptions(args, SHORT_TIMEOUT);

		// make sure a results.json was NOT created
		Assertions.assertFalse(Files.exists(RESULTS_DEFAULT), debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next1")
	public void testCountOutput() throws IOException {
		String[] args = {
				TEXT_FLAG, Project1Test.HELLO, QUERY_FLAG, Project2Test.SIMPLE, COUNTS_FLAG
		};

		// make sure to delete old index.json and counts.json if it exists
		Files.deleteIfExists(INDEX_DEFAULT);
		Files.deleteIfExists(COUNTS_DEFAULT);

		testNoExceptions(args, SHORT_TIMEOUT);

		// make sure a results.json was NOT created
		Assertions.assertFalse(Files.exists(COUNTS_DEFAULT), debug);
	}

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next1")
	@Tag("next2")
	public void testThreadBuild() throws IOException {
		String[] args = {
				TEXT_FLAG, TEXT_PATH.resolve("guten").toString(), THREADS_FLAG, "2"
		};

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

	/**
	 * Tests that next project functionality is not present.
	 *
	 * @throws IOException if an IO error occurs
	 */
	@Test
	@Tag("next1")
	@Tag("next2")
	@Tag("next3a")
	@Tag("next3b")
	public void testCrawlOutput() throws IOException {
		String seed = "https://usf-cs272-fall2022.github.io/project-web/input/birds/index.html";
		Path actual = ACTUAL_PATH.resolve("counts-birds.json");

		String[] args = {
				HTML_FLAG, seed, MAX_FLAG, "1", THREADS_FLAG, "2", COUNTS_FLAG, actual.toString()
		};

		// make sure to remove actual file before running
		Files.deleteIfExists(actual);

		Assertions.assertTimeoutPreemptively(LONG_TIMEOUT, () -> {
			try {
				System.out.printf("%nRunning Driver %s...%n", String.join(" ", args));
				Driver.main(args);
			}
			catch (Exception ignored) {
				// exceptions are okay in this case
				// output to console for record keeping
				ignored.printStackTrace();
			}
		});

		// no file output is okay in this case
		// but, if a file was generated, check its output
		if (Files.isRegularFile(actual)) {
			String result = Files.readString(actual, UTF_8);
			Assertions.assertFalse(result.contains("birds"), debug);
			Files.deleteIfExists(actual);
		}
	}

	/**
	 * Test placeholder for last project (no "next" test functionality). Only
	 * tests that valid flag/value pairs do not throw an exception.
	 */
	@Test
	@Tag("next4")
	public void testLastProject() {
		String seed = "https://usf-cs272-fall2022.github.io/project-web/input/birds/index.html";
		String[] args = {
				HTML_FLAG, seed, MAX_FLAG, "2", THREADS_FLAG, "2"
		};
		testNoExceptions(args, LONG_TIMEOUT);
	}
}
