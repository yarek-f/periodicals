package ua.mapper;

import ua.domain.User;
import ua.dto.UserSignUpDto;

public class Mapper {

    public static User convertToUser(UserSignUpDto userSignUpDto){
        return new User(userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }
}
