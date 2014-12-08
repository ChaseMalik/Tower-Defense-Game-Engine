package gamePlayer.mainClasses.welcomeScreen.startingOptions;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class MultiPlayerOptions extends Options {
    private Button newGameOption;
    private Button joinGameOption;

    public MultiPlayerOptions() {
        newGameOption = createOptionButton(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.NEW_GAME));
        
        joinGameOption = createOptionButton(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.JOIN_GAME));

        this.getChildren().add(newGameOption);
        this.getChildren().add(joinGameOption);
    }

    public Node getNewGameOption () {
        return newGameOption;
    }

    public Node getJoinGameOption () {
        return joinGameOption;
    }
}
