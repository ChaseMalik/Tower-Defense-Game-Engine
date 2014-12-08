package gameAuthoring.scenes.pathBuilding.buildingPanes;

/**
 * An interface for the PathBackgroundSelectionPane to use. This ensures
 * this pane cannot get access to any of the data in PathBuildingScene.
 * @author Austin Kyker
 *
 */
public interface BackgroundBuilding {
    void setBackground (String filePath);
}
