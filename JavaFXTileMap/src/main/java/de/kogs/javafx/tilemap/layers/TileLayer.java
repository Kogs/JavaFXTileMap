package de.kogs.javafx.tilemap.layers;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import de.kogs.javafx.tilemap.TileMap;
import de.kogs.javafx.tilemap.TileSet;
import de.kogs.javafx.tilemap.elements.TileSetElement;

public class TileLayer extends Layer {

	private List<Long> data = new ArrayList<Long>();
	private List<TileSetElement> elements = new ArrayList<TileSetElement>();
	
	private Canvas canvas = new Canvas();
	
	public TileLayer (int height, int width, String name) {
		super(height, width, name);
		canvas.setWidth(width);
		canvas.setHeight(height);
		canvas.setCache(true);
		canvas.cacheHintProperty().bind(TileMap.graphicQuality);
		getChildren().add(canvas);
	}
	
	@Override
	public void draw() {
		// if (elements.isEmpty()) {
		// createElementsFromData(data);
		// }
		for (TileSetElement element : elements) {
			canvas.getGraphicsContext2D().drawImage(element.getImage(), element.getX(), element.getY());
		}
	}
	
	public void setData(List<Long> data) {
		this.data = data;
	}
	
	@Override
	public void createElements() {
		createElementsFromData(data);
	}

	public void createElementsFromData(List<Long> data) {
		elements.clear();
		int posX = 0;
		int posY = 0;
		for (Long set : data) {
			TileSetElement element = TileSet.getElementById(set.intValue());
			
			if (posX + element.getSize() > width) {
				posX = 0;
				posY += element.getSize();
			}
			
			if (set.intValue() != 0) {
				try {
					TileSetElement clone = element.clone();
					clone.setX(posX);
					clone.setY(posY);
					elements.add(clone);
					clone.setParentLayer(this);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			posX += element.getSize();
		}
	}

	@Override
	public TileSetElement getElementAtPos(double x, double y) {
		for (TileSetElement element : elements) {
			
			if (element.getBounds().contains(x, y)) { return element; }
		}
		
		return null;
	}
	
	public List<TileSetElement> getElementsWithPropertie(String key, String value) {
		List<TileSetElement> tileSetElements = new ArrayList<TileSetElement>();
		for (TileSetElement element : elements) {
			
			if (value == null) {
				if (element.getTileProperties().containsKey(key)) {
					tileSetElements.add(element);
				}
			} else {
				if (element.getTileProperties().containsKey(key)
						&& element.getTileProperties().get(key).equalsIgnoreCase(value)) {
					tileSetElements.add(element);
				}
				
			}
		}
		
		return tileSetElements;
	}

	public void removeElement(TileSetElement element) {
		System.out.println("Remove " + element);
		elements.remove(element);
		element.setParentLayer(null);
		System.out.println(elements.contains(element));
		draw();
	}
	
	/**
	 * Not Tested
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public List<TileSetElement> getElementsInLine(Point2D point1, Point2D point2) {
		List<TileSetElement> elements = new ArrayList<TileSetElement>();
		
		double x0 = point1.getX();
		double y0 = point1.getY();
		double x1 = point2.getX();
		double y1 = point2.getY();
		
		int dx = (int) Math.abs(x1 - x0);
		int dy = (int) Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		while (true) {
			TileSetElement elementAtPos = getElementAtPos(x0, y0);
			if (elementAtPos != null && !elements.contains(elementAtPos)) {
				elements.add(elementAtPos);
			}
			
			if (x0 == x1 && y0 == y1) {
				break;
			}
			
			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}
			
			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}
		
		return elements;
	}
	
}
