package ua.services;

import ua.domain.Role;
import ua.domain.User;
import ua.dto.CustomerSignUpDto;
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

}
