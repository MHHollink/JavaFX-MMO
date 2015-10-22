package nl.marcusink.mmo.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import nl.marcusink.mmo.client.Main;
import nl.marcusink.mmo.client.controller.connection.ServerConnection;
import nl.marcusink.mmo.client.controller.connection.SocketObserver;
import nl.marcusink.mmo.client.controller.connection.hasher.Hash;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterController implements SocketObserver {

    public TextField username;
    public PasswordField passwordFirst;
    public PasswordField passwordSecond;
    public TextField email;
    public TextField firstName;
    public TextField lastName;
    public DatePicker birthday;
    public Button register;
    public Label usernameRequiredLabel;
    public Label passwordRequiredLabel;
    public Label passwordMatchLabel;
    public Label emailInvalidLabel;
    public Label firstNameNoAZLabel;
    public Label lastNameNoAZLabel;
    public Label birthdayRequiredLabel;
    public Label usernameExistsLabel;

    public void handleRegister() {
        String fName, lName, uName, pass, pass2 , mail;
        LocalDate date;

        fName = firstName.getText();
        lName = lastName.getText();
        uName = username.getText();
        pass = passwordFirst.getText();
        pass2 = passwordSecond.getText();
        mail = email.getText();
        date = birthday.getValue();

        usernameRequiredLabel.setVisible(false);
        passwordRequiredLabel.setVisible(false);
        passwordMatchLabel.setVisible(false);
        firstNameNoAZLabel.setVisible(false);
        lastNameNoAZLabel.setVisible(false);
        emailInvalidLabel.setVisible(false);
        birthdayRequiredLabel.setVisible(false);
        usernameExistsLabel.setVisible(false);

        if(uName != null && uName.length() >= 6) {
            if (pass != null && pass.length() >= 6) {
                if (isMatch(pass, pass2)) {
                    if (isOnlyLetters(fName)) {
                        if (isOnlyLetters(lName)) {
                            if (isValidEmail(mail)) {
                                if (date!=null) {

                                        doRegister(uName, fName, lName, pass, mail, date.toEpochDay());

                                } else birthdayRequiredLabel.setVisible(true);
                            } else emailInvalidLabel.setVisible(true);
                        } else lastNameNoAZLabel.setVisible(true);
                    } else firstNameNoAZLabel.setVisible(true);
                } else passwordMatchLabel.setVisible(true);
            } else passwordRequiredLabel.setVisible(true);
        } else usernameRequiredLabel.setVisible(true);
    }

    private void doRegister(String username, String firstName, String lastName, String password, String email, long birthday) {

            password = Hash.md5(password);
            ServerConnection.getInstance().send("/register "+username+" "+password+" "+email+" "+firstName+" "+lastName+" "+String.valueOf(birthday));

    }

    @SuppressWarnings("unused")
    @FXML
    protected void initialize() {
        ServerConnection.getInstance().getRunnable().register(this);
    }

    private boolean isValidEmail(String input){
        return (input != null) && input.matches("([A-z0-9-]+(?:\\.[A-z0-9-]+)*)@((?:[A-z0-9-]+\\.)*[A-z0-9-]{1,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)");
    }

    private boolean isMatch(String a, String b){
        return a.equals(b);
    }

    private boolean isOnlyLetters(String input) {
        return input != null && input.matches("([A-Za-z])+");
    }

    @Override
    public void update(String data) {
        if(data.contains("/register")) {
            if(data.contains("success")) {
                try {
                    Scene scene = new Scene(
                            FXMLLoader.load(
                                    Main.class.getClass().getResource(
                                            "/views/login.fxml"
                                    )
                            )
                    );
                    ServerConnection.getInstance().getRunnable().unregister(this);
                    Platform.runLater(() -> Main.mainStage.setScene(scene));
                } catch (IOException e) {
                    // Show notification of some error
                    e.printStackTrace();
                }
            }
            if(data.contains("failed")) {
                usernameExistsLabel.setVisible(true);
            }
        }
    }
}
