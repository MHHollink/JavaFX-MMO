package nl.marcusink.mmo.server.database.table;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_owns_avatar")
public class UserOwnsAvatar implements Serializable{

    @Id
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Id
    @OneToOne(targetEntity = Character.class)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "avater", nullable = false, unique = true)
    private Avatar avatar;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserOwnsAvatar that = (UserOwnsAvatar) object;

        return !(user != null ? !user.equals(that.user) : that.user != null)
                && !(avatar != null ? !avatar.equals(that.avatar) : that.avatar != null);

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        return result;
    }
}
