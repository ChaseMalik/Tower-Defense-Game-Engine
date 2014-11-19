package gamePlayer.guiItems.controlDock;

import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;

public abstract class ControlDockButton extends Button {
    public String myPropertiesPath = "./src/gamePlayer/properties/guiItems/controlDockButtons/";
    public abstract void initialize(Dimension2D containerSize);
}
