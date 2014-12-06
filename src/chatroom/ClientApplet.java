package chatroom;

import java.applet.Applet;
import java.awt.LayoutManager;

import javafx.scene.layout.BorderPane;

/**
 * @author $cotty $haw
 * 
 * Our ClientApplet is currently set up as an applet and is available
 * on a Web page. It can easily be changed into a stand-alone app and
 * maintain the ability to run its own process, like our Server.
 * 
 */
public class ClientApplet extends Applet {

    // String constants
    private static final String HOST_NAME = "host";
    private static final String PORT_NUMBER = "port";
    private static final String ADD_MESSAGE = "Center";

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = 5484563350299263809L;

    protected void initialize () {
        String host = getParameter(HOST_NAME);
        int port = Integer.parseInt(getParameter(PORT_NUMBER));
        setLayout((LayoutManager)new BorderPane());
        add(ADD_MESSAGE, new Client(host, port));
    }
}
