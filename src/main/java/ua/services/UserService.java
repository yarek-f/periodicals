package ua.services;

import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.User;
import ua.dto.CustomerDto;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;

import java.util.List;

public interface UserService {
    List<String> signUp(UserSignUpDto userDto);
    void delete(UserSignUpDto userDto);
    User get(String email);
    List<User> getAll();
    Role valid(String email, String password);
    List<Publisher> getAllSubscriptions(String email);
    Customer getCustomer(String profile);
    void addSubscription(int customerId, int publisherId);
    void unsubscribe(int customerId, int publisherId);
    List<Publisher> sortBy(String sortingType, String topic, List<Publisher> resultList);
    List<String> getTopicsByPublishers(List<Publisher> publishersList);
    List<String> getAllTopics();
    List<PublisherDto> getPagination(int skip, int limit, List<Publisher> currentList);
    List<String> replenishBalance(UserSignUpDto customerDto);
    boolean isSubscribed(int customerId, int publisherId);
    List<String> withdrawFromBalance(String email, int publisherId, double price);
    List<CustomerDto> getAllCustomer();
    List<String> edit(UserSignUpDto userDto, String customerEmail);

    void deactivateUser(String email);
    void activate(String email);
}
