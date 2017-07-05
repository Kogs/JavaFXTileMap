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
public interface ResourceHandler {

	public abstract void initialize(String resourcesPath) throws Exception;
	
	public abstract URL getResourceURL(String resourceName);
	
	public abstract File getResource(String resourceName);
	
	public abstract InputStream getResourceAsStream(String resourceName);
	
	public abstract void loadResources();
	
}
