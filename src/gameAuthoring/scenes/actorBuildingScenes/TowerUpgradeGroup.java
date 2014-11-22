package gameAuthoring.scenes.actorBuildingScenes;

import gameEngine.actors.BaseTower;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.image.ImageView;
import utilities.StringToImageViewConverter;

public class TowerUpgradeGroup implements Iterable<BaseTower> {

    private static final double TOWER_IMG_WIDTH = 100;
    private static final double TOWER_IMG_HEIGHT = 90;

    private List<BaseTower> myTowerGroup;
    private int myNumPossibleUpgrades;

    public TowerUpgradeGroup(BaseTower base) {
        this(base, 3);
    }

    public TowerUpgradeGroup(BaseTower base, int numPossibleUpgrades) {
        myTowerGroup = new ArrayList<BaseTower>();
        myTowerGroup.add(0, base);
        myNumPossibleUpgrades = numPossibleUpgrades;
    }

    public boolean addTower(BaseTower tower) {
        if(myTowerGroup.size() <= myNumPossibleUpgrades) {
            return myTowerGroup.add(tower);
        }
        return false;
    }

    @Override
    public Iterator<BaseTower> iterator () {
        return myTowerGroup.iterator();
    }
    
    public int getNumTowersInGroup() {
        return myTowerGroup.size();
    }

    public List<ImageView> fetchImageViews () {
        List<ImageView> imageViews = new ArrayList<ImageView>();
        int numTowersInGroup = myTowerGroup.size();
        for(int i = 0; i < numTowersInGroup; i++){
            ImageView towerImg = myTowerGroup.get(i).getNode();
            towerImg.setFitWidth(TOWER_IMG_WIDTH);
            towerImg.setFitHeight(TOWER_IMG_HEIGHT);
            imageViews.add(towerImg);
        }
        for(int i = numTowersInGroup; i < myNumPossibleUpgrades; i++) {
            imageViews.add(StringToImageViewConverter.getImageView(TOWER_IMG_WIDTH,
                                                                   TOWER_IMG_HEIGHT, 
                                                                   ActorBuildingScene.ADD_TOWER_IMG_PATH));
        }
        return imageViews;
    }
}
