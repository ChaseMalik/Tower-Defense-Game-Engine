package utilities.audio;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * @author $cotty $haw
 * 
 * The AudioViewer tests playing audio files on a MediaPlayer. If the
 * user loads any audio file with any allowed file extensions to this
 * package, it should play.
 * 
 */
public class AudioViewer extends Application {

    private static final int MY_JUKEBOX_WIDTH = 500;
    private static final int MY_JUKEBOX_HEIGHT = 500;
    private static final String AUDIO_PLAYER_TEST_FILE = "joe.mp3";
    private static final String MY_JUKE_BOX_TITLE = "$cotty $haw's Jukebox - Brand New Day";

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage jukeBox) {
        final URL RESOURCE = getClass().getResource(AUDIO_PLAYER_TEST_FILE);
        final Media MEDIA = new Media(RESOURCE.toString());
        final MediaPlayer PLAYER = new MediaPlayer(MEDIA);
        PLAYER.play();

        jukeBox.setTitle(MY_JUKE_BOX_TITLE);
        jukeBox.setWidth(MY_JUKEBOX_WIDTH);
        jukeBox.setHeight(MY_JUKEBOX_HEIGHT);
        jukeBox.show();
    }
}
