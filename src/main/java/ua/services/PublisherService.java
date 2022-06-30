package ua.services;

import ua.domain.Publisher;
import ua.dto.CustomerDto;
import ua.dto.PublisherDto;

import java.util.List;

public interface PublisherService {
    List<String> create(PublisherDto publisherDto);
    Publisher get(String name);
    List<Publisher> getAll();
    List<Publisher> getByTopic(String topic);
    List<String> addNewVersion(PublisherDto publisherDto);
    List<String> editPublisher(PublisherDto publisherDto);
    List<CustomerDto> getAllSubscribers(String publisherName);
}
