package nl.marcusink.mmo.server.database.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avatars", schema = "public")
public class Avatar {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "profession")
    private String profession;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "gender", nullable = false)
    private String gender;

    public String getName() {
        return name;
    }

    public void setCharacterName(String characterName) {
        this.name = characterName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
