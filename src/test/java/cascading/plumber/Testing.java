/**
 *  Copyright 2012 Bertrand Dechoux
 *  
 *  This file is part of the cascading.plumber project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at

 *      http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
