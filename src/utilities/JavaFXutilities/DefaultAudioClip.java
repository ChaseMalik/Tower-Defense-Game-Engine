package utilities.JavaFXutilities;

import javafx.scene.media.AudioClip;
import javafx.scene.media.AudioTrack;

public class DefaultAudioClip extends AudioClip {

    public void setAudioBalance (double balance) {
        setBalance(balance);
    }

    public void setAudioVolume (double volume) {
        setVolume(volume);
    }
}
