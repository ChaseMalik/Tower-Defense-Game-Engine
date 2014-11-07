package gameAuthoring.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class BuildingScene extends Scene {
    
    private String myTitle;

    public BuildingScene (BorderPane root, String title) {
        super(root);
        myTitle = title;
    }
    
    public String getTitle() {
        return myTitle;
    }
    
    

}
