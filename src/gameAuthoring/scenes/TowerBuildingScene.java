package gameAuthoring.scenes;

import gameAuthoring.actorData.actorDataHolders.TowerDataHolder;
import javafx.scene.layout.BorderPane;

public class TowerBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Tower Building";
    
    private TowerDataHolder myTowerDataHolder;

    public TowerBuildingScene (BorderPane root) {
        super(root, TITLE);
        myTowerDataHolder = new TowerDataHolder();
    }

}
