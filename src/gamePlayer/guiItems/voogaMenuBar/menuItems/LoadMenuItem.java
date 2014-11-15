package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import javafx.scene.control.MenuItem;

public class LoadMenuItem extends MenuItem {
    public LoadMenuItem() {
        this.setText(GuiBuilderConstants.TEXT_GEN.get(GuiText.LOAD));
    }
}
