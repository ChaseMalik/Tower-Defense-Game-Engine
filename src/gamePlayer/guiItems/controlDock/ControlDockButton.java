package gamePlayer.guiItems.controlDock;

import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;

public abstract class ControlDockButton extends Button {
    public abstract void initialize(Dimension2D containerSize);
}
