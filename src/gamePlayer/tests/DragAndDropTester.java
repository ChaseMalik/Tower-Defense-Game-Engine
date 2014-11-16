package gamePlayer.tests;

import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragAndDropTester extends Application {
	
	AnchorPane myContainer;
	Dimension2D mySize = new Dimension2D(900,400);
	Group myStore, myBadSpace, myGoodSpace;
	Node startNode;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		buildContainer();
		buildItem();
		primaryStage.setScene(new Scene(myContainer));
		primaryStage.show();
	}
	
	private void buildContainer() {
		myContainer = new AnchorPane();
		myContainer.setMinSize(mySize.getWidth(),mySize.getHeight());
		myStore = new Group();
		myBadSpace = new Group();
		myGoodSpace = new Group();
		myStore.getChildren().add(new Rectangle(mySize.getWidth()/3, mySize.getHeight(), Color.LIGHTGREY));
		myBadSpace.getChildren().add(new Rectangle(mySize.getWidth()/3, mySize.getHeight(), Color.LIGHTPINK));
		myGoodSpace.getChildren().add(new Rectangle(mySize.getWidth()/3, mySize.getHeight(), Color.LIGHTGREEN));
		
		AnchorPane.setTopAnchor(myStore, 0.);
		AnchorPane.setLeftAnchor(myStore, 0.);
		AnchorPane.setTopAnchor(myBadSpace, 0.);
		AnchorPane.setLeftAnchor(myBadSpace, mySize.getWidth()/3);
		AnchorPane.setTopAnchor(myGoodSpace, 0.);
		AnchorPane.setLeftAnchor(myGoodSpace, 2.*mySize.getWidth()/3);
		
		
		myContainer.getChildren().addAll(myStore,myBadSpace,myGoodSpace);
	}
	
	private void buildItem() {
		startNode = new Circle(50, Color.BLACK);
		startNode.setOnMouseClicked(event -> newDrag(event.getX(), event.getY()));
		startNode.setTranslateX(100);
		startNode.setTranslateY(100);
		myContainer.getChildren().add(startNode);
	}
	
	private void newDrag(double X, double Y) {
		Node draggable = new Circle(40, Color.GREY);
		myContainer.setOnMouseMoved(event -> drag(draggable, event.getX(), event.getY()));
		myContainer.setOnMouseReleased(event -> drop(draggable, event.getX(), event.getY()));
		myContainer.getChildren().add(draggable);
	}
	
	private void drag(Node node, double X, double Y) {
		node.setTranslateX(X);
		node.setTranslateY(Y);
	}
	
	private void drop(Node node, double X, double Y) {
		
		if (X < 2.*mySize.getWidth()/3) {
			System.out.println("Invalid placement");
			return;
		}
		
		myContainer.setOnMouseMoved(null);
		myContainer.setOnMouseReleased(null);
		node.setVisible(false);
		ImageView img = new ImageView();
		img.setImage(getImage("Turret"));
		img.setX(X - img.getImage().getWidth()/2);
		img.setY(Y - img.getImage().getHeight()/2);
		myContainer.getChildren().add(img);
	}
	
	private Image getImage(String name) {

		String imagePath = name + ".png";
		
		return new Image("file:"+imagePath,
				50, 50, false, false);
	
	}
	

}
