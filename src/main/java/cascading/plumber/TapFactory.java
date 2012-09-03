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

import java.net.URI;

import org.apache.commons.httpclient.URIException;

import cascading.scheme.Scheme;
import cascading.tap.Tap;

/**
 * Factory for {@link Tap}p that can be registered as recipe using a
 * {@link RecipesHolder}.
 */
public interface TapFactory {

	/**
	 * @param uri
	 *            the path as a Uniform Resource Locator
	 * @param scheme
	 *            the scheme that should be used
	 */
	<Config, Input, Output, SourceContext, SinkContext> Tap<Config, Input, Output> create(
			URI uri,
			Scheme<Config, Input, Output, SourceContext, SinkContext> scheme)
			throws URIException;

}
