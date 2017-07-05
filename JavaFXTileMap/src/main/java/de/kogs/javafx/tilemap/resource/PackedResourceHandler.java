package de.kogs.javafx.tilemap.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackedResourceHandler implements ResourceHandler {
	
	private String baseDir = "";
	private JarFile jar;
	

	public void initialize(String resourcesPath) throws Exception {
		try {
			File jarFile = new File(
					PackedResourceHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			jar = new JarFile(jarFile);
			
			if (!resourcesPath.isEmpty()) {
				baseDir = resourcesPath;
			} else {
				baseDir = jarFile.getParent() + File.separator + ".." + File.separator + "resources" + File.separator;
			}
			
		} catch (IOException e) {
			baseDir = "";
			System.err.println("jar not found: " + e.getMessage());
		}
		
		if (baseDir.isEmpty()) {
			throw new Exception("Failed to Load Resources BaseDir");
		}
		System.out.println("Resource BaseDir setted to: " + baseDir);
		
	}
	
	private boolean extractFile(String resourceName) {
		if (jar != null) {
			resourceName = parseResourceName(resourceName);
			System.out.println("Searching for File in jar " + resourceName);
			for (Enumeration<JarEntry> file = jar.entries(); file.hasMoreElements();) {
				JarEntry entry = file.nextElement();
				if (!entry.getName().endsWith("class")) {
					if (entry.getName().equals(resourceName)) {
						System.out.println("File found in jar " + resourceName);
						File fl = new File(baseDir, entry.getName());
						if (!fl.exists()) {
							fl.getParentFile().mkdirs();
							fl = new File(baseDir, entry.getName());
						}
						if (entry.isDirectory()) {
							continue;
						}
						try {
							InputStream is = jar.getInputStream(entry);
							FileOutputStream fo = new FileOutputStream(fl);
							while (is.available() > 0) {
								fo.write(is.read());
							}
							fo.close();
							is.close();
							return true;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
		
	}
	

	public URL getResourceURL(String resourceName) {
		try {
			return getResource(resourceName).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public File getResource(String resourceName) {
		try {
			resourceName = parseResourceName(resourceName);
			File file = new File(baseDir + resourceName);
			if (file.exists()) {
				return file;
			}
		} catch (Exception e) {
		
		}
		
		System.err.println(
				"Failed to get Resource " + resourceName + " from baseDir " + baseDir + "\nTry to extract it from jar");
		boolean extracted = extractFile(resourceName);
		if (extracted) {
			return getResource(resourceName);
		}
		
		return null;
	}
	

	public InputStream getResourceAsStream(String resourceName) {
		InputStream stream = null;
		try {
			resourceName = parseResourceName(resourceName);
			stream = new FileInputStream(baseDir + resourceName);
		} catch (FileNotFoundException e) {
			System.err.println(
					"Failed to get Resource " + resourceName + " from baseDir " + baseDir + "\nTry to extract it from jar");
			boolean extracted = extractFile(resourceName);
			if (extracted) {
				return getResourceAsStream(resourceName);
			}
		}
		return stream;
		
	}
	
	private static String parseResourceName(String resourceName) {
		// resourceName = resourceName.replaceAll("\\", "/");
		if (resourceName.startsWith("/")) {
			resourceName = resourceName.replaceFirst("/", "");
		}
		
		return resourceName;
	}
	
	/* (non-Javadoc)
	 * @see de.kogs.game.ResourceHandler#loadResources(java.lang.String)
	 */
	public void loadResources() {
		// TODO unzip files
		System.out.println("Unzipping Resources");
		
		for (Enumeration<JarEntry> file = jar.entries(); file.hasMoreElements();) {
			JarEntry entry = file.nextElement();
			String entryName = entry.getName();
			if (!entryName.endsWith("class") && !entryName.contains("META-INF") && entryName.contains(".")
					&& !entryName.contains("java")) {
				File fl = new File(baseDir, entry.getName());
				if (!fl.exists()) {
					fl.getParentFile().mkdirs();
					fl = new File(baseDir, entry.getName());
				}
				if (entry.isDirectory()) {
					continue;
				}
				try {
					InputStream is = jar.getInputStream(entry);
					FileOutputStream fo = new FileOutputStream(fl);
					while (is.available() > 0) {
						fo.write(is.read());
					}
					fo.close();
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
