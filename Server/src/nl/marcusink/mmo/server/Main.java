package nl.marcusink.mmo.server;


import nl.marcusink.mmo.server.connection.GameServer;

import java.util.logging.Level;

public class Main {

    // Public settings :
    public static void main(String[] args){
         java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.ALL);
         new GameServer();
    }

}
