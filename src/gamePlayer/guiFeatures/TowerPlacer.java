package gamePlayer.guiFeatures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Singleton class to drag towers from the store and place them on the game map
 * 
 * @author brianbolze
 *
 */

public class TowerPlacer {
	
	//private TowerPlacerListener myListener = (GuiConstants.GameManager?????)

    private static TowerPlacer myReference = null;

    public static TowerPlacer getInstance() {
        if (myReference==null) {
            myReference = new TowerPlacer();
        }
        return myReference;
    }

    /**
     * Drags an item and attempts to place it on the map
     * 
     * @param rootNode : The Node to attach the mouseListener to
     * @param itemID : The unique integer identifier of the item (tower) to be placed
     */
    public void placeItem(int itemID, Group rootNode) {

        Circle dragCircle = new Circle(50, Color.RED);
        dragCircle.setOpacity(0.1);

        // TODO : Add the image of the tower to be added to the dragged object
        rootNode.setOnMouseMoved(event -> drag(dragCircle, event.getX(), event.getY()));
        rootNode.setOnMouseReleased(event -> drop(dragCircle, event.getX(), event.getY(), itemID, rootNode));
        rootNode.getChildren().add(dragCircle);
        
    }

    private void drag(Circle node, double X, double Y) {
        node.setTranslateX(X);
        node.setTranslateY(Y);
        if (validPlacement(X,Y)) {
            node.setFill(Color.GREEN);
        } else {
            node.setFill(Color.RED);
        }
    }

    private void drop(Circle node, double X, double Y, int itemID, Group rootNode) {

        if (!validPlacement(X,Y)) { //pass this off to myListener
        	// TODO : Make this an alert message
            System.out.println("Invalid placement");
            return;
        }

        rootNode.setOnMouseMoved(null);
        rootNode.setOnMouseReleased(null);
        node.setVisible(false);

        //myListener.addTower(itemID, new Dimension2D(X,Y));
    }

    private boolean validPlacement(double X, double Y) {
        // TODO: Add the real logic 
    	// return myListener.validPlacement(X,Y) // -- have the GameWorld do this??
        return (X < 250 && X > 50 && Y < 250 && Y > 50);
    }

}
