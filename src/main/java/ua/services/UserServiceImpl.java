package ua.services;

import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.dao.UserMySqlDao;
import ua.domain.Customer;
import ua.domain.User;
import ua.dto.CustomerSignUpDto;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private UserMySqlDao userMySqlDao;
    private Connection connection;
    private CustomerMySqlDao customerMySqlDao;

    {
        try {
            connection = DataSource.getConnection();
            userMySqlDao = new UserMySqlDao(connection);
            customerMySqlDao = new CustomerMySqlDao(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> signUp(UserSignUpDto userSignUpDto) {
        Map<String, String> validation = validateUser(userSignUpDto);

        if (validation.isEmpty()) {
            User user = Mapper.convertToUser(userSignUpDto);

            System.out.println("USER INSIDE SERVICE -> " + user.toString());
            userMySqlDao.signUp(user);
        }

        return validation;
    }

    @Override
    public Map<String, String> signUp(CustomerSignUpDto customerSignUpDto) {
        Map<String, String> validation = validateCustomer(customerSignUpDto);

        if (validation.isEmpty()) {
            Customer customer = Mapper.convertToCustomer(customerSignUpDto);

            System.out.println("CUSTOMER INSIDE SERVICE -> " + customer.toString());
            customerMySqlDao.signUp(customer);
        }

        return validation;
    }

    @Override
    public boolean delete(UserSignUpDto userDto) {
        return userMySqlDao.delete(userDto.getEmail());
    }

    @Override
    public boolean delete(CustomerSignUpDto customerDto) {
        return customerMySqlDao.delete(customerDto.getEmail());
    }

    private Map<String, String> validateCustomer(CustomerSignUpDto customerSignUpDto) {
        Map<String, String> checkResult = new TreeMap<>();

        if (!validEmail(customerSignUpDto.getEmail())) {
            checkResult.put("email", "false");
        }
        if (!validPassword(customerSignUpDto.getPassword())) {
            checkResult.put("password", "false");
        }
        if (!validPhoneNumber(customerSignUpDto.getPhoneNumber())) {
            checkResult.put("number", "false");
        }
        if (!validConfirmPassword(customerSignUpDto.getPassword(), customerSignUpDto.getConfirmPassword())) {
            checkResult.put("confirmPassword", "false");
        }
        return checkResult;
    }

    public Map<String, String> validateUser(UserSignUpDto userSignUpDto) {
        Map<String, String> checkResult = new TreeMap<>();

        if (!validEmail(userSignUpDto.getEmail())) {
            checkResult.put("email", "false");
        }
        if (!validPassword(userSignUpDto.getPassword())) {
            checkResult.put("password", "false");
        }
        if (!validConfirmPassword(userSignUpDto.getPassword(), userSignUpDto.getConfirmPassword())) {
            checkResult.put("confirmPassword", "false");
        }
        return checkResult;
    }


    private boolean validEmail(String email) { //fixme
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validDob(String dob) { //fixme
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        if(dob.equals(dtf.format(now))){
           return true;
        }
        return false;
    }

    private boolean validPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"); //todo
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^\\+?(38)?(\\d{10,11})$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean validConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public List<User> getAll() {

        return userMySqlDao.getAll();
    }

    @Override
    public User get(String email) {
        return userMySqlDao.get(email);
    }

    @Override
    public boolean updateRole(int id) {
        return false;
    }


}
