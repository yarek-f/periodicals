package ua.dto;

import ua.domain.Topics;

import java.time.LocalDateTime;

public class PublisherDto {
    private String id;
    private String image;
    private String name;
    private String version;
    private String topic;
    private String price;
    private String description;
    private String create;
    private String updated;
    private String isActive;

    public PublisherDto(String id, String name, String topic, String create, String updated, String isActive) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
    }

    public PublisherDto(String image, String name, String topic, String price, String description) {
        this.image = image;
        this.name = name;
        this.topic = topic;
        this.price = price;
        this.description = description;
    }

    public PublisherDto(String id, String image, String name, String version, String topic, String price, String description, String create, String updated, String isActive) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.topic = topic;
        this.price = price;
        this.image = image;
        this.create = create;
        this.updated = updated;
        this.isActive = isActive;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "PublisherDto{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", topic='" + topic + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", create='" + create + '\'' +
                ", updated='" + updated + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
