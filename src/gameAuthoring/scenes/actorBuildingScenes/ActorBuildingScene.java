package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.IBehaviorKeyValuePair;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.SliderInfo;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.RealActor;
import gameEngine.actors.behaviors.IBehavior;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utilities.DragAndDropFilePane;
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

    protected static final String ADD_TOWER_IMG_PATH = "./src/gameAuthoring/Resources/otherImages/addTower.png";
    protected static final int DRAG_AND_DROP_WIDTH = 560;
    public static final int ACTOR_IMG_HEIGHT = 150;
    public static final int ACTOR_IMG_WIDTH = 150;

    protected DragAndDropFilePane myDragAndDrop;
    protected TextField myActorNameField;
    protected String myActorImgPath;
    protected List<BehaviorBuilder> myBehaviorBuilders;
    private String myActorImageDirectory;
    private List<BackendRoute> myRoutes;
    private String myTitle;

    public ActorBuildingScene (BorderPane root, List<BackendRoute> routes, String title, 
                               String behaviorXMLFileLocation, String actorImageDirectory) {
        super(root, title);
        myTitle = title;
        myRoutes = routes;
        myActorImageDirectory = actorImageDirectory;
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
            myBehaviorBuilders.add(new BehaviorBuilder(behaviorType, myRoutes, behaviorOptions, new SliderInfo("speed", 0, 5)));
        }
    }

    private void setupDragAndDropForActorImage () {
        myDragAndDrop = 
                new DragAndDropFilePane(DRAG_AND_DROP_WIDTH, AuthorController.SCREEN_HEIGHT, 
                                        new String[]{".jpg", ".jpeg", ".png"}, 
                                        myActorImageDirectory);
        myDragAndDrop.addObserver(this);
        myDragAndDrop.getPane().getStyleClass().add("dragAndDrop");
        myPane.setRight(myDragAndDrop.getPane());
    }

    protected abstract void initializeActorsAndBuildActorDisplay ();
    

    private void setupFileMenu () {
        BuildingSceneMenu menu = new BuildingSceneMenu();
        menu.addObserver(this);
        myPane.setTop(menu.getNode());
    }

    private void createCenterDisplay() {
        VBox centerOptionsBox = new VBox(25);
        Label title = new Label(super.getTitle() + " Behaviors.");
        title.getStyleClass().add("behaviorsTitle");
        centerOptionsBox.getChildren().addAll(title, createActorNameTextField());
        centerOptionsBox.setPadding(new Insets(10));
        for(BehaviorBuilder builder:myBehaviorBuilders){
            centerOptionsBox.getChildren().add(builder.getContainer());
        }
        centerOptionsBox.getChildren().add(createSaveButton()); 
        myPane.setCenter(centerOptionsBox);
    }

    private VBox createActorNameTextField () {
        VBox box = new VBox(5);
        Label label = new Label(myTitle.concat(" Name"));
        myActorNameField = new TextField();
        box.getChildren().addAll(label, myActorNameField);
        return box;
    }

    private Button createSaveButton(){
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event->handleSaveButtonClicked());
        return saveButton;              
    }

    private void handleSaveButtonClicked () {
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
            myActorImgPath = ((File) arg1).getAbsolutePath();   
            showActorImage();
        }
        else if(obs instanceof BuildingSceneMenu) {
            finishBuildingActors();
        }
    }

    public void showActorImage () {
        ImageView imageView;
        try {
            FileInputStream actorImgFileStream = new FileInputStream(new File(myActorImgPath));
            Image actorImg = new Image(actorImgFileStream, ACTOR_IMG_WIDTH, ACTOR_IMG_HEIGHT, 
                                       true, false);
            imageView = new ImageView(actorImg);
            imageView.setScaleX(1.5);
            imageView.setScaleY(1.5);
            imageView.setLayoutX(220);
            imageView.setLayoutY(220);        
            Pane rightPane = new Pane();
            rightPane.setPrefWidth(DRAG_AND_DROP_WIDTH);
            rightPane.getChildren().add(imageView);
            rightPane.setStyle("-fx-background-color: white;");
            myPane.getChildren().remove(myDragAndDrop);
            configureAndDisplayRightPane(rightPane);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

    protected void configureAndDisplayRightPane (Pane rightPane) {
        myPane.setRight(rightPane);
    }

    protected abstract void finishBuildingActors();
}


