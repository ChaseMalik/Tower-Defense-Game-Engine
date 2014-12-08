package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.guiItems.voogaMenuBar.VoogaMenuItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;

public class ReplayMenuItem extends VoogaMenuItem {
    @Override
    public void initialize() {
        this.textProperty().bind(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.REPLAY));
        this.setOnAction(event->GuiConstants.GUI_MANAGER.replayGame());
    }

}
