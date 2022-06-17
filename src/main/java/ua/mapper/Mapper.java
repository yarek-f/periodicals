package ua.mapper;

import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.domain.User;
import ua.dto.PublisherDto;
import ua.dto.UserGetDto;
import ua.dto.UserSignUpDto;

import java.time.LocalDate;

public class Mapper {

    public static User convertToUser(UserSignUpDto userSignUpDto) {
        return new User(userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }

    public static Customer convertToCustomer(UserSignUpDto userSignUpDto) {
        if (!userSignUpDto.getDob().equals("")&&userSignUpDto.getDob() != null){
            return new Customer(userSignUpDto.getFullName(), LocalDate.parse(userSignUpDto.getDob()),
                    userSignUpDto.getPhoneNumber(), userSignUpDto.getEmail(), userSignUpDto.getPassword());
        }else{
            return new Customer(userSignUpDto.getFullName(),
                    userSignUpDto.getPhoneNumber(), userSignUpDto.getEmail(), userSignUpDto.getPassword());
        }

    }

    public static PublisherDto convertToPublisherDto(Publisher publisher){
        return new PublisherDto(String.valueOf(publisher.getId()), publisher.getImage(), publisher.getName(), String.valueOf(publisher.getVersion()), publisher.getTopic().toString(),
                String.valueOf(publisher.getPrice()), publisher.getDescription(), publisher.getCreate().toString(), publisher.getUpdated().toString(), String.valueOf(publisher.isActive()));
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


}
