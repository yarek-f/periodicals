package ua.dto;

public class CustomerDto {
    private String id;
    private String fullName;
    private String dob;
    private String phoneNumber;
    private String email;
    private String balance;
    private String isActive;
    private String created;
    private String updated;


    private String password;
    private String confirmPassword;


    public CustomerDto(){}

    public CustomerDto(String balance) {
        this.balance = balance;
    }

    public CustomerDto(String id, String fullName, String dob, String phoneNumber, String email, String balance, String isActive, String created, String updated) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balance;
        this.isActive = isActive;
        this.created = created;
        this.updated = updated;
    }

    public CustomerDto(String fullName, String dob, String phoneNumber, String email, String password, String confirmPassword) {
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", balance='" + balance + '\'' +
                ", isActive='" + isActive + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
