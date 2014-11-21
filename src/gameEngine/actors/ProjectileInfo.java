package gameEngine.actors;

import java.util.Set;
import javafx.scene.image.Image;

/**
 * Data object that stores information about a projectile - its speed, damage, and image
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class ProjectileInfo {
    protected double mySpeed;
    protected double myDamage;
    protected Image myImage;
    protected Set<String> myHittableEnemies;
    
    public ProjectileInfo(double speed, double damage, Image image, Set<String> hittableEnemies){
        mySpeed=speed;
        myDamage=damage;
        myImage=image;
    }

    public double getMySpeed () {
        return mySpeed;
    }

    public void setMySpeed (double mySpeed) {
        this.mySpeed = mySpeed;
    }

    public double getMyDamage () {
        return myDamage;
    }

    public void setMyDamage (double myDamage) {
        this.myDamage = myDamage;
    }

    public Image getMyImage () {
        return myImage;
    }

    public void setMyImage (Image myImage) {
        this.myImage = myImage;
    }
    
    public Set<String> getHittableEnemies(){
        return myHittableEnemies;
    }
    
}
