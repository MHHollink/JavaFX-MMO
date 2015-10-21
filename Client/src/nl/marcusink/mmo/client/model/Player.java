package nl.marcusink.mmo.client.model;

import com.google.gson.Gson;

import java.util.HashMap;

public class Player {

    private String username;
    private String passwordFirst;
    private String email;
    private String firstName;
    private String lastName;
    private Long birthday;

    private HashMap<String, Avatar> avatars;

    public Player(String username, String passwordFirst, String email,
                  String firstName, String lastName, Long birthday) {
        this.username = username;
        this.passwordFirst = passwordFirst;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;

        this.avatars = new HashMap<>();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void addAvatar(String name, Avatar avatar) {
        avatars.put(name.toLowerCase(), avatar);
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
