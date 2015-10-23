package nl.marcusink.mmo.client.controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.controller.connection.SocketObserver;
import nl.marcusink.mmo.client.model.Avatar;
import nl.marcusink.mmo.client.model.User;
import nl.marcusink.mmo.client.utils.log;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ProfileController implements SocketObserver {

    public ChoiceBox serverSelector;
    public TableView charactersTableView;
    public TableColumn nameColumn;
    public TableColumn levelColumn;
    public Button createAvatar;

    private static ObservableList<Avatar> characters = FXCollections.observableArrayList();

    @SuppressWarnings("unused")
    @FXML
    protected void initialize() {
        log.D("initializing " + getClass().getSimpleName());
        ServerConnection.getInstance().getRunnable().register(this);

        serverSelector.getItems().add(0, "AA");
        serverSelector.getItems().add(1, "AB");
        serverSelector.getItems().add(2, "AC");
        serverSelector.getItems().add(3, "BA");

        serverSelector.setValue("AA");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Avatar,String>("name")
        );
        levelColumn.setCellValueFactory(
                new PropertyValueFactory<Avatar, Integer>("level")
        );

        charactersTableView.setItems(characters);
    }

    public void serverSelectChange() {
        reloadCharacters((String) serverSelector.getValue());
    }

    private void reloadCharacters(String server) {
        characters.clear();
        ServerConnection.getInstance().send("/request avatars "+User.getInstance().getPlayer().getUsername());
    }

    @Override
    public void update(String data) {
        if(data.contains("/request")) {
            if (data.contains("avatars")) {
                ArrayList<Avatar> avatars = new Gson().fromJson(data.split(" ")[2], ArrayList.class);

                for (Avatar avatar : avatars) {
                    User.getInstance().getPlayer().addAvatar(avatar);
                }

                characters.addAll(User.getInstance().getPlayer().getAvatars());
            }
        }
    }

    public void createAvatar() {
        ServerConnection.getInstance().send("/create "+ User.getInstance().getPlayer().getUsername() +" Thor Male God Asgardian");
    }
}
