package nl.marcusink.mmo.client.controller.connection;

import nl.marcusink.mmo.client.utils.log;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection {

    public static final String ip = "";
    public static final int port  = 5576;

    private ServerConnectionRunnable runnable;

    private static ServerConnection instance;
    public static ServerConnection getInstance() {
        if(instance == null) instance = new ServerConnection();
        return instance;
    }

    private ServerConnection() {
        log.I("Server connection is being established");
        try {
            Socket socketConnection = new Socket(ip, port);
            //socketConnection = new Socket(InetAddress.getLocalHost(), port);

            runnable = new ServerConnectionRunnable(socketConnection);
            new Thread(runnable).start();

        } catch (IOException e) {
            log.E("The connection could not be established!");
            e.printStackTrace();
        }
    }

    public void send(String s) {
        runnable.send(s);
    }

    public ServerConnectionRunnable getRunnable() {
        return runnable;
    }
}
