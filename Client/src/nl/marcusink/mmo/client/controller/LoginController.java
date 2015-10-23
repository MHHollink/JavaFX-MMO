package nl.marcusink.mmo.client.controller;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nl.marcusink.mmo.client.Main;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.controller.connection.SocketObserver;
import nl.marcusink.mmo.client.controller.connection.hasher.Hash;
import nl.marcusink.mmo.client.model.Player;
import nl.marcusink.mmo.client.model.User;
import nl.marcusink.mmo.client.utils.log;

import java.io.IOException;

public class LoginController implements SocketObserver {

    public TextField username;
    public PasswordField password;
    public Button submitLogin;
    public Button register;
    public Label errorLabel;

    public void handleLoginSubmit() {
        log.I("Attempt lo login");

        String uName = username.getText();
        String pass = password.getText();

        ServerConnection.getInstance().send("/login "+ uName +" "+ Hash.md5(pass));
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
        ServerConnection.getInstance().getRunnable().unregister(this);
    }

    @SuppressWarnings("unused")
    @FXML
    protected void initialize() {
        log.D("initializing "+getClass().getSimpleName());
        ServerConnection.getInstance().getRunnable().register(this);
    }

    @Override
    public void update(String data) {
        if(data.contains("/login")){
            if(data.contains("success")) {
                User.getInstance().login();
                ServerConnection.getInstance().send("/request my-data " + username.getText());
            }

            if(data.contains("failed")) {
                username.setText("");
                password.setText("");
                errorLabel.setVisible(true);
            }
        }

        if(data.contains("/request")) {
            if (data.contains("my-data")) {
                Player player = new Gson().fromJson(data.split(" ")[2], Player.class);
                User.getInstance().setPlayer(player);

                try {
                    Scene scene = new Scene(
                            FXMLLoader.load(
                                    Main.class.getClass().getResource(
                                            "/views/profile.fxml"
                                    )
                            )
                    );
                    ServerConnection.getInstance().getRunnable().unregister(this);
                    Platform.runLater(() -> Main.mainStage.setScene(scene));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
