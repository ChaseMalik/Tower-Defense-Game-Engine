package utilities.audio;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MediaPlayerTest extends Application {

    private static final int myMovieTheaterWidth = 1600;
    private static final int myMovieTheaterHeight = 900;
    private static final String MEDIA_PLAYER_TEST_FILE = "mongols.mp4";
    private static final String MY_MOVIE_THEATER_TITLE = "$cotty $haw's Movie Theater";

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage movieTheater) {
        movieTheater.setTitle(MY_MOVIE_THEATER_TITLE);
        Group root = new Group();
        Scene scene = new Scene(root, myMovieTheaterWidth, myMovieTheaterHeight);

        final URL resource = getClass().getResource(MEDIA_PLAYER_TEST_FILE);
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        MediaViewer mediaViewer = new MediaViewer();
        scene.setRoot(mediaViewer);

        movieTheater.setScene(scene);
        movieTheater.show();
    }
}
