package gameAuthoring.scenes;

import java.util.Observable;
import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class BuildingScene extends Observable {
    
    public static final String STYLESHEET_PACKAGE = "./gameAuthoring/Stylesheets/";
    private Scene myScene;
    private String myTitle;

    public BuildingScene (BorderPane root, String title) {
        myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myTitle = title;
        myScene.getStylesheets().add(getStyleSheetFileName()); 
    }
    
    private String getStyleSheetFileName () {
        return STYLESHEET_PACKAGE + myTitle.replace(" ", "").toLowerCase().concat(".css");
    }

    public String getTitle() {
        return myTitle;
    }
    
    public Scene getScene() {
        return myScene;
    }
}
