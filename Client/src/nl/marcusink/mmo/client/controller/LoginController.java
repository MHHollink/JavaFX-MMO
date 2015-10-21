package nl.marcusink.mmo.client.controller;

import com.sun.corba.se.spi.activation.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.marcusink.mmo.client.Main;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.controller.connection.ServerConnectionRunnable;
import nl.marcusink.mmo.client.controller.connection.SocketObserver;
import nl.marcusink.mmo.client.model.Player;
import nl.marcusink.mmo.client.model.PlayerList;
import nl.marcusink.mmo.client.model.User;

import java.io.IOException;

public class LoginController implements SocketObserver {

    public TextField username;
    public PasswordField password;
    public Button submitLogin;
    public Button register;

    public void handleLoginSubmit() {

        String uname = username.getText();
        String pass = password.getText();

        if(PlayerList.getInstance().get(uname) != null) {
            // Player does exist
            Player player = PlayerList.getInstance().get(uname);
            User.getInstance().login();
            User.getInstance().setPlayer(player);

            try {
                Scene scene = new Scene(
                        FXMLLoader.load(
                                Main.class.getClass().getResource(
                                        "/views/profile.fxml"
                                )
                        )
                );
                Main.mainStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Player does NOT exist
        }
    }

    public void handleRegister() throws Exception{
        Scene scene = new Scene(
                FXMLLoader.load(
                        Main.class.getClass().getResource(
                                "/views/register.fxml"
                        )
                )
        );
        Main.mainStage.setScene(scene);
    }

    @FXML
    protected void initialize() {
        ServerConnection.getInstance().getRunnable().register(this);
    }

    @Override
    public void update(String data) {

    }
}
