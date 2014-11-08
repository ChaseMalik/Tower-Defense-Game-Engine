package gameAuthoring.scenes.pathBuilding;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.EnemyEndingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.EnemyStartingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.LineDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.SelectComponentPane;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private SelectComponentPane mySelectionComponentPane;

    private Pane myLinePathOptionPane;
    private Pane myCurvePathOptionPane;   
    private Pane myBuildScreenPane;
    private Pane mySelectComponentOptionPane;


    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        myPath = new Path();
        myGroup = new Group();
        createBuildingPanes();
        setEnemyStartingLocationsSelection();
        createPathBuildingOptions(); 
        this.setOnKeyReleased(event->handleKeyPress(event));
    }
    
    private void createBuildingPanes () {
        //NOT GOOD, MAYBE USE OBSERVABLES INSTEAD?!?!?!?
        myEnemyStartingLocationsPane = new EnemyStartingLocationsPane(myGroup, myPath, this);
        myEnemyEndingLocationsPane = new EnemyEndingLocationsPane(myGroup, myPath, this);
        
        myLineDrawingPane = new LineDrawingPane(myGroup, myPath);
        mySelectionComponentPane = new SelectComponentPane(myGroup, myPath);
    }

    private void setEnemyStartingLocationsSelection () {
        myPane.setLeft(myEnemyStartingLocationsPane);       
    }

    private void handleKeyPress (KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE){
            LinkedList<PathComponent> deletedComponent = myPath.deleteSelectedComponent();
            if(deletedComponent != null)
                myBuildScreenPane.getChildren().removeAll(deletedComponent);
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
    }

    private void setCurveDrawerMode () {
        myCurvePathOptionPane.getStyleClass().add("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected");  
    }

    private void setLineDrawerMode () {
        myLinePathOptionPane.getStyleClass().add("selected");
        myCurvePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected");
    }

    private Pane createPathComponentOption (String componentName) {
        Pane pathComponentPane = new Pane();
        pathComponentPane.setPrefHeight(PATH_COMPONENT_OPTION_HEIGHT);
        pathComponentPane.getChildren().add(new Label(componentName));
        pathComponentPane.getStyleClass().add("pathComponentOption");
        return pathComponentPane;
    }

    public void proceedToEndLocationsSelection () {
        switchBuildingPanes(myEnemyEndingLocationsPane);
    }
    
    public void proceedToLineDrawing() {
        switchBuildingPanes(myLineDrawingPane);
    }
    
    public void switchBuildingPanes(BuildingPane nextPane) {
        myPane.getChildren().remove(myPane.getLeft());
        myPane.setLeft(nextPane);
        nextPane.refreshScreen();
    }


}