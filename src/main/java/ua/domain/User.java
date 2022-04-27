package ua.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {
    private int id;
    private Role role;
    private String email;
    private String password;
    private boolean isActive;
    private LocalDateTime created;
    private LocalDateTime update;

    public User(){}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Role role, String email, String password) {
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public User(int id, Role role, String email, String password, boolean isActive, LocalDateTime created, LocalDateTime update) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.created = created;
        this.update = update;
    }

    public Timestamp convertToTimestamp(LocalDateTime date){
        if(date == null) date = LocalDateTime.now();
        return Timestamp.valueOf(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdate() {
        return update;
    }

    public void setUpdate(LocalDateTime update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", created=" + created +
                ", update=" + update +
                '}';
    }
}

