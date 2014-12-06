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
 * Client class
 * While-Read/Write loop (Client side)
 */
public class Client extends Panel implements Runnable {

    // String constants
    private static final String MY_TEXT_FIELD_MESSAGE = "My text field";
    private static final String MY_TEXT_AREA_MESSAGE = "My text area";
    private static final String CONNECTED_TO_MY_SOCKET_MESSAGE = "connected to ";
    private static final String EMPTY_STRING = "";
    private static final String NEWLINE = "\n";
    private static final String IO_EXCEPTION_MESSAGE = "IOException in Client.java";

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
        add(MY_TEXT_FIELD_MESSAGE, myTextField);
        add(MY_TEXT_AREA_MESSAGE, myTextArea);

        // Receives messages when a user types a line and hits return
        myTextField.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                sendUserMessageToServer(e.getActionCommand());
            }
        });

        // Connects to the server
        try {
            mySocket = new Socket(host, port);
            System.out.println(CONNECTED_TO_MY_SOCKET_MESSAGE + mySocket);

            myDataIn = new DataInputStream(mySocket.getInputStream());
            myDataOut = new DataOutputStream(mySocket.getOutputStream());

            // Starts background threads for receiving messages
            new Thread(this).start();
        }
        catch (IOException ex) {
            System.out.println(IO_EXCEPTION_MESSAGE);
        }
    }

    // Sends user messages to the server
    private void sendUserMessageToServer (String message) {
        try {
            myDataOut.writeUTF(message);
            myTextField.setText(EMPTY_STRING);
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
                myTextArea.append(message + NEWLINE);
            }
        }
        catch (IOException ex) {
            System.out.println(IO_EXCEPTION_MESSAGE);
        }
    }
}