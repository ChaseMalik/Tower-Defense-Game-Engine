package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.CurveDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.EnemyEndingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.EnemyStartingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.LineDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.SelectComponentPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PathBuildingScene extends BuildingScene {

    private static final String TITLE = "Path Building";
    private static final double PATH_BUILDING_OPTIONS_WIDTH_RATIO = .3;
    private static final double PATH_BUILDING_OPTIONS_WIDTH = 
            AuthorController.SCREEN_WIDTH * PATH_BUILDING_OPTIONS_WIDTH_RATIO;
    private static final int PATH_COMPONENT_OPTION_HEIGHT = 150;

    private Path  myPath;
    private BorderPane myPane;
    private static Group myGroup;

    private EnemyStartingLocationsPane myEnemyStartingLocationsPane;
    private EnemyEndingLocationsPane myEnemyEndingLocationsPane;
    private LineDrawingPane myLineDrawingPane;
    private CurveDrawingPane myCurveDrawingPane;
    private SelectComponentPane mySelectionComponentPane;
    private BuildingPane myCurrentBuildingPane;

    private Pane myLinePathOptionPane;
    private Pane myCurvePathOptionPane;   
    private Pane mySelectComponentOptionPane;


    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        myGroup = new Group();
        myPath = new Path(myGroup);
        createBuildingPanes();
        createPathBuildingOptions();
        setOnKeyReleased(event->handleKeyPress(event));
        setCurrentBuildingPane(myEnemyStartingLocationsPane); 
    }

    private void createBuildingPanes () {
        //NOT GOOD, MAYBE USE OBSERVABLES INSTEAD?!?!?!?
        myEnemyStartingLocationsPane = new EnemyStartingLocationsPane(myGroup, myPath, this);
        myEnemyEndingLocationsPane = new EnemyEndingLocationsPane(myGroup, myPath, this);
        myLineDrawingPane = new LineDrawingPane(myGroup, myPath);
        myCurveDrawingPane = new CurveDrawingPane(myGroup, myPath);
        mySelectionComponentPane = new SelectComponentPane(myGroup, myPath);
    }

    private void handleKeyPress (KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE){
            List<PathComponent> deletedComponent = myPath.deleteSelectedComponent();
            if(deletedComponent != null)
                myCurrentBuildingPane.removeConnectedComponentFromScreen(deletedComponent);
        }
    }


    private void createPathBuildingOptions () {
        VBox pathBuildingOptions = new VBox(10);
        pathBuildingOptions.setPrefWidth(PATH_BUILDING_OPTIONS_WIDTH);
        myPane.setRight(pathBuildingOptions);

        myLinePathOptionPane = createPathComponentOption("Line");
        myLinePathOptionPane.setOnMouseClicked(event->setLineDrawerMode());

        myCurvePathOptionPane = createPathComponentOption("Curve");
        myCurvePathOptionPane.setOnMouseClicked(event->setCurveDrawerMode());

        mySelectComponentOptionPane = createPathComponentOption("Selection");
        mySelectComponentOptionPane.setOnMouseClicked(event->setSelectionMode());

        pathBuildingOptions.getChildren().addAll(myLinePathOptionPane,
                                                 myCurvePathOptionPane,
                                                 mySelectComponentOptionPane);
    }

    private void setSelectionMode () {
        myCurvePathOptionPane.getStyleClass().remove("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().add("selected");
        mySelectionComponentPane.addListenersToComponents();
        setCurrentBuildingPane(mySelectionComponentPane);
    }

    private void setCurveDrawerMode () {
        myCurvePathOptionPane.getStyleClass().add("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected"); 
        setCurrentBuildingPane(myCurveDrawingPane);
    }

    public void setLineDrawerMode () {
        myLinePathOptionPane.getStyleClass().add("selected");
        myCurvePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected");
        setCurrentBuildingPane(myLineDrawingPane);
    }

    private Pane createPathComponentOption (String componentName) {
        Pane pathComponentPane = new Pane();
        pathComponentPane.setPrefHeight(PATH_COMPONENT_OPTION_HEIGHT);
        pathComponentPane.getChildren().add(new Label(componentName));
        pathComponentPane.getStyleClass().add("pathComponentOption");
        return pathComponentPane;
    }

    public void proceedToEndLocationsSelection () {
        if(myPath.startingLocationsConfiguredCorrectly()){
            setCurrentBuildingPane(myEnemyEndingLocationsPane);
        }
    }
    
    public void proceedToLineDrawerMode () {
        if(myPath.endingLocationsConfiguredCorrectly()){
            setLineDrawerMode();
        }
    }

    public void setCurrentBuildingPane(BuildingPane nextPane) {
        myCurrentBuildingPane = nextPane;
        myPane.getChildren().remove(myPane.getLeft());
        myPane.setLeft(nextPane);
        nextPane.refreshScreen();
    }

}