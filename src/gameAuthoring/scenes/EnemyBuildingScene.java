package gameAuthoring.scenes;

import gameAuthoring.actorData.actorDataHolders.EnemyDataHolder;
import javafx.scene.layout.BorderPane;

public class EnemyBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Enemy Building";
    
    private EnemyDataHolder myEnemyDataHolder;

    public EnemyBuildingScene (BorderPane root) {
        super(root, TITLE);
        myEnemyDataHolder = new EnemyDataHolder();
    }

}
