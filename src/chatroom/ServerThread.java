package chatroom;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {

    private Server server;
    private Socket socket;

    public ServerThread (Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        start();
    }

    // This runs in a separate thread when start() is called in the
    // constructor.
    public void run() {
        try {
            // DataInputStream for communication; the client
            // is using a DataOutputStream to write to us
            DataInputStream din = new DataInputStream(socket.getInputStream());

            // Perpetual loop for reading messages and transmitting them to all clients through the server
            while (true) {
                String message = din.readUTF();
                System.out.println("Sending " + message);
                server.sendToAll(message);
            }
        }
        catch (EOFException e) {
            System.out.println("EOFException occurred in ServerThread.java");
        }
        catch (IOException e) {
            System.out.println("IOException occurred in ServerThread.java");
        }
        finally {
            // Remove any closed connections
            server.removeConnection(socket);
        }
    }

}
