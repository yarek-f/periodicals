package ua.mapper;

import ua.domain.Customer;
import ua.domain.User;
import ua.dto.CustomerSignUpDto;
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
}
