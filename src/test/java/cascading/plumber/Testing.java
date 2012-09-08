package cascading.plumber;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Files;

/**
 * Boiler plate code for testing.
 */
public class Testing {

	/**
	 * In order to be able to compare the content of files across platforms, the
	 * line seperator should be constant and consistent with the reference file
	 * in the source repository.
	 */
	public static void setup() {
		System.getProperties().setProperty("line.separator", "\r\n");
	}
	
	/**
	 * Assert that both files have the same content.
	 */
	public static void assertSame(String referencePath, String outputPath) {
		assertThat(Testing.contentOf(outputPath)).isEqualTo(Testing.contentOf(referencePath));
	}

	/**
	 * @return the full content of the file as a {@link String}
	 */
	private static String contentOf(String filename) {
		try {
			return Files.toString(new File(filename), Charsets.UTF_8);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}


}
