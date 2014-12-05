package chatroom;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class Server {

    private ServerSocket mySocket;
    private Dictionary myOutputStreams = new Hashtable();

    // Constructor and socket acceptor that listens
    public Server (int port) throws IOException {
        listen(port);
    }

    private void listen (int port) throws IOException {
        mySocket = new ServerSocket(port);
        System.out.println("Listening on " + mySocket);

        // Perpetual socket acceptance
        while (true) {
            Socket s = mySocket.accept();
            System.out.println("Connection from " + s);

            // DataOutputStream writes data to the other side
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            myOutputStreams.put(s, dout);
            new ServerThread(this, s);
        }
    }

    static public void main (String args[]) throws Exception {
        int port = Integer.parseInt(args[0]);
        new Server(port);
    }

    public void removeConnection (Socket socket) {
        // TODO Auto-generated method stub

    }

    public void sendToAll (String message) {
        // TODO Auto-generated method stub

    }

}
