// This entire file is part of my masterpiece.
// Allan Kiplagat

package gamePlayer.guiContainers;

import gamePlayer.mainClasses.guiBuilder.GuiElement;


/**
 * Tag interface for a GuiContainer which hosts GuiElements  
 * Known implementing classes: BottomContainer, CenterContainer, LeftContainer, RightContainer, TopContainer classes
 * @author allankiplagat
 *
 */
public interface GuiContainer extends GuiElement {
    public String myPropertiesPath =  "guiContainers/";   
}