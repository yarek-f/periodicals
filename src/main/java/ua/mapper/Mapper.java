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

    public static Customer convertToCustomer(CustomerSignUpDto customerSignUpDto) {
        return new Customer(customerSignUpDto.getFullName(), LocalDate.parse(customerSignUpDto.getDob()),
                customerSignUpDto.getPhoneNumber(), customerSignUpDto.getEmail(), customerSignUpDto.getPassword());
    }
}
