package nl.marcusink.mmo.server.connection;

import nl.marcusink.mmo.server.connection.crypter.Crypt;
import nl.marcusink.mmo.server.database.Database;
import nl.marcusink.mmo.server.utils.log;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class GameServerClient implements Runnable{

    // Server properties
    private final GameServer server;
    private Socket clientSocket;
    private boolean active = true;
    private Scanner input;
    private PrintWriter output;

    // Client ID;
    private String clientPrefix;

    /**
     * MMOClient constructor, constructs a single client instance for each individual client
     *
     * @param server
     *          Server object, containing information about the server and its database
     * @param clientSocket
     *          Client socket, hold the information about the created client socket for input and output stream
     * @param connectionID
     *          The client number, used as indicator to see how many clients are connected,
     *          and which spot in the clients array the user is saved
     */
    public GameServerClient(GameServer server, Socket clientSocket, int connectionID) {
        this.server = server;
        this.clientSocket = clientSocket;

        this.clientPrefix = "Client "+connectionID+" ";

        log.I("new client has been created with clientId set to '" + clientPrefix + "'");
    }

    @Override
    public void run() {
        try{
            input = new Scanner(clientSocket.getInputStream());
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            output.println("/connectedTo "+ GameServer.getIp());
            while (active) {
                if (input.hasNextLine()) {
                    String data = Crypt.decrypt(input.nextLine());
                    String[] args = data.split(" ");

                    log.I(clientPrefix + " > " + data);

                    if(data.contains("/login") && Database.Queries.login(null, null))

                    if (data.contains("/disconnectMe")) active = false;

                } else active = false;
            }

        }catch (Exception e){
            log.E(clientPrefix+ " exited caused by exeption");
            active=false;
            e.printStackTrace();
        }
        try { clientSocket.close(); } catch (IOException ignored) {}
        server.getClients().remove(this);
        Thread.currentThread().stop();
    }

    public void disconnect() {
        log.I(clientPrefix + " > Disconnectiong client" );
        try {
            output.println(Crypt.encrypt("/serverDisconnected"));
            active = false;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
