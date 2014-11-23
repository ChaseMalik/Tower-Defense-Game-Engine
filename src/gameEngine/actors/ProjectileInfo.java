package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;

public class ProjectileInfo {

    String myImage;
    double mySpeed;
    List<IBehavior> onHitEffects;
    
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
