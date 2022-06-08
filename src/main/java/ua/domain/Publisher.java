package ua.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Publisher implements Publishers{
    private int id;
    private String name;
    private Topics topic;
    private double price;
    private String image;
    private LocalDateTime create;
    private LocalDateTime updated;
    private boolean isActive;

    private List<Subscribers> subscribers = new ArrayList<>(); //??
    private boolean inPublic; //fixme (???) change name

    public Publisher(String name, Topics topic) {
        this.name = name;
        this.topic = topic;
    }



    public boolean isInPublic() {
        return inPublic;
    }

    public void setInPublic(boolean inPublic) {
        this.inPublic = inPublic;
        notifySubscriber();
    }

    public Publisher(int id, String name, Topics topic, double price, String image, LocalDateTime create, LocalDateTime updated, boolean isActive) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.price = price;
        this.image = image;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
    }

    public Publisher(int id, String name, Topics topic, LocalDateTime create, LocalDateTime updated, boolean isActive) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Subscribers> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscribers> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic=" + topic +
                ", create=" + create +
                ", updated=" + updated +
                ", isActive=" + isActive +
                '}';
    }
}
