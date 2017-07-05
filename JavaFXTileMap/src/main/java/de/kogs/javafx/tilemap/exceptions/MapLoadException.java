package de.kogs.javafx.tilemap.exceptions;

public class MapLoadException extends RuntimeException {
	public MapLoadException (String string) {
		super(string);
	}
	
	public MapLoadException (String string, Throwable couse) {
		super(string, couse);
	}
	
}
