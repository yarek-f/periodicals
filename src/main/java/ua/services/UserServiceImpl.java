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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private UserMySqlDao userMySqlDao;
    private CustomerMySqlDao customerMySqlDao;
    private Connection connection;

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
    public List<String> signUp(UserSignUpDto userSignUpDto) {
        List<String> validation = validateUser(userSignUpDto);

        if (validation.isEmpty()) {
            User user = Mapper.convertToUser(userSignUpDto);
            Customer customer = Mapper.convertToCustomer(userSignUpDto);

            System.out.println("USER AND CUSTOMER INSIDE SERVICE -> " + user.toString());
            userMySqlDao.signUp(user);
            customerMySqlDao.signUp(customer);
        }

        return validation;
    }

    @Override
    public boolean valid(String email, String password){
        User user = get(email);
        if (user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public List<String> validateUser(UserSignUpDto userSignUpDto) {
        List<String> checkResult = new ArrayList<>();

        if (!validPhoneNumber(userSignUpDto.getPhoneNumber())){
            checkResult.add("phoneNumber");
        }
//        if (!validDob(userSignUpDto.getDob())){ fixme
//            checkResult.add("dob");
//        }
        if (!validEmail(userSignUpDto.getEmail())) {
            checkResult.add("email");
        }
        if (!validPassword(userSignUpDto.getPassword())) {
            checkResult.add("password");
        }
        if (!validConfirmPassword(userSignUpDto.getPassword(), userSignUpDto.getConfirmPassword())) {
            checkResult.add("confirmPassword");
        }
        if (!validCheckBox(userSignUpDto.getCheckBox())){
            checkResult.add("check");
        }
        return checkResult;
    }


    @Override
    public boolean delete(UserSignUpDto userDto) {
        return userMySqlDao.delete(userDto.getEmail());
    }

    @Override
    public boolean delete(CustomerSignUpDto customerDto) {
        return customerMySqlDao.delete(customerDto.getEmail());
    }

    private boolean validCheckBox(String checkBox) {
        return checkBox != null;
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
    public User get(String email) {//fixme
        return userMySqlDao.get(email);
    }

    @Override
    public boolean updateRole(int id) {
        return false;
    }


}
