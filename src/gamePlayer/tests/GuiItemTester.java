package gamePlayer.tests;

import gamePlayer.unusedCodeWarehouse.guiItems.store.StoreItemCell;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Just a very simple tester to test any new GUI items
 * 
 * @author brianbolze
 *
 */
public class GuiItemTester extends Application {
	
	Pane myContainer;
	Dimension2D mySize = new Dimension2D(200,200);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		buildContent();
		buildComponent();
		primaryStage.setScene(new Scene(myContainer));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void buildContent() {
		myContainer = new VBox();
		myContainer.setMinSize(mySize.getWidth(),mySize.getHeight());
	}
	
	// Add whatever you want to test here!!!
	private void buildComponent() {
		StoreItemCell cell = new StoreItemCell("Turret");
		cell.initialize(mySize);
		myContainer.getChildren().add(cell.getNode());
	}
}
