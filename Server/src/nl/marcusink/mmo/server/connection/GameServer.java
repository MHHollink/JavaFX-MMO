package nl.marcusink.mmo.server.connection;

import nl.marcusink.mmo.server.database.Database;
import nl.marcusink.mmo.server.database.table.Server;
import nl.marcusink.mmo.server.utils.log;
import org.hibernate.Session;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServer {
    // 92.108.159.52:25565
    private static String IP = "127.0.0.1"; // DEFAULT TO LOCALHOST
    private static int PORT = 5576;        // ALWAYS BIND TO PORT 25565

    // Server socket and connected clients arrayList
    private static ServerSocket server;
    private ArrayList<GameServerClient> clients;

    // Scanner to catch console input and the active state of the main thread.
    private Scanner commandLineScanner;
    final boolean[] active = {true};

    public GameServer() {
        try {
            IP = getIp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.I("Server has been started on "+IP);
        this.commandLineScanner = new Scanner(System.in);

        // Async thread holding the console scanner, trying to catch 'stop' in each loop.
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (active[0]) {
                    String command = commandLineScanner.nextLine();

                    if(command.contains("stop")) {

                        for (GameServerClient client : clients) {
                            log.I("Server is closing");
                            client.disconnect();
                        }

                        setServerActiveState(IP+":"+PORT,false);

                        try {
                            server.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        active[0] = false;
                        log.I("Server has stopped");
                        System.exit(0);

                    }
                }
            }
        }).start();


        try {
            server = new ServerSocket(PORT);
            clients = new ArrayList<>();

            log.I("Server updating activeState");
            setServerActiveState(IP + ":" + PORT, true);

            log.I("Server is starting to accept clients");
            // main thread loop
            while(active[0]){
                Socket clientSocket = server.accept();
                log.I("New client is trying to connect");
                GameServerClient client = new GameServerClient(this, clientSocket, clients.size());

                clients.add(client);
                new Thread(client).start();
            }
        }
        catch (IOException e) {

        }
    }

    /**
     * When the server is started in the main thread, the database receives an update about the activity of the server.
     * The Method also handles the shutdown of the server. Setting its active state in the DB to false.
     *
     * @param serverIp
     *          What ip is used in this server, this parameter should be formatted as [IP:PORT]
     * @param state
     *          What is the new state of the server, active = true / disabled = false
     */
    public void setServerActiveState(String serverIp, boolean state) {
        Session session = Database.getDatabaseInstance().getSessionFactory().openSession();
        session.beginTransaction();

        Server server = session.get(Server.class, serverIp);
        if(server == null) {
            log.W("Server was not registered, it wil be inserted in the database");
            server = new Server(serverIp, "", "", 0, 0, state);
            log.W("Server was inserted but is not configured. Please do so before next run!");
        }

        log.I("The server is now active");
        server.setActive(state);
        session.save(server);
        session.getTransaction().commit();
        session.close();
    }

    public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            return in.readLine();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<GameServerClient> getClients() {
        return clients;
    }

    public static String getIP() {
        return IP+":"+PORT;
    }
}
