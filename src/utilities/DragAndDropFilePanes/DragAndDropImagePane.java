package utilities.DragAndDropFilePanes;

import javafx.scene.image.ImageView;
import utilities.StringToImageViewConverter;

public class DragAndDropImagePane extends DragAndDropFilePane {
    
    private ImageView myImageView;

    public DragAndDropImagePane (double width,
                                 double height,
                                 String fileDestination) {
        super(width, height, new String[] {".jpeg", ".jpg", ".png"}, fileDestination);
    }

    @Override
    protected void actOnFile () {
        myContainer.getChildren().remove(myDragAndDropPane);
        myImageView = StringToImageViewConverter.getImageView(myDragAndDropPane.getWidth(), 
                                                                      myDragAndDropPane.getHeight(), 
                                                                      myFile.getAbsolutePath());
        myContainer.getChildren().add(myImageView); 
        this.setChanged();
        this.notifyObservers(myFile.getAbsolutePath());       
    }
    
    public ImageView getImageView () {
        return myImageView;
    }
    
    public String getImagePath () {
        return myFile.getAbsolutePath();
    }

    public void setHeight (double height) {
        myImageView.setPreserveRatio(true);
        myImageView.setFitHeight(height);
        myImageView.autosize();
        myContainer.setPrefHeight(height);
        
    }
}
