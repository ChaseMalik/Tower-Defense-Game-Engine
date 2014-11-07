package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class BuildingScene extends Scene {
    
    public static final String STYLESHEET_PACKAGE = "./gameAuthoring/Stylesheets/";
    
    private String myTitle;

    public BuildingScene (BorderPane root, String title) {
        super(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myTitle = title;
        this.getStylesheets().add(getStyleSheetFileName()); 
    }
    
    private String getStyleSheetFileName () {
        return STYLESHEET_PACKAGE + myTitle.replace(" ", "").toLowerCase().concat(".css");
    }

    public String getTitle() {
        return myTitle;
    }
    
    

}
