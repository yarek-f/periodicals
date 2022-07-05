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
    boolean delete(UserSignUpDto userDto);
    boolean delete(CustomerDto customerDto);
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
    List<Publisher> isSubscribed(List<Publisher> list, String email);
    List<PublisherDto> getPagination(int skip, int limit, List<Publisher> currentList);
    List<String> replenishBalance(UserSignUpDto customerDto);
    boolean isSubscribed(int customerId, int publisherId);
    List<String> withdrawFromBalance(String email, int publisherId, double price);

    List<String> edit(UserSignUpDto userDto, String customerEmail);

    void deactivateUser(int id);

    void activate(int id);
}
