package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.pathData.PathDataHolder;
import gameAuthoring.scenes.BuildingScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class PathBuildingScene extends BuildingScene {
    
    private static final String TITLE = "Path Building";
    private static final double DRAW_SCREEN_WIDTH_RATIO = .7;
    private static final double DRAW_SCREEN_WIDTH = 
            AuthorController.SCREEN_WIDTH*DRAW_SCREEN_WIDTH_RATIO;
    private static final double PATH_BUILDING_OPTIONS_WIDTH_RATIO = .3;
    private static final double PATH_BUILDING_OPTIONS_WIDTH = 
            AuthorController.SCREEN_WIDTH*PATH_BUILDING_OPTIONS_WIDTH_RATIO;
    private static final String BUILD_SCREEN_CSS_CLASS = "buildScreen";
    private static final int PATH_COMPONENT_OPTION_HEIGHT = 150;
    
    private PathDataHolder  myPathDataHolder;
    private BorderPane myPane;
    private enum PATH_DRAWING_MODE { OFF, LINE_MODE, CURVE_MODE };
    private PATH_DRAWING_MODE currentDrawingMode;
    
    private Pane myLinePathOptionPane;
    private Pane myCurvePathOptionPane;   
    private Pane myBuildScreenPane;
      
    private Line myLineBeingCreated;

    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        createBuildScreen();
        createPathBuildingOptions();
        myPathDataHolder = new PathDataHolder();
        currentDrawingMode = PATH_DRAWING_MODE.OFF;
        
    }
    
    private void createBuildScreen () {
        myBuildScreenPane = new Pane();
        myBuildScreenPane.setPrefWidth(DRAW_SCREEN_WIDTH);
        myBuildScreenPane.getStyleClass().add(BUILD_SCREEN_CSS_CLASS);
        myBuildScreenPane.setOnMouseClicked(event->handleBuildScreenClick(event));
        myPane.setLeft(myBuildScreenPane);
        myBuildScreenPane.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                if(currentDrawingMode != PATH_DRAWING_MODE.OFF) {
                    if(myLineBeingCreated != null){
                        myLineBeingCreated.setEndX(event.getX());
                        myLineBeingCreated.setEndY(event.getY());
                    }
                }
                  
            }        
        });
    }

    private void handleBuildScreenClick (MouseEvent event) {
        System.out.println(currentDrawingMode == PATH_DRAWING_MODE.LINE_MODE);
        switch(currentDrawingMode) {
            case OFF: break;
            case LINE_MODE:
                if(myLineBeingCreated == null){
                    myLineBeingCreated = new Line();
                    myBuildScreenPane.getChildren().add(myLineBeingCreated);
                    myLineBeingCreated.setStartX(event.getX());
                    myLineBeingCreated.setStartY(event.getY());
                    myLineBeingCreated.setEndX(event.getX());
                    myLineBeingCreated.setEndY(event.getY());
                }
                else {
                    Line tempLine = myLineBeingCreated;
                    myBuildScreenPane.getChildren().remove(myLineBeingCreated);
                    myBuildScreenPane.getChildren().add(tempLine);
                    myLineBeingCreated = null;
                }
                break;
        }
    }

    private void createPathBuildingOptions () {
        VBox pathBuildingOptions = new VBox(10);
        pathBuildingOptions.setPrefWidth(PATH_BUILDING_OPTIONS_WIDTH);
        pathBuildingOptions.setStyle("-fx-background-color: red");
        myPane.setRight(pathBuildingOptions);
        
        myLinePathOptionPane = createPathComponentOption("Line");
        myLinePathOptionPane.setOnMouseClicked(event->setLineDrawerMode());
        
        myCurvePathOptionPane = createPathComponentOption("Curve");
        myCurvePathOptionPane.setOnMouseClicked(event->setCurveDrawerMode());
        
        
        pathBuildingOptions.getChildren().addAll(myLinePathOptionPane,
                                                 myCurvePathOptionPane);
         
    }

    private void setCurveDrawerMode () {
        currentDrawingMode = PATH_DRAWING_MODE.CURVE_MODE;
        myCurvePathOptionPane.getStyleClass().add("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
    }

    private void setLineDrawerMode () {
        currentDrawingMode = PATH_DRAWING_MODE.LINE_MODE;
        myLinePathOptionPane.getStyleClass().add("selected");
        myCurvePathOptionPane.getStyleClass().remove("selected");
    }

    private Pane createPathComponentOption (String componentName) {
        Pane pathComponentPane = new Pane();
        pathComponentPane.setPrefHeight(PATH_COMPONENT_OPTION_HEIGHT);
        pathComponentPane.getChildren().add(new Label(componentName));
        pathComponentPane.getStyleClass().add("pathComponentOption");
        return pathComponentPane;
    }
}