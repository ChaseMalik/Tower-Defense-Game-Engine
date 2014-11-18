package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.ProjectileInfo;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.actors.behaviors.NullBehavior;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import utilities.reflection.Reflection;

public class AttackBuilder extends BehaviorBuilder implements Observer {

    private static final String ATTACK = "attack";

    private Slider myAttackSpeedSlider;
    private Slider myRangeSlider;
    private Image myImageForEnemyProjectile;

    public AttackBuilder(List<BackendRoute> routes, List<String> attackOptions) {
        super(routes, attackOptions);
        createCenterDisplay(ATTACK);
        setUpSlider();
        myContainer.getChildren().addAll(myAttackSpeedSlider,
                                         myRangeSlider);
        myComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                handleSelection();
            }    
        });
        //        DragAndDropFilePane dragAndDrop = 
        //                new DragAndDropFilePane(200, 300, new String[]{".jpg", ".jpeg", ".png"}, 
        //                        "./src/gameAuthoring/Resources/enemyImages/");
        //        dragAndDrop.addObserver(this);
        //        dragAndDrop.getPane().getStyleClass().add("dragAndDrop");
        //        myContainer.getChildren().add(dragAndDrop.getPane());
    }

    private void handleSelection () {
        if(isNoAttackSelected()) {
            myAttackSpeedSlider.setDisable(true);
            myRangeSlider.setDisable(true);
        }
        else {
            myAttackSpeedSlider.setDisable(false);
            myRangeSlider.setDisable(false);
        }
    }

    private boolean isNoAttackSelected () {
        return "No attack".equalsIgnoreCase(myComboBox.getValue());
    }

    private void setUpSlider () {
        myAttackSpeedSlider = new Slider();
        setSliderProperties(myAttackSpeedSlider);
        myRangeSlider = new Slider();
        setSliderProperties(myRangeSlider);
    }

    @Override
    public IBehaviorKeyValuePair buildBehavior () {
        if(isNoAttackSelected()) {
            return new IBehaviorKeyValuePair(ATTACK, new NullBehavior());
        }
        String typeOfAttack = myComboBox.getValue();
        int speed = (int) myAttackSpeedSlider.getValue();
        double range = myRangeSlider.getValue();
        String className = "gameEngine.actors.behaviors." + typeOfAttack;
        return new IBehaviorKeyValuePair(ATTACK, 
                (IBehavior) Reflection.createInstance(className, speed, range, 
                                                      new ProjectileInfo(10, 10, null)));
       }

    @Override
    public void update (Observable arg0, Object arg1) {
        try {
            myImageForEnemyProjectile = new Image(new FileInputStream((File) arg1), 200, 200, false, true);    
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void reset() {
        super.reset();
        setSliderProperties(myAttackSpeedSlider);
        setSliderProperties(myRangeSlider);
    }
}