package utilities.video;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class VideoPlayerTest extends Application {

    private static final int MY_MOVIE_THEATER_WIDTH = 1600;
    private static final int MY_MOVIE_THEATER_HEIGHT = 900;
    private static final String MEDIA_PLAYER_TEST_FILE = "mongols.mp4";
    private static final String MY_MOVIE_THEATER_TITLE = "$cotty $haw's Movie Theater";

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage movieTheater) {
        movieTheater.setTitle(MY_MOVIE_THEATER_TITLE);
        Group root = new Group();
        Scene scene = new Scene(root, MY_MOVIE_THEATER_WIDTH, MY_MOVIE_THEATER_HEIGHT);

        final URL RESOURCE = getClass().getResource(MEDIA_PLAYER_TEST_FILE);
        final Media MEDIA = new Media(RESOURCE.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(MEDIA);
        mediaPlayer.setAutoPlay(true);

        VideoPlayer videoPlayer = new VideoPlayer();
        scene.setRoot(videoPlayer);

        VideoController videoController = new VideoController(mediaPlayer);
        scene.setRoot(videoController);

        movieTheater.setScene(scene);
        movieTheater.show();
    }
}
