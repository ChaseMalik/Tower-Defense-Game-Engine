package gamePlayer.guiItems.voogaMenuBar.menuItems;

import gamePlayer.guiItems.voogaMenuBar.VoogaMenuItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

public class ChangeThemeMenuItem extends VoogaMenuItem {

	@Override
	public void initialize() {
		this.setText("Change Theme");
		this.setOnAction(event -> GuiConstants.GUI_MANAGER.changeTheme());
	}

}
