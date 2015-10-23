package nl.marcusink.mmo.client.model;

import com.google.gson.Gson;
import nl.marcusink.mmo.client.utils.log;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private String username;
    private String passwordFirst;
    private String email;
    private String fName;
    private String lName;
    private String BDay;

    private HashMap<String, Avatar> avatars;

    public Player(String username, String passwordFirst, String email,
                  String firstName, String lastName, String birthday) {
        this.username = username;
        this.passwordFirst = passwordFirst;
        this.email = email;
        this.fName = firstName;
        this.lName = lastName;
        this.BDay = birthday;

        log.D("An player has been created");
        log.D("These are the details: " + this.toString());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordFirst() {
        return passwordFirst;
    }

    public void setPasswordFirst(String passwordFirst) {
        this.passwordFirst = passwordFirst;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBDay() {
        return BDay;
    }

    public void setBDay(String BDay) {
        this.BDay = BDay;
    }

    public void addAvatar(Avatar avatar) {
        if (avatars == null) this.avatars = new HashMap<>();
        avatars.put(avatar.getName().toLowerCase(), avatar);
    }

    public Avatar getAvatarByName(String name) {
        if (avatars == null) return null;
        return avatars.get(name.toLowerCase());
    }

    public ArrayList<Avatar> getAvatars () {
        ArrayList<Avatar> list = new ArrayList<>();
        if (avatars == null) this.avatars = new HashMap<>();

        for (String key : avatars.keySet()) {
            list.add(avatars.get(key));
        }
        return list;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
