package de.kogs.javafx.tilemap;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Window;


public class TestMain extends Application{
	public static Window window;
	
	private TileMap tileMap;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

//		primaryStage.setTitle("TileMapTest");
//		Pane root = new Pane();
//		tileMap = new TileMap(new File("F:/hg/schule/Game/noNameGame/src/main/resources/maps/tutorialMap.json"));
//		root.getChildren().add(tileMap);
//		Scene scene = new Scene(root,600,600);
//		primaryStage.setScene(scene);
//		primaryStage.setHeight(600);
//		primaryStage.setWidth(800);
//		primaryStage.setMinWidth(350);
//		primaryStage.setMinHeight(150);
//		primaryStage.show();
//		
//		
//		window = scene.getWindow();
//		
//		tileMap.render();
//		
//		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//			public void handle(KeyEvent event) {
//				KeyCode code = event.getCode();
//				switch (code) {
//				case LEFT:{
//						tileMap.setTranslateX(tileMap.getTranslateX() + 10);
//					break;	
//				}case RIGHT:{
//						tileMap.setTranslateX(tileMap.getTranslateX() - 10);
//					break;
//				}case UP:{
//						tileMap.setTranslateY(tileMap.getTranslateY() + 10);
//					break;
//				}case DOWN:{
//						tileMap.setTranslateY(tileMap.getTranslateY() - 10);
//					break;
//				}default:
//					break;
//				}
//			}
//		});
//		
//		scene.setOnScroll(new EventHandler<ScrollEvent>() {
//			
//			public void handle(ScrollEvent arg0) {
//				if (arg0.getDeltaY() > 0) {
//					tileMap.setScaleX(tileMap.getScaleX() + 0.1);
//					tileMap.setScaleY(tileMap.getScaleY() + 0.1);
//				} else {
//					tileMap.setScaleX(tileMap.getScaleX() - 0.1);
//					tileMap.setScaleY(tileMap.getScaleY() - 0.1);
//				}
//			}
//		});
//		
//
//		TileLayer walls = (TileLayer) tileMap.getLayerByName("background");
//		List<TileSetElement> elementsInLine = walls.getElementsInLine(new Point2D(0, 0), new Point2D(200, 200));
//		for (TileSetElement element : elementsInLine) {
//			System.out.println(element);
//		}

	}
	
}
