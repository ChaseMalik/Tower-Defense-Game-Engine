package chatroom;

import java.net.Socket;

public class ServerThread extends Thread {

    private Server server;
    private Socket socket;

    public ServerThread (Server server, Socket socket) {
        this.server = server;
    }

}
