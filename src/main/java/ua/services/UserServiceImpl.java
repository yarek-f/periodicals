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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        if (validPhoneNumberIfExist(userSignUpDto.getPhoneNumber())){
            checkResult.add("existPhoneNumber");
        }
        if (!validDob(userSignUpDto.getDob())){
            checkResult.add("dob");
        }
        if (validEmailIfExist(userSignUpDto.getEmail())) {
            checkResult.add("emailExist");
        }
        if (!validName(userSignUpDto.getFullName())) {
            checkResult.add("fullName");
        }
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
        return false;
//        return userMySqlDao.delete(userDto.getEmail());
    }

    @Override
    public boolean delete(CustomerSignUpDto customerDto) { //fixme
        return false;
//        return customerMySqlDao.delete(Integer.valueOf(customerDto.getId()));
    }

    private boolean validCheckBox(String checkBox) {
        return checkBox != null;
    }

    private boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validName(String name){
        if (name != null && !name.equals("")){
            return name.length() < 50;
        }else{
         return false;
        }
    }

    private boolean validEmailIfExist(String email) {
        return userMySqlDao.get(email).getEmail() != null;
    }

    private boolean validPhoneNumberIfExist(String phoneNumber) {
        return customerMySqlDao.getByPhoneNumber(phoneNumber).getPhoneNumber() != null;
    }

    private boolean validDob(String dob) {
        if (dob != null && !dob.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime today = LocalDateTime.now();
            String formatDateTime = today.format(formatter);

            Date now = null;
            Date dateOfBirth= null;
            try {
                now = new SimpleDateFormat("yyyy-MM-dd").parse(formatDateTime);
                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dob);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return dateOfBirth.before(now);
        } else {
            return true;
        }

    }

    private boolean validPassword(String password) {
        Pattern pattern = Pattern.compile("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=\"])(?=\\S+$).{8,}$)|(^(?=.*[0-9])(?=.*[а-я])(?=.*[А-Я])(?=.*[@#$%^&+=\"])(?=\\S+$).{8,}$)"); //todo
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
