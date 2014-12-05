package chatroom;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Server {

    // ServerSocket for accepting new connections
    private ServerSocket mySocket;
    private Dictionary<Socket, DataOutputStream> myOutputStreams =
            new Hashtable<Socket, DataOutputStream>();

    // Constructor socket acceptor that listens to ports
    public Server (int port) throws IOException {
        listen(port);
    }

    // Listens to ports to accept connections
    private void listen (int port) throws IOException {
        mySocket = new ServerSocket(port);
        System.out.println("Listening on " + mySocket);

        // Infinite loop for accepting connections
        while (true) {
            Socket s = mySocket.accept();
            System.out.println("Connection from " + s);

            // DataOutputStream for writing data to the others
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            myOutputStreams.put(s, dataOut);

            new ServerThread(this, s);
        }
    }

    // Gets an enumeration of each OutputStream for all connected clients
    Enumeration<DataOutputStream> getOutputStreams () {
        return myOutputStreams.elements();
    }

    // Sends a message to all clients (utility routine)
    void sendToAll (String message) {

        // Synchronization to prevent errors if another thread calls removeConnection()
        synchronized (myOutputStreams) {

            // Gets the output stream and send the message for each client
            for (Enumeration<?> e = getOutputStreams(); e.hasMoreElements();) {

                DataOutputStream dataOut = (DataOutputStream)e.nextElement();

                try {
                    dataOut.writeUTF(message);
                }
                catch (IOException ex) {
                    System.out.println("IOException occured in Server.java");
                }
            }
        }
    }

    // Remove a socket and its corresponding output stream if the client's connection is closed
    void removeConnection (Socket s) {
        // Synchronization to prevent errors if another thread calls sendToAll()
        synchronized (myOutputStreams) {
            System.out.println("Removing connection to " + s);
            myOutputStreams.remove(s);

            try {
                s.close();
            }
            catch (IOException ex) {
                System.out.println("Error closing " + s);
                ex.printStackTrace();
            }
        }
    }

    public static void main (String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        new Server(port);
    }
}
