package utilities.audio;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * @author $cotty $haw
 *
 * Our AudioPlayerTest tests playing audio files on a MediaPlayer. If
 * the user uses an audio file in this package, it should play.
 * 
 */
public class AudioPlayerTest extends Application {
    
    private static int jukeBoxWidth = 500;
    private static int jukeBoxHeight = 500;
    private static String jukeBoxTitle = "$cotty $haw's Juke Box - Brand New Day (Joe Budden)";
    private static final String AUDIO_PLAYER_TEST_FILE = "joe.mp3";

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage jukeBox) {
        final URL resource = getClass().getResource(AUDIO_PLAYER_TEST_FILE);
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        jukeBox.setTitle(jukeBoxTitle);
        jukeBox.setWidth(jukeBoxWidth);
        jukeBox.setHeight(jukeBoxHeight);
        jukeBox.show();
    }
}
