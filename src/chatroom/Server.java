package chatroom;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class Server {

    private ServerSocket socket;
    private Dictionary outputStreams = new Hashtable();

    // Constructor and socket acceptor that listens
    public Server (int port) throws IOException {
        listen(port);
    }

    private void listen (int port) throws IOException {
        socket = new ServerSocket(port);
        System.out.println("Listening on " + socket);

        // Perpetual socket acceptance
        while (true) {
            Socket s = socket.accept();
            System.out.println("Connection from " + s);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            outputStreams.put(s, dout);
            new ServerThread(this, s);
        }
    }

    static public void main (String args[]) throws Exception {
        int port = Integer.parseInt(args[0]);
        new Server(port);
    }

}
