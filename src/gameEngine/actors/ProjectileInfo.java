package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;

public class ProjectileInfo {

    private String myImage;
    private double mySpeed;
    private List<IBehavior> myOnHitEffects;
    private List<BaseEnemy> myEnemiesCanDamage;   
    
    public ProjectileInfo(String image, double speed, List<BaseEnemy> enemiesCanDamage, 
                          List<IBehavior> onHitEffects){
        myImage = image;
        mySpeed = speed;
        myEnemiesCanDamage = enemiesCanDamage;
        myOnHitEffects = onHitEffects;
    }

    public double getSpeed () {
        return mySpeed;
    }
    
    public String getImage () {
        return myImage;
    }
    
    public List<IBehavior> getOnHit(){
        return myOnHitEffects;
    }
    
    public List<BaseEnemy> getEnemiesCanDamage(){
        return myEnemiesCanDamage;
    }
}
