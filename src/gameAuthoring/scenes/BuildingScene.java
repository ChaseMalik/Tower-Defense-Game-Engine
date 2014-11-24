package gameAuthoring.scenes;

import java.util.Observable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class BuildingScene extends Observable {
    
    public static final String STYLESHEET_PACKAGE = "./gameAuthoring/Stylesheets/";
    private Scene myScene;
    private String myTitle;
    protected BorderPane myPane;

    public BuildingScene (BorderPane root, String title) {
        myPane = root;
        myScene = new Scene(root);
        myTitle = title;
        myScene.getStylesheets().add(getStyleSheetFileName()); 
    }
    
    private String getStyleSheetFileName () {
        return STYLESHEET_PACKAGE + myTitle.concat("building").toLowerCase().concat(".css");
    }

    public String getTitle() {
        return myTitle;
    }
    
    public Scene getScene() {
        return myScene;
    }
}
