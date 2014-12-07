package utilities.video;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

class VideoPlayer extends BorderPane {

    private static final String PLAY_BUTTON_TEXT = "Play";
    private static final String PAUSE_BUTTON_TEXT = "Pause";

    private MediaPlayer myMediaPlayer;
    private MediaView myMediaView;
    private final boolean replayVideo = true;
    private boolean stopVideo = false;
    private boolean cycleComplete = false;
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

        final Button playButton = new Button(PLAY_BUTTON_TEXT);
        definePlayButtonBehaviors(mediaPlayer, playButton);
        myMediaBar.getChildren().add(playButton);

        mediaPlayer.setCycleCount(replayVideo ? MediaPlayer.INDEFINITE : 1);
        defineMediaPlayerBehaviors(mediaPlayer, playButton);
    }

    private void defineMediaPlayerBehaviors (final MediaPlayer mediaPlayer, final Button playButton) {
        mediaPlayer.setOnPlaying(new Runnable() {
            public void run () {
                if (stopVideo) {
                    mediaPlayer.pause();
                    stopVideo = false;
                }
                else {
                    playButton.setText(PAUSE_BUTTON_TEXT);
                }
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            public void run () {
                playButton.setText(PLAY_BUTTON_TEXT);
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run () {
                if (!replayVideo) {
                    playButton.setText(PLAY_BUTTON_TEXT);
                    stopVideo = true;
                    cycleComplete = true;
                }
            }
        });
    }

    private void definePlayButtonBehaviors (final MediaPlayer mediaPlayer, final Button playButton) {
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle (ActionEvent e) {
                Status status = mediaPlayer.getStatus();

                if (status == Status.HALTED || status == Status.UNKNOWN) {
                    return;
                }

                if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                    if (cycleComplete) {
                        mediaPlayer.seek(mediaPlayer.getStartTime());
                        cycleComplete = false;
                    }
                    mediaPlayer.play();
                }
                else {
                    mediaPlayer.pause();
                }
            }
        });
    }
}