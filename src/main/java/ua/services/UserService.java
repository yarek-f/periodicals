package ua.services;

import ua.domain.User;
import ua.dto.CustomerSignUpDto;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<String> signUp(UserSignUpDto userDto);
//    Map<String, String> signUp(CustomerSignUpDto customerDto);
    boolean delete(UserSignUpDto userDto);
    boolean delete(CustomerSignUpDto customerDto);
    User get(String email);
    boolean updateRole(int id);
    List<User> getAll();

}
