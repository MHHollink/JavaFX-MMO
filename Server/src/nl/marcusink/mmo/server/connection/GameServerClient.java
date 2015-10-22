package nl.marcusink.mmo.server.connection;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
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
            output.println(Crypt.encrypt("/connectedTo " + GameServer.getIp()));
            while (active) {
                if (input.hasNextLine()) {

                    String data = receive(input.nextLine()); // received data
                    String[] args = data.split(" ");         // split into separate arguments

                    log.I(clientPrefix + " > " + data);     // log the data that was recieved
                    String response = "";                   // Empty response that will be filled up ...

                    if(data.contains("/login")) {
                        if (Database.Queries.login(args[1], args[2])) response = "/login success";
                        else                                          response = "/login failed";
                    }

                    if(data.contains("/register")) {
                        if (Database.Queries.register(args[1], args[2], args[3], args[4], args[5], args[6])) response = "/register success";
                        else                                                                                 response = "/register failed";
                    }

                    if(data.contains("/create")){
                        if (Database.Queries.createAvatar(args[1], args[2], args[3], args[4], args[5])) response = "/create success";
                        else                                                                            response = "/create failed";
                    }

                    if(data.contains("/request")) {
                        if(data.contains("my-data")) response = "/request my-data " + Database.Queries.profileRequest(args[2]);
                    }

                    send(response);

                    if (data.contains("/disconnectMe")) active = false;

                } else active = false;
            }

        }catch (Exception e){
            log.E(clientPrefix+ " exited caused by exception");
            active=false;
            e.printStackTrace();
        }
        try { clientSocket.close(); } catch (IOException ignored) {}
        server.getClients().remove(this);
    }

    public void disconnect() {
        log.I(clientPrefix + " > Disconnecting client");
        try {
            output.println(Crypt.encrypt("/serverDisconnected"));
            active = false;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void send(String s) {
        try {
            output.println(
                    URLEncoder.encode(          // ENCODE THE
                            Crypt.encrypt(s),   // ENCRYPTED MESSAGE
                            "ASCII"
                    )
            );
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String receive(String s) {
        try {
            return Crypt.decrypt(           // DECRYPT THE
                    URLDecoder.decode(      // DECODED MESSAGE
                            s,
                            "UTF-8"
                    )
            );
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | NoSuchPaddingException | Base64DecodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
