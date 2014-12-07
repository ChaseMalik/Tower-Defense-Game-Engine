package utilities.video;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

class VideoPlayer extends BorderPane {

    private static final String PLAY_BUTTON_TEXT = "Play";
    private static final String PAUSE_BUTTON_TEXT = "Pause";

    private MediaPlayer myMediaPlayer;
    private MediaView myMediaView;
    private Slider myTimeSlider;
    private Label myTimeLabel;
    private Duration myDuration;
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
        definePlayButtonBehavior(mediaPlayer, playButton);
        myMediaBar.getChildren().add(playButton);

        myTimeSlider = new Slider();
        HBox.setHgrow(myTimeSlider, Priority.ALWAYS);
        myTimeSlider.setMinWidth(50);
        myTimeSlider.setMaxWidth(Double.MAX_VALUE);
        myMediaBar.getChildren().add(myTimeSlider);
        
        myTimeLabel = new Label();
        myTimeLabel.setText("囧");
        myTimeLabel.setPrefWidth(150);
        myTimeLabel.setMinWidth(50);
        myMediaBar.getChildren().add(myTimeLabel);

        mediaPlayer.setCycleCount(replayVideo ? MediaPlayer.INDEFINITE : 1);
        defineMediaPlayerBehavior(mediaPlayer, playButton);
    }

    private void defineMediaPlayerBehavior (final MediaPlayer player, final Button button) {
        player.setOnPlaying(new Runnable() {
            public void run () {
                if (stopVideo) {
                    player.pause();
                    stopVideo = false;
                }
                else {
                    button.setText(PAUSE_BUTTON_TEXT);
                }
            }
        });

        player.setOnPaused(new Runnable() {
            public void run () {
                button.setText(PLAY_BUTTON_TEXT);
            }
        });

        player.setOnEndOfMedia(new Runnable() {
            public void run () {
                if (!replayVideo) {
                    button.setText(PLAY_BUTTON_TEXT);
                    stopVideo = true;
                    cycleComplete = true;
                }
            }
        });
    }

    private void definePlayButtonBehavior (final MediaPlayer player, final Button button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle (ActionEvent e) {
                Status status = player.getStatus();

                if (status == Status.HALTED || status == Status.UNKNOWN) {
                    return;
                }

                if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                    if (cycleComplete) {
                        player.seek(player.getStartTime());
                        cycleComplete = false;
                    }
                    player.play();
                }
                else {
                    player.pause();
                }
            }
        });
    }
}