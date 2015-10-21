package nl.marcusink.mmo.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.controller.connection.hasher.Hash;
import nl.marcusink.mmo.client.model.Player;
import nl.marcusink.mmo.client.model.PlayerList;

import java.util.Date;

public class Main extends Application{

    // Public settings :
    public static String mainTitle = "Equilibrium - The Second Dimension";
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Scene scene = new Scene(
                FXMLLoader.load(
                        getClass().getResource(
                                "/views/login.fxml"
                        )
                )
        );

        mainStage.setTitle(mainTitle);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();

        mainStage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                mainStage.close();
                ServerConnection.getInstance().getRunnable().send("/disconnectMe");
            }
        });


        mainStage.setOnHiding(event -> {
            ServerConnection.getInstance().getRunnable().send("/disconnectMe");
        });

        Player p = new Player("Mjollnir94", Hash.md5("askask"), "mh@mail.com","Marcel","Hollink", new Date().getTime());
        PlayerList.getInstance().put("Mjollnir94", p);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
