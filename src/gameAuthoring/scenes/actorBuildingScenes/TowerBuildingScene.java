package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.mainclasses.controllerInterfaces.ITowerConfiguring;
import gameAuthoring.scenes.actorBuildingScenes.actorListView.EnemySelectionDisplay;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utilities.JavaFXutilities.numericalTextFields.LabeledNumericalTextField;
import utilities.errorPopup.ErrorPopup;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;


/**
 * Scene to build a new tower.
 * 
 * @author Austin Kyker
 *
 */
public class TowerBuildingScene extends ActorBuildingScene {

    private static final String SELL = "Sell";
    private static final String COST = "Cost";
    private static final double ENEMY_DISPLAY_HEIGHT = 110;
    private static final double DRAG_AND_DROP_HEIGHT = 300;
    private static final String TITLE = "Tower";
    private static final String IMG_DIR = AuthorController.gameDir + "/towerImages/";
    private static final String BEHAVIOR_XML_LOC =
            "./src/gameAuthoring/Resources/actorBehaviors/TowerBehaviors.xml";
    private static final double FIELD_WIDTH = 50;
    protected static final int NUM_UPGRADES_POSSIBLE = 3;

    private List<BaseEnemy> myEnemiesTowerCanShoot;
    private ProjectilePane myProjectilePane;
    private List<TowerUpgradeGroup> myTowerUpgradeGroups;
    private EnemySelectionDisplay myEnemySelectionView;
    private TilePane myTilePane;
    private TowerUpgradeGroup myCurrentlySelectedTowerGroup;
    private ITowerConfiguring myTowerConfiguringController;
    private LabeledNumericalTextField myBuildCostField;
    private LabeledNumericalTextField mySellValueField;

    public TowerBuildingScene (BorderPane root, ITowerConfiguring controller) {
        super(root, TITLE, BEHAVIOR_XML_LOC, IMG_DIR);
        myTowerConfiguringController = controller;
    }

    /**
     * Adds to the bottom of the right pane, a list view all of all possible enemies
     * so the user can select the enemies that the tower can attack. Enemies that
     * are not selected will be immune to the projectiles of the tower.
     */
    @Override
    protected void configureAndDisplayRightPane () {
        VBox container = new VBox();
        myProjectilePane = new ProjectilePane();
        myEnemiesTowerCanShoot = new ArrayList<BaseEnemy>();
        myDragAndDrop.setHeight(DRAG_AND_DROP_HEIGHT);
        setupEnemyTowerCanShootSelection();
        container.getChildren().addAll(myProjectilePane.getNode(),
                                       myDragAndDrop.getPane(),
                                       myEnemySelectionView);
        myPane.setRight(container);
    }

    private void setupEnemyTowerCanShootSelection () {
        myEnemySelectionView =
                new EnemySelectionDisplay(myTowerConfiguringController.fetchEnemies());
        myEnemySelectionView.setPrefHeight(ENEMY_DISPLAY_HEIGHT);
        myEnemySelectionView.setOrientation(Orientation.HORIZONTAL);
        myEnemySelectionView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        myEnemySelectionView.getSelectionModel().getSelectedItems()
                .addListener(new ListChangeListener<BaseActor>() {
                    @Override
                    public void onChanged (ListChangeListener.Change<? extends BaseActor> change) {
                        myEnemiesTowerCanShoot =
                                myEnemySelectionView.getSelectionModel().getSelectedItems();
                    }
                });
    }

    @Override
    protected void makeNewActor (Map<String, IBehavior> iBehaviorMap) {
        BaseTower tower =
                new BaseTower(iBehaviorMap, myActorImgPath,
                              myActorNameField.getText(),
                              myRangeSliderContainer.getSliderValue(),
                              myBuildCostField.getNumberEntered(),
                              mySellValueField.getNumberEntered(),
                              myProjectilePane.makeProjectileInfo(myEnemiesTowerCanShoot));
        if (myCurrentlySelectedTowerGroup == null) {
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
        for (int i = 0; i < myTowerUpgradeGroups.size(); i++) {
            List<ImageView> upgradeGroupViews = myTowerUpgradeGroups.get(i).fetchImageViews();
            int towersInGroup = myTowerUpgradeGroups.get(i).getNumTowersInGroup();
            final int index = i;
            for (int j = 0; j < upgradeGroupViews.size(); j++) {
                myTilePane.getChildren().add(upgradeGroupViews.get(j));
                if (j >= towersInGroup) {
                    upgradeGroupViews.get(j)
                            .setOnMouseClicked(event -> handleAddUpgrade(myTowerUpgradeGroups
                                    .get(index)));
                }
            }
        }
    }

    private void handleAddUpgrade (TowerUpgradeGroup groupSelected) {
        myCurrentlySelectedTowerGroup = groupSelected;
        clearFields();
    }

    @Override
    protected void finishBuildingActors () {
        myTowerConfiguringController.configureTowers(myTowerUpgradeGroups);
    }

    @Override
    protected void initializeActorsAndBuildActorDisplay () {
        myTowerUpgradeGroups = new ArrayList<TowerUpgradeGroup>();
        myTilePane = new TilePane();
        myTilePane.setPrefWidth(340);
        myTilePane.setPadding(new Insets(5, 0, 5, 0));
        myTilePane.setVgap(Constants.SM_PADDING);
        myTilePane.setHgap(Constants.SM_PADDING);
        myTilePane.setPrefColumns(NUM_UPGRADES_POSSIBLE);
        myTilePane.setStyle("-fx-background-color: DAE6F3;");
        redrawTowerDisplay();
        myPane.setLeft(myTilePane);
    }

    @Override
    protected HBox addRequiredNumericalTextFields () {
        HBox fieldsContainer = new HBox(10);
        myBuildCostField = new LabeledNumericalTextField(COST, FIELD_WIDTH);
        mySellValueField = new LabeledNumericalTextField(SELL, FIELD_WIDTH);
        fieldsContainer.getChildren().addAll(myBuildCostField, mySellValueField);
        return fieldsContainer;
    }

    @Override
    public boolean actorSpecificFieldsValid () {
        if(myEnemiesTowerCanShoot.size() == 0) {
            new ErrorPopup(Constants.NO_ENEMY_TOWER_CAN_SHOOT);
            return false;
        }
        return myBuildCostField.isValueEntered() && mySellValueField.isValueEntered() &&
                myProjectilePane.isInfoValid();
    }

    @Override
    protected void clearActorSpecificFields () {
        myBuildCostField.clearField();
        mySellValueField.clearField();
    }
}
