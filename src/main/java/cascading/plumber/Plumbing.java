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

import cascading.plumber.grids.HadoopGrid;
import cascading.plumber.grids.InMemoryGrid;
import cascading.plumber.taps.DfsFactory;
import cascading.plumber.taps.FileTapFactory;
import cascading.plumber.taps.HfsFactory;
import cascading.plumber.taps.LfsFactory;
import cascading.scheme.Scheme;
import cascading.tap.Tap;
import cascading.tuple.Fields;

/**
 * Provide a non naive {@link Plumber} with expected defaults.
 */
public final class Plumbing {
	public interface SchemeKeys {
		/**
		 * Key for using a default grid implementation agnostic TextLine
		 * {@link Scheme}.
		 */
		Object TEXT_LINE = new DefaultTextLineKey();
	}

	/**
	 * Provide a non naive {@link Plumber} with expected defaults.
	 * 
	 * @see #addDefaultTapRecipes(Plumber)
	 * @see #addDefaultSchemeRecipes(Plumber)
	 */
	public static Plumber getDefaultPlumber() {
		Plumber plumber = new Plumber();
		addDefaultTapRecipes(plumber);
		addDefaultSchemeRecipes(plumber);
		return plumber;
	}

	/**
	 * The only default {@link Scheme} recipe is a grid implementation agnostic
	 * TextLine.
	 * 
	 * @see SchemeKeys#TEXT_LINE
	 */
	public static void addDefaultSchemeRecipes(Plumber plumber) {
		plumber.registerTextLine(SchemeKeys.TEXT_LINE, new Fields("offset",
				"line"));
	}

	/**
	 * The default {@link Tap} recipes allows you to use hadoop {@link Tap} when
	 * using {@link HadoopGrid} and local {@link Tap} when using
	 * {@link InMemoryGrid}. You only need to change the uri path when creating
	 * the {@link Tap}.
	 */
	public static void addDefaultTapRecipes(Plumber plumber) {
		plumber.useHadoopGrid().register(null, new HfsFactory());
		plumber.useHadoopGrid().register("hfs", new HfsFactory());
		plumber.useHadoopGrid().register("dfs", new DfsFactory());
		plumber.useHadoopGrid().register("lfs", new LfsFactory());
		plumber.useInMemoryGrid().register(null, new FileTapFactory());
	}
	
	/**
	 * Custom class for default TextLine key. Provide a short description and an
	 * instance which should not be equal to any user provided key.
	 */
	private static class DefaultTextLineKey {
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
	

}
