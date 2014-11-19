package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.actorBuildingScenes.actorListView.CreatedActorsDisplay;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.IBehaviorKeyValuePair;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utilities.DragAndDropFilePane;
import utilities.ErrorPopup;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

public abstract class ActorBuildingScene extends BuildingScene implements Observer {

    private static final String CLASS_ROUTE_TO_BUILDERS = "gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.";
    private static final String FILE_NOT_FOUND_ERROR_MSG = "Image file representing actor could not be found";
    private static final int DRAG_AND_DROP_WIDTH = 560;
    public static final int ACTOR_IMG_HEIGHT = 150;
    public static final int ACTOR_IMG_WIDTH = 150;
    
    protected BorderPane myPane;
    protected ObservableList<BaseActor> myActors;
    protected CreatedActorsDisplay myCreatedActorDisplay;
    protected DragAndDropFilePane myDragAndDrop;
    protected TextField myActorNameField;
    protected Image myActorImage;
    protected List<BehaviorBuilder> myBehaviorBuilders;
    private String myActorImageDirectory;
    private List<BackendRoute> myRoutes;
    
    public ActorBuildingScene (BorderPane root, List<BackendRoute> routes, String title, 
                               String behaviorXMLFileLocation, String actorImageDirectory) {
        super(root, title);
        myPane = root;
        myRoutes = routes;
        myActorImageDirectory = actorImageDirectory;
        setupBehaviorBuilders(behaviorXMLFileLocation);
        setupFileMenu();
        initializeActorsAndBuildActorDisplay();
        createCenterDisplay();  
        setupDragAndDropForActorImage();
    }

    private void setupBehaviorBuilders (String behaviorXMLFileLocation) {
        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        XMLParser parser = new XMLParser(new File(behaviorXMLFileLocation));
        List<String> allBehaviorTypes = parser.getAllBehaviorTypes();
        for(String behaviorType:allBehaviorTypes){
            List<String> behaviorOptions = parser.getValuesFromTag(behaviorType);
            myBehaviorBuilders.add((BehaviorBuilder) Reflection.createInstance(CLASS_ROUTE_TO_BUILDERS + capitalize(behaviorType) + "Builder", myRoutes, behaviorOptions));
        }
    }

    private String capitalize (String behaviorType) {
        return behaviorType.substring(0, 1).toUpperCase().concat(behaviorType.substring(1));
    }

    private void setupDragAndDropForActorImage () {
        myDragAndDrop = 
                new DragAndDropFilePane(DRAG_AND_DROP_WIDTH, AuthorController.SCREEN_HEIGHT, new String[]{".jpg", ".jpeg", ".png"}, 
                                        myActorImageDirectory);
        myDragAndDrop.addObserver(this);
        myDragAndDrop.getPane().getStyleClass().add("dragAndDrop");
        myPane.setRight(myDragAndDrop.getPane());
    }

    private void initializeActorsAndBuildActorDisplay () {
        myActors = FXCollections.observableArrayList();
        myCreatedActorDisplay = new CreatedActorsDisplay(myActors);
        myPane.setLeft(myCreatedActorDisplay);
    }

    private void setupFileMenu () {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem finishedBuildingItem = new MenuItem("Finished");
        finishedBuildingItem.setOnAction(event->finishBuildingActors());
        menu.getItems().add(finishedBuildingItem);
        menuBar.getMenus().add(menu);
        myPane.setTop(menuBar);
    }
    
    private void createCenterDisplay() {
        VBox centerOptionsBox = new VBox(25);
        Label title = new Label(super.getTitle() + " Behaviors.");
        title.getStyleClass().add("behaviorsTitle");
        centerOptionsBox.getChildren().addAll(title, createEnemyNameTextField());
        centerOptionsBox.setPadding(new Insets(10));
        for(BehaviorBuilder builder:myBehaviorBuilders){
            centerOptionsBox.getChildren().add(builder.getContainer());
        }
        centerOptionsBox.getChildren().add(createSaveButton()); 
        myPane.setCenter(centerOptionsBox);
    }

    private VBox createEnemyNameTextField () {
        VBox box = new VBox(5);
        Label label = new Label("Enemy Name");
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
        if(fieldsAreValidForEnemyCreation(iBehaviorMap) && enemyNameIsUnique()){
            makeNewEnemy(iBehaviorMap);
            clearFields();
        }
    }

    private void makeNewEnemy (Map<String, IBehavior> iBehaviorMap) {
        myActors.add(new BaseActor(iBehaviorMap,
                                    myActorImage,
                                    myActorNameField.getText()));
        System.out.println(myActors.size());
    }

    private boolean enemyNameIsUnique () {
        return myActors
                .stream()
                .filter(enemy -> enemy.toString().equalsIgnoreCase(myActorNameField.getText()))
                .count() == 0;
    }
    
    private void clearFields() {
        myActorNameField.clear();
        myPane.getChildren().remove(myPane.getRight());
        myPane.setRight(myDragAndDrop.getPane());
        for(BehaviorBuilder builder:myBehaviorBuilders) {
            builder.reset();
        }
        myActorImage = null;
    }

    private boolean fieldsAreValidForEnemyCreation (Map<String, IBehavior> iBehaviorMap) {
        return myActorImage != null && 
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

    public void finishBuildingActors() {
        this.setChanged();
        this.notifyObservers(myActors);
    }
    
    @Override
    public void update (Observable arg0, Object arg1) {
        try {
            myActorImage = new Image(new FileInputStream((File) arg1), ACTOR_IMG_WIDTH, ACTOR_IMG_HEIGHT, true, false);    
            ImageView imageView = new ImageView(myActorImage);
            imageView.setScaleX(1.5);
            imageView.setScaleY(1.5);
            imageView.setLayoutX(220);
            imageView.setLayoutY(220);
            Pane rightPane = new Pane();
            rightPane.setPrefWidth(DRAG_AND_DROP_WIDTH);
            rightPane.getChildren().add(imageView);
            rightPane.setStyle("-fx-background-color: white;");
            myPane.getChildren().remove(myDragAndDrop);
            myPane.setRight(rightPane);
        }
        catch (FileNotFoundException e) {
            new ErrorPopup(FILE_NOT_FOUND_ERROR_MSG);
        } 
    }

}
