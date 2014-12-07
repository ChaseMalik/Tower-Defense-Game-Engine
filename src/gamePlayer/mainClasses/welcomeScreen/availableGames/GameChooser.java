package gamePlayer.mainClasses.welcomeScreen.availableGames;

import gamePlayer.mainClasses.welcomeScreen.WelcomeScreen;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class GameChooser extends ScrollPane {
    private VBox myBox;
    public static final double WIDTH = WelcomeScreen.PANE_WIDTH;
    public static final double HEIGHT = WelcomeScreen.PANE_HEIGHT;
    
    public GameChooser (String directory) {        
        myBox = new VBox();
        myBox.setPrefSize(WIDTH,HEIGHT);
        loadGameDescriptions(directory);
        this.setContent(myBox);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);    
    }
    
    private void loadGameDescriptions (String directory) {
        GameDescriptionLoader loader = new GameDescriptionLoader();      
        myBox.getChildren().addAll(loader.getDescriptions(directory));
    }
}
