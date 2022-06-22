package ua.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Customer implements Subscribers {
    private int id;
    private String fullName;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private double balance;
    private String password;
    private LocalDateTime created;
    private LocalDateTime update;
    private boolean isActive;

    private List<Publishers> publishersList;
    private Wallet wallet;
    private Subscription subscription;

    public Customer() {
    }

    public Customer(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    public Customer(int id, String fullName, LocalDate dob, String phoneNumber, String email, double balance) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balance;
    }

    public Customer(String fullName, LocalDate dob, String phoneNumber, String email, String password) {
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }


    public Customer(String fullName, String phoneNumber, String email, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public void update() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public List<Publishers> getPublishersList() {
        return publishersList;
    }

    public void setPublishersList(List<Publishers> publishersList) {
        this.publishersList = publishersList;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created=" + created +
                ", update=" + update +
                ", publishersList=" + publishersList +
                ", wallet=" + wallet +
                ", subscription=" + subscription +
                ", isActive=" + isActive +
                '}';
    }
}
