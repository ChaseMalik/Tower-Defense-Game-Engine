package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.List;

public class ProjectileInfo {

    private String myImage;
    private IBehavior myMovement;
    private List<IBehavior> myOnHitEffects;
    private List<String> myEnemyTypes;
    private int myDamage;
    public ProjectileInfo(String image, int damage, List<IBehavior> list, List<String> types){
        myDamage=damage;
        myImage = image;
        for(IBehavior m : list){
            if(m.toString().equals("movement"))
                myMovement=m;
        }
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
    public List<IBehavior> copyEffects(){
        List<IBehavior> l=new ArrayList<>();
        for(IBehavior b: l){
            l.add(b.copy());
        }
        return l;
    }
    public ProjectileInfo copy(){
        return new ProjectileInfo(myImage,myDamage,copyEffects(),myEnemyTypes);
    }
}
