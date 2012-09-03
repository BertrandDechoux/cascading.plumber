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
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.Retain;
import cascading.tap.Tap;
import cascading.tuple.Fields;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Files;

/**
 * Demonstrate the use case of {@link Plumber}.
 */
public class PlumberTest {

	/**
	 * In order to be able to compare the content of files across platforms, the
	 * line seperator should be constant and consistent with the reference file
	 * in the source repository.
	 */
	@Before
	private void setup() {
		System.getProperties().setProperty("line.separator", "\r\n");
	}

	/**
	 * Launch a simple cascading {@link Flow} in memory by only switching a
	 * flag.
	 */
	@Test
	public void shouldRunInMemory() {
		setup();

		boolean hadoop = false;
		String sourcePath = "src/test/resources/extract.txt";
		String sinkPath = "target/output/extract.txt";

		launchCopy(hadoop, sourcePath, sinkPath);
		assertSame(sinkPath, sourcePath);
	}

	/**
	 * Launch a copy {@link Flow}. This definition is platform agnostic and
	 * could be used with a Hadoop cluster.
	 */
	@SuppressWarnings("rawtypes")
	private void launchCopy(boolean hadoop, String sourcePath, String sinkPath) {
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useGrid(hadoop);
		Tap source = grid.createTap(sourcePath, Plumbing.SchemeKeys.TEXT_LINE);
		Tap sink = grid.createTap(sinkPath, Plumbing.SchemeKeys.TEXT_LINE);

		Pipe pipe = new Retain(new Pipe("main"), new Fields("line"));

		FlowConnector connector = grid.createFlowConnector(new Properties());
		connector.connect("main", source, sink, pipe).complete();
	}

	/**
	 * Assert that both files have the same content.
	 */
	private void assertSame(String referencePath, String outputPath) {
		assertThat(contentOf(outputPath)).isEqualTo(contentOf(referencePath));
	}

	/**
	 * @return the full content of the file as a {@link String}
	 */
	private String contentOf(String filename) {
		try {
			return Files.toString(new File(filename), Charsets.UTF_8);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
