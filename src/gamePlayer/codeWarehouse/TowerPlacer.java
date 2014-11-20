package gamePlayer.codeWarehouse;

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
     * @param itemID : Needs to be the Name of the properties file in the spriteResources folder
     */
    public void placeItem(String itemID, Group rootNode) {

        Circle dragCircle = new Circle(50, Color.RED);
        dragCircle.setOpacity(0.1);

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

    private void drop(Circle node, double X, double Y, String itemID, Group rootNode) {

        if (!validPlacement(X,Y)) {
            System.out.println("Invalid placement");
            return;
        }

        rootNode.setOnMouseMoved(null);
        rootNode.setOnMouseReleased(null);
        node.setVisible(false);

        //GuiConstants.GUI_MANAGER.addItem(itemID, new Dimension2D(X,Y));
    }

    private boolean validPlacement(double X, double Y) {
        // TODO: Add the real logic 
        return (X < 250 && X > 50 && Y < 250 && Y > 50);
    }

}
