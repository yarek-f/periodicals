package ua.dto;

import ua.domain.Topics;

import java.time.LocalDateTime;

public class PublisherGetDto {
    private String id;
    private String name;
    private String topic;
    private String create;
    private String updated;
    private String isActive;

    public PublisherGetDto(String id, String name, String topic, String create, String updated, String isActive) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "PublisherGetDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                ", create='" + create + '\'' +
                ", updated='" + updated + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
