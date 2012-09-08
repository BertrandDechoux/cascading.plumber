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

import cascading.flow.FlowConnector;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.Retain;
import cascading.tap.Tap;
import cascading.tuple.Fields;

/**
 * Demonstrate the use case of TemplateTap.
 */
public class TemplateTapTest {

	@Before
	public void setup() {
		Testing.setup();
	}

	@Test
	public void shouldDispatchResultOfCopyOnAnOffsetBasis() {
		Testing.setup();

		boolean hadoop = false;
		String sourcePath = "src/test/resources/extract.txt";
		String sinkPath = "target/output/dispatch";

		launch(hadoop, sourcePath, sinkPath);
		Testing.assertSame("src/test/resources/dispatch/0", "target/output/dispatch/0");
		Testing.assertSame("src/test/resources/dispatch/1", "target/output/dispatch/1");
		Testing.assertSame("src/test/resources/dispatch/2", "target/output/dispatch/2");
		Testing.assertSame("src/test/resources/dispatch/3", "target/output/dispatch/3");
		Testing.assertSame("src/test/resources/dispatch/4", "target/output/dispatch/4");
		Testing.assertSame("src/test/resources/dispatch/5", "target/output/dispatch/5");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void launch(boolean hadoop, String sourcePath, String sinkPath) {
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useGrid(hadoop);
		
		Tap source = grid.createTap(sourcePath, Plumbing.SchemeKeys.TEXT_LINE);
		Tap sink = grid.createTap(sinkPath, Plumbing.SchemeKeys.TEXT_LINE);
		sink = grid.createTemplateTap(sink, "%s");

		Pipe pipe = new Retain(new Pipe("copy-dispatch"), new Fields( "offset", "line" ) );

		FlowConnector connector = grid.createFlowConnector(new Properties());
		connector.connect("main", source, sink, pipe).complete();
	}
}
