package de.kogs.javafx.tilemap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import de.kogs.javafx.tilemap.elements.TileSetElement;

public class TileSet {

	private static List<TileSetElement> allElements = new ArrayList<TileSetElement>();
	
	private Image tileImage;
	private int imageheight = 0;
	private int imagewidht = 0;
	private int tileSize = 32;
	private String name = "";
	private HashMap<Integer, HashMap<String, String>> tileproperties = new HashMap<Integer, HashMap<String, String>>();
	private String transparentColor;
	private boolean firstTileSet;
	
	
	private ArrayList<TileSetElement> elements = new ArrayList<TileSetElement>();

	public TileSet (Image tileImage, int imageHeight, int imageWidth,
			String name) {
		this.tileImage = tileImage;
		imageheight = imageHeight;
		imagewidht = imageWidth;
		this.name = name;

	}

	public boolean createElements() {
		if (tileImage != null) {
			elements.clear();
			if(!allElements.contains(TileSetElement.ElementZero(tileSize))){
				allElements.add(TileSetElement.ElementZero(tileSize));
			}
			int elementCount = (imageheight * imagewidht) / (tileSize * tileSize) - 1;
			int xPos = 0;
			int yPos = 0;
			int allElementsSize = TileSetElement.lastTileSetID;
		
			for (int i = allElementsSize + 0; i < (allElementsSize + elementCount); i++) {
				
				TileSetElement setElement = new TileSetElement(tileSize);
				TileSetElement.lastTileSetID++;
				if(!isFirstTileSet()){
					setElement.setElementID(TileSetElement.lastTileSetID+1);
				}else{
					setElement.setElementID(TileSetElement.lastTileSetID);
				}
				
				if (xPos + tileSize > imagewidht) {
					xPos = 0;
					yPos += tileSize;
				}

				WritableImage wImage = new WritableImage(tileSize, tileSize);
				PixelWriter pixelWriter = wImage.getPixelWriter();
				
				PixelReader pixelReader = tileImage.getPixelReader();
				
				for (int x = 0; x < tileSize; x++) {
					for (int y = 0; y < tileSize; y++) {
						if (tileImage.getWidth() > x + xPos && tileImage.getHeight() > y + yPos) {
//							int rgb = pixelReader.getArgb();
//							
//							java.awt.Color helpColor = new java.awt.Color(rgb, true);
//							int red = helpColor.getRed();
//							int green = helpColor.getGreen();
//							int blue = helpColor.getBlue();
//							double a = (helpColor.getAlpha()) / 255.0;
//							
//							if (transparentColor != null
//									&& transparentColor.equalsIgnoreCase("#" + Integer.toHexString(helpColor.getRGB() & 0xffffff))) {
//								a = 0.0;
//							}
							
							Color col = pixelReader.getColor(x + xPos, y + yPos);
							pixelWriter.setColor(x, y, col);
						}
					}
				}
				setElement.setImage(wImage);
				
				setElement.setTileProperties(tileproperties.get(i - allElementsSize));
				elements.add(setElement);

				xPos += tileSize;

			}
			allElements.addAll(elements);
			return true;
		} else {
			return false;
		}

	}

	public List<TileSetElement> getElements() {
		return elements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransparentColor() {
		return transparentColor;
	}

	public void setTransparentColor(String transparentColor) {
		this.transparentColor = transparentColor;
	}

	public static TileSetElement getElementById(int set) {
		for(TileSetElement element : allElements){
			if(element.getElementID() == set){
				return element;
			}
		}
		System.out.println("No Element found by id: "+ set);
		return null;
	}

	public static List<TileSetElement> getAllElements() {
		return allElements;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public HashMap<Integer, HashMap<String, String>> getTileproperties() {
		return tileproperties;
	}

	public void showTileSetImages() {
		final Popup popup = new Popup();


		Canvas canvas = new Canvas();
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				popup.hide();
			}
		});
		int posX = 0;
		int posY = 0;
		for (TileSetElement setElement :  elements) {
			TileSetElement element = TileSet.getElementById(setElement.getElementID());
			if (posX + element.getSize() > imagewidht) {
				posX = 0;
				posY += element.getSize();
			}

			element.setX(posX);
			element.setY(posY);


				canvas.getGraphicsContext2D().drawImage(element.getImage(), posX, posY);

			posX += element.getSize();

		}

		popup.getContent().add(canvas);
		popup.show(TestMain.window);

	}

	public boolean isFirstTileSet() {
		return firstTileSet;
	}

	public void setFirstTileSet(boolean firstTileSet) {
		this.firstTileSet = firstTileSet;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

}
