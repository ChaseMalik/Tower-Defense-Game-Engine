package gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Will represent a cell in the TowerUpgrades listview that
 * allows users to create towers and their upgrades as well as
 * edit and remove them.
 * @author Austin Kyker
 *
 */
public class TowerUpgradesCell extends ListCell<TowerUpgrades> {

    public TowerUpgradesCell() {

    }

    @Override
    public void updateItem(TowerUpgrades item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null) {
            HBox box = new HBox(10);
            for(int i = 0; i < item.getTowers().length; i++){
                VBox actorCellContainer = new VBox(5);
                actorCellContainer.setAlignment(Pos.CENTER);
                Label actorNameLabel = new Label(item.toString());
                ImageView actorImgView = new ImageView(item.getTowers()[i].getNode().getImage()); 
                actorCellContainer.getChildren().addAll(actorImgView, actorNameLabel);    
                setGraphic(actorCellContainer);
                box.getChildren().add(actorCellContainer);
            }
        }
        else {
            setGraphic(null);
        }
    }
}
