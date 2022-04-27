package ua.dto;

public class UserGetDto{
    private String id;
    private String role;
    private String email;
    private String password;
    private String isActive;
    private String created;
    private String update;

    public UserGetDto(String id, String role, String email, String password, String isActive, String created, String update) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.created = created;
        this.update = update;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "UserGetDto{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive='" + isActive + '\'' +
                ", created='" + created + '\'' +
                ", update='" + update + '\'' +
                '}';
    }
}
