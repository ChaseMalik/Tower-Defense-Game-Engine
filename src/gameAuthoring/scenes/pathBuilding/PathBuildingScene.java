package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.pathData.PathDataHolder;
import gameAuthoring.scenes.BuildingScene;
import javafx.scene.control.Label;
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
    
    private PathDataHolder  myPathDataHolder;
    private BorderPane myPane;
    private enum PATH_DRAWING_MODE { OFF, LINE_MODE, CURVE_MODE };
    private PATH_DRAWING_MODE currentDrawingMode;

    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        createBuildScreen();
        createPathBuildingOptions();
        myPathDataHolder = new PathDataHolder();
        currentDrawingMode = PATH_DRAWING_MODE.OFF;
    }

    private void createPathBuildingOptions () {
        VBox pathBuildingOptions = new VBox(10);
        pathBuildingOptions.setPrefWidth(PATH_BUILDING_OPTIONS_WIDTH);
        pathBuildingOptions.setStyle("-fx-background-color: red");
        myPane.setRight(pathBuildingOptions);
        
        Pane linePathComponentOption = createPathComponentOption("Line");
        linePathComponentOption.setOnMouseClicked(event->setLineDrawerMode());
        
        Pane curvePathComponentOption = createPathComponentOption("Curve");
        curvePathComponentOption.setOnMouseClicked(event->setCurveDrawerMode());
        
        
        pathBuildingOptions.getChildren().addAll(linePathComponentOption,
                                                 curvePathComponentOption);
         
    }

    private Pane createPathComponentOption (String componentName) {
        Pane pathComponentPane = new Pane();
        pathComponentPane.setPrefHeight(PATH_COMPONENT_OPTION_HEIGHT);
        pathComponentPane.getChildren().add(new Label(componentName));
        pathComponentPane.getStyleClass().add("pathComponentOption");
        return pathComponentPane;
    }

    private void createBuildScreen () {
        VBox buildScreen = new VBox();
        buildScreen.setPrefWidth(DRAW_SCREEN_WIDTH);
        buildScreen.getStyleClass().add(BUILD_SCREEN_CSS_CLASS);
        myPane.setLeft(buildScreen);
    }

}
