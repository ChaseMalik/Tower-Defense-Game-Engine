package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;

public class ProjectileInfo {

    private String myImage;
    private IBehavior myMovement;
    private List<IBehavior> myOnHitEffects;
    private List<String> myEnemyTypes;
    
    public ProjectileInfo(String image, IBehavior move, List<IBehavior> list, List<String> types){
        myImage = image;
        myMovement=move;
        myOnHitEffects=list;
        myEnemyTypes=types;

    }

 
    
    public String getImage () {
        return myImage;
    }
    
    public List<IBehavior> getOnHit(){
        return myOnHitEffects;
    }
    
    public List<String> getEnemiesTypes(){
        return myEnemyTypes;
    }
    public BaseMovementBehavior getMove(){
        return (BaseMovementBehavior)myMovement;
    }
    public ProjectileInfo copy(){
        return new ProjectileInfo(myImage,myMovement.copy(), myOnHitEffects,myEnemyTypes);
    }
}
