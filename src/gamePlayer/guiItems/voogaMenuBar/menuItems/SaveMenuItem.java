package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import javafx.scene.control.MenuItem;

public class SaveMenuItem extends MenuItem {
    public SaveMenuItem() {
        this.setText(GuiBuilderConstants.TEXT_GEN.get(GuiText.SAVE));
    }
}
