package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorBuilder;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.BehaviorMapBuilder;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.ProjectileInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropCopyImagePane;
import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropImagePane;
import utilities.JavaFXutilities.slider.SliderContainer;
import utilities.XMLParsing.XMLParser;

public class ProjectilePane extends Observable implements Observer {
    
    private static final String PROJECTILE_IMG_DIR = 
            AuthorController.gameDir + "projectileImages/";
    private static final String PROJECTILE_BEHAVIORS_XML = 
            "./src/gameAuthoring/Resources/actorBehaviors/ProjectileBehaviors.xml";
    private HBox myContainer;
    private DragAndDropImagePane myDropImgPane;
    private ArrayList<BehaviorBuilder> myBehaviorBuilders;
    private SliderContainer myDamageSlider;
    
    public ProjectilePane() {
        
        
        myBehaviorBuilders = new ArrayList<BehaviorBuilder>();
        XMLParser parser = new XMLParser(new File(PROJECTILE_BEHAVIORS_XML));
        List<String> allBehaviorTypes = parser.getAllBehaviorTypes();
        for(String behaviorType:allBehaviorTypes){
            List<String> behaviorOptions = parser.getValuesFromTag(behaviorType);
            myBehaviorBuilders.add(new BehaviorBuilder(behaviorType, behaviorOptions, 
                                                       parser.getSliderInfo(behaviorType)));
        }
        
        myDamageSlider = new SliderContainer("Damage", 1, 5);
        HBox behaviorBox = new HBox(10);
        behaviorBox.getChildren().add(myDamageSlider);
        for(BehaviorBuilder builder:myBehaviorBuilders) {
            behaviorBox.getChildren().add(builder.getContainer());
        }
                
        File dir = new File(PROJECTILE_IMG_DIR);
        dir.mkdir();
        
        myContainer = new HBox(20);
        myContainer.setPadding(new Insets(10));
        Label title = new Label("Projectile");
        title.setStyle("-fx-font-size: 18px");
        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(title, behaviorBox);
        myDropImgPane = new DragAndDropCopyImagePane(200, 100, PROJECTILE_IMG_DIR);
        myDropImgPane.getPane().setStyle("-fx-background-color: white; " +
                                         "-fx-border: 2px; -fx-border-color: gray; " +
                                         "-fx-border-radius: 5px");
        myContainer.getChildren().addAll(leftBox, myDropImgPane.getPane());
    }

    public HBox getNode () {
        return myContainer;
    }

    @Override
    public void update (Observable arg0, Object arg1) {
        myContainer.getChildren().remove(myDropImgPane.getPane());             
    }
    
    public ProjectileInfo makeProjectileInfo(List<BaseEnemy> enemiesCanDamage) {
        List<String> enemyStrings = enemiesCanDamage
                .stream()
                .map(enemy->enemy.toString())
                .collect(Collectors.toList());
        return new ProjectileInfo(myDropImgPane.getImagePath(),
                                  (int) myDamageSlider.getSliderValue(),
                                  BehaviorMapBuilder.buildMap(myBehaviorBuilders),
                                  enemyStrings);
    }
}
