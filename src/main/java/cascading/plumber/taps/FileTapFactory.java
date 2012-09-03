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
package cascading.plumber.taps;

import java.net.URI;

import org.apache.commons.httpclient.URIException;

import cascading.plumber.TapFactory;
import cascading.scheme.Scheme;
import cascading.tap.Tap;
import cascading.tap.local.FileTap;

/**
 * {@link TapFactory} for {@link FileTap}.
 */
public final class FileTapFactory implements TapFactory {
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <Config, Input, Output, SourceContext, SinkContext> Tap<Config, Input, Output> create(
			URI uri,
			Scheme<Config, Input, Output, SourceContext, SinkContext> scheme)
			throws URIException {
		return (Tap) new FileTap((Scheme) scheme, uri.getPath());
	}
}