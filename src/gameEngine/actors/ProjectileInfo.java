package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *  info class that defines projectiles
 * 
 *
 */
public class ProjectileInfo {

    private String myImage;
    private IBehavior myMovement;
    private List<IBehavior> myOnHitEffects;
    private List<String> myEnemyTypes;
    private double myDamage;



    public ProjectileInfo(String image, double damage, Map<String,IBehavior> map, List<String> types){
        myDamage=damage;
        myImage = image;
        myOnHitEffects=new ArrayList<>();
        if(map != null) {
            for(String s: map.keySet()){
                myOnHitEffects.add(map.get(s));
            }
            myMovement=map.get("movement");
            myOnHitEffects.remove(myMovement);
            myMovement=myMovement.copy();
                    
        }
        myEnemyTypes=types;

    }
    public ProjectileInfo(String image, double damage, IBehavior move, List<IBehavior> list, List<String> types){
        myImage=image;
        myDamage=damage;
        myOnHitEffects=list;
        myMovement=move;
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

    public double getMyDamage () {
        return myDamage;
    }

    public void setMyDamage (double myDamage) {
        this.myDamage = myDamage;
    }
    public ProjectileInfo copy(){
        return new ProjectileInfo(myImage,myDamage,myMovement.copy(), myOnHitEffects,myEnemyTypes);
    }
}
