package utilities.DragAndDropFilePanes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import utilities.errorPopup.ErrorPopup;

public class DragAndDropCopyImagePane extends DragAndDropImagePane {
    
    private String myFileDestination;

    public DragAndDropCopyImagePane (double width, double height, String fileDestination) {
        super(width, height);
        myFileDestination = fileDestination;
    }
    
    @Override
    protected void actOnFile (File file) {
        File targetFile = new File(myFileDestination + file.getName().toString());  
        myFile = targetFile;
        try {
            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
            displayImage();
        }
        catch (IOException e) {
            new ErrorPopup("Image file could not be copied.");
        }       
    }
}