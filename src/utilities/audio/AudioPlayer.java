package utilities.audio;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author $cotty $haw
 *
 * Our AudioPlayer  is our listener class. It uses a Socket object that has
 * two Streams: one for reading data in and one for writing data out.
 * The Server also needs enumeration and synchronization to avoid any
 * errors caused by other threads if they try to call sendToAll() and
 * removeConnection(). Also, we listen to ports to accept connections
 * and to return new Socket objects. This lets us get connections one
 * at a time if any are incoming.
 * 
 */
public class AudioPlayer {

    public static void playAudio (File file) {
        Media media = new Media(file.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

}
