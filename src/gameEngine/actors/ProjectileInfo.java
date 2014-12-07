package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.sun.javafx.collections.MappingChange.Map;

public class ProjectileInfo {

    private String myImage;
    private IBehavior myMovement;
    private List<IBehavior> myOnHitEffects;
    private List<String> myEnemyTypes;
    private int myDamage;



    public ProjectileInfo(String image, int damage,HashMap<String,IBehavior> map, List<String> types){
        myDamage=damage;
        myImage = image;
        myOnHitEffects=new ArrayList<>();
        for(String s: map.keySet()){
            myOnHitEffects.add(map.get(s));
        }
        myMovement=map.get("movement");
        myOnHitEffects.remove(myMovement);
        myEnemyTypes=types;

    }
    public ProjectileInfo(String image, int damage, IBehavior move, List<IBehavior> list, List<String> types){
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

    public int getMyDamage () {
        return myDamage;
    }

    public void setMyDamage (int myDamage) {
        this.myDamage = myDamage;
    }
    public ProjectileInfo copy(){
        return new ProjectileInfo(myImage,myDamage,myMovement.copy(), myOnHitEffects,myEnemyTypes);
    }
}
