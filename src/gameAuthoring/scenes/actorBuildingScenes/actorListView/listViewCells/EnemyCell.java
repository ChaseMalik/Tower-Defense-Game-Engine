package gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells;

import gameEngine.actors.BaseEnemy;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Represents an enemy displayed in a list view. The size of the image
 * of the enemy displayed can be modified by the input parameters to the
 * constructor.
 * @author Austin Kyker
 *
 */
public class EnemyCell extends ListCell<BaseEnemy> {

    private double myFitWidth;
    private double myFitHeight;
    
    public EnemyCell(double width, double height) {
        myFitWidth = width;
        myFitHeight = height;
    }

    @Override
    public void updateItem(BaseEnemy item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null) {
            VBox actorCellContainer = new VBox(5);
            actorCellContainer.setAlignment(Pos.CENTER);
            Label actorNameLabel = new Label(item.toString());
            ImageView actorImgView = item.getNode();
            actorImgView.setFitWidth(myFitWidth);
            actorImgView.setFitHeight(myFitHeight);
            actorCellContainer.getChildren().addAll(actorImgView, actorNameLabel);    
            setGraphic(actorCellContainer);
        }
        else {
            setGraphic(null);
        }
    }
}
