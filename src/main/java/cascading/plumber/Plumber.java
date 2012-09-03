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

import cascading.flow.Flow;
import cascading.plumber.grids.HadoopGrid;
import cascading.plumber.grids.InMemoryGrid;
import cascading.scheme.Scheme;
import cascading.scheme.hadoop.TextLine.Compress;
import cascading.tuple.Fields;

/**
 * Your knowledgable servitor that will help you write your {@link Flow} in such
 * a way that -with only a (boolean) switch- you can decide whether to use
 * {@link HadoopGrid} or {@link InMemoryGrid}.
 */
public class Plumber implements RecipesHolder {
	private final Grid hadoopGrid = new HadoopGrid();
	private final Grid inMemoryGrid = new InMemoryGrid();

	/**
	 * @return the {@link HadoopGrid} customized with its recipes.
	 */
	public Grid useHadoopGrid() {
		return hadoopGrid;
	}

	/**
	 * @return the {@link InMemoryGrid} customized with its recipes.
	 */
	public Grid useInMemoryGrid() {
		return inMemoryGrid;
	}

	/**
	 * Parametrized choice of the {@link Grid}.
	 * 
	 * @see #useHadoopGrid()
	 * @see #useInMemoryGrid()
	 */
	public Grid useGrid(boolean hadoop) {
		return (hadoop) ? hadoopGrid : inMemoryGrid;
	}

	/**
	 * Register the provided {@link Scheme} for {@link HadoopGrid} and
	 * {@link InMemoryGrid} using the same key.
	 */
	@Override
	public <Config, Input, Output, SourceContext, SinkContext> void register(
			Object key,
			Scheme<Config, Input, Output, SourceContext, SinkContext> scheme) {
		hadoopGrid.register(key, scheme);
		inMemoryGrid.register(key, scheme);
	}

	/**
	 * Register a implementation related TextLine {@link Scheme} for
	 * {@link HadoopGrid} and {@link InMemoryGrid} using the same key.
	 */
	public <Config, Input, Output, SourceContext, SinkContext> void registerTextLine(
			Object key, Fields fields) {
		hadoopGrid.register(key, new cascading.scheme.hadoop.TextLine(fields));
		inMemoryGrid.register(key, new cascading.scheme.local.TextLine(fields));
	}

	/**
	 * Register a implementation related TextDelimited {@link Scheme} for
	 * {@link HadoopGrid} and {@link InMemoryGrid} using the same key.
	 */
	public <Config, Input, Output, SourceContext, SinkContext> void registerTextDelimited(
			Object key, String delimiter, Fields fields) {
		hadoopGrid.register(key, new cascading.scheme.hadoop.TextDelimited(
				fields, delimiter));
		inMemoryGrid.register(key, new cascading.scheme.local.TextDelimited(
				fields, delimiter));
	}

	/**
	 * Register a implementation related unsafe TextDelimited {@link Scheme} for
	 * {@link HadoopGrid} and {@link InMemoryGrid} using the same key.
	 */
	public <Config, Input, Output, SourceContext, SinkContext> void registerUnsafeTextDelimited(
			Object key, String delimiter, Fields fields) {
		hadoopGrid.register(key, new cascading.scheme.hadoop.TextDelimited(
				fields, Compress.DEFAULT, false, false, delimiter, false, null,
				null, false));
		inMemoryGrid.register(key, new cascading.scheme.local.TextDelimited(
				fields, false, false, delimiter, false, null, null, false));
	}

	/**
	 * Register the provided {@link TapFactory} for {@link HadoopGrid} and
	 * {@link InMemoryGrid} using the same uriScheme as key.
	 */
	@Override
	public void register(String uriScheme, TapFactory tapFactory) {
		hadoopGrid.register(uriScheme, tapFactory);
		inMemoryGrid.register(uriScheme, tapFactory);
	}

}
