package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.mainClasses.guiBuilder.GuiBuilderConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;

public class LoadMenuItem extends VoogaMenuItem {
        
    public void initialize() {
        this.setText(GuiBuilderConstants.TEXT_GEN.get(GuiText.LOAD));
    }
}
