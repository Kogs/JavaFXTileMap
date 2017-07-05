package de.kogs.javafx.tilemap.elements;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

import java.util.HashMap;

public class TileSetElement extends TileElement{
	
	private static TileSetElement ElementZero;
	public static int lastTileSetID = 0;
	
	private int elementID = 0;

	private Image image;
	private int size;
	private double x;
	private double y;

	
	public TileSetElement(int size) {
		setSize(size);
		// imageView = new TileSetImageView(this);
		// imageView.setFitHeight(size);
		// imageView.setFitWidth(size);
	}

	public int getElementID() {
		return elementID;
	}

	public void setElementID(int elementID) {
		this.elementID = elementID;
	}



	@Override
	public TileSetElement clone() throws CloneNotSupportedException {
		TileSetElement clonedElement = new TileSetElement(getSize());
		clonedElement.setElementID(getElementID());
		clonedElement.setImage(getImage());
		clonedElement.setX(getX());
		clonedElement.setY(getY());
		HashMap<String, String> props = new HashMap<String, String>();
		if (getTileProperties() != null) {
			props.putAll(getTileProperties());
		}
		clonedElement.setTileProperties(props);
		return clonedElement;
	}
	
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public static TileSetElement ElementZero(int tileSize){
		if(ElementZero != null){
			return ElementZero;
		}
		TileSetElement elementZero = new TileSetElement(tileSize);
		
		// elementZero.getNode().setMouseTransparent(true);
		// elementZero.getNode().setFitHeight(tileSize);
		// elementZero.getNode().setFitWidth(tileSize);
		ElementZero = elementZero;
		return elementZero;
	}
	
	@Override
	public String toString() {
		return "TileSetElement, elementID: " + elementID + " TileProperties: " + tileProperties;
	}

	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Bounds getBounds() {
		return new BoundingBox(x, y, size, size);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + elementID;
		result = prime * result + size;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		TileSetElement other = (TileSetElement) obj;
		if (elementID != other.elementID) { return false; }
		if (size != other.size) { return false; }
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) { return false; }
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) { return false; }
		return true;
	}

}
