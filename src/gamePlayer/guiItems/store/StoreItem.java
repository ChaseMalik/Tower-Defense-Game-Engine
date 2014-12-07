package gamePlayer.guiItems.store;

import utilities.JavaFXutilities.imageView.StringToImageViewConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StoreItem {
    private int ID;
    private String name;
    private ImageView imageView;
    private String description;
    private BooleanBinding availableBinding;
    
    public StoreItem(String name, String imagePath, BooleanProperty available) {
        this.name = name;
        this.imageView = StringToImageViewConverter.getImageView(100, 100, imagePath);
        this.availableBinding = Bindings.and(new SimpleBooleanProperty(true),available);
    }
    
    public int getID () {
        return ID;
    }
    public String getName(){
    	return name;
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

    public void setID (int iD) {
        ID = iD;
    }

    public void setImageView (ImageView imageView) {
        this.imageView = imageView;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
