package gamePlayer.guiItems.store;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;

public class StoreItem {
    private int ID;
    private ImageView imageView;
    private String description;
    private BooleanBinding availableBinding;
    
    public StoreItem(int ID,ImageView imageView,String description, BooleanProperty available) {
        this.ID = ID;
        this.imageView = imageView;
        this.description = description;
        this.availableBinding = Bindings.and(new SimpleBooleanProperty(true),available);
    }
    
    public int getID () {
        return ID;
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
