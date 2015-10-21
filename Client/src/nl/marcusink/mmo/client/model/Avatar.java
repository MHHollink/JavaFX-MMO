package nl.marcusink.mmo.client.model;


import com.google.gson.Gson;

public class Avatar {

    private String name;
    private int level;

    public Avatar(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
