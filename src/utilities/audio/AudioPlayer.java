package utilities.audio;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author $cotty $haw
 *
 * Our AudioPlayer is a MediaPlayer that plays audio files. It can be
 * used with AudioPlayerTest.java to play audio files that are placed
 * in this package.
 * 
 */
public class AudioPlayer {

    protected static void playAudio (File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

}
