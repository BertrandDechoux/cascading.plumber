/**
 * Copyright 2012 Bertrand Dechoux
 * 
 * This file is part of the cascading.plumber project.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cascading.plumber.grids;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;
import org.junit.Test;

import cascading.flow.FlowConnector;
import cascading.plumber.Grid;
import cascading.tap.Tap;

/**
 * Test of common codes of the two {@link Grid} implementations.
 */
public class AbstractGridTest {
	public static final String KEY = "testKey";
	public static final String VALUE = "testValue";

	@Test
	public void shouldCopyConfigurationIntoProperties() {
		Configuration configuration = new Configuration();
		configuration.set(KEY, VALUE);
		new MockGrid().createFlowConnector(configuration);
	}

	@Test
	public void shouldCopyJobConfIntoProperties() {
		JobConf jobConf = new JobConf();
		jobConf.set(KEY, VALUE);
		new MockGrid().createFlowConnector(jobConf);
	}

	/**
	 * Mock implementation testing that the abstract super class does indeed
	 * copy the configuration from one wrapper to an other.
	 */
	public static class MockGrid extends AbstractGrid {

		/*
		 * (non-Javadoc)
		 * 
		 * @see cascading.plumber.Grid#createTemplateTap(cascading.tap.Tap,
		 * java.lang.String)
		 */
		@Override
		public <Config, Input, Output> Tap<Config, Input, Output> createTemplateTap(
				Tap<Config, Input, Output> parent, String pathTemplate) {
			throw new IllegalStateException();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cascading.plumber.grids.AbstractGrid#createFlowConnector(java.util
		 * .Properties)
		 */
		@Override
		public FlowConnector createFlowConnector(Properties properties) {
			assertThat(properties.getProperty(KEY)).isEqualTo(VALUE);
			return null;
		}

	}
}
