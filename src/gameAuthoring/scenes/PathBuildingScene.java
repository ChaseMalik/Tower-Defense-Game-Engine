package gameAuthoring.scenes;

import gameAuthoring.pathData.PathDataHolder;
import javafx.scene.layout.BorderPane;

public class PathBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Path Building";
    
    private PathDataHolder  myPathDataHolder;

    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPathDataHolder = new PathDataHolder();
    }

}
