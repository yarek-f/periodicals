import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.Topics;
import ua.domain.User;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;
import ua.services.PublisherServiceImpl;
import ua.services.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTestClass {
    public static void main(String[] args) {
        String fullName = "Billi Jhyn";
        String dob = "1996-12-12";
        String phoneNumber = "+380952356459";
        String email = "borysko@gmail.com";
        String password = "123456Q@q";
        String confirmPassword = "123456Q@q";


        UserServiceImpl userService = new UserServiceImpl();
//        UserSignUpDto userSignUpDto = new UserSignUpDto(fullName, dob, phoneNumber, email, password, confirmPassword);
//        userService.signUp(userSignUpDto); //todo

        UserMySqlDao userMySqlDao = null;
        CustomerMySqlDao customerMySqlDao = null;
        try {
            userMySqlDao = new UserMySqlDao(DataSource.getConnection());
            customerMySqlDao = new CustomerMySqlDao(DataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        customerMySqlDao.signUp(Mapper.convertToCustomer(userSignUpDto));
    }
}
