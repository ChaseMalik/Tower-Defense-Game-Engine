package gameEngine.actors;

public class ProjectileInfo {

    String myImage;
    double mySpeed;
    
    public ProjectileInfo(String image, double speed){
        myImage = image;
        mySpeed = speed;
    }

    public double getSpeed () {
        return mySpeed;
    }
    
    public String getImage () {
        return myImage;
    }
}
