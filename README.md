cascading.plumber
=================

Switching between Hadoop Cascading and InMemory Cascading made simple.

See [PlumberTest.java](https://github.com/BertrandDechoux/cascading.plumber/blob/master/src/test/java/cascading/plumber/PlumberTest.java) in order to see a Plumber in action.

Here is how to launch a copy Flow without any hardwiring to Hadoop Cascading or InMemory Cascading :
```java
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
```