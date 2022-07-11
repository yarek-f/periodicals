package ua.mapper;

import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.domain.User;
import ua.dto.CustomerDto;
import ua.dto.PublisherDto;
import ua.dto.UserGetDto;
import ua.dto.UserSignUpDto;

import java.time.LocalDate;

public class Mapper {

    public static User convertToUser(UserSignUpDto userSignUpDto) {
        return new User(userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }

    public static CustomerDto convertToCustomerDto(Customer customer){
        return new CustomerDto(String.valueOf(customer.getId()), customer.getFullName(), customer.getDob()!=null?customer.getDob().toString():null,
                    customer.getPhoneNumber(), customer.getEmail(), String.format("%.2f", customer.getBalance()), String.valueOf(customer.isActive()),
                    customer.getCreated().toLocalDate().toString(), customer.getUpdate().toLocalDate().toString());
    }

    public static Customer convertToCustomer(UserSignUpDto userSignUpDto) {
        if (!userSignUpDto.getDob().equals("") && userSignUpDto.getDob() != null){
            return new Customer(userSignUpDto.getFullName(), LocalDate.parse(userSignUpDto.getDob()),
                    userSignUpDto.getPhoneNumber(), userSignUpDto.getEmail(), userSignUpDto.getPassword());
        }else{
            return new Customer(userSignUpDto.getFullName(),
                    userSignUpDto.getPhoneNumber(), userSignUpDto.getEmail(), userSignUpDto.getPassword());
        }

    }
    public static UserSignUpDto convertToUserDto(Customer customer){
        if (customer.getDob() == null){
            return new UserSignUpDto(String.valueOf(customer.getId()), customer.getFullName(), null,
                    customer.getPhoneNumber(), customer.getEmail(), String.valueOf(customer.getBalance()));
        }
        return new UserSignUpDto(String.valueOf(customer.getId()), customer.getFullName(), customer.getDob().toString(),
                                customer.getPhoneNumber(), customer.getEmail(), String.valueOf(customer.getBalance()));
    }

    public static PublisherDto convertToPublisherDto(Publisher publisher){
        return new PublisherDto(String.valueOf(publisher.getId()), publisher.getImage(), publisher.getName(), String.valueOf(publisher.getVersion()), publisher.getTopic().toString(),
                String.valueOf(publisher.getPrice()), publisher.getDescription(), publisher.getCreate().toLocalDate().toString(), publisher.getUpdated().toLocalDate().toString(), String.valueOf(publisher.isActive()));
    }

    public static UserGetDto convertToUserDto(User user) {
        return new UserGetDto(String.valueOf(user.getId()), user.getRole().toString(), user.getEmail(), user.getPassword(),
                                String.valueOf(user.isActive()), user.getCreated().toString(), user.getUpdate().toString());
    }

    public static Publisher convertToPublisher(PublisherDto publisherCreatDto) {
        return new Publisher(publisherCreatDto.getImage(), publisherCreatDto.getName(), Topics.valueOf(publisherCreatDto.getTopic()),
                Double.valueOf(publisherCreatDto.getPrice()), publisherCreatDto.getDescription());
    }



    public static Publisher convertToAddNewVersionPublisher(PublisherDto publisherCreatDto) {
        return new Publisher(publisherCreatDto.getName(), publisherCreatDto.getImage(), Integer.valueOf(publisherCreatDto.getVersion()));
    }


    public static Customer convertToCustomerBalance(UserSignUpDto userDTO) {
        return new Customer(userDTO.getEmail(), Double.valueOf(userDTO.getBalance()));
    }
}
