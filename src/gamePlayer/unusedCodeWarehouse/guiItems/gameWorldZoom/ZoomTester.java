package gamePlayer.unusedCodeWarehouse.guiItems.gameWorldZoom;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ZoomTester extends Application {
	
	ZoomWrapper myContainer;
	Group myContent;
	Dimension2D mySize = new Dimension2D(600,400);
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		buildContent();
		buildContainer();
		primaryStage.setScene(new Scene(myContainer));
		primaryStage.show();
	}
	
	private void buildContainer() {
		myContainer = new ZoomWrapper(myContent);
		myContainer.setMinSize(mySize.getWidth(),mySize.getHeight());
	}
	
	private void buildContent() {
		ImageView imageView1 = new ImageView();
		String path1 = "file:./src/gamePlayer/unusedCodeWarehouse/spriteResources/Hulk.jpg";
		imageView1.setImage(new Image(path1, mySize.getWidth(), mySize.getHeight(), false, false));
		myContent = new Group(imageView1);
	}

}
