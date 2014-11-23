package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;

public class ProjectileInfo {

    private String myImage;
    private BaseMovementBehavior myMovement;
    private List<IBehavior> onHitEffects;
    private List<String> myTypes;
    
    public ProjectileInfo(String image, BaseMovementBehavior move, List<IBehavior> list, List<String> types){
        myImage = image;
        myMovement=move;
        myTypes=types;
    }

    public double getSpeed () {
        return mySpeed;
    }
    
    public String getImage () {
        return myImage;
    }
    public List<IBehavior> getOnHit(){
        return onHitEffects;
    }
    public 
}
