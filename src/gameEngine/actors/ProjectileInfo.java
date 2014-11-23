package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;

public class ProjectileInfo {

    private String myImage;
    private double mySpeed;
    private List<IBehavior> onHitEffects;
    
    public ProjectileInfo(String image, double speed, List<IBehavior> list){
        myImage = image;
        mySpeed = speed;
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
}
