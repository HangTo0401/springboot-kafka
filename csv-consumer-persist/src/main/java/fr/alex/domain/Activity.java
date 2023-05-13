package fr.alex.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Activity {

    @Id
    @GeneratedValue
    private UUID id;

    private String ipAddress;
    private ZonedDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(ipAddress, activity.ipAddress) &&
                Objects.equals(lastLogin, activity.lastLogin) &&
                Objects.equals(user, activity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, lastLogin, user);
    }
}
