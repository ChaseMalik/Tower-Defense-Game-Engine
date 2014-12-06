package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.controllerInterfaces.PathConfiguring;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BackgroundBuilding;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.CurveDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingOptionPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.LineDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.PathBackgroundSelectionPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.SelectComponentPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane.EnemyEndingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane.EnemyStartingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.TowerRegionsPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.StringToImageViewConverter;
import utilities.errorPopup.ErrorPopup;

/**
 * This class allows the user to build a path from a starting location to an 
 * ending location. The scene manages the different BuildingPanes that allow
 * the user different on-click functionality.
 * @author Austin Kyker
 *
 */
public class PathBuildingScene extends BuildingScene implements BackgroundBuilding {

    private static final String SELECTED_CSS_CLASS = "selected";
    public static final int BUILDING_OPTIONS_PADDING = 10;
    public static final double SIDE_PANE_WIDTH = 
            ((AuthorController.SCREEN_WIDTH-BuildingPane.DRAW_SCREEN_WIDTH)/2);
    private static final String TITLE = "Path";

    private Path  myPath;
    private BorderPane myPane;
    private static Group myGroup;

    private PathBackgroundSelectionPane myBackgroundSelectionPane;
    private EnemyStartingLocationsPane myEnemyStartingLocationsPane;
    private EnemyEndingLocationsPane myEnemyEndingLocationsPane;
    private LineDrawingPane myLineDrawingPane;
    private CurveDrawingPane myCurveDrawingPane;
    private SelectComponentPane mySelectionComponentPane;
    private TowerRegionsPane myTowerRegionsPane;
    private BuildingPane myCurrentBuildingPane;

    private BuildingOptionPane myFinishedPathBuildingOptionPane;
    private BuildingOptionPane myResetBuildOptionPane;
    private DefaultMapSelectionPane myDefaultMapSelectionPane;
    private PathConfiguring myPathConfiguringController;
    private VBox myPathBuildingOptionsContainer;
    private Map<BuildingPane, BuildingOptionPane> myBuildingPaneToOptionPaneMap;


    public PathBuildingScene (BorderPane root, PathConfiguring controller) {
        super(root, TITLE);
        myPathConfiguringController = controller;
        myPane = root;
        myGroup = new Group();
        myPath = new Path(myGroup);
        myBuildingPaneToOptionPaneMap = new HashMap<BuildingPane, BuildingOptionPane>();
        createAndDisplayDefaultMapSelectionPane();
        createBuildingPanes();
        createPathBuildingOptions();
        this.getScene().setOnKeyReleased(event->handleKeyPress(event));
        setCurrentBuildingPane(myBackgroundSelectionPane); 
    }

    private void createAndDisplayDefaultMapSelectionPane() {
        myDefaultMapSelectionPane = new DefaultMapSelectionPane((BackgroundBuilding) this);
        myPane.setLeft(myDefaultMapSelectionPane.getDefaultMapsScrollPane());
    }

    private void createBuildingPanes () {
        myBackgroundSelectionPane = new PathBackgroundSelectionPane(myGroup, this);
        myEnemyStartingLocationsPane = new EnemyStartingLocationsPane(myGroup, myPath, this);
        myEnemyEndingLocationsPane = new EnemyEndingLocationsPane(myGroup, myPath, this);
        myLineDrawingPane = new LineDrawingPane(myGroup, myPath);
        myCurveDrawingPane = new CurveDrawingPane(myGroup, myPath);
        mySelectionComponentPane = new SelectComponentPane(myGroup, myPath);
        myTowerRegionsPane = new TowerRegionsPane(myGroup, myPath);
    }

    private void handleKeyPress (KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE){
            List<PathComponent> deletedComponent = myPath.deleteSelectedComponent();
            if(deletedComponent != null) {
                for(PathComponent comp:deletedComponent) {
                    myGroup.getChildren().remove(comp);
                    myGroup.getChildren().removeAll(comp.getExtraNodes());
                }
            }
        }
    }

    /**
     * Shows all of the path building options. The options are line drawing, curve drawing,
     * selection, and finished which will check to see if the path is connected, and if it is,
     * will proceed to the next scene (enemy building)
     */
    private void createPathBuildingOptions () {
        myPathBuildingOptionsContainer = new VBox(BUILDING_OPTIONS_PADDING);
        myPathBuildingOptionsContainer.setPadding(new Insets(BUILDING_OPTIONS_PADDING));
        myPathBuildingOptionsContainer.setPrefWidth(SIDE_PANE_WIDTH);
        myPane.setRight(myPathBuildingOptionsContainer);

        myResetBuildOptionPane = new BuildingOptionPane("Reset");
        myResetBuildOptionPane.setOnMouseReleased(event->resetBuild());

        myFinishedPathBuildingOptionPane = new BuildingOptionPane("Finished");
        myFinishedPathBuildingOptionPane.setOnMouseReleased(event->handleFinishButtonClick());

        myPathBuildingOptionsContainer.getChildren()
        .addAll(myResetBuildOptionPane,
                getBuildingOptionsPane("Line", myLineDrawingPane),
                getBuildingOptionsPane("Curve", myCurveDrawingPane),
                getBuildingOptionsPane("Selection", mySelectionComponentPane),
                getBuildingOptionsPane("Regions", myTowerRegionsPane),
                myFinishedPathBuildingOptionPane);
    }

    private BuildingOptionPane getBuildingOptionsPane (String name, BuildingPane pane) {
        BuildingOptionPane optionsPane = new BuildingOptionPane(name);
        myBuildingPaneToOptionPaneMap.put(pane, optionsPane);
        optionsPane.setOnMouseReleased(event->setCurrentDrawingPane(pane));
        return optionsPane;
    }

    private void resetBuild () {
        myPath.resetPath();
        setCurrentBuildingPane(myBackgroundSelectionPane);        
        deselectOptionsComponents();
    }

    private void handleFinishButtonClick () {
        if(myPath.isCompletedAndRoutesVerified()) {
            myPathConfiguringController.setTowerRegions(myTowerRegionsPane.getBackendTowerRegions());
            myPathConfiguringController.configurePath(myPath);
        }
        else {
            new ErrorPopup("You're route does not go from the starting location to " +
                    "the ending location or your components are not connected " +
                    "together. To see the connectivity of your routes use the " +
                    "selection tool on the right. To start over press the reset " +
                    "button. HINT: build your routes starting at the start " +
                    "locations and then connecting subsequent components until " +
                    "you reach the ending location.");
        }
    }

    public void proceedToLineDrawerModeIfLocationsVerified () {
        if(myPath.endingLocationsConfiguredCorrectly()){
            setCurrentBuildingPane(myLineDrawingPane);
            myBuildingPaneToOptionPaneMap.get(myLineDrawingPane).selectPane();
        }
    }

    private void deselectOptionsComponents() {
        for(Node node:myPathBuildingOptionsContainer.getChildren()){
            node.getStyleClass().remove(SELECTED_CSS_CLASS);
        }
    }

    private boolean canDrawPathComponents() {
        return !(isCurrentPane(myEnemyStartingLocationsPane) || 
                isCurrentPane(myEnemyEndingLocationsPane) ||
                isCurrentPane(myBackgroundSelectionPane));
    }

    private boolean isCurrentPane(BuildingPane pane){
        return myCurrentBuildingPane.equals(pane);
    }

    public void proceedToStartLocationSelection () {
        setCurrentBuildingPane(myEnemyStartingLocationsPane);        
    }

    public void proceedToEndLocationsSelection () {
        if(myPath.startingLocationsConfiguredCorrectly()){
            setCurrentBuildingPane(myEnemyEndingLocationsPane);
        }
    }
    
    /**
     * Drawing panes include tower regions, curve drawing, line drawing,
     * and selection.
     * @param pane
     */
    private void setCurrentDrawingPane (BuildingPane pane) {
        if(!isCurrentPane(pane) && canDrawPathComponents()) {
            deselectOptionsComponents();
            setCurrentBuildingPane(pane);
            myBuildingPaneToOptionPaneMap.get(pane).selectPane();
        }
    }

    /**
     * Include all panes: curve, line, regions, enemy starting and ending
     * locations, background selection, etc.
     * @param nextPane
     */
    public void setCurrentBuildingPane(BuildingPane nextPane) {
        if(myCurrentBuildingPane != null) {
            myCurrentBuildingPane.executeExitFunction();
        }
        myCurrentBuildingPane = nextPane;
        nextPane.executeEnterFunction();
        myPane.getChildren().remove(myPane.getCenter());
        myPane.setCenter(nextPane);
        nextPane.refreshScreen();
    }

    @Override
    public void setBackground (String imageFileName) {
        ImageView imageView = 
                StringToImageViewConverter.getImageView(BuildingPane.DRAW_SCREEN_WIDTH, 
                                                        AuthorController.SCREEN_HEIGHT, 
                                                        imageFileName);
        myGroup.getChildren().add(imageView); 
        myPathConfiguringController.setBackground(imageFileName);     
        proceedToStartLocationSelection();      
    }
}