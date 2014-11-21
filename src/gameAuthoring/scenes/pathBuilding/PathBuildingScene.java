package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.CurveDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.DrawingComponentOptionPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.LineDrawingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.PathBackgroundSelectionPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.SelectComponentPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane.EnemyEndingLocationsPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.locationPane.EnemyStartingLocationsPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.Path;
import gameAuthoring.scenes.pathBuilding.pathComponents.PathComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utilities.errorPopup.ErrorPopup;

public class PathBuildingScene extends BuildingScene implements Observer {

    public static final int BUILDING_OPTIONS_PADDING = 10;
    private static final String TITLE = "Path";
    public static final double PATH_BUILDING_OPTIONS_WIDTH_RATIO = 
            1 - BuildingPane.DRAW_SCREEN_WIDTH_RATIO - DefaultMapSelectionPane.SCREEN_WIDTH_RATIO;
    public static final double PATH_BUILDING_OPTIONS_WIDTH = 
            AuthorController.SCREEN_WIDTH * PATH_BUILDING_OPTIONS_WIDTH_RATIO;

    private Path  myPath;
    private BorderPane myPane;
    private static Group myGroup;

    private PathBackgroundSelectionPane myBackgroundSelectionPane;
    private EnemyStartingLocationsPane myEnemyStartingLocationsPane;
    private EnemyEndingLocationsPane myEnemyEndingLocationsPane;
    private LineDrawingPane myLineDrawingPane;
    private CurveDrawingPane myCurveDrawingPane;
    private SelectComponentPane mySelectionComponentPane;
    private BuildingPane myCurrentBuildingPane;

    private VBox myLinePathOptionPane;
    private VBox myCurvePathOptionPane;   
    private VBox mySelectComponentOptionPane;
    private VBox myFinishedPathBuildingOptionPane;
    private DefaultMapSelectionPane myDefaultMapSelectionPane;


    public PathBuildingScene (BorderPane root) {
        super(root, TITLE);
        myPane = root;
        myGroup = new Group();
        myPath = new Path(myGroup);
        createAndDisplayDefaultMapSelectionPane();
        createBuildingPanes();
        createPathBuildingOptions();
        this.getScene().setOnKeyReleased(event->handleKeyPress(event));
        setCurrentBuildingPane(myBackgroundSelectionPane); 
    }

    private void createAndDisplayDefaultMapSelectionPane() {
        myDefaultMapSelectionPane = new DefaultMapSelectionPane();
        myDefaultMapSelectionPane.addObserver(this);
        myPane.setLeft(myDefaultMapSelectionPane.getDefaultMapsScrollPane());
    }

    private void createBuildingPanes () {
        myBackgroundSelectionPane = new PathBackgroundSelectionPane(myGroup, this);
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
        VBox pathBuildingOptions = new VBox(BUILDING_OPTIONS_PADDING);
        pathBuildingOptions.setPadding(new Insets(BUILDING_OPTIONS_PADDING));
        pathBuildingOptions.setPrefWidth(PATH_BUILDING_OPTIONS_WIDTH);
        myPane.setRight(pathBuildingOptions);

        myLinePathOptionPane = new DrawingComponentOptionPane("Line");
        myLinePathOptionPane.setOnMouseReleased(event->setCurrentDrawingPane(myLineDrawingPane));

        myCurvePathOptionPane = new DrawingComponentOptionPane("Curve");
        myCurvePathOptionPane.setOnMouseReleased(event->setCurrentDrawingPane(myCurveDrawingPane));

        mySelectComponentOptionPane = new DrawingComponentOptionPane("Selection");
        mySelectComponentOptionPane.setOnMouseReleased(event->setSelectionMode());

        myFinishedPathBuildingOptionPane = new DrawingComponentOptionPane("Finished");
        myFinishedPathBuildingOptionPane.setOnMouseReleased(event->handleFinishButtonClick());

        pathBuildingOptions.getChildren().addAll(myLinePathOptionPane, myCurvePathOptionPane,
                                                 mySelectComponentOptionPane, myFinishedPathBuildingOptionPane);
    }

    private void handleFinishButtonClick () {
        if(myPath.isCompletedAndRoutesVerified()) {
            this.setChanged();
            this.notifyObservers(myPath);
        }
    }

    private void setSelectionMode () {
        if(!isCurrentPane(mySelectionComponentPane) && canDrawPathComponents()) {
            mySelectionComponentPane.addListenersToComponents();
            setCurrentDrawingPane(mySelectionComponentPane);
        }
    }

    private void setCurrentDrawingPane (BuildingPane pane) {
        if(!isCurrentPane(pane) && canDrawPathComponents()) {
            deselectOptionsComponents();
            setCurrentBuildingPane(pane);
        }
    }
    
    public void proceedToLineDrawerModeIfLocationsVerified () {
        if(myPath.endingLocationsConfiguredCorrectly()){
            myLinePathOptionPane.getStyleClass().add("selected");
            setCurrentBuildingPane(myLineDrawingPane);
        }
    }
   
    private void deselectOptionsComponents() {
        myLinePathOptionPane.getStyleClass().remove("selected");
        myCurvePathOptionPane.getStyleClass().remove("selected");
        mySelectComponentOptionPane.getStyleClass().remove("selected");
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

    public void setCurrentBuildingPane(BuildingPane nextPane) {
        myCurrentBuildingPane = nextPane;
        myPane.getChildren().remove(myPane.getCenter());
        myPane.setCenter(nextPane);
        nextPane.refreshScreen();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.equals(myDefaultMapSelectionPane)){
            if(isCurrentPane(myBackgroundSelectionPane)){
                try {
                    ImageView imageView = new ImageView();
                    Image image = new Image(new FileInputStream((File) arg), BuildingPane.DRAW_SCREEN_WIDTH, 
                                            AuthorController.SCREEN_HEIGHT, false, true);
                    imageView.setImage(image);
                    myGroup.getChildren().add(imageView);   
                    proceedToStartLocationSelection();
                } catch (FileNotFoundException e) {
                    new ErrorPopup("Image file could not be found.");
                }
            }
        }
    }
}