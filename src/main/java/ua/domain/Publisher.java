package ua.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Publisher implements Publishers{
    private int id;
    private String image;
    private String name;
    private int version;
    private Topics topic;
    private Double price;
    private String description;
    private LocalDateTime create;
    private LocalDateTime updated;
    private boolean isActive;
    private boolean isSubscribed;

    private List<Subscribers> subscribers = new ArrayList<>(); //??
    private boolean inPublic; //fixme (???) change name

    public Publisher() {
    }

    public Publisher(String name, Topics topic) {
        this.name = name;
        this.topic = topic;
    }

    public Publisher(String name, String image, int version) {
        this.name = name;
        this.image = image;
        this.version = version;
    }

    public Publisher(String image, String name, Topics topic, Double price, String description) {
        this.image = image;
        this.name = name;
        this.topic = topic;
        this.price = price;
        this.description = description;
    }

    public boolean isInPublic() {
        return inPublic;
    }

    public void setInPublic(boolean inPublic) {
        this.inPublic = inPublic;
        notifySubscriber();
    }

    public Publisher(int id, String image, String name, int version, Topics topic, Double price, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.version = version;
        this.topic = topic;
        this.price = price;
        this.description = description;
    }

    public Publisher(int id, String image, String name, int version, Topics topic, double price, String description, LocalDateTime create, LocalDateTime updated, boolean isActive) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.version = version;
        this.topic = topic;
        this.price = price;
        this.description = description;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", topic=" + topic +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", create=" + create +
                ", updated=" + updated +
                ", isActive=" + isActive +
                ", isSubscribed=" + isSubscribed +
                ", subscribers=" + subscribers +
                ", inPublic=" + inPublic +
                '}';
    }
}
