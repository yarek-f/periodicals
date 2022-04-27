package ua.services;

import ua.domain.User;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, String> signUp(UserSignUpDto userDto);

    User get(String email);
    boolean updateRole(int id);
    List<User> getAll();

}
