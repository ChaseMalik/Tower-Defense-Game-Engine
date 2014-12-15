package gameEngine.ManagerInterface;

import gameEngine.Data.TowerTileGrid;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.scene.layout.GridPane;
/**
 *  Interface that defines the interactions allowed between an actor and the manager
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public interface InformationInterface {
    public List<BaseActor> getRequiredActors(BaseActor actor, Class<? extends BaseActor> infoType);
        
    public boolean checkNewPath(); 
    
    public GridPane getReferencePane();
    
    public TowerTileGrid getExistingTowerTiles();
    
    public double getEarthquakeMagnitude();
}
