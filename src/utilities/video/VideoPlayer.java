package utilities.video;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

class VideoPlayer extends BorderPane {
    private MediaPlayer myMediaPlayer;
    private MediaView myMediaView;
    private final boolean replayVideo = true;
    private boolean stopVideo = false;
    private boolean cycleComplete = false;
    //    private Duration myDuration;
    //    private Slider timeSlider;
    //    private Label playTime;
    //    private Slider volumeSlider;
    private HBox myMediaBar;

    public VideoPlayer (final MediaPlayer mediaPlayer) {
        this.myMediaPlayer = mediaPlayer;
        myMediaView = new MediaView(mediaPlayer);
        Pane moviePane = new Pane() { };
        moviePane.getChildren().add(myMediaView);
        setCenter(moviePane);
        myMediaBar = new HBox();
        myMediaBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(myMediaBar, Pos.CENTER);
        setBottom(myMediaBar);

        final Button playButton = new Button(">");

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mediaPlayer.getStatus();

                if (status == Status.UNKNOWN || status == Status.HALTED) {
                    // don't do anything in these states
                    return;
                }

                if (status == Status.PAUSED || status == Status.READY
                        || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (cycleComplete) {
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        cycleComplete = false;
                    }
                    mediaPlayer.play();
                } else {
                    mediaPlayer.pause();
                }
            }
        });
//        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                updateValues();
//            }
//        });

        mediaPlayer.setCycleCount(replayVideo ? MediaPlayer.INDEFINITE : 1);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!replayVideo) {
                    playButton.setText(">");
                    stopVideo = true;
                    cycleComplete = true;
                }
            }
        });
    }
}