package de.kogs.javafx.tilemap.elements;

import java.util.HashMap;

import de.kogs.javafx.tilemap.layers.Layer;
import javafx.scene.Node;

public abstract class TileElement {

	protected HashMap<String, String> tileProperties = new HashMap<String ,String>();
	private Layer parentLayer;
	
	public abstract TileElement clone() throws CloneNotSupportedException;
	
	public HashMap<String, String> getTileProperties() {
		return tileProperties;
	}

	public void setTileProperties(HashMap<String, String> tileProperties) {
		this.tileProperties = tileProperties;
	}
	
	public Layer getParentLayer() {
		return parentLayer;
	}
	
	public void setParentLayer(Layer parentLayer) {
		this.parentLayer = parentLayer;
	}
	
}
