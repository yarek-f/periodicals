package ua.services;

import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public interface PublisherService {
    List<String> create(PublisherDto publisherDto);
    Publisher get(String name);
    List<Publisher> getAll();
    List<Publisher> getByTopic(String topic);
    List<String> addNewVersion(PublisherDto publisherDto);
    List<String> editPublisher(PublisherDto publisherDto);
}
