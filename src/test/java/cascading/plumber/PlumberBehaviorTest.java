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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * What should happen when something goes wrong.
 */
public class PlumberBehaviorTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void shouldFailGracefullyWhenTapfactoryIsNotFound() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Unable to find TapFactory for uriScheme 'mongodb'. Current mapping is {null=FileTapFactory}.");
		
		// Oops! I forgot to register a mongodb factoryTap!
		String sourcePath = "mongodb://localhost/database.collection";
		
		// get Grid without any mongodb related recipe
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useInMemoryGrid();
		
		// try creating a unknown tap
		grid.createTap(sourcePath, Plumbing.SchemeKeys.TEXT_LINE);
	}
	
	@Test
	public void shouldAllowForNoScheme() {
		String sourcePath = "target/output/none.txt";
		
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useInMemoryGrid();
		
		// create tap without scheme
		grid.createTap(sourcePath, null);
	}
	
	@Test
	public void shouldFailWhenSchemeNotFoundForNonNullKey() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Unable to find Scheme for scheme key 'unmappedKey'. Current mapping is {DefaultTextLineKey=TextLine[['offset', 'line']->[ALL]]}.");
		
		String sourcePath = "target/output/none.txt";
		
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useInMemoryGrid();
		
		// try creating a tap with a key to an unmapped scheme
		Object unknownSchemeKey = "unmappedKey";
		grid.createTap(sourcePath, unknownSchemeKey);
	}

}
