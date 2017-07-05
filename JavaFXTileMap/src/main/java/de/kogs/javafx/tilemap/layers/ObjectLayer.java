package de.kogs.javafx.tilemap.layers;

import de.kogs.javafx.tilemap.elements.ObjectElement;

import java.util.ArrayList;
import java.util.List;

public class ObjectLayer extends Layer {
	
	private List<ObjectElement> objects;
	
	public ObjectLayer (int height, int width, String name) {
		super(height, width, name);
	}
	
	@Override
	public void draw() {
	}
	
	@Override
	public void createElements() {
	}
	
	@Override
	public ObjectElement getElementAtPos(double x, double y) {
		
		return null;
	}
	
	/**
	 * @deprecated use {@link #getObjectsByType(String)}
	 */
	@Deprecated
	public List<ObjectElement> getElementsWithType(String type) {
		return getObjectsByType(type);
	}
	
	public List<ObjectElement> getObjectsByType(String type) {
		List<ObjectElement> objectsByType = new ArrayList<ObjectElement>();
		for (ObjectElement element : objects) {
			if (element.getType().equalsIgnoreCase(type)) {
				objectsByType.add(element);
			}
		}
		return objectsByType;
	}
	
	public List<ObjectElement> getObjects() {
		return objects;
	}
	
	public void setObjects(List<ObjectElement> objects) {
		this.objects = objects;
	}
	
}
