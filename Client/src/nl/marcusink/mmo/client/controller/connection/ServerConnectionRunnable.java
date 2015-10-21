package nl.marcusink.mmo.client.controller.connection;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import nl.marcusink.mmo.client.controller.connection.crypter.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
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
            while (active) {
                if (input.hasNextLine()) {
                    data = input.nextLine();
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
        try {
            data = Crypt.decrypt(data);
            for (SocketObserver observer : observers) observer.update(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | Base64DecodingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void register(SocketObserver so){
        observers.add(so);
    };

    public void unregister(SocketObserver so){
        observers.remove(so);
    };

    public void send(String s) {
        try {
            output.println(Crypt.encrypt(s));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
