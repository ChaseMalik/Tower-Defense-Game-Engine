package utilities.JavaFXutilities.DragAndDropFilePanes;

import java.io.File;

public class DragAndDropNoCopyImagePane extends DragAndDropImagePane {

    public DragAndDropNoCopyImagePane (double width, double height) {
        super(width, height);
    }

    @Override
    protected void actOnFile (File file) {
        myFile = file;
        displayImage();
        
    }

}
