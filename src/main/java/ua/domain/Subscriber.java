package ua.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Subscriber implements Subscribers {
    private int id;
    private String fullName;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime update;
    private List<Publishers> publishersList;
    private Wallet wallet;
    private boolean isActive;



    public void update() {

    }
}
