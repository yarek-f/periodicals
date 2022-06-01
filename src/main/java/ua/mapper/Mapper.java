package ua.mapper;

import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.User;
import ua.dto.CustomerSignUpDto;
import ua.dto.PublisherGetDto;
import ua.dto.UserSignUpDto;

import java.time.LocalDate;

public class Mapper {

    public static User convertToUser(UserSignUpDto userSignUpDto) {
        return new User(userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }

    public static Customer convertToCustomer(UserSignUpDto userSignUpDto) {
        return new Customer(userSignUpDto.getFullName(), LocalDate.parse(userSignUpDto.getDob()),
                userSignUpDto.getPhoneNumber(), userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }

    public static PublisherGetDto convertToPublisherDto(Publisher publisher){
        return new PublisherGetDto(String.valueOf(publisher.getId()), publisher.getName(), publisher.getTopic().toString(),
                publisher.getCreate().toString(), publisher.getUpdated().toString(), String.valueOf(publisher.isActive()));
    }

}
