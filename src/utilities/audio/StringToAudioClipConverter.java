package utilities.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.media.AudioClip;
import utilities.errorPopup.ErrorPopup;

/**
 * 
 * @author $cotty $haw
 * 
 * This class will let the user get an audio clip from a string.
 *
 */
public class StringToAudioClipConverter {
    
    public static AudioClip getAudioClip(double width, double height, String path) {
//        CenteredImageView imgView = new CenteredImageView();
//        try {
            AudioClip audio;
//            audio = new AudioClip(new FileInputStream(new File(path.replace("\\", "/"))), width, height, false, false);
            audio = new AudioClip(path.replace("\\", "/"));
//            imgView.setImage(audio);
//            imgView.setFitWidth(width);
//            imgView.setFitHeight(height);
//        }
//        catch (FileNotFoundException e) {
//            new ErrorPopup("File " + path + " could not be loaded.");
//        }
        return audio;
    }
}
