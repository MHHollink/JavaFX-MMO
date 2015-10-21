package nl.marcusink.mmo.client.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import nl.marcusink.mmo.client.Main;
import nl.marcusink.mmo.client.controller.connection.SocketObserver;
import nl.marcusink.mmo.client.controller.connection.crypter.Crypt;
import nl.marcusink.mmo.client.controller.connection.hasher.Hash;
import nl.marcusink.mmo.client.model.Player;
import nl.marcusink.mmo.client.model.PlayerList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;

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
        String fname, lname, uname, pass, pass2 , mail;
        LocalDate date;

        fname = firstName.getText();
        lname = lastName.getText();
        uname = username.getText();
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

        if(uname != null && uname.length() >= 6) {
            if (pass != null && pass.length() >= 6) {
                if (isMatch(pass, pass2)) {
                    if (isOnlyLetters(fname)) {
                        if (isOnlyLetters(lname)) {
                            if (isValidEmail(mail)) {
                                if (date!=null) {

                                        doRegister(uname, fname, lname, pass, mail, date.toEpochDay());

                                } else birthdayRequiredLabel.setVisible(true);
                            } else emailInvalidLabel.setVisible(true);
                        } else lastNameNoAZLabel.setVisible(true);
                    } else firstNameNoAZLabel.setVisible(true);
                } else passwordMatchLabel.setVisible(true);
            } else passwordRequiredLabel.setVisible(true);
        } else usernameRequiredLabel.setVisible(true);
    }

    private void doRegister(String username, String firstName, String lastName, String password, String email, Long birthday) {
        if(PlayerList.getInstance().get(username) == null) {

            password = Hash.md5(password);
            PlayerList.getInstance().put( username , new Player(username,password,email,firstName,lastName,birthday));

            try {
                Scene scene = new Scene(
                        FXMLLoader.load(
                                Main.class.getClass().getResource(
                                        "/views/login.fxml"
                                )
                        )
                );
                Main.mainStage.setScene(scene);
            } catch (IOException e) {
                // Show notification of some error
                e.printStackTrace();
            }

        } else {
            usernameExistsLabel.setVisible(true);
        }
    }

    private boolean isValidEmail(String input){
        return input != null && input.matches("([A-z0-9-]+(?:\\.[A-z0-9-]+)*)@((?:[A-z0-9-]+\\.)*[A-z0-9-]{1,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)");
    }

    private boolean isMatch(String a, String b){
        return a.equals(b);
    }

    private boolean isOnlyLetters(String input) {
        return input != null && input.matches("([A-Za-z])+");
    }

    private int getAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    @Override
    public void update(String data) {

    }
}
