package utilities.video;

import javafx.application.Platform;
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
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

class VideoPlayer extends BorderPane {

    private static final int PADDING = 20;
    private static final int SLIDER_WIDTH = 50;
    private static final int BUTTON_WIDTH = 75;
    private static final int INT_CONVERT = 100;
    private static final int LABEL_WIDTH = 150;

    private static final double DOUBLE_CONVERT = 100.0;

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;

    private static final String SPACE = "      ";

    private static final String MEDIA_PLAYER_BACKGROUND_COLOR = "-fx-background-color: #bfc2c7;";
    private static final String MOVIE_PANE_BACKGROUND_COLOR = "-fx-background-color: #000000;";

    private static final String PLAY_BUTTON_TEXT = "PLAY";
    private static final String STOP_BUTTON_TEXT = "STOP";
    private static final String MUTE_BUTTON_TEXT = "MUTE";
    private static final String UNMUTE_BUTTON_TEXT = "UNMUTE";

    private static final String VOLUME_LABEL_TEXT = "Volume: ";
    private static final String TIME_LABEL_TEXT = "0:00:00 / 0:00:00";
    private static final String TIME_FORMAT = "%d:%02d:%02d/%d:%02d:%02d";

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
        definePlayButtonBehavior(player, PLAY_BUTTON);

        createAndDefineTimeComponents(player);

        final Button VOLUME_BUTTON = new Button(MUTE_BUTTON_TEXT);
        createAndDefineVolumeComponents(player, VOLUME_BUTTON);

        defineMediaPlayerBehavior(player, PLAY_BUTTON);
    }

    private void createMediaPlayer (final MediaPlayer player) {
        this.myMediaPlayer = player;
        setStyle(MEDIA_PLAYER_BACKGROUND_COLOR);
        myMediaView = new MediaView(player);
        Pane moviePane = new Pane() { };
        moviePane.getChildren().add(myMediaView);
        moviePane.setStyle(MOVIE_PANE_BACKGROUND_COLOR);
        setCenter(moviePane);
    }

    private void defineMediaBarBehavior () {
        myMediaBar = new HBox();
        myMediaBar.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(myMediaBar, Pos.CENTER);
        myMediaBar.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        setBottom(myMediaBar);
        myMediaBar.getChildren().add(new Label(SPACE));
    }

    private void definePlayButtonBehavior (final MediaPlayer player, final Button button) {
        button.setPrefWidth(BUTTON_WIDTH);
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle (ActionEvent e) {
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
        });
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated (Observable observable) {
                updateValues();
            }
        });
        myMediaBar.getChildren().add(button);
        myMediaBar.getChildren().add(new Label(SPACE));
    }

    private void createAndDefineTimeComponents (final MediaPlayer player) {
        myTimeSlider = new Slider();
        HBox.setHgrow(myTimeSlider, Priority.ALWAYS);
        myTimeSlider.setMinWidth(SLIDER_WIDTH);
        myTimeSlider.setMaxWidth(Double.MAX_VALUE);
        myTimeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated (Observable observable) {
                if (myTimeSlider.isValueChanging()) {
                    player.seek(myDuration.multiply(myTimeSlider.getValue() / DOUBLE_CONVERT));
                }
            }
        });
        myMediaBar.getChildren().add(myTimeSlider);
        myTimeLabel = new Label(TIME_LABEL_TEXT);
        myTimeLabel.setPrefWidth(LABEL_WIDTH);
        myMediaBar.getChildren().add(myTimeLabel);
    }

    private void createAndDefineVolumeComponents (final MediaPlayer player, final Button button) {
        button.setPrefWidth(BUTTON_WIDTH);
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle (ActionEvent e) {
                double volume = player.getVolume();
                player.setVolume(volume == 0.0 ? 1.0 : 0.0);
                button.setText(volume == 0.0 ? MUTE_BUTTON_TEXT : UNMUTE_BUTTON_TEXT);
            }
        });
        myMediaBar.getChildren().add(button);
        myMediaBar.getChildren().add(new Label(SPACE));
        myMediaBar.getChildren().add(new Label(VOLUME_LABEL_TEXT));
        myVolumeSlider = new Slider();
        myVolumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated (Observable observable) {
                if (myVolumeSlider.isValueChanging()) {
                    player.setVolume(myVolumeSlider.getValue() / DOUBLE_CONVERT);
                }
                button.setText(player.getVolume() > 0.0 ? MUTE_BUTTON_TEXT : UNMUTE_BUTTON_TEXT);
            }
        });
        myMediaBar.getChildren().add(myVolumeSlider);
        myMediaBar.getChildren().add(new Label(SPACE));
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
        int time = (int)Math.floor(elapsed.toSeconds());

        int hours = time / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
        time -= hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
        int minutes = time / SECONDS_PER_MINUTE;
        time -= minutes * SECONDS_PER_MINUTE;
        int seconds = time;

        return formatTime(videoDuration, hours, minutes, seconds);
    }

    private static String formatTime (Duration duration, int intHours, int intMin, int intSec) {
        int time = (int)Math.floor(duration.toSeconds());

        int hours = time / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
        time -= hours * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
        int minutes = time / SECONDS_PER_MINUTE;
        time -= minutes * SECONDS_PER_MINUTE;
        int seconds = time;

        return String.format(TIME_FORMAT, intHours, intMin, intSec, hours, minutes, seconds);
    }
}
