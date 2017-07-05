package de.kogs.javafx.tilemap.layers;


import de.kogs.javafx.tilemap.elements.TileElement;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Layer extends Pane {
	
	protected double opacity = 1;
	protected boolean visible = true;
	private String name;

	protected int height;
	protected int width;
	
	public Layer (int height, int width, String name) {
		setWidth(width);
		setPrefWidth(width);
		setHeight(height);
		setPrefHeight(height);
		this.height = height;
		this.width = width;
		this.name = name;
	}
	
	public abstract void createElements();
	
	public abstract void draw();
	
	
	public int getX() {
		return (int) getLayoutX();
	}
	
	public void setX(int x) {
		setLayoutX(x);
	}
	
	public int getY() {
		return (int) getLayoutY();
	}
	
	public void setY(int y) {
		setLayoutY(y);
	}
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract TileElement getElementAtPos(double x, double y);
	
}
