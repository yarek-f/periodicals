package ua.services;

import ua.domain.Publisher;
import ua.dto.UserSignUpDto;

import java.util.List;
import java.util.Map;

public interface PublisherService {
    Map<String, String> signUp(UserSignUpDto userDto);

    Publisher get(String email);
    boolean updateRole(int id);
    List<Publisher> getAll();
}
