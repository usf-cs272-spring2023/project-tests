package edu.usfca.cs272;

import static edu.usfca.cs272.ProjectPath.ACTUAL;
import static edu.usfca.cs272.ProjectPath.EXPECTED;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * A test suite for project v4.0.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CrawlPageTests extends ProjectTests {
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
		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(1)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"simple, simple,   input/simple/",
			"simple, subdir,   input/simple/a/b/c/subdir.html",
			"simple, capital,  input/simple/capital_extension.HTML",
			"simple, hello,    input/simple/hello.html",
			"simple, mixed,    input/simple/mixed_case.htm",
			"simple, position, input/simple/position.html",
			"simple, stems,    input/simple/stems.html",
			"simple, symbols,  input/simple/symbols.html",
			"simple, dir,      input/simple/dir.txt",
			"simple, wrong,    input/simple/wrong_extension.html",
			"birds,  raven,    input/birds/raven.html",
			"birds,  falcon,   input/birds/falcon.html#file=hello.jpg",
		})
		public void testIndex(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}

		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(3)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"simple, stems, input/simple/stems.html",
			"birds,  birds, input/birds/"
		})
		public void testCountsIndex(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.COUNTS, ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}

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
	 * Tests the output of this project on special web pages.
	 */
	@Nested
	@Order(3)
	@Tag("test-v4.0")
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("test-v5.0")
	@TestMethodOrder(OrderAnnotation.class)
	public class SpecialTests {
		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(1)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"special, empty, input/simple/empty.html"
		})
		public void testEmptyIndex(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Map.of(ProjectFlag.QUERY, ProjectPath.QUERY_SIMPLE.text);
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX, ProjectFlag.COUNTS, ProjectFlag.RESULTS);
			testCrawl(seed, subdir, id, input, output);
		}

		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(2)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"special, type-double,  input/simple/double_extension.html.txt",
			"special, type-noext,   input/simple/no_extension",
			"special, type-nowhere, input/simple/no_extension#nowhere.html",
			"special, type-cover,   input/guten/1661-h/images/cover.jpg",
		})
		public void testNotHtml(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}

		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(3)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"special, status-404, https://www.cs.usfca.edu/~cs272/redirect/nowhere",
			"special, status-410, https://www.cs.usfca.edu/~cs272/redirect/gone"
		})
		public void testNotOkay(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}

		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(4)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"special, redirect-1, https://www.cs.usfca.edu/~cs272/redirect/one",
			"special, redirect-2, https://www.cs.usfca.edu/~cs272/redirect/two",
			"special, redirect-3, https://www.cs.usfca.edu/~cs272/redirect/three"
		})
		public void testRedirect(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}

		/**
		 * Tests crawl output.
		 *
		 * @param subdir the expected output subdirectory
		 * @param id the unique test and file id
		 * @param seed the seed url
		 * @throws MalformedURLException if unable to create seed url
		 */
		@Order(5)
		@ParameterizedTest(name = "{index} {2}")
		@CsvSource({
			"special, loop-1, https://www.cs.usfca.edu/~cs272/redirect/loop1",
			"special, loop-2, https://www.cs.usfca.edu/~cs272/redirect/loop2"
		})
		public void testFailedRedirect(String subdir, String id, String seed) throws MalformedURLException {
			Map<ProjectFlag, String> input = Collections.emptyMap();
			List<ProjectFlag> output = List.of(ProjectFlag.INDEX);
			testCrawl(seed, subdir, id, input, output);
		}
	}

	/**
	 * Tests the exception handling of this project.
	 */
	@Nested
	@Order(4)
	@Tag("test-v4.0")
	@Tag("test-v4.1")
	@Tag("test-v4.x")
	@Tag("test-v5.0")
	@TestMethodOrder(OrderAnnotation.class)
	public class ExceptionTests {

	}

	/** Base URL for the GitHub test website. */
	public static final String GITHUB = "https://usf-cs272-spring2023.github.io/project-web/";

	/** Base directory for crawl output. */
	public static final Path CRAWL = EXPECTED.resolve("crawl");

	/**
	 * Tests the output of crawl.
	 *
	 * @param seed the seed link
	 * @param subdir the expected output subdir
	 * @param id the test id
	 * @param input the input flags to use
	 * @param output the output flags to use
	 * @throws MalformedURLException if unable to convert seed to URL
	 */
	public static void testCrawl(String seed, String subdir, String id, Map<ProjectFlag, String> input, List<ProjectFlag> output) throws MalformedURLException {
		URL url = new URL(new URL(GITHUB), seed);

		Map<ProjectFlag, String> config = new LinkedHashMap<>();
		Map<Path, Path> files = new LinkedHashMap<>();

		// configure input parameters
		config.put(ProjectFlag.HTML, url.toString());
		config.putAll(input);

		// configure output parameters
		for (ProjectFlag flag : output) {
			String name = String.format("%s%s.json", id, flag.flag);
			Path actual = ACTUAL.resolve(name);
			Path expected = CRAWL.resolve(subdir).resolve(name);

			config.put(flag, actual.toString());
			files.put(actual, expected);
		}

		// convert configuration to argument array
		String[] args = config.entrySet().stream()
				.flatMap(entry -> Stream.of(entry.getKey().flag, entry.getValue()))
				.toArray(String[]::new);

		checkAllOutput(args, files);
	}
}
