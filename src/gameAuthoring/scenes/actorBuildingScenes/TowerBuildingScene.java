package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.actorBuildingScenes.actorListView.EnemySelectionDisplay;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TowerBuildingScene extends ActorBuildingScene {
    
    private static final double ENEMY_DISPLAY_HEIGHT = 110;
    private static final double DRAG_AND_DROP_HEIGHT = 
            AuthorController.SCREEN_HEIGHT - (10 + ENEMY_DISPLAY_HEIGHT);
    private static final String TITLE = "Tower";
    private static final String IMG_DIR = "./src/gameAuthoring/Resources/towerImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/TowerBehaviors.xml";

    private List<BaseActor> myCreatedEnemies;
    private List<BaseActor> myEnemiesTowerCanShoot;
    private EnemySelectionDisplay myEnemySelectionView;
    
    public TowerBuildingScene (BorderPane root, List<BaseActor> enemies, 
                               List<BackendRoute> enemyRoutes) {
        super(root, enemyRoutes, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
        myCreatedEnemies = enemies;
    }
    
    @Override
    protected void configureAndDisplayRightPane (Pane rightPane) {
        VBox rightContainer = new  VBox();
        myEnemySelectionView = new EnemySelectionDisplay(myCreatedEnemies);
        rightPane.setPrefHeight(DRAG_AND_DROP_HEIGHT);
        myEnemySelectionView.setPrefHeight(ENEMY_DISPLAY_HEIGHT);
        myEnemySelectionView.setOrientation(Orientation.HORIZONTAL); 
        myEnemySelectionView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        myEnemySelectionView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<BaseActor>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends BaseActor> change) {
                myEnemiesTowerCanShoot = myEnemySelectionView.getSelectionModel().getSelectedItems();
                System.out.println(myEnemiesTowerCanShoot.size());
            }
        });
        rightContainer.getChildren().addAll(rightPane, myEnemySelectionView);
        myPane.setRight(rightContainer);
    }
    
    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        myActors.add(new BaseActor(iBehaviorMap,
                                   myActorImage,
                                   myActorNameField.getText()));       
    }
}