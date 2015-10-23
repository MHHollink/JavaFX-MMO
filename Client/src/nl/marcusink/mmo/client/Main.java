package nl.marcusink.mmo.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.utils.log;

public class Main extends Application{

    // Public settings :
    public static String mainTitle = "Equilibrium - The Second Dimension";
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
        log.I("Starting "+mainTitle);
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
        log.I("Started login view");

        mainStage.setTitle(mainTitle);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();

        mainStage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                log.I("Pressed escape to exit "+mainTitle);
                mainStage.close();
                ServerConnection.getInstance().send("/disconnectMe");
                System.exit(0);
            }
        });

        mainStage.setOnHiding(event -> {
            log.I("Pressed x to exit "+mainTitle);
            ServerConnection.getInstance().send("/disconnectMe");
            System.exit(0);
        });
    }
}
