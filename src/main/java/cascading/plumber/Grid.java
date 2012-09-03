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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;

import cascading.flow.FlowConnector;
import cascading.plumber.grids.HadoopGrid;
import cascading.plumber.grids.InMemoryGrid;
import cascading.tap.Tap;

/**
 * Cascading 2 has two modes : {@link HadoopGrid} and {@link InMemoryGrid}.
 */
public interface Grid extends RecipesHolder {

	/**
	 * Create a {@link Tap}.
	 * 
	 * @param uriPath
	 *            the path to the resource (Uniform Resource Locator)
	 * @param schemeKey
	 *            the scheme identifier
	 */
	<Config, Input, Output> Tap<Config, Input, Output> createTap(
			String uriPath, Object schemeKey);

	/**
	 * Create a {@link FlowConnector} related to the implementation of the
	 * {@link Grid}.
	 */
	FlowConnector createFlowConnector(Properties properties);

	/**
	 * Create a {@link FlowConnector} using {@link JobConf} as
	 * {@link Properties}.
	 * 
	 * @see #createFlowConnector(Properties)
	 */
	FlowConnector createFlowConnector(JobConf jobConf);

	/**
	 * Create a {@link FlowConnector} using {@link Configuration} as
	 * {@link Properties}.
	 * 
	 * @see #createFlowConnector(Properties)
	 */
	FlowConnector createFlowConnector(Configuration configuration);

}
