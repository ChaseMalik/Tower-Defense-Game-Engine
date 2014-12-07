package gamePlayer.mainClasses.welcomeScreen.options;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import gamePlayer.mainClasses.welcomeScreen.Options;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PlayerCountOptions extends Options {
    private Button singlePlayerOption;
    private Button multiPlayerOption;

    public PlayerCountOptions() {
        singlePlayerOption = createOptionButton(GuiConstants.TEXT_GEN.get(GuiText.SINGLE_PLAYER));
        multiPlayerOption = createOptionButton(GuiConstants.TEXT_GEN.get(GuiText.MULTI_PLAYER));

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
