package chatroom;

import java.applet.Applet;
import java.awt.LayoutManager;

import javafx.scene.layout.BorderPane;

/**
 * @author $cotty $haw
 *
 * The Command Factory uses reflection to hide implementation details
 * and creates the commands from the user's inputs using a key-value
 * system.
 * 
 * It is static because our createCommand method is recursive, so we
 * need to avoid passing the factory to the individual commands.
 * 
 * Listener class
 * While-Accept loop
 * Per-Thread class
 * While-Read/Write loop (Server side)
 * Removing dead connections
 * Client class
 * While-Read/Write loop (Client side)
 */
public class ClientApplet extends Applet {

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = 5484563350299263809L;

    protected void initialize () {
        String host = getParameter("host");
        int port = Integer.parseInt(getParameter("port"));
        setLayout((LayoutManager)new BorderPane());
        add("Center", new Client(host, port));
    }
}
