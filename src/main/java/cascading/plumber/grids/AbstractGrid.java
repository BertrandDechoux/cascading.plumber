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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.httpclient.URIException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;

import cascading.flow.FlowConnector;
import cascading.plumber.Grid;
import cascading.plumber.TapFactory;
import cascading.scheme.Scheme;
import cascading.tap.Tap;

/**
 * Common code for the implementations of {@link Grid}. Only the implementation
 * of the {@link FlowConnector} change.
 */
public abstract class AbstractGrid implements Grid {
	private Map<Object, Scheme<?, ?, ?, ?, ?>> keyToScheme = new HashMap<Object, Scheme<?, ?, ?, ?, ?>>();
	private Map<String, TapFactory> uriSchemeToTap = new HashMap<String, TapFactory>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see cascading.plumber.RecipesHolder#register(java.lang.Object,
	 * cascading.scheme.Scheme)
	 */
	@Override
	public final <Config, Input, Output, SourceContext, SinkContext> void register(
			Object key,
			Scheme<Config, Input, Output, SourceContext, SinkContext> scheme) {
		keyToScheme.put(key, scheme);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cascading.plumber.RecipesHolder#register(java.lang.String,
	 * cascading.plumber.TapFactory)
	 */
	@Override
	public final void register(String uriScheme, TapFactory tapFactory) {
		uriSchemeToTap.put(uriScheme, tapFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cascading.plumber.grid.Grid#createTap(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final <Config, Input, Output> Tap<Config, Input, Output> createTap(
			String uriPath, Object schemeKey) {
		try {
			URI uri = new URI(uriPath);
			TapFactory tapFactory = getTapFactory(uri);
			Scheme<?, ?, ?, ?, ?> scheme = getScheme(schemeKey);
			return (Tap<Config, Input, Output>) tapFactory.create(uri, scheme);
		} catch (URIException e) {
			throw new IllegalArgumentException(e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cascading.plumber.grid.Grid#createFlowConnector(java.util.Properties)
	 */
	@Override
	public abstract FlowConnector createFlowConnector(Properties properties);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cascading.plumber.grid.Grid#createFlowConnector(org.apache.hadoop.mapred
	 * .JobConf)
	 */
	@Override
	public final FlowConnector createFlowConnector(JobConf jobConf) {
		return createFlowConnector(asProperties(jobConf));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cascading.plumber.grid.Grid#createFlowConnector(org.apache.hadoop.conf
	 * .Configuration)
	 */
	@Override
	public final FlowConnector createFlowConnector(Configuration configuration) {
		return createFlowConnector(asProperties(configuration));
	}

	/**
	 * Transform a {@link JobConf} or a {@link Configuration} into a
	 * {@link Properties}.
	 */
	private Properties asProperties(Iterable<Entry<String, String>> conf) {
		Properties properties = new Properties();
		for (Entry<String, String> entry : conf) {
			properties.put(entry.getKey(), entry.getValue());
		}
		return properties;
	}

	/**
	 * Get the {@link TapFactory} associated with the {@link URI}'s scheme. If
	 * none, throw an explicit {@link Exception} with the current mapping.
	 */
	private TapFactory getTapFactory(URI uri) {
		TapFactory tapFactory = uriSchemeToTap.get(uri.getScheme());
		if(tapFactory == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Unable to find TapFactory for uriScheme '");
			buffer.append(uri.getScheme());
			buffer.append("'. Current mapping is ");
			buffer.append(uriSchemeToTap);
			buffer.append(".");
			throw new IllegalArgumentException(buffer.toString());
		}
		return tapFactory;
	}

	/**
	 * Get the {@link Scheme} associated with the schemeKey. Explicitly fail if
	 * a non-null key was provided and no associated {@link Scheme} was found.
	 */
	private Scheme<?, ?, ?, ?, ?> getScheme(Object schemeKey) {
		Scheme<?, ?, ?, ?, ?> scheme = keyToScheme.get(schemeKey);
		if (scheme == null && schemeKey !=null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Unable to find Scheme for scheme key '");
			buffer.append(schemeKey);
			buffer.append("'. Current mapping is ");
			buffer.append(keyToScheme);
			buffer.append(".");
			throw new IllegalArgumentException(buffer.toString());
		}
		return scheme;
	}

}
