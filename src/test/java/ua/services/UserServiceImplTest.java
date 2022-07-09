package ua.services;

import org.junit.jupiter.api.*;
import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Customer;
import ua.domain.Role;
import ua.dto.UserSignUpDto;

import java.time.LocalDate;
import java.util.List;

class UserServiceImplTest {
    private UserServiceImpl userService = new UserServiceImpl();
    private CustomerMySqlDao customerDao = new CustomerMySqlDao();
    private UserMySqlDao userDao = new UserMySqlDao();
    private PublisherMySqlDao publisherDao = new PublisherMySqlDao();

    @BeforeAll
    static void dbInit() {
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

    @Test
    void SIGN_UP_POSITIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        List<String> validation = userService.signUp(userDto);
        Assertions.assertEquals(userDao.get(userDto.getEmail()).getEmail(), userDto.getEmail());
        Assertions.assertEquals(customerDao.get(userDto.getEmail()).getPhoneNumber(), userDto.getPhoneNumber());
        Assertions.assertEquals(customerDao.get(userDto.getEmail()).getFullName(), userDto.getFullName());
        Assertions.assertTrue(validation.isEmpty());
        userService.delete(userDto);
    }

    @Test
    void SIGN_UP_NEGATIVE_TEST(){
        UserSignUpDto existedUserDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(existedUserDto);

        LocalDate date = LocalDate.now().plusDays(2);
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiysfgbnsfgnsfgnsfgnsgnxfgnxfgnxfgndfgnsfgnsffgmxfgn", date.toString(), "+38456", "zelya", "456", "12", null);
        List<String> generalValidation = userService.signUp(userDto);
        Assertions.assertFalse(generalValidation.isEmpty());
        Assertions.assertEquals(generalValidation.size(), 7);

        UserSignUpDto userDto2 = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        List<String> additionalValidation = userService.signUp(userDto2);
        Assertions.assertFalse(additionalValidation.isEmpty());
        Assertions.assertEquals(additionalValidation.size(), 2);
        userService.delete(existedUserDto);
    }

    @Test
    void EDIT_USER_DATA_POSITIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        UserSignUpDto editUserDto = new UserSignUpDto("Vova Zelen", "1983-01-23", "+380956312456", "vova@gmail.com", "Ukr@vova22", "Ukr@vova22", null);
        userService.signUp(userDto);
        Assertions.assertEquals(userDto.getEmail(), userDao.get("zelya@ukr.net").getEmail());
        List<String> validation = userService.edit(editUserDto, userDto.getEmail());
        Assertions.assertEquals(customerDao.get(editUserDto.getEmail()).getFullName(), editUserDto.getFullName());
        Assertions.assertEquals(customerDao.get(editUserDto.getEmail()).getPhoneNumber(), editUserDto.getPhoneNumber());
        Assertions.assertTrue(validation.isEmpty());
        userService.delete(editUserDto);
    }

    @Test
    void EDIT_USER_DATA_NEGATIVE_TEST(){
        LocalDate date = LocalDate.now().plusDays(2);
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        UserSignUpDto editUserDto = new UserSignUpDto("Volodymyr Zelenskiysfgbnsfgnsfgnsfgnsgnxfgnxfgnxfgndfgnsfgnsffgmxfgn", date.toString(), "+38456", "zelya", "456", "12", null);
        userService.signUp(userDto);
        Assertions.assertEquals(userDto.getEmail(), userDao.get("zelya@ukr.net").getEmail());
        List<String> validation = userService.edit(editUserDto, userDto.getEmail());
        Assertions.assertNotEquals(customerDao.get(editUserDto.getEmail()).getFullName(), editUserDto.getFullName());
        Assertions.assertNotEquals(customerDao.get(editUserDto.getEmail()).getPhoneNumber(), editUserDto.getPhoneNumber());
        Assertions.assertFalse(validation.isEmpty());
        Assertions.assertEquals(validation.size(), 6);
        userService.delete(userDto);
    }

    @Test
    void VALID_LOGIN_DATA_POSITIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956312456", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        Role role = userService.valid(userDto.getEmail(), userDto.getPassword());
        Assertions.assertEquals(role, Role.USER);
        userService.delete(userDto);
    }

    @Test
    void VALID_LOGIN_DATA_NEGATIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380956", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        Role role = userService.valid(userDto.getEmail(), userDto.getPassword());
        Assertions.assertNull(role);
    }

    @Test
    void IS_SUBSCRIBED_POSITIVE_TEST(){
        Customer customer = new Customer("Volodymyr Zelenskiy", LocalDate.of(1965, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        int id = customerDao.signUp(customer);
        customerDao.addSubscription(id, 1);
        Assertions.assertTrue(userService.isSubscribed(id, 1));
        customerDao.deleteSubscription(id, 1);
        customerDao.delete(id);
    }

    @Test
    void IS_SUBSCRIBED_NEGATIVE_TEST(){
        Customer customer = new Customer("Volodymyr Zelenskiy", LocalDate.of(1965, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        int id = customerDao.signUp(customer);
        Assertions.assertFalse(userService.isSubscribed(id, 1));
        customerDao.deleteSubscription(id, 1);
        customerDao.delete(id);
    }

    @Test
    void ACTIVATE_AND_DEACTIVATE_USER_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380974563123", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        userService.deactivateUser(userDto.getEmail());
        Assertions.assertFalse(userDao.getUserIgnoringFieldIsActive(userDto.getEmail()).isActive());
        Assertions.assertFalse(customerDao.get(userDto.getEmail()).isActive());

        userService.activate(userDto.getEmail());
        Assertions.assertTrue(userDao.getUserIgnoringFieldIsActive(userDto.getEmail()).isActive());
        Assertions.assertTrue(customerDao.get(userDto.getEmail()).isActive());
        userService.delete(userDto);
    }

    @Test
    void REPLENISH_BALANCE_POSITIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380974563123", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        UserSignUpDto replenishUserBalance = new UserSignUpDto(userDto.getEmail(), "235.33");
        List<String> validation = userService.replenishBalance(replenishUserBalance);
        Assertions.assertTrue(validation.isEmpty());
        Assertions.assertEquals(customerDao.get(userDto.getEmail()).getBalance(), 235.33);
        userService.delete(userDto);
    }

    @Test
    void REPLENISH_BALANCE_NEGATIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380974563123", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        UserSignUpDto replenishUserBalance = new UserSignUpDto(userDto.getEmail(), "-235.33");
        List<String> validation = userService.replenishBalance(replenishUserBalance);
        Assertions.assertFalse(validation.isEmpty());
        userService.delete(userDto);
    }

    @Test
    void WITHDRAW_FROM_BALANCE_POSITIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380974563123", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        UserSignUpDto replenishUserBalance = new UserSignUpDto(userDto.getEmail(), "100");
        userService.replenishBalance(replenishUserBalance);
        double price = publisherDao.getById(1).getPrice();
        List<String> validation = userService.withdrawFromBalance(userDto.getEmail(), 1, price);

        Assertions.assertTrue(validation.isEmpty());
        Assertions.assertEquals(Double.valueOf(replenishUserBalance.getBalance())-price, customerDao.get(userDto.getEmail()).getBalance());
        userService.delete(userDto);
    }

    @Test
    void WITHDRAW_FROM_BALANCE_NEGATIVE_TEST(){
        UserSignUpDto userDto = new UserSignUpDto("Volodymyr Zelenskiy", "1983-01-23", "+380974563123", "zelya@ukr.net", "Ukr@vova22", "Ukr@vova22", "notNull");
        userService.signUp(userDto);
        double price = publisherDao.getById(1).getPrice();
        List<String> validation = userService.withdrawFromBalance(userDto.getEmail(), 1, price);

        Assertions.assertFalse(validation.isEmpty());
        Assertions.assertEquals(0, customerDao.get(userDto.getEmail()).getBalance());
        userService.delete(userDto);
    }

}