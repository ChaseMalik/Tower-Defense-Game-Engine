package gameEngine.actors;

import javafx.scene.image.Image;

public class ProjectileInfo {
    protected double mySpeed;
    protected double myDamage;
    protected Image myImage;
    
    public ProjectileInfo(double speed, double damage, Image image){
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
    
}
