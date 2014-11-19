package gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells;

import gameEngine.actors.BaseActor;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ActorCell extends ListCell<BaseActor> {

    private double myFitWidth;
    private double myFitHeight;
    
    public ActorCell(double width, double height) {
        myFitWidth = width;
        myFitHeight = height;
    }

    @Override
    public void updateItem(BaseActor item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null && !empty) {
            VBox actorCellContainer = new VBox(5);
            actorCellContainer.setAlignment(Pos.CENTER);
            Label actorNameLabel = new Label(item.toString());
            ImageView actorImgView = new ImageView(item.getImage()); 
            actorImgView.setFitWidth(myFitWidth);
            actorImgView.setFitHeight(myFitHeight);
            actorCellContainer.getChildren().addAll(actorImgView, actorNameLabel);    
            setGraphic(actorCellContainer);
        }
    }
}
