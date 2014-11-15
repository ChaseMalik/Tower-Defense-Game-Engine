package gameAuthoring.scenes.pathBuilding;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.CurveDrawingPane;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utilities.ErrorPopup;

public class PathBuildingScene extends BuildingScene implements Observer{

	private static final int BUILDING_OPTIONS_PADDING = 10;
	private static final String TITLE = "Path Building";
	private static final String DRAWING_OPTIONS_IMG_DIR = 
			"./src/gameAuthoring/Resources/PathDrawingOptionsImages/";
	private static final double PATH_BUILDING_OPTIONS_WIDTH_RATIO = 
			1 - BuildingPane.DRAW_SCREEN_WIDTH_RATIO - DefaultMapSelectionPane.SCREEN_WIDTH_RATIO;
	private static final double PATH_BUILDING_OPTIONS_WIDTH = 
			AuthorController.SCREEN_WIDTH * PATH_BUILDING_OPTIONS_WIDTH_RATIO;
	private static final double OPTIONS_IMAGE_WIDTH = PATH_BUILDING_OPTIONS_WIDTH - 3*BUILDING_OPTIONS_PADDING;
	private static final int OPTIONS_IMAGE_HEIGHT = 103;

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
		createDefaultMapSelectionPane();
		createBuildingPanes();
		createPathBuildingOptions();
		this.getScene().setOnKeyReleased(event->handleKeyPress(event));
		setCurrentBuildingPane(myBackgroundSelectionPane); 
	}

	private void createDefaultMapSelectionPane() {

		myDefaultMapSelectionPane = new DefaultMapSelectionPane();
		myDefaultMapSelectionPane.addObserver(this);
		myPane.setLeft(myDefaultMapSelectionPane.getDefaultMapsScrollPane());


	}

	private void createBuildingPanes () {
		//NOT GOOD, MAYBE USE OBSERVABLES INSTEAD?!?!?!?
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
		pathBuildingOptions.setPadding(new Insets(BUILDING_OPTIONS_PADDING, BUILDING_OPTIONS_PADDING, 
				BUILDING_OPTIONS_PADDING, 0));
		pathBuildingOptions.setPrefWidth(PATH_BUILDING_OPTIONS_WIDTH);
		myPane.setRight(pathBuildingOptions);

		myLinePathOptionPane = createPathComponentOption("Line");
		myLinePathOptionPane.setOnMouseClicked(event->setLineDrawerMode());

		myCurvePathOptionPane = createPathComponentOption("Curve");
		myCurvePathOptionPane.setOnMouseClicked(event->setCurveDrawerMode());

		mySelectComponentOptionPane = createPathComponentOption("Selection");
		mySelectComponentOptionPane.setOnMouseClicked(event->setSelectionMode());

		myFinishedPathBuildingOptionPane = createPathComponentOption("Finished");
		myFinishedPathBuildingOptionPane.setOnMouseClicked(event->handleFinishButtonClick());


		pathBuildingOptions.getChildren().addAll(myLinePathOptionPane,
				myCurvePathOptionPane,
				mySelectComponentOptionPane,
				myFinishedPathBuildingOptionPane);
	}

	private void handleFinishButtonClick () {
		if(myPath.isCompletedAndRoutesVerified()) {
			this.setChanged();
			this.notifyObservers(myPath);
		}
	}

	private void setSelectionMode () {
		if(!isCurrentPane(mySelectionComponentPane) && canDrawComponents()) {
			myCurvePathOptionPane.getStyleClass().remove("selected");
			myLinePathOptionPane.getStyleClass().remove("selected");
			mySelectComponentOptionPane.getStyleClass().add("selected");
			mySelectionComponentPane.addListenersToComponents();
			setCurrentBuildingPane(mySelectionComponentPane);
		}
	}

	private void setCurveDrawerMode () {
		if(!isCurrentPane(myCurveDrawingPane) && canDrawComponents()) {
			myCurvePathOptionPane.getStyleClass().add("selected");
			myLinePathOptionPane.getStyleClass().remove("selected");
			mySelectComponentOptionPane.getStyleClass().remove("selected"); 
			setCurrentBuildingPane(myCurveDrawingPane);
		}
	}

	public void setLineDrawerMode () {
		if(!isCurrentPane(myLineDrawingPane) && canDrawComponents()){
			proceedToLineDrawing();
		}
	}

	private void proceedToLineDrawing () {
		myLinePathOptionPane.getStyleClass().add("selected");
		myCurvePathOptionPane.getStyleClass().remove("selected");
		mySelectComponentOptionPane.getStyleClass().remove("selected");
		setCurrentBuildingPane(myLineDrawingPane);
	}

	private boolean canDrawComponents() {
		return !(isCurrentPane(myEnemyStartingLocationsPane) || 
				isCurrentPane(myEnemyEndingLocationsPane) ||
				isCurrentPane(myBackgroundSelectionPane));
	}

	private boolean isCurrentPane(BuildingPane pane){
		return myCurrentBuildingPane.equals(pane);
	}

	private VBox createPathComponentOption (String componentName) {
		VBox pathComponentBox = new VBox();
		pathComponentBox.setPadding(new Insets(BUILDING_OPTIONS_PADDING));
		pathComponentBox.getChildren().add(new Label(componentName));
		pathComponentBox.getStyleClass().add("pathComponentOption");
		ImageView imageView = new ImageView();
		File imgFile = new File(DRAWING_OPTIONS_IMG_DIR + componentName.toLowerCase() + ".png");
		try {
			imageView.setImage(new Image(new FileInputStream(imgFile), OPTIONS_IMAGE_WIDTH, 
					OPTIONS_IMAGE_HEIGHT, false, true));
			pathComponentBox.getChildren().add(imageView);
		}
		catch (FileNotFoundException e) {
			new ErrorPopup("No file found representing " + componentName + " image.");
		}
		return pathComponentBox;
	}

	public void proceedToEndLocationsSelection () {
		if(myPath.startingLocationsConfiguredCorrectly()){
			setCurrentBuildingPane(myEnemyEndingLocationsPane);
		}
	}

	public void proceedToLineDrawerModeIfLocationsVerified () {
		if(myPath.endingLocationsConfiguredCorrectly()){
			proceedToLineDrawing();
		}
	}

	public void setCurrentBuildingPane(BuildingPane nextPane) {
		myCurrentBuildingPane = nextPane;
		myPane.getChildren().remove(myPane.getCenter());
		myPane.setCenter(nextPane);
		nextPane.refreshScreen();
	}

	public void proceedToStartLocationSelection () {
		setCurrentBuildingPane(myEnemyStartingLocationsPane);        
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o.equals(myDefaultMapSelectionPane)){
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