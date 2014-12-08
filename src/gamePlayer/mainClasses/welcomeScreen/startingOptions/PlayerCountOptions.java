package gamePlayer.mainClasses.welcomeScreen.startingOptions;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PlayerCountOptions extends Options {
    private Button singlePlayerOption;
    private Button multiPlayerOption;

    public PlayerCountOptions() {
        singlePlayerOption = createOptionButton(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.SINGLE_PLAYER));
        multiPlayerOption = createOptionButton(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.MULTI_PLAYER));

        this.getChildren().add(singlePlayerOption);
        this.getChildren().add(multiPlayerOption);
    }

    public Node getSinglePlayerOption () {
        return singlePlayerOption;
    }

    public Node getMultiPlayerOption () {
        return multiPlayerOption;
    }
}
