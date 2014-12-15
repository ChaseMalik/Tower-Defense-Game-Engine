package utilities.video;

import javafx.application.Platform;
import javafx.geometry.Insets;
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

/**
 * @author $cotty $haw
 * 
 * The VideoPlayer is a MediaPlayer that plays video files. All users
 * can use it with VideoViewer.java to play any video files placed in
 * this package.
 * 
 */
class VideoPlayer extends BorderPane {

    private static final int PADDING = 20;
    private static final int BUTTON_WIDTH = 75;
    private static final int INT_CONVERT = 100;
    private static final int LABEL_WIDTH = 150;

    private static final double DOUBLE_CONVERT = 100.0;

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;

    private static final String SPACE = "      ";

    private static final String MEDIA_PLAYER_BACKGROUND_COLOR = "-fx-background-color: #bbc0c4;";
    private static final String MOVIE_PANE_BACKGROUND_COLOR = "-fx-background-color: #000000;";

    private static final String PLAY_BUTTON_TEXT = "PLAY";
    private static final String STOP_BUTTON_TEXT = "STOP";
    private static final String MUTE_BUTTON_TEXT = "MUTE";
    private static final String UNMUTE_BUTTON_TEXT = "UNMUTE";

    private static final String VOLUME_LABEL_TEXT = "Volume: ";
    private static final String TIME_LABEL_TEXT = "0:00:00 / 0:00:00";
    private static final String TIME_FORMAT = "%d:%02d:%02d / %d:%02d:%02d";

    private MediaPlayer myMediaPlayer;
    private MediaView myMediaView;
    private Slider myTimeSlider;
    private Label myTimeLabel;
    private Slider myVolumeSlider;
    private Duration myDuration;
    private boolean myVideoWillReplay = true;
    private boolean myVideoIsStopped;
    private boolean myCycleIsComplete;
    private HBox myMediaBar;

    public VideoPlayer (final MediaPlayer player) {
        createMediaPlayer(player);
        defineMediaBarBehavior();

        final Button PLAY_BUTTON = new Button(PLAY_BUTTON_TEXT);
        createAndDefineVisualComponents(player, PLAY_BUTTON);
        createAndDefineAudioComponents(player);

        defineMediaPlayerBehavior(player, PLAY_BUTTON);
    }

    private void createMediaPlayer (final MediaPlayer player) {
        myMediaPlayer = player;
        setStyle(MEDIA_PLAYER_BACKGROUND_COLOR);
        myMediaView = new MediaView(player);
        Pane moviePane = new Pane() { };
        moviePane.getChildren().add(myMediaView);
        moviePane.setStyle(MOVIE_PANE_BACKGROUND_COLOR);
        setCenter(moviePane);
    }

    private void defineMediaBarBehavior () {
        myMediaBar = new HBox();
        setBottom(myMediaBar);
        BorderPane.setAlignment(myMediaBar, Pos.CENTER);
        myMediaBar.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
    }

    private void createAndDefineVisualComponents (final MediaPlayer player, final Button button) {
        button.setPrefWidth(BUTTON_WIDTH);
        button.setOnAction(event->playOrPause(player));
        player.currentTimeProperty().addListener(observable->updateValues());
        myMediaBar.getChildren().addAll(button, new Label(SPACE));

        myTimeSlider = new Slider();
        HBox.setHgrow(myTimeSlider, Priority.ALWAYS);
        myTimeSlider.valueProperty().addListener(observable->bindPlayerAndSliderTimes(player));
        myMediaBar.getChildren().add(myTimeSlider);
        myTimeLabel = new Label(TIME_LABEL_TEXT);
        myTimeLabel.setPrefWidth(LABEL_WIDTH);
        myMediaBar.getChildren().add(myTimeLabel);
    }

    private void bindPlayerAndSliderTimes (final MediaPlayer player) {
        if (myTimeSlider.isValueChanging()) {
            player.seek(myDuration.multiply(myTimeSlider.getValue() / DOUBLE_CONVERT));
        }
    }

    private void playOrPause (final MediaPlayer player) {
        Status status = player.getStatus();
        if (status == Status.HALTED || status == Status.UNKNOWN) {
            return;
        }
        if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
            if (myCycleIsComplete) {
                player.seek(player.getStartTime());
                myCycleIsComplete = false;
            }
            player.play();
        }
        else {
            player.pause();
        }
    }

    private void createAndDefineAudioComponents (final MediaPlayer player) {
        final Button VOLUME_BUTTON = new Button(MUTE_BUTTON_TEXT);
        VOLUME_BUTTON.setPrefWidth(BUTTON_WIDTH);
        myMediaBar.getChildren().addAll(VOLUME_BUTTON, new Label(SPACE), new Label(VOLUME_LABEL_TEXT));

        myVolumeSlider = new Slider();
        player.volumeProperty().bind(myVolumeSlider.valueProperty().divide(DOUBLE_CONVERT));
        VOLUME_BUTTON.setOnAction(event->muteOrUnmute(player, VOLUME_BUTTON, myVolumeSlider));
        myMediaBar.getChildren().add(myVolumeSlider);
    }

    private void muteOrUnmute (final MediaPlayer player, final Button button, final Slider slider) {
        if (player.isMute()) {
            player.setMute(false);
            button.setText(MUTE_BUTTON_TEXT);
            slider.setOpacity(1.0);
        }
        else {
            player.setMute(true);
            button.setText(UNMUTE_BUTTON_TEXT);
            slider.setOpacity(0.5);
        }
    }

    private void defineMediaPlayerBehavior (final MediaPlayer player, final Button button) {
        player.setCycleCount(myVideoWillReplay ? MediaPlayer.INDEFINITE : 1);
        player.setOnPlaying(new Runnable() {
            public void run () {
                if (myVideoIsStopped) {
                    player.pause();
                    myVideoIsStopped = false;
                }
                button.setText(myVideoIsStopped ? PLAY_BUTTON_TEXT : STOP_BUTTON_TEXT);
            }
        });
        player.setOnPaused(new Runnable() {
            public void run () {
                button.setText(PLAY_BUTTON_TEXT);
            }
        });
        player.setOnReady(new Runnable() {
            public void run () {
                myDuration = player.getMedia().getDuration();
                updateValues();
            }
        });
        player.setOnEndOfMedia(new Runnable() {
            public void run () {
                button.setText(myVideoWillReplay ? STOP_BUTTON_TEXT : PLAY_BUTTON_TEXT);
                myVideoIsStopped = !myVideoWillReplay;
                myCycleIsComplete = !myVideoWillReplay;
            }
        });
    }

    private void updateValues () {
        Platform.runLater(new Runnable() {
            public void run () {
                Duration currentTime = myMediaPlayer.getCurrentTime();
                myTimeLabel.setText(calculateElapsedTime(currentTime, myDuration));
                myTimeSlider.setDisable(myDuration.isUnknown());

                boolean durationValid = myDuration.greaterThan(Duration.ZERO) ? true : false;
                boolean sliderActive = !myTimeSlider.isDisabled() ? true : false;
                boolean sliderValueChanging = !myTimeSlider.isValueChanging() ? true : false;

                if (durationValid && sliderActive && sliderValueChanging) {
                    double duration = myDuration.toMillis();
                    double doubleTime = currentTime.divide(duration).toMillis();
                    double timeSliderValue = doubleTime * DOUBLE_CONVERT;
                    myTimeSlider.setValue(timeSliderValue);
                }
                if (!myVolumeSlider.isValueChanging()) {
                    double playerVolume = myMediaPlayer.getVolume();
                    myVolumeSlider.setValue((int)Math.round(playerVolume * INT_CONVERT));
                }
            }
        });
    }

    private static String calculateElapsedTime (Duration elapsed, Duration videoDuration) {
        int seconds = (int)Math.floor(elapsed.toSeconds());

        int hours = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
        seconds %= MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
        int minutes = seconds / SECONDS_PER_MINUTE;
        seconds %= SECONDS_PER_MINUTE;

        return formatTime(videoDuration, hours, minutes, seconds);
    }

    private static String formatTime (Duration duration, int intHours, int intMin, int intSec) {
        int seconds = (int)Math.floor(duration.toSeconds()) + 5000;

        int hours = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
        seconds %= MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
        int minutes = seconds / SECONDS_PER_MINUTE;
        seconds %= SECONDS_PER_MINUTE;

        return String.format(TIME_FORMAT, intHours, intMin, intSec, hours, minutes, seconds);
    }
}