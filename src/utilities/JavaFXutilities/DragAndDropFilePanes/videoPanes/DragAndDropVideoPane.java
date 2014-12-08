package utilities.JavaFXutilities.DragAndDropFilePanes.videoPanes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropFilePane;
import utilities.errorPopup.ErrorPopup;

/**
 * @author $cotty $haw
 * 
 * The DragAndDropAudioPane is a utility that will accept audio files
 * with the allowed extensions that the user dragged and dropped into
 * the pane. This class extends Observable, so it can listen for when
 * an audio file drops.
 * 
 */
public class DragAndDropVideoPane extends DragAndDropFilePane {

    private static final String ERROR_WHILE_ADDING_AUDIO_FILE = "Error adding audio file";
    private static String[] myAllowedFileExtensions = {".gif", ".mp4"};
    private String myFileDestination;

    public DragAndDropVideoPane (double width, double height, String fileDestination) {
        super(width, height, myAllowedFileExtensions);
        myFileDestination = fileDestination;
    }

    @Override
    protected void actOnFile (File file) {
        File targetFile = new File(myFileDestination + file.getName().toString());  
        myFile = targetFile;
        try {
            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
        }
        catch (IOException e) {
            new ErrorPopup(ERROR_WHILE_ADDING_AUDIO_FILE);
        }       
    }
}