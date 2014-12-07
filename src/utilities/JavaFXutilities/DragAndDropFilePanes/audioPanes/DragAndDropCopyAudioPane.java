package utilities.JavaFXutilities.DragAndDropFilePanes.audioPanes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import utilities.JavaFXutilities.DragAndDropFilePanes.imagePanes.DragAndDropFilePane;
import utilities.errorPopup.ErrorPopup;

public class DragAndDropCopyAudioPane extends DragAndDropFilePane {

    private static final String ERROR_WHILE_ADDING_AUDIO_FILE = "Audio file could not be added.";
    private String myFileDestination;

    public DragAndDropCopyAudioPane (double width, double height, String fileDestination) {
        super(width, height, new String[] {".mp3", ".mp4"});
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