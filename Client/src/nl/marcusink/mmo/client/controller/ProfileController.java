package nl.marcusink.mmo.client.controller;

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
        switch (server) {
            case "AA" :
                characters.addAll(new Avatar("Thor", 16), new Avatar("IronMan", 7));
                break;
            case "AB" :
                characters.addAll(new Avatar("Falcon", 4));
                break;
            case "AC" :
                characters.addAll(new Avatar("Hulk", 23));
                break;
            case "BA" :
                characters.addAll(new Avatar("NickFury", 5));
                break;
        }
    }

    @Override
    public void update(String data) {

    }

    public void createAvatar() {
        ServerConnection.getInstance().send("/create "+ User.getInstance().getPlayer().getUsername() +" Thor Male God Asgardian");
    }
}
