package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.actorBuildingScenes.actorListView.EnemySelectionDisplay;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * Scene to build a new tower.
 * @author Austin Kyker
 *
 */
public class TowerBuildingScene extends ActorBuildingScene {

    private static final double ENEMY_DISPLAY_HEIGHT = 110;
    private static final double DRAG_AND_DROP_HEIGHT = 
            AuthorController.SCREEN_HEIGHT - (10 + ENEMY_DISPLAY_HEIGHT);
    private static final String TITLE = "Tower";
    private static final String IMG_DIR = "./src/gameAuthoring/Resources/towerImages/";
    private static final String BEHAVIOR_XML_LOC = "./src/gameAuthoring/Resources/actorBehaviors/TowerBehaviors.xml";

    private List<BaseEnemy> myCreatedEnemies;
    private List<BaseEnemy> myEnemiesTowerCanShoot;
    private List<TowerUpgradeGroup> myTowerUpgradeGroups;
    private EnemySelectionDisplay myEnemySelectionView;
    private TilePane myTilePane;
    private TowerUpgradeGroup myCurrentlySelectedTowerGroup;

    public TowerBuildingScene (BorderPane root, List<BaseEnemy> enemies) {
        super(root, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
        myCreatedEnemies = enemies;
    }

    /**
     * Adds to the bottom of the right pane, a list view all of all possible enemies
     * so the user can select the enemies that the tower can attack. Enemies that
     * are not selected will be immune to the projectiles of the tower.
     */
    @Override
    protected void configureAndDisplayRightPane (Pane rightPane) {
        VBox rightContainer = new  VBox();
        rightPane.setPrefHeight(DRAG_AND_DROP_HEIGHT);
        setupEnemyTowerCanShootSelection();
        rightContainer.getChildren().addAll(rightPane, myEnemySelectionView);
        myPane.setRight(rightContainer);
    }

    private void setupEnemyTowerCanShootSelection () {
        myEnemySelectionView = new EnemySelectionDisplay(myCreatedEnemies);
        myEnemySelectionView.setPrefHeight(ENEMY_DISPLAY_HEIGHT);
        myEnemySelectionView.setOrientation(Orientation.HORIZONTAL); 
        myEnemySelectionView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        myEnemySelectionView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<BaseActor>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends BaseActor> change) {
                myEnemiesTowerCanShoot = myEnemySelectionView.getSelectionModel().getSelectedItems();
            }
        });
    }

    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        BaseTower tower = new BaseTower(iBehaviorMap, myActorImgPath, myActorNameField.getText(), 10, null);
        if(myCurrentlySelectedTowerGroup == null) {
            TowerUpgradeGroup group = new TowerUpgradeGroup(tower);
            myTowerUpgradeGroups.add(group);  
        } 
        else {
            myCurrentlySelectedTowerGroup.addTower(tower);
            myCurrentlySelectedTowerGroup = null;
        }
        redrawTowerDisplay();
    }

    private void redrawTowerDisplay () {
        myTilePane.getChildren().clear();
        for(int i = 0; i < myTowerUpgradeGroups.size(); i++) {
            List<ImageView> upgradeGroupViews = myTowerUpgradeGroups.get(i).fetchImageViews();
            int towersInGroup = myTowerUpgradeGroups.get(i).getNumTowersInGroup();
            final int index = i;
            for(int j = 0; j < upgradeGroupViews.size(); j++) {
                myTilePane.getChildren().add(upgradeGroupViews.get(j));
                if(j >= towersInGroup) {
                    upgradeGroupViews.get(j).setOnMouseClicked(event->handleAddUpgrade(myTowerUpgradeGroups.get(index)));
                }
            }
        }        
    }

    private void handleAddUpgrade (TowerUpgradeGroup groupSelected) {
        myCurrentlySelectedTowerGroup = groupSelected;
        clearFields();
    }

    @Override
    protected void finishBuildingActors() {        
        this.setChanged();
        this.notifyObservers(myTowerUpgradeGroups);
    }

    @Override
    protected void initializeActorsAndBuildActorDisplay () {
        myTowerUpgradeGroups = new ArrayList<TowerUpgradeGroup>();
        myTilePane = new TilePane();
        myTilePane.setPrefWidth(340);
        myTilePane.setPadding(new Insets(5, 0, 5, 0));
        myTilePane.setVgap(10);
        myTilePane.setHgap(10);
        myTilePane.setPrefColumns(3);
        myTilePane.setStyle("-fx-background-color: DAE6F3;"); 
        redrawTowerDisplay();
        myPane.setLeft(myTilePane);
    }
}