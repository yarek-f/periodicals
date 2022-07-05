package ua.services;

import ua.dao.CustomerMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.*;
import ua.dto.CustomerDto;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserServiceImpl implements UserService {
    private UserMySqlDao userMySqlDao = new UserMySqlDao();
    private CustomerMySqlDao customerMySqlDao = new CustomerMySqlDao();
//    private Connection connection;
//
//    {
//        try {
//            connection = DataSource.getConnection();
//            userMySqlDao = new UserMySqlDao(connection);
//            customerMySqlDao = new CustomerMySqlDao(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean isSubscribed(int customerId, int publisherId){
        return customerMySqlDao.isSubscribed(customerId, publisherId);
    }

    @Override
    public void deactivateUser(int id) {
        userMySqlDao.deactivate(id);
        customerMySqlDao.deactivate(id);
    }

    @Override
    public void activate(int id) {
        userMySqlDao.activate(id);
        customerMySqlDao.activate(id);
    }

    @Override
    public List<String> replenishBalance(UserSignUpDto userDTO) {
        List<String> validation = validateBalance(userDTO);
        if (validation.isEmpty()) {
            System.out.println("BALANCE INSIDE SERVICE -> " + userDTO.getBalance());
            Customer customer = Mapper.convertToCustomerBalance(userDTO);
            customerMySqlDao.replenishBalance(customer);
        }

        return validation;
    }

    @Override
    public List<String> withdrawFromBalance(String email, int publisherId, double price) {
        List<String> checkResult = new ArrayList<>();
        double currentBalance = customerMySqlDao.get(email).getBalance();
        int customerId = customerMySqlDao.get(email).getId();
        if (currentBalance < price){
            checkResult.add("isNotEnoughMoney");
        } else if(isSubscribed(customerId, publisherId)) {
            checkResult.add("isAlreadySubscribed");
        } else {
            customerMySqlDao.withdrawFromBalance(email, price);
        }
        return checkResult;
    }

    @Override
    public void unsubscribe(int customerId, int publisherId) {
        customerMySqlDao.deleteSubscritpion(customerId, publisherId);
    }

    private List<String> validateBalance(UserSignUpDto customerDto) {
        List<String> checkResult = new ArrayList<>();
        double balance = Double.valueOf(customerDto.getBalance());
        if (balance < 0) {
            checkResult.add("balance");
        }
        return checkResult;
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
    public List<String> edit(UserSignUpDto userDto, String customerEmail) {
        List<String> validation = validateEditingUser(userDto);
        if (validation.isEmpty()) {
            User user = Mapper.convertToUser(userDto);
            Customer customer = Mapper.convertToCustomer(userDto);

            System.out.println("USER AND CUSTOMER INSIDE SERVICE -> " + user.toString());
            userMySqlDao.edit(user, customerEmail);
            customerMySqlDao.edit(customer, customerEmail);
        }

        return validation;
    }

    private List<String> validateEditingUser(UserSignUpDto userDto) {
        List<String> checkResult = new ArrayList<>();

        if (!validName(userDto.getFullName())) {
            checkResult.add("fullName");
        }
        if (!validDob(userDto.getDob())) {
            checkResult.add("dob");
        }
        if (!validPhoneNumber(userDto.getPhoneNumber())) {
            checkResult.add("phoneNumber");
        }
        if (!validEmail(userDto.getEmail())) {
            checkResult.add("email");
        }
        if (!validPassword(userDto.getPassword())) {
            checkResult.add("password");
        }
        if (!validConfirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            checkResult.add("confirmPassword");
        }
        return checkResult;
    }

    private List<String> validateUser(UserSignUpDto userSignUpDto) {
        List<String> checkResult = new ArrayList<>();

        if (!validPhoneNumber(userSignUpDto.getPhoneNumber())) {
            checkResult.add("phoneNumber");
        }
        if (validPhoneNumberIfExist(userSignUpDto.getPhoneNumber())) {
            checkResult.add("existPhoneNumber");
        }
        if (!validDob(userSignUpDto.getDob())) {
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
        if (!validCheckBox(userSignUpDto.getCheckBox())) {
            checkResult.add("check");
        }
        return checkResult;
    }



    @Override
    public Role valid(String email, String password) {
        User user = get(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user.getRole();
            }
        }
        return null;
    }

    @Override
    public boolean delete(UserSignUpDto userDto) {
        return false;
//        return userMySqlDao.delete(userDto.getEmail());
    }

    @Override
    public boolean delete(CustomerDto customerDto) { //fixme
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

    private boolean validName(String name) {
        if (name != null && !name.equals("")) {
            return name.length() < 50;
        } else {
            return false;
        }
    }

    private boolean validEmailIfExist(String email) {
        if (userMySqlDao.get(email) == null) return false;
        return userMySqlDao.get(email).getEmail() != null;
    }

    private boolean validPhoneNumberIfExist(String phoneNumber) {
        return customerMySqlDao.getByPhoneNumber(phoneNumber).getPhoneNumber() != null;
    }

    private boolean validDob(String dob) {
        if (dob != null && !dob.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime today = LocalDateTime.now();
            String formatDateTime = today.format(formatter);

            Date now = null;
            Date dateOfBirth = null;
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

    @Override
    public List<Publisher> getAllSubscriptions(String email) {
        return userMySqlDao.getAllSubscriptions(email);
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
    public void addSubscription(int customerId, int publisherId) {
        customerMySqlDao.addSubscription(customerId, publisherId);
    }



    @Override
    public Customer getCustomer(String profile) {
        return customerMySqlDao.get(profile);
    }

    @Override
    public boolean updateRole(int id) {
        return false;
    }

    public List<Publisher> sortBy(String sortingType, String topic, List<Publisher> resultList) {
        PublisherService publisherService = new PublisherServiceImpl();
//        List<Publisher> publishersList = publisherService.getAll();

        if (topic != null) {
//            resultList = publisherService.getByTopic(topic);
            resultList = resultList.stream()
                    .filter(e -> e.getTopic().toString().equals(topic))
                    .collect(Collectors.toList());
        }

        if (sortingType != null && sortingType.equals("byName") && topic != null) {
            resultList = sortByName(resultList.stream().filter(e -> e.getTopic().toString().equals(topic))
                    .collect(Collectors.toList()));
        } else if (sortingType != null && sortingType.equals("byName")) {
            resultList = sortByName(resultList);
        }

        if (sortingType != null && sortingType.equals("byPrice") && topic != null) {
            resultList = sortByPrice(resultList.stream().filter(e -> e.getTopic().toString().equals(topic))
                    .collect(Collectors.toList()));
        } else if (sortingType != null && sortingType.equals("byPrice")) {
            resultList = sortByPrice(resultList);
        }
        return resultList;
    }

    public List<Publisher> sortByName(List<Publisher> publishersList) {
        if (publishersList != null) {
            return publishersList.stream()
                    .sorted(Comparator.comparing(Publisher::getName))
                    .collect(Collectors.toList());
        } else return null;
    }

    public List<Publisher> sortByPrice(List<Publisher> publishersList) {
        if (publishersList != null) {
            return publishersList.stream()
                    .sorted(Comparator.comparing(Publisher::getPrice))
                    .collect(Collectors.toList());
        } else return null;
    }

    public List<String> getTopicsByPublishers(List<Publisher> publishersList) {
        if (publishersList != null) {
            return publishersList.stream()
                    .map(e -> e.getTopic().toString())
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<String> getAllTopics() {
        return Stream.of(Topics.values())
                .map(Topics::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<Publisher> isSubscribed(List<Publisher> list, String email) {
        UserService userService = new UserServiceImpl();
        int customerId = userService.getCustomer(email).getId();
        for (Publisher p : list) {
            if (userService.isSubscribed(customerId, p.getId())) {
                p.setSubscribed(1);
            }
        }
        return list;
    }

    public List<PublisherDto> getPagination(int skip, int limit, List<Publisher> currentList) {
        return currentList.stream()
                .skip(skip)
                .limit(limit)
                .map(e -> Mapper.convertToPublisherDto(e))
                .collect(Collectors.toList());
    }


}
