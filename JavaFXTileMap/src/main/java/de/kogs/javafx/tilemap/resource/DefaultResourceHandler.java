/**
 *
 */
package de.kogs.javafx.tilemap.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author <a href="mailto:marcel.vogel@proemion.com">mv1015</a>
 *
 */
public class DefaultResourceHandler implements ResourceHandler {

	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#initialize(java.lang.String)
	 */
	
	public void initialize(String resourcesPath) throws Exception {
	}
	
	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#getResourceURL(java.lang.String)
	 */
	
	public URL getResourceURL(String resourceName) {
		return DefaultResourceHandler.class.getResource(parseResourceName(resourceName));
	}
	
	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#getResource(java.lang.String)
	 */
	
	public File getResource(String resourceName) {
		return new File(getResourceURL(resourceName).getFile());
	}
	


	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#getResourceAsStream(java.lang.String)
	 */
	
	public InputStream getResourceAsStream(String resourceName) {
		return DefaultResourceHandler.class.getResourceAsStream(parseResourceName(resourceName));
	}
	
	private static String parseResourceName(String resourceName) {
		// resourceName = resourceName.replaceAll("\\", "/");
		if (!resourceName.startsWith("/")) {
			return "/" + resourceName;
		}
		return resourceName;
	}
	
	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#loadResources(java.lang.String)
	 */
	
	public void loadResources() {
	}
	
}
