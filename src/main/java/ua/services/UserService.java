package ua.services;

import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.User;
import ua.dto.CustomerSignUpDto;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<String> signUp(UserSignUpDto userDto);
    boolean delete(UserSignUpDto userDto);
    boolean delete(CustomerSignUpDto customerDto);
    User get(String email);
    boolean updateRole(int id);
    List<User> getAll();
    Role valid(String email, String password);
    List<Publisher> getAllSubscriptions(String email);
    Customer getCustomer(String profile);
    void addSubscription(int customerId, int publisherId);
    void unsubscribe(int customerId, int publisherId);
    List<Publisher> sortBy(String sortingType, String topic, List<Publisher> resultList);
    List<Publisher> sortByName(List<Publisher> publishersList);
    List<Publisher> sortByPrice(List<Publisher> publishersList);
    List<String> getTopicsByPublishers(List<Publisher> publishersList);
    List<String> getAllTopics();
    List<PublisherDto> getPagination(int skip, int limit, List<Publisher> currentList);
    List<String> replenishBalance(UserSignUpDto customerDto);
    boolean isSubscribed(int customerId, int publisherId);
    List<String> withdrawFromBalance(UserSignUpDto customerDto);
}
