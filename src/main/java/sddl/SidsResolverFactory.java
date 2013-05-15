/**
 * 
 */
package sddl;

/**
 * @author BQ
 *
 */
interface SidsResolver {
	String[] resolveSids(String[] sids);
}

public class SidsResolverFactory {
	private static SidsResolver instance;
	
	public static void setInstance(SidsResolver sidsResolver) {
	    instance = sidsResolver;
	  }

	public static SidsResolver getInstance() {
		if (instance == null) {
			throw new IllegalStateException("SidsResolverFactory not initialized. "
					+ "Did you forget to call SidsResolverFactory.setInstance() ?");
		}

		return instance;
	}
}