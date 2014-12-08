package gameEngine.ManagerInterface;

import gameEngine.Data.TowerTileGrid;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;

public interface InformationInterface {
    public List<BaseActor> getRequiredActors(BaseActor actor, Class<? extends BaseActor> infoType);
        
    public boolean checkNewPath(); 
    
    public GridPane getReferencePane();
    
    public TowerTileGrid getExistingTowerTiles();
    
    public double getEarthquakeMagnitude();
}
