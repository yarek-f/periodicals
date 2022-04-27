package ua.domain;

public interface Publishers {
    void addSubscriber(Subscribers s);
    void removeSubscriber(Subscribers s);
    void notifySubscriber();
}
