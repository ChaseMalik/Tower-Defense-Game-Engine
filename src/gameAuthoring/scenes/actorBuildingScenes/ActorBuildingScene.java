package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.IBehaviorKeyValuePair;
import gameEngine.actors.behaviors.IBehavior;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utilities.SliderContainer;
import utilities.DragAndDropFilePanes.DragAndDropCopyImagePane;
import utilities.DragAndDropFilePanes.DragAndDropFilePane;
import utilities.DragAndDropFilePanes.DragAndDropImagePane;
import utilities.XMLParsing.XMLParser;

/**
 * Class that is extended by EnemyBuildingScene and TowerBuildingScene. Creates a
 * ListView of the created actors on the left side of the screen so that the user can easily
 * create, edit, and remove them. Builds of the BehaviorBuilder components into a pane in the center
 * of the screen. These JavaFX components allow the user the ability to specify the information that
 * is needed to build the behavior objects that is necessary to ultimately build an actor.
 * Finally a drag and drop file pane on the right side of the screen allows the user to input
 * an image for the actor.
 * @author Austin Kyker
 *
 */
public abstract class ActorBuildingScene extends BuildingScene implements Observer {

    private static final int CENTER_DISPLAY_WIDTH = 230;
    protected static final String ADD_TOWER_IMG_PATH = "./src/gameAuthoring/Resources/otherImages/addTower.png";
    protected static final int DRAG_AND_DROP_WIDTH = 560;
    public static final int ACTOR_IMG_HEIGHT = 150;
    public static final int ACTOR_IMG_WIDTH = 150;

    protected DragAndDropImagePane myDragAndDrop;
    protected TextField myActorNameField;
    protected String myActorImgPath;
    protected List<BehaviorBuilder> myBehaviorBuilders;
    protected SliderContainer myRangeSliderContainer;
    private String myActorImageDirectory;
    private String myTitle;

    public ActorBuildingScene (BorderPane root, String title, String behaviorXMLFileLocation, 
                               String actorImageDirectory) {
        super(root, title);
        myTitle = title;
        myActorImageDirectory = actorImageDirectory;
        File imgDir = new File(myActorImageDirectory);
        imgDir.mkdir();
        setupBehaviorBuilders(behaviorXMLFileLocation);
        setupFileMenu();
        initializeActorsAndBuildActorDisplay();
        createCenterDisplay();  
        setupDragAndDropForActorImage();
        myActorNameField.requestFocus();
    }

    private void setupBehaviorBuilders (String behaviorXMLFileLocation) {
        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        XMLParser parser = new XMLParser(new File(behaviorXMLFileLocation));
        List<String> allBehaviorTypes = parser.getAllBehaviorTypes();
        for(String behaviorType:allBehaviorTypes){
            List<String> behaviorOptions = parser.getValuesFromTag(behaviorType);
            myBehaviorBuilders.add(new BehaviorBuilder(behaviorType, behaviorOptions, parser.getSliderInfo(behaviorType)));
        }
    }

    private void setupDragAndDropForActorImage () {
        myDragAndDrop = 
                new DragAndDropCopyImagePane(DRAG_AND_DROP_WIDTH, AuthorController.SCREEN_HEIGHT,  
                                        myActorImageDirectory);
        myDragAndDrop.addObserver(this);
        myDragAndDrop.getPane().getStyleClass().add("dragAndDrop");
        myPane.setRight(myDragAndDrop.getPane());
    }

    protected abstract void initializeActorsAndBuildActorDisplay ();

    private void setupFileMenu () {
        BuildingSceneMenu menu = new BuildingSceneMenu();
        MenuItem saveItem = new MenuItem("Save " + myTitle);
        saveItem.setOnAction(event->attemptToSaveActor());
        menu.addMenuItemToFileMenu(saveItem);
        menu.addObserver(this);
        myPane.setTop(menu.getNode());
    }

    private void createCenterDisplay() {
        VBox centerOptionsBox = new VBox(25);
        centerOptionsBox.setPrefWidth(CENTER_DISPLAY_WIDTH);
        Label title = new Label(super.getTitle() + " Behaviors.");
        title.getStyleClass().add("behaviorsTitle");
        myRangeSliderContainer = new SliderContainer("range", 0, 100);
        centerOptionsBox.getChildren().addAll(title, createActorNameTextField(), myRangeSliderContainer);
        centerOptionsBox.setPadding(new Insets(10));
        for(BehaviorBuilder builder:myBehaviorBuilders){
            centerOptionsBox.getChildren().add(builder.getContainer());
        } 
        myPane.setCenter(centerOptionsBox);
    }

    private VBox createActorNameTextField () {
        VBox box = new VBox(5);
        Label label = new Label(myTitle.concat(" Name"));
        myActorNameField = new TextField();
        box.getChildren().addAll(label, myActorNameField);
        return box;
    }

    private void attemptToSaveActor () {
        Map<String, IBehavior> iBehaviorMap = buildIBehaviorMap();
        if(fieldsAreValidForActiveCreation(iBehaviorMap)){
            makeNewActor(iBehaviorMap);
            clearFields();
        }
    }

    protected abstract void makeNewActor (Map<String, IBehavior> iBehaviorMap);

    protected void clearFields() {
        myActorNameField.clear();
        myPane.getChildren().remove(myPane.getRight());
        myPane.setRight(myDragAndDrop.getPane());
        for(BehaviorBuilder builder:myBehaviorBuilders) {
            builder.reset();
        }
        myDragAndDrop.reset();
        myActorImgPath = "";
    }

    private boolean fieldsAreValidForActiveCreation (Map<String, IBehavior> iBehaviorMap) {
        return !myActorImgPath.isEmpty() && 
                !iBehaviorMap.isEmpty() &&
                !myActorNameField.getText().isEmpty();
    }

    private Map<String, IBehavior> buildIBehaviorMap () {
        Map<String, IBehavior> iBehaviorMap = new HashMap<String, IBehavior>();
        for(BehaviorBuilder builder:myBehaviorBuilders){
            IBehaviorKeyValuePair pair = builder.buildBehavior();
            iBehaviorMap.put(pair.getTypeOfBehavior(), pair.getMyIBehavior());
        }
        return iBehaviorMap;
    }

    @Override
    public void update (Observable obs, Object arg1) {
        if(obs instanceof DragAndDropFilePane ){
            myActorImgPath = (String) arg1;   
            configureAndDisplayRightPane();
        }
        else if(obs instanceof BuildingSceneMenu) {
            finishBuildingActors();
        }
    }

    protected abstract void configureAndDisplayRightPane();

    protected abstract void finishBuildingActors();
}


