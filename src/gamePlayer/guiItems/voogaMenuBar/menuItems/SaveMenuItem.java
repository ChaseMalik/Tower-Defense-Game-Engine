package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;

public class SaveMenuItem extends VoogaMenuItem {
    
    public void initialize() {
        this.setText(GuiBuilderConstants.TEXT_GEN.get(GuiText.SAVE));
    }
}
