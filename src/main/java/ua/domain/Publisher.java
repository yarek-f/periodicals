package ua.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Publisher implements Publishers{
    private int id;
    private String name;
    private Topics topic;
    private LocalDate create;
    private LocalDateTime updated;
    private boolean isActive;

    private List<Subscribers> subscribers = new ArrayList<>(); //??
    private boolean inPublic; //fixme (???) change name


    public boolean isInPublic() {
        return inPublic;
    }

    public void setInPublic(boolean inPublic) {
        this.inPublic = inPublic;
        notifySubscriber();
    }

    public void addSubscriber(Subscribers s) {
        subscribers.add(s);
    }

    public void removeSubscriber(Subscribers s) {
        subscribers.remove(s);
    }

    public void notifySubscriber() {
        for (Subscribers subscribers : this.subscribers) {
            subscribers.update();
        }
    }
}
