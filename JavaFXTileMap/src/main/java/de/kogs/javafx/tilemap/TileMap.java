package de.kogs.javafx.tilemap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.kogs.javafx.tilemap.elements.ObjectElement;
import de.kogs.javafx.tilemap.elements.TileSetElement;
import de.kogs.javafx.tilemap.exceptions.MapLoadException;
import de.kogs.javafx.tilemap.layers.Layer;
import de.kogs.javafx.tilemap.layers.ObjectLayer;
import de.kogs.javafx.tilemap.layers.TileLayer;
import de.kogs.javafx.tilemap.resource.DefaultResourceHandler;
import de.kogs.javafx.tilemap.resource.ResourceHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class TileMap extends Pane {
	
	private Logger logger = Logger.getLogger("TileMap");
	
	private List<TileSet> tileSets = new ArrayList<TileSet>();
	private List<File> tileSetFiles = new ArrayList<File>();
	private List<Layer> layers = new ArrayList<Layer>();
	public static ObjectProperty<CacheHint> graphicQuality = new SimpleObjectProperty<CacheHint>(CacheHint.SPEED);
	
	private String hash = "";
	private Long tileheight;
	private Long tilewidth;
	
	/**
	 * @throws FileNotFoundException
	 */
	public TileMap (String mapFilePath) throws MapLoadException {
		this(new DefaultResourceHandler(), mapFilePath);
	}
	
	public TileMap (ResourceHandler resourceHandler, String mapFilePath) throws MapLoadException {
		JSONParser parser = new JSONParser();
		
		InputStreamReader reader = new InputStreamReader(resourceHandler.getResourceAsStream(mapFilePath));
		
		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			{
//				InputStream is = new FileInputStream(jsonFile);
//				byte[] buffer = new byte[8192];
//				int read = 0;
//				while ((read = is.read(buffer)) > 0) {
//					md.update(buffer, 0, read);
//				}
//				is.close();
//			}
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			tileheight = (Long) jsonObject.get("tileheight");
			tilewidth = (Long) jsonObject.get("tilewidth");
			
			setHeight((Long) jsonObject.get("height") * tileheight);
			setWidth((Long) jsonObject.get("width") * tilewidth);
			if (tileheight != tilewidth) {
				throw new RuntimeException("Tiles must have the same height and widht");
			}
			
			// Layers
			logger.info("Load Layers");
			
			JSONArray layers = (JSONArray) jsonObject.get("layers");
			Iterator<JSONObject> layersInt = layers.iterator();
			while (layersInt.hasNext()) {
				
				JSONObject layerJSONobj = (layersInt.next());
				String name = (String) layerJSONobj.get("name");
				Long height = (Long) layerJSONobj.get("height") * tileheight;
				Long width = (Long) layerJSONobj.get("width") * tilewidth;
				String type = (String) layerJSONobj.get("type");
				
				Layer layer;
				
				if (type.equals("tilelayer")) {
					layer = new TileLayer(height.intValue(), width.intValue(), name);
					
					JSONArray data = (JSONArray) layerJSONobj.get("data");
					if (data != null) {
						List<Long> dataList = new ArrayList<Long>();
						dataList.addAll(data);
						((TileLayer) layer).setData(dataList);
					}
					
				} else if (type.equals("objectgroup")) {
					layer = new ObjectLayer(height.intValue(), width.intValue(), name);
					
					List<ObjectElement> objectElements = new ArrayList<ObjectElement>();
					
					JSONArray objects = (JSONArray) layerJSONobj.get("objects");
					Iterator<JSONObject> objectsInt = objects.iterator();
					while (objectsInt.hasNext()) {
						
						JSONObject objectJSONobj = (objectsInt.next());
						double x = (Long) objectJSONobj.get("x");
						double y = (Long) objectJSONobj.get("y");
						double objWidth = (Long) objectJSONobj.get("width");
						double objHeight = (Long) objectJSONobj.get("height");
						String objType = (String) objectJSONobj.get("type");
						String objName = (String) objectJSONobj.get("name");
						
						ObjectElement element = new ObjectElement(x, y, objWidth, objHeight);
						
						JSONObject propertys = (JSONObject) objectJSONobj.get("properties");
						if (propertys != null) {
							System.out.println(propertys.keySet());
							HashMap<String, String> propertyValues = new HashMap<String, String>();
							for (String set : (Set<String>) propertys.keySet()) {
								propertyValues.put(set, (String) propertys.get(set));
							}
							element.setTileProperties(propertyValues);
							element.setParentLayer(layer);
						}
						element.setName(objName);
						element.setType(objType);
						objectElements.add(element);
					}
					
					((ObjectLayer) layer).setObjects(objectElements);
					
				} else {
					logger.warning("Layer type " + type + " is not supported yet");
					continue;
				}
				layer.setVisible((Boolean) layerJSONobj.get("visible"));
				try {
					layer.setOpacity((Double) layerJSONobj.get("opacity"));
				} catch (ClassCastException e) {}
				try {
					layer.setOpacity((Long) layerJSONobj.get("opacity"));
				} catch (ClassCastException e) {}
				layer.setLayoutX((Long) layerJSONobj.get("x"));
				layer.setLayoutY((Long) layerJSONobj.get("y"));
				
				this.layers.add(layer);
				
			}
			
			// TileSets
			logger.info("Load TileSets");
			
			JSONArray tilesets = (JSONArray) jsonObject.get("tilesets");
			Iterator<JSONObject> tilesetInt = tilesets.iterator();
			boolean firstTileSet = true;
			String rootFolderPath = mapFilePath;
			if (rootFolderPath.contains("/")) {
//				rootFolderPath = rootFolderPath.replaceAll("\\", "/");
				int lastIndexOfSeperator = rootFolderPath.lastIndexOf("/");
				rootFolderPath = rootFolderPath.substring(0, lastIndexOfSeperator) + "/";
			} else {
				rootFolderPath = "";
			}
			while (tilesetInt.hasNext()) {
				JSONObject tileSetJSONobj = (tilesetInt.next());

				String tileImagePath = rootFolderPath + tileSetJSONobj.get("image");
				logger.info("Load TileImage " + tileImagePath);
				Image tileImage = new Image(resourceHandler.getResourceAsStream(tileImagePath));
				
				// hash
//				InputStream is = new FileInputStream(tileImageFile);
//				byte[] buffer = new byte[8192];
//				int read = 0;
//				while ((read = is.read(buffer)) > 0) {
//					md.update(buffer, 0, read);
//				}
				
				Long imageHeight = (Long) tileSetJSONobj.get("imageheight");
				Long imageWidth = (Long) tileSetJSONobj.get("imagewidth");
				String name = (String) tileSetJSONobj.get("name");
				
				TileSet tileSet = new TileSet(tileImage, imageHeight.intValue(), imageWidth.intValue(), name);
				tileSet.setFirstTileSet(firstTileSet);
				firstTileSet = false;
				
				int tileWidth = ((Long) tileSetJSONobj.get("tilewidth")).intValue();
				int tileHeight = ((Long) tileSetJSONobj.get("tileheight")).intValue();
				
				if (tileHeight != tileWidth) {
					throw new RuntimeException("Tiles must have the same height and widht");
				}
				
				tileSet.setTileSize(tileHeight);
				tileSet.setTransparentColor((String) tileSetJSONobj.get("transparentcolor"));
				
				JSONObject propertys = (JSONObject) tileSetJSONobj.get("tileproperties");
				if (propertys != null) {
					for (String set : (Set<String>) propertys.keySet()) {
						
						HashMap<String, String> propertyValues = new HashMap<String, String>();
						
						JSONObject values = (JSONObject) propertys.get(set);
						for (String vSet : (Set<String>) values.keySet()) {
							propertyValues.put(vSet, (String) values.get(vSet));
						}
						
						tileSet.getTileproperties().put(Integer.parseInt(set), propertyValues);
						
					}
				}
				tileSets.add(tileSet);
			}
			logger
					.info("Map Leaded: " + mapFilePath + " --> Layers: " + layers.size() + "  TileSets: " + tileSets.size());
					
//			byte[] md5 = md.digest();
//			BigInteger bi = new BigInteger(1, md5);
//			hash = bi.toString(16);
		} catch (FileNotFoundException e) {
			throw new MapLoadException("Map File not Found", e);
		} catch (IOException e) {
			throw new MapLoadException("Map Load failed", e);
		} catch (ParseException e) {
			throw new MapLoadException("Map parseing failed", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		} catch (NoSuchAlgorithmException e) {
//			throw new MapLoadException("Failed to generate Hash value", e);
//		}
	
	}
	
	// TODO rename to load
	public void render() {
		long start = System.currentTimeMillis();
		
		for (TileSet tileSet : tileSets) {
			tileSet.createElements();
			// tileSet.showTileSetImages();
		}
		
		for (Layer layer : layers) {
			layer.createElements();
			layer.draw();
			getChildren().add(layer);
		}
		
		long end = System.currentTimeMillis();
		int elementsPerLayer = (int) Math.round((getHeight() * getWidth()) / (tileheight * tilewidth));
		System.out.println("Rendered Layer Elements: " + TileSet.getAllElements().size() + ", Map Elements: "
				+ (layers.size() * elementsPerLayer) + " in Millis: " + (end - start));
				
	}
	
	public List<Layer> getLayers() {
		return layers;
	}
	
	public List<TileSet> getTileSets() {
		return tileSets;
	}
	
	public List<TileSetElement> getElementsAtPos(double x, double y) {
		List<TileSetElement> elementsAtPos = new ArrayList<TileSetElement>();
		for (Layer layer : layers) {
			if (layer.getClass() == TileLayer.class) {
				TileSetElement elementAtPos = (TileSetElement) layer.getElementAtPos(x, y);
				if (elementAtPos != null) {
					elementsAtPos.add(elementAtPos);
				}
			}
		}
		return elementsAtPos;
	}
	
	public ObjectLayer getObjectLayerByName(String name) {
		for (Layer layer : layers) {
			if (layer.getClass() == ObjectLayer.class && layer.getName().equalsIgnoreCase(name)) {
				return (ObjectLayer) layer;
			}
		}
		return null;
	}
	
	public Layer getLayerByName(String name) {
		for (Layer layer : layers) {
			if (layer.getName().equalsIgnoreCase(name)) {
				return layer;
			}
		}
		return null;
	}
	
	public Long getTileheight() {
		return tileheight;
	}
	
	public Long getTilewidth() {
		return tilewidth;
	}
	
	public List<File> getTileSetFiles() {
		return tileSetFiles;
	}
	
}
