package nl.marcusink.mmo.client.model;


import com.google.gson.Gson;
import nl.marcusink.mmo.client.utils.log;

public class User {
    private static User instance = new User();

    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    private Player player;
    private boolean loggedIn;

    private User() {
        loggedIn = false;
        player = null;
    }

    public void login(){
        if(loggedIn) {
            log.W("You were already logged in!");
        } else { log.I("You logged in!"); }
        loggedIn = true;
    }

    public void logOut(){
        if (loggedIn) log.I("you have been logged out");
        loggedIn = false;
        player = null;
    }

    public void setPlayer(Player player) {
        if(loggedIn) {
            this.player = player;
            log.D("Your account has been set with the following user details: "+ player.toString());
        } else {
            log.W("Something went wrong while setting your user details in place");
            log.W("Please redo the login");
        }
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
