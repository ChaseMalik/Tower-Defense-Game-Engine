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
import javafx.scene.shape.CubicCurve;
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
    
    private Path  myPath;
    private BorderPane myPane;
    private enum PATH_DRAWING_MODE { SELECT_MODE, LINE_MODE, CURVE_MODE };
    private PATH_DRAWING_MODE myCurrentDrawingMode;
    
    private Pane myLinePathOptionPane;
    private Pane myCurvePathOptionPane;   
    private Pane myBuildScreenPane;
    private Pane mySelectComponentOptionPane;
      
    private PathLine myLineBeingCreated;
    private CubicCurve myCurveBeingCreated;
    
    private double mouseX;
    private double mouseY;

    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        createBuildScreen();
        createPathBuildingOptions();
        myPath = new Path();
        myCurrentDrawingMode = PATH_DRAWING_MODE.SELECT_MODE;  
    }
    
    private void createBuildScreen () {
        myBuildScreenPane = new Pane();
        myBuildScreenPane.setPrefWidth(DRAW_SCREEN_WIDTH);
        myBuildScreenPane.getStyleClass().add(BUILD_SCREEN_CSS_CLASS);
        myBuildScreenPane.setOnMousePressed(event->handleBuildScreenClick(event));
        myPane.setLeft(myBuildScreenPane);
        myBuildScreenPane.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event) {
                if(myCurrentDrawingMode != PATH_DRAWING_MODE.SELECT_MODE) {
                    if(myLineBeingCreated != null){
                        myLineBeingCreated.setEndX(event.getX());
                        myLineBeingCreated.setEndY(event.getY());
                    }
                }
            }        
        });
    }

    private void handleBuildScreenClick (MouseEvent event) {
        System.out.println(myCurrentDrawingMode == PATH_DRAWING_MODE.LINE_MODE);
        switch(myCurrentDrawingMode) {
            case LINE_MODE:
                if(myLineBeingCreated == null){
                    myLineBeingCreated = new PathLine(event.getX(), event.getY());
                    myBuildScreenPane.getChildren().add(myLineBeingCreated);
                    myPath.addPathComponentToPath(myLineBeingCreated);
                }
                else {
                    PathLine tempLine = myLineBeingCreated;
                    myBuildScreenPane.getChildren().remove(myLineBeingCreated);
                    myBuildScreenPane.getChildren().add(tempLine);
                    
                    tempLine.setOnMousePressed(new EventHandler<MouseEvent>(){

                        @Override
                        public void handle (MouseEvent event) {
                            mouseX = event.getSceneX();
                            mouseY = event.getSceneY();                           
                        }
                        
                    });
                    tempLine.setOnMouseDragged(new EventHandler<MouseEvent>(){

                        @Override
                        public void handle (MouseEvent event) {
                            if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
                                double deltaX = event.getSceneX() - mouseX;
                                double deltaY = event.getSceneY() - mouseY;
                                myPath.moveConnectedComponent(tempLine, deltaX, deltaY);
                                mouseX = event.getSceneX();
                                mouseY = event.getSceneY(); 
                            }
                            
                        }
                        
                    });
                    myLineBeingCreated = null;
                }
                break;
            
            default:
           
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
        
        mySelectComponentOptionPane = createPathComponentOption("Selection");
        mySelectComponentOptionPane.setOnMouseClicked(event->setSelectionMode());
        
        
        pathBuildingOptions.getChildren().addAll(myLinePathOptionPane,
                                                 myCurvePathOptionPane,
                                                 mySelectComponentOptionPane);
         
    }

    private void setSelectionMode () {
        myCurrentDrawingMode = PATH_DRAWING_MODE.SELECT_MODE;
        myCurvePathOptionPane.getStyleClass().remove("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().add("selected");
    }

    private void setCurveDrawerMode () {
        myCurrentDrawingMode = PATH_DRAWING_MODE.CURVE_MODE;
        myCurvePathOptionPane.getStyleClass().add("selected");
        myLinePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected");  
    }

    private void setLineDrawerMode () {
        myCurrentDrawingMode = PATH_DRAWING_MODE.LINE_MODE;
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
}