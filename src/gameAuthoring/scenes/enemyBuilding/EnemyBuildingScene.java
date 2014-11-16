package gameAuthoring.scenes.enemyBuilding;


import gameAuthoring.scenes.BuildingScene;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.behaviors.IBehavior;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.DragAndDropFilePane;

public class EnemyBuildingScene extends BuildingScene implements Observer {

    public static final int ENEMY_IMG_HEIGHT = 150;
    public static final int ENEMY_IMG_WIDTH = 150;

    private static final String TITLE = "Enemy Building";

    private BorderPane myPane;
    private BaseActor myCurrentlySelectedEnemy;
    private Image myImageForEnemyBeingCreated;
    private ObservableList<BaseActor> myEnemies;
    private List<BehaviorBuilder> myBehaviorBuilders;
    private EnemiesScrollPane myEnemiesScrollPane;

    public EnemyBuildingScene (BorderPane root, List<BackendRoute> enemyRoutes) {
        super(root, TITLE);
        myPane = root;
        myEnemies = FXCollections.observableArrayList();
        myEnemiesScrollPane = new EnemiesScrollPane(myEnemies);
        myPane.setLeft(myEnemiesScrollPane);
        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        myBehaviorBuilders.add(new MovementBuilder(enemyRoutes));
        myBehaviorBuilders.add(new AttackBuilder());
        createCenterDisplay();
    }

    private void createCenterDisplay() {
        VBox centerOptionsBox = new VBox();
        centerOptionsBox.setPadding(new Insets(10, 10, 10, 20));
        for(BehaviorBuilder builder:myBehaviorBuilders){
            centerOptionsBox.getChildren().add(builder.getContainer());
        }
        centerOptionsBox.getChildren().add(createSaveButton());
        centerOptionsBox.setSpacing(20);	
        HBox centerNode = new HBox(10);
        centerNode.getChildren().add(centerOptionsBox);
        DragAndDropFilePane dragAndDrop = 
                new DragAndDropFilePane(400, 300, new String[]{".jpg", ".jpeg", ".png"}, 
                        "./src/gameAuthoring/Resources/enemyImages/");
        dragAndDrop.addObserver(this);
        dragAndDrop.getPane().getStyleClass().add("dragAndDrop");
        centerNode.getChildren().add(dragAndDrop.getPane());
        myPane.setCenter(centerNode);
    }

    private Button createSaveButton(){
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<IBehavior> behaviors = myBehaviorBuilders
                        .stream()
                        .map(builder->builder.buildBehavior())
                        .filter(behavior -> behavior != null)
                        .collect(Collectors.toList());
                if(myImageForEnemyBeingCreated != null && !behaviors.isEmpty()){
                    BaseActor enemy = new BaseActor(behaviors,
                                                    myImageForEnemyBeingCreated);
                    myEnemies.add(enemy);
                }
            }
        });
        return saveButton;		
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        try {
            myImageForEnemyBeingCreated = new Image(new FileInputStream((File) arg1), ENEMY_IMG_WIDTH, ENEMY_IMG_HEIGHT, false, true);    
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}