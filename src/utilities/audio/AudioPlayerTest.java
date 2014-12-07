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

    private static final int myJukeBoxWidth = 500;
    private static final int myJukeBoxHeight = 500;
    private static final String AUDIO_PLAYER_TEST_FILE = "joe.mp3";
    private static final String MY_JUKE_BOX_TITLE = "$cotty $haw's Juke Box - Brand New Day";

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage jukeBox) {
        final URL resource = getClass().getResource(AUDIO_PLAYER_TEST_FILE);
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        jukeBox.setTitle(MY_JUKE_BOX_TITLE);
        jukeBox.setWidth(myJukeBoxWidth);
        jukeBox.setHeight(myJukeBoxHeight);
        jukeBox.show();
    }
}
