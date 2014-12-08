package gameEngine;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import java.util.List;
import javafx.geometry.Point2D;

public interface InformationInterface {
    public List<BaseActor> getRequiredActors(BaseActor actor, Class<? extends BaseActor> infoType);
    
    public List<Point2D> getAIPath(BaseEnemy a);
    
    public boolean checkNewPath(); 
    
}
