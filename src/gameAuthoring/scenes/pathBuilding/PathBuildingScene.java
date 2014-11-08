package gameAuthoring.scenes.pathBuilding;

import java.util.LinkedList;
import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import javafx.event.EventHandler;
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
    private static final double DRAW_SCREEN_WIDTH_RATIO = .7;
    private static final double DRAW_SCREEN_WIDTH = 
            AuthorController.SCREEN_WIDTH*DRAW_SCREEN_WIDTH_RATIO;
    private static final double PATH_BUILDING_OPTIONS_WIDTH_RATIO = .3;
    private static final double PATH_BUILDING_OPTIONS_WIDTH = 
            AuthorController.SCREEN_WIDTH*PATH_BUILDING_OPTIONS_WIDTH_RATIO;
    private static final String BUILD_SCREEN_CSS_CLASS = "buildScreen";
    private static final int PATH_COMPONENT_OPTION_HEIGHT = 150;
    private static final double MIN_LINE_LENGTH = 30;

    private Path  myPath;
    private BorderPane myPane;
    private enum PATH_DRAWING_MODE { DRAW_STARTS, DRAW_ENDS, SELECT_MODE, LINE_MODE, CURVE_MODE };
    private PATH_DRAWING_MODE myCurrentDrawingMode;

    private Pane myLinePathOptionPane;
    private Pane myCurvePathOptionPane;   
    private Pane myBuildScreenPane;
    private Pane mySelectComponentOptionPane;

    private PathLine myLineBeingCreated;

    private Button mySaveStartsButton;
    private Button mySaveEndsButton;
    private Label mySaveStartsLabel;
    private Label mySaveEndsLabel;

    private double mouseX;
    private double mouseY;

    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        this.setOnKeyReleased(event->handleKeyPress(event));
        createBuildScreen();
        createPathBuildingOptions();
        myPath = new Path();
        myCurrentDrawingMode = PATH_DRAWING_MODE.DRAW_STARTS;  
    }

    private void createBuildScreen () {
        myBuildScreenPane = new Pane();
        myBuildScreenPane.setPrefWidth(DRAW_SCREEN_WIDTH);
        myBuildScreenPane.getStyleClass().add(BUILD_SCREEN_CSS_CLASS);
        myBuildScreenPane.setOnMousePressed(event->handleBuildScreenClick(event));
        myPane.setLeft(myBuildScreenPane);

        mySaveStartsButton = new Button("Set Start Locations");
        mySaveStartsButton.setOnAction(event->proceedToDrawingEnds());
        mySaveStartsLabel = new Label("Click to add start locations");
        mySaveStartsLabel.setLayoutX(190);
        mySaveStartsLabel.setLayoutY(270);
        mySaveStartsButton.setLayoutX(200);
        mySaveStartsButton.setLayoutY(300);

        myBuildScreenPane.getChildren().addAll(mySaveStartsLabel, mySaveStartsButton);

        mySaveEndsButton = new Button("Set End Locations");
        mySaveEndsButton.setOnAction(event->proceedToDrawLines());
        mySaveEndsLabel = new Label("Click to add end locations");
        mySaveEndsLabel.setLayoutX(190);
        mySaveEndsLabel.setLayoutY(270);
        mySaveEndsButton.setLayoutX(200);
        mySaveEndsButton.setLayoutY(300);

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


    private void handleKeyPress (KeyEvent event) {
        System.out.println("keypressed");
        if(event.getCode() == KeyCode.DELETE){
            LinkedList<PathComponent> deletedComponent = myPath.deleteSelectedComponent();
            if(deletedComponent != null)
                myBuildScreenPane.getChildren().removeAll(deletedComponent);
        }
    }

    private void proceedToDrawLines () {
        if(myPath.endingLocationsConfiguredCorrectly()){
            myBuildScreenPane.getChildren().removeAll(mySaveEndsLabel, mySaveEndsButton);
            this.setLineDrawerMode();
        }
    }

    private void proceedToDrawingEnds () {
        if(myPath.startingLocationsConfiguredCorrectly()){
            myBuildScreenPane.getChildren().removeAll(mySaveStartsLabel, mySaveStartsButton);
            myBuildScreenPane.getChildren().addAll(mySaveEndsLabel, mySaveEndsButton);
            myCurrentDrawingMode = PATH_DRAWING_MODE.DRAW_ENDS;
        }
    }

    private void handleBuildScreenClick (MouseEvent event) {
        switch(myCurrentDrawingMode) {
            case DRAW_STARTS:
                StartingLocation createdStartingLoc = myPath.addStartingLocation(event.getSceneX(), event.getSceneY());
                if(createdStartingLoc != null){
                    myBuildScreenPane.getChildren().add(createdStartingLoc);
                }
                break;
            case DRAW_ENDS:
                EndingLocation createdEndingLoc = myPath.addEndingLocation(event.getSceneX(), event.getSceneY());
                if(createdEndingLoc != null){
                    myBuildScreenPane.getChildren().add(createdEndingLoc);
                }
                break;
            case LINE_MODE:
                if(myLineBeingCreated == null){
                    myLineBeingCreated = new PathLine(event.getX(), event.getY());
                    draw(myLineBeingCreated);
                    myPath.addPathComponentToPath(myLineBeingCreated);
                }
                else {
                    myBuildScreenPane.getChildren().remove(myLineBeingCreated);
                    if(myLineBeingCreated.getLength() > MIN_LINE_LENGTH){
                        PathLine tempLine = myLineBeingCreated;
                        draw(tempLine);
                        myPath.tryToConnectComponentEndToEndLocation(tempLine);
                        tempLine.setOnMousePressed(new EventHandler<MouseEvent>(){

                            @Override
                            public void handle (MouseEvent event) {
                                if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
                                    mouseX = event.getSceneX();
                                    mouseY = event.getSceneY();   
                                    myPath.setSelectedComponent(tempLine);
                                }
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
                        tempLine.setOnMouseReleased(new EventHandler<MouseEvent>(){

                            @Override
                            public void handle (MouseEvent event) {
                                if(myCurrentDrawingMode == PATH_DRAWING_MODE.SELECT_MODE){
                                    myPath.tryToConnectComponents(tempLine);
                                }
                            }
                        });
                    }
                    myLineBeingCreated = null;
                }
                break;

            default:

        }
    }

    private void draw (PathComponent comp) {
       myBuildScreenPane.getChildren().add(0, (Node) comp);
        
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