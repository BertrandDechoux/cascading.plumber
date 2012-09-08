package cascading.plumber.taps;

import java.net.URI;

import cascading.plumber.TapFactory;

/**
 * Ensure that the mapping of {@link URI}'s scheme to {@link TapFactory} can be
 * easily displayed to the user. It is a 'package' class because it should not
 * be used directly by the user.
 */
/* package */abstract class AbstractTapFactory implements TapFactory {
	/**
	 * Use the class simple name as a mean to convey shortly what is the
	 * {@link TapFactory}.
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
