package gameAuthoring.scenes.actorBuildingScenes.actorListView.listViewCells;

import gameEngine.actors.BaseTower;

/**
 * Object to hold a tower and all its upgrades. This will be used to make
 * a ListView in the tower creation scene.
 * @author Austin Kyker
 *
 */
public class TowerUpgrades {
    private BaseTower[] myTowers;

    public BaseTower[] getTowers () {
        return myTowers;
    }
}
