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
package cascading.plumber.grids;

import java.util.Properties;

import cascading.flow.FlowConnector;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.plumber.Grid;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tap.hadoop.TemplateTap;

/**
 * Implementation of {@link Grid} for Hadoop. The default, historic mode of
 * Cascading.
 */
public final class HadoopGrid extends AbstractGrid {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cascading.plumber.grid.AbstractGrid#createFlowConnector(java.util.Properties
	 * )
	 */
	@Override
	public FlowConnector createFlowConnector(Properties properties) {
		return new HadoopFlowConnector(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cascading.plumber.Grid#createTemplateTap(cascading.tap.Tap,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Config, Input, Output> Tap<Config, Input, Output> createTemplateTap(
			Tap<Config, Input, Output> parent, String pathTemplate) {
		// Can only handle Hfs. This is the implementation's limitation.
		return (Tap<Config, Input, Output>) new TemplateTap((Hfs) parent, pathTemplate);
	}

}
