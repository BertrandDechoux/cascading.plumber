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
		String sourcePath = "/directory";
		
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useInMemoryGrid();
		
		// create tap without scheme
		grid.createTap(sourcePath, null);
	}
	
	@Test
	public void shouldFailWhenSchemeNotFoundForNonNullKey() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Unable to find Scheme for scheme key 'unmappedKey'. Current mapping is {DefaultTextLineKey=TextLine[['offset', 'line']->[ALL]]}.");
		
		String sourcePath = "/directory";
		
		Plumber plumber = Plumbing.getDefaultPlumber();
		Grid grid = plumber.useInMemoryGrid();
		
		// try creating a tap with a key to an unmapped scheme
		Object unknownSchemeKey = "unmappedKey";
		grid.createTap(sourcePath, unknownSchemeKey);
	}

}
