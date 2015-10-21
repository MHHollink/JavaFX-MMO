package nl.marcusink.mmo.client.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerList extends HashMap<String, Player> {

    private static PlayerList instance;

    public static PlayerList getInstance() {
        if(instance == null){
            instance = new PlayerList();
        }
        return instance;
    }

    private PlayerList(){}

    @Override
    public Player put(String key, Player value) {
        return super.put(key.toLowerCase(), value);
    }

    @Override
    public Player get(Object key) {
        return super.get(key.toString().toLowerCase());
    }
}
