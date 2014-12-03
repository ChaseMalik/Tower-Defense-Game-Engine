package gameAuthoring.scenes.actorBuildingScenes;

import gameAuthoring.mainclasses.AuthorController;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.ProjectileInfo;
import gameEngine.actors.behaviors.LinearMovement;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.SliderContainer;
import utilities.JavaFXutilities.DragAndDropFilePanes.DragAndDropCopyImagePane;
import utilities.JavaFXutilities.DragAndDropFilePanes.DragAndDropImagePane;

public class ProjectilePane extends Observable implements Observer {
    
    private static final String PROJECTILE_IMG_DIR = AuthorController.gameDir + "projectileImages/";
    private SliderContainer mySliderContainer;
    private HBox myContainer;
    private DragAndDropImagePane myDropImgPane;
    
    public ProjectilePane() {
        File dir = new File(PROJECTILE_IMG_DIR);
        dir.mkdir();
        myContainer = new HBox(20);
        myContainer.setPadding(new Insets(10));
        mySliderContainer = new SliderContainer("speed", 0, 5);
        Label title = new Label("Projectile");
        title.setStyle("-fx-font-size: 18px");
        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(title, mySliderContainer);
        myDropImgPane = new DragAndDropCopyImagePane(200, 100, PROJECTILE_IMG_DIR);
        myDropImgPane.getPane().setStyle("-fx-background-color: white; -fx-border: 2px; -fx-border-color: gray; -fx-border-radius: 5px");
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
                                  new LinearMovement(5),
                                  null,
                                  enemyStrings);
    }
}
