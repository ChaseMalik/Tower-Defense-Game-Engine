package gamePlayer.guiItems.all;

import gamePlayer.guiItems.GuiItem;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;


public class CenterItemPlaceholder extends Pane implements GuiItem {

    @Override
    public void initialize (List<Double> componentSize) {
        this.setPrefSize(componentSize.get(0)*1, componentSize.get(1)*1);
        this.setStyle("-fx-background-color: yellow;");
    }

    @Override
    public Node getNode () {
        return this;
    }
}
