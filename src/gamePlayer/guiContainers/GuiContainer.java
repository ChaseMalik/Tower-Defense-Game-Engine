package gamePlayer.guiContainers;

import gamePlayer.mainClasses.GuiElement;


/**
 * Tag interface for a GuiContainer which hosts GuiElements  
 * Known implementing classes: BottomContainer, CenterContainer, LeftContainer, RightContainer, TopContainer classes
 * @author allankiplagat
 *
 */
public interface GuiContainer extends GuiElement {
    public static final String myPropertiesPath =  "./src/gamePlayer/properties/guiContainers/";   
}