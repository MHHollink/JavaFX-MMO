package nl.marcusink.mmo.server.database.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id // ID = primary key
    @Column(name = "username", unique = true, nullable = false) // Specify that this value is a tableColumn
    private String username;

    @Column(name = "password", nullable = false) // Specify that this value is a tableColumn
    private String password;

    @Column(name = "first_name") // Specify that this value is a tableColumn
    private String fName;

    @Column(name = "last_name") // Specify that this value is a tableColumn
    private String lName;

    @Column(name = "email") // Specify that this value is a tableColumn
    private String email;

    @Column(name = "birth_date")
    private String BDay;

    /**
     * All getters and setters below!
     */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBDay() {
        return BDay;
    }

    public void setBDay(String BDay) {
        this.BDay = BDay;
    }
}
