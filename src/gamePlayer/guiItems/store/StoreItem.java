package gamePlayer.guiItems.store;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StoreItem {
    private String name;
    private int cost;
    private ImageView imageView;
    private String description;
    private BooleanBinding availableBinding;
    
    public static final double ICON_SIZE = 100;
    
    public StoreItem(String name, String imagePath, int cost, BooleanProperty available) {
        this.name = name;
        this.cost = cost;
        this.imageView = StringToImageViewConverter.getImageView(ICON_SIZE, ICON_SIZE, imagePath);
        this.availableBinding = Bindings.and(new SimpleBooleanProperty(true),available);
    }
    
    public String getName(){
    	return name;
    }
    
    public int getCost(){
    	return cost;
    }
    
    public ImageView getImageView () {
        return imageView;
    }
    public String getDescription () {
        return description;
    }

    public BooleanBinding availableBinding () {
        return availableBinding;
    }

    public void setImageView (ImageView imageView) {
        this.imageView = imageView;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
