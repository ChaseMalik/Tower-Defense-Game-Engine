package utilities.video;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class VideoPlayer extends BorderPane {
    private MediaPlayer myMediaPlayer;
    private MediaView myMediaView;
    private HBox mediaBar;

    public VideoPlayer (final MediaPlayer mediaPlayer) {
        this.myMediaPlayer = mediaPlayer;
        myMediaView = new MediaView(mediaPlayer);
        Pane moviePane = new Pane() {};
        moviePane.getChildren().add(myMediaView);
        setCenter(moviePane);
        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        setBottom(mediaBar);
    }
}