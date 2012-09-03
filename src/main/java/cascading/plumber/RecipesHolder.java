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

import cascading.scheme.Scheme;
import cascading.tap.Tap;

/**
 * Something able to record under what conditions such or such {@link Tap} or
 * {@link Scheme} should be created.
 */
public interface RecipesHolder {

	/**
	 * Associate a {@link Scheme} instance to a key. Use the key for reusing the
	 * {@link Scheme} instance when creating a {@link Tap}.
	 * 
	 * @see Grid#createTap(String, Object)
	 */
	<Config, Input, Output, SourceContext, SinkContext> void register(
			Object key,
			Scheme<Config, Input, Output, SourceContext, SinkContext> scheme);

	/**
	 * Record how to create a {@link Tap} by associating a {@link TapFactory} to
	 * an uri scheme. The uri scheme will be used to choose the right
	 * {@link TapFactory}.
	 * 
	 * @see Grid#createTap(String, Object)
	 */
	void register(String uriScheme, TapFactory tapFactory);

}
