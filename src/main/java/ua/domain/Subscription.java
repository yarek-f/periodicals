package ua.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Subscription {
    private String name;
    private List<Publishers> publishersList;
    private int id;
    private boolean isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int days;
}
