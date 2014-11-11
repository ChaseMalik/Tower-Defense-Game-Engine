package gamePlayer.guiItems.all;

import java.util.List;
import gamePlayer.guiItems.GuiItem;
import javafx.scene.layout.Pane;


public class BottomItemPlaceholder extends Pane implements GuiItem {

    @Override
    public void initialize (List<Double> componentSize) {
        this.setPrefSize(componentSize.get(0)*1, componentSize.get(1)*1);
    }
}
