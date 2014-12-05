package chatroom;

import java.net.Socket;

public class ServerThread extends Thread {

    // The Server that spawned us
    private Server server;
    // The Socket connected to our client
    private Socket socket;
    // Constructor.

    public ServerThread (Server server, Socket socket) {
        this.server = server;
    }

}
