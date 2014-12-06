package chatroom;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
public class Client extends Panel implements Runnable {

    /**
     * Generated serial version ID
     */
    private static final long serialVersionUID = -1649877892361198998L;

    // Components for the visual display of the chat windows
    private TextField myTextField = new TextField();
    private TextArea myTextArea = new TextArea();

    // Socket connected to the server
    private Socket mySocket;

    // Streams from the socket that communicate to the server
    private DataOutputStream myDataOut;
    private DataInputStream myDataIn;

    protected Client (String host, int port) {

        setLayout(new BorderLayout());
        add("My text field", myTextField);
        add("My text area", myTextArea);

        // Receives messages when a user types a line and hits return
        myTextField.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                sendUserMessageToServer(e.getActionCommand());
            }
        });

        // Connects to the server
        try {
            mySocket = new Socket(host, port);
            System.out.println("connected to " + mySocket);

            myDataIn = new DataInputStream(mySocket.getInputStream());
            myDataOut = new DataOutputStream(mySocket.getOutputStream());

            // Starts background threads for receiving messages
            new Thread(this).start();
        }
        catch (IOException ex) {
            System.out.println("IOException occurred in Client.java");
        }
    }

    // Sends user messages to the server
    private void sendUserMessageToServer (String message) {
        try {
            myDataOut.writeUTF(message);
            myTextField.setText("");
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    // Runs on background threads to display messages from other windows
    public void run () {
        try {
            while (true) {
                String message = myDataIn.readUTF();
                myTextArea.append(message + "\n");
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}