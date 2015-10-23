package nl.marcusink.mmo.client.controller.connection;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import nl.marcusink.mmo.client.controller.connection.crypter.Crypt;
import nl.marcusink.mmo.client.utils.log;

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
import java.util.ArrayList;
import java.util.Scanner;

public class ServerConnectionRunnable implements Runnable {

    private boolean active = true;

    private static Socket socket;
    private static Scanner input;
    private static PrintWriter output;

    private ArrayList<SocketObserver> observers;
    private String data;

    protected ServerConnectionRunnable(Socket socketConnection) {
        try {
            socket = socketConnection;
            input = new Scanner(socketConnection.getInputStream());
            output = new PrintWriter(socketConnection.getOutputStream(), true);
        } catch (IOException e){
            e.printStackTrace();
        }

        observers = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            log.I("Server connection has been established, your game is now running");
            while (active) {
                if (input.hasNextLine()) {
                    data = receive( input.nextLine() );
                    notifyObservers();
                }
            }
            output.close();
            input.close();
            socket.close();
        }catch (Exception e){
            active = false;
            System.out.println("Socket is closed");
            e.printStackTrace();
        }
    }

    private void notifyObservers() {
        log.T("recieved a message from the server, notifying controller about the new data!");
        log.T(data);
        for (SocketObserver observer : observers) observer.update(data);
    }

    public void register(SocketObserver so){
        log.D(so.getClass().getSimpleName() + "has registered him self to the socket observer");
        observers.add(so);
    }

    public void unregister(SocketObserver so){
        log.D(so.getClass().getSimpleName() + "has unregistered him self to the socket observer");
        observers.remove(so);
    }

    protected void send(String s) {
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
