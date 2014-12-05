package utilities.JavaFXutilities;

import javafx.scene.image.ImageView;

public class CenteredImageView extends ImageView{

    public double getXCenter() {
        return getX() + getFitWidth()/2;
    }
    
    public double getYCenter() {
        return getY() + getFitHeight()/2;
    }
    
    public void setXCenter(double x){
        setX(x-getFitWidth()/2);
    }
    
    public void setYCenter(double y){
        setY(y-getFitHeight()/2);
    }
}
