package chatroom;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * @author $cotty $haw
 *
 * Our ServerThread is our per-thread class. Multithreading allows us
 * to respond as quickly as we can to every user by creating a thread
 * for each connection. This class also removes any closed connection
 * to prevent exceptions, lag, and the overuse of memory. We also try
 * to read incoming messages for the server to write to every client.
 * 
 */
public class ServerThread extends Thread {

    // Server for spawning threads
    private Server myServer;
    // Socket connected to the client
    private Socket mySocket;

    protected ServerThread (Server server, Socket socket) {
        this.myServer = server;
        this.mySocket = socket;

        start();
    }

    // Runs in a separate thread when start() is called in the constructor
    public void run () {
        try {

            // DataInputStream used for receiving what the client writes
            DataInputStream myDataIn = new DataInputStream(mySocket.getInputStream());

            // Infinite loop for reading in messages for the server to send to all clients
            while (true) {
                String message = myDataIn.readUTF();
                System.out.println("Sending " + message);
                myServer.sendToAll(message);
            }
        }
        catch (EOFException ex) {
            System.out.println("EOFException occurred in ServerThread.java");
        }
        catch (IOException ex) {
            System.out.println("IOException occurred in ServerThread.java");
        }
        finally {
            // Removes any closed connections
            myServer.removeConnection(mySocket);
        }
    }
}