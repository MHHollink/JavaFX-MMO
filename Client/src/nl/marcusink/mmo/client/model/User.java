package nl.marcusink.mmo.client.model;


import com.google.gson.Gson;

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
            System.out.println("You were already logged in!");
        }
        loggedIn = true;
    }

    public void logOut(){
        loggedIn = false;
    }

    public void setPlayer(Player player) {
        if(loggedIn) {
            this.player = player;
        } else {
            System.out.println("You were already logged in!");
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
