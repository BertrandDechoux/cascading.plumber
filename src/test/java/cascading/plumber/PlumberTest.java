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

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.Retain;
import cascading.tap.Tap;
import cascading.tuple.Fields;

/**
 * Demonstrate the use case of {@link Plumber}.
 */
public class PlumberTest {

	@Before
	public void setup() {
		Testing.setup();
	}

	/**
	 * Launch a simple cascading {@link Flow} in memory by only switching a
	 * flag.
	 */
	@Test
	public void shouldRunInMemory() {
		Testing.setup();

		boolean hadoop = false;
		String sourcePath = "src/test/resources/extract.txt";
		String sinkPath = "target/output/extract.txt";

		launchCopy(hadoop, sourcePath, sinkPath);
		Testing.assertSame(sinkPath, sourcePath);
	}

	/**
	 * Launch a copy {@link Flow}. This definition is platform agnostic and
	 * could be used with a Hadoop cluster.
	 */
	@SuppressWarnings("rawtypes")
	private void launchCopy(boolean hadoop, String sourcePath, String sinkPath) {
		// get you preferred Grid using a Plumber
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useGrid(hadoop);
		
		// create the Grid related taps
		Tap source = grid.createTap(sourcePath, Plumbing.SchemeKeys.TEXT_LINE);
		Tap sink = grid.createTap(sinkPath, Plumbing.SchemeKeys.TEXT_LINE);

		// create your Flow (here a simple copy all lines)
		Pipe pipe = new Retain(new Pipe("main"), new Fields("line"));

		// connect and run the Flow using the Grid related FlowConnector
		FlowConnector connector = grid.createFlowConnector(new Properties());
		connector.connect("main", source, sink, pipe).complete();
	}
}
