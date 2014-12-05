package chatroom;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {

    // The ServerSocket we'll use for accepting new connections
    private ServerSocket ss;
    // A mapping from sockets to DataOutputStreams. This will
    // help us avoid having to create a DataOutputStream each time
    // we want to write to a stream.
    private Hashtable outputStreams = new Hashtable();

    // Constructor and while-accept loop all in one.
    public Server (int port) throws IOException {
        // All we have to do is listen
        listen(port);
    }

    private void listen (int port) throws IOException {
        // Create the ServerSocket
        ss = new ServerSocket(port);
        // Tell the world we're ready to go
        System.out.println("Listening on " + ss);
        // Keep accepting connections forever
        while (true) {
            // Grab the next incoming connection
            Socket s = ss.accept();
            // Tell the world we've got it
            System.out.println("Connection from " + s);
            // Create a DataOutputStream for writing data to the
            // other side
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            // Save this stream so we don't need to make it again
            outputStreams.put(s, dout);
            // Create a new thread for this connection, and then forget
            // about it
            new ServerThread(this, s);
        }
    }

    // Main routine
    // Usage: java Server >port<
    static public void main (String args[]) throws Exception {
        // Get the port # from the command line
        int port = Integer.parseInt(args[0]);
        // Create a Server object, which will automatically begin
        // accepting connections.
        new Server(port);
    }

}
