package de.kogs.javafx.tilemap.elements;

import javafx.scene.Node;

public class ObjectElement extends TileElement{

	private double x= 0;
	private double y= 0;
	
	private double width = 0;
	private double height = 0;
	
	private String type = "";
	private String name = "";
	
	public ObjectElement(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}


	public Node getNode() {
		return null;
	}

	
	@Override
	public ObjectElement clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}




	public double getWidth() {
		return width;
	}




	public void setWidth(double width) {
		this.width = width;
	}




	public double getHeight() {
		return height;
	}




	public void setHeight(double height) {
		this.height = height;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
