package utilities.JavaFXutilities.DragAndDropFilePanes.audioPanes;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import utilities.errorPopup.ErrorPopup;

public class DragAndDropCopyAudioPane extends DragAndDropAudioPane {

    private String myFileDestination;

    public DragAndDropCopyAudioPane (double width, double height, String fileDestination) {
        super(width, height);
        myFileDestination = fileDestination;
    }

    @Override
    protected void actOnFile (File file) {
        File targetFile = new File(myFileDestination + file.getName().toString());  
        myFile = targetFile;
        try {
            Files.copy(file.toPath(), targetFile.toPath(), REPLACE_EXISTING);
//            playAudio();
            System.out.println("play audio");
        }
        catch (IOException e) {
            new ErrorPopup("Audio file could not be played.");
        }       
    }
}