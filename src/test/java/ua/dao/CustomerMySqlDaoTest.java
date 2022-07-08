package ua.dao;

import org.junit.jupiter.api.*;
import ua.domain.Customer;
import ua.domain.Publisher;

import java.time.LocalDate;
import java.util.List;

class CustomerMySqlDaoTest {
    private CustomerMySqlDao customerDao = new CustomerMySqlDao();
    private PublisherMySqlDao publisherDao = new PublisherMySqlDao();
    private Customer customer = new Customer();
    private int id = 0;

    @BeforeAll
    static void dbInit(){
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

    @BeforeEach
    void before(){
        customer = new Customer("Volodymyr Zelenskiy", LocalDate.of(1965, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        id = customerDao.signUp(customer);
    }

    @AfterEach
    void after(){
        customerDao.delete(id);
    }

    @Test
    void SIGN_UP_USER_POSITIVE_TEST() {
        Assertions.assertEquals(customerDao.get(customer.getEmail()).getId(), id);
    }

    @Test
    void SIGN_UP_USER_NEGATIVE_TEST() {
        Assertions.assertNotEquals(1, id);
    }

    @Test
    void EDIT_POSITIVE_TEST(){
        Customer editedCustomer = new Customer("Petro Oleksiyovych Poroshenko", LocalDate.of(1965, 9, 26), "+380984578963", "president@gmail.com", "Pa$5word");
        customerDao.edit(editedCustomer, customer.getEmail());
        Assertions.assertEquals(customerDao.get(editedCustomer.getEmail()).getId(), id);
        Assertions.assertEquals(customerDao.get(editedCustomer.getEmail()).getFullName(), "Petro Oleksiyovych Poroshenko");
        Assertions.assertEquals(customerDao.get(editedCustomer.getEmail()).getEmail(), "president@gmail.com");
    }

    @Test
    void EDIT_NEGATIVE_TEST(){
        Customer editedCustomer = new Customer("Petro Oleksiyovych Poroshenko", LocalDate.of(1965, 9, 26), "+380984578963", "president@gmail.com", "Pa$5word");
        customerDao.edit(editedCustomer, customer.getEmail());
        Assertions.assertNull(customerDao.get(customer.getEmail()).getFullName());
        Assertions.assertNull(customerDao.get(customer.getEmail()).getEmail());
    }

    @Test
    void WITHDRAW_FROM_BALANCE_POSITIVE_TEST(){
        customerDao.withdrawFromBalance("zelya@ukr.net", 25.55);
        Assertions.assertEquals(-25.55, customerDao.get(customer.getEmail()).getBalance());
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 100.55));
        Assertions.assertEquals(75, customerDao.get(customer.getEmail()).getBalance());
    }

    @Test
    void WITHDRAW_FROM_BALANCE_NEGATIVE_TEST(){
        customerDao.withdrawFromBalance("zelya@ukr.net", 25.55);
        Assertions.assertNotEquals(0, customerDao.get(customer.getEmail()).getBalance());
    }

    @Test
    void REPLENISH_BALANCE_POSITIVE_TEST(){
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 100.55));
        Assertions.assertEquals(customerDao.get(customer.getEmail()).getBalance(), 100.55);
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 99.45));
        Assertions.assertEquals(customerDao.get(customer.getEmail()).getBalance(), 200);
    }

    @Test
    void REPLENISH_BALANCE_NEGATIVE_TEST(){
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 100.55));
        Assertions.assertNotEquals(customerDao.get(customer.getEmail()).getBalance(), 0);
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 99.45));
        Assertions.assertNotEquals(customerDao.get(customer.getEmail()).getBalance(), 100.55);
    }

    @Test
    void DEACTIVATE_POSITIVE_TEST(){
        customerDao.deactivate(id);
        Assertions.assertFalse(customerDao.get("zelya@ukr.net").isActive());
    }

    @Test
    void DEACTIVATE_NEGATIVE_TEST(){
        customerDao.deactivate(id);
        Assertions.assertNotEquals(true, customerDao.get("zelya@ukr.net").isActive());
    }

    @Test
    void ACTIVATE_POSITIVE_TEST(){
        customerDao.deactivate(id);
        Assertions.assertFalse(customerDao.get("zelya@ukr.net").isActive());
        customerDao.activate(id);
        Assertions.assertTrue(customerDao.get("zelya@ukr.net").isActive());
    }

    @Test
    void ACTIVATE_NEGATIVE_TEST(){
        customerDao.deactivate(id);
        Assertions.assertFalse(customerDao.get("zelya@ukr.net").isActive());
        customerDao.activate(id);
        Assertions.assertNotEquals(false, customerDao.get("zelya@ukr.net").isActive());
    }

    @Test
    void GET_ALL_AND_ADD_SUBSCRIPTIONS_POSITIVE_TEST(){
        customerDao.addSubscription(id, 1);
        customerDao.addSubscription(id, 2);
        customerDao.addSubscription(id, 3);

        List<Publisher> subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        String publisherName = subscriptionsList.get(0).getName();

        Assertions.assertEquals(3, subscriptionsList.size());
        Assertions.assertEquals(1, publisherDao.get(publisherName).getId());
        customerDao.deleteSubscription(id, 1);
        customerDao.deleteSubscription(id, 2);
        customerDao.deleteSubscription(id, 3);
    }

    @Test
    void GET_ALL_AND_ADD_SUBSCRIPTIONS_NEGATIVE_TEST(){
        customerDao.addSubscription(id, 1);
        customerDao.addSubscription(id, 2);
        customerDao.addSubscription(id, 3);

        List<Publisher> subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        String publisherName = subscriptionsList.get(0).getName();

        Assertions.assertNotEquals(4, subscriptionsList.size());
        Assertions.assertNotEquals(3, publisherDao.get(publisherName).getId());
        customerDao.deleteSubscription(id, 1);
        customerDao.deleteSubscription(id, 2);
        customerDao.deleteSubscription(id, 3);
    }

    @Test
    void DELETE_SUBSCRIPTION_POSITIVE_TEST(){
        customerDao.addSubscription(id, 1);
        customerDao.addSubscription(id, 2);
        customerDao.addSubscription(id, 3);
        List<Publisher> subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        Assertions.assertEquals(3, subscriptionsList.size());
        customerDao.deleteSubscription(id, 1);
        subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        Assertions.assertEquals(2, subscriptionsList.size());
        customerDao.deleteSubscription(id, 2);
        customerDao.deleteSubscription(id, 3);
    }

    @Test
    void DELETE_SUBSCRIPTION_NEGATIVE_TEST(){
        customerDao.addSubscription(id, 1);
        customerDao.addSubscription(id, 2);
        customerDao.addSubscription(id, 3);
        List<Publisher> subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        Assertions.assertNotEquals(2, subscriptionsList.size());

        customerDao.deleteSubscription(id, 1);
        subscriptionsList = customerDao.getAllSubscriptions(customer.getEmail());
        Assertions.assertNotEquals(3, subscriptionsList.size());

        customerDao.deleteSubscription(id, 2);
        customerDao.deleteSubscription(id, 3);
    }

    @Test
    void IS_SUBSCRIBED_POSITIVE_TEST(){
        customerDao.addSubscription(id, 1);
        Assertions.assertTrue(customerDao.isSubscribed(id, 1));
        customerDao.deleteSubscription(id, 1);
    }

    @Test
    void IS_SUBSCRIBED_NEGATIVE_TEST(){
        customerDao.addSubscription(id, 1);
        Assertions.assertFalse(customerDao.isSubscribed(id, 2));
        customerDao.deleteSubscription(id, 1);
    }

    @Test
    void GET_CUSTOMER_BY_EMAIL_POSITIVE_TEST(){
        Assertions.assertEquals(customerDao.get("zelya@ukr.net").getEmail(), "zelya@ukr.net");
        Assertions.assertEquals(customerDao.get("zelya@ukr.net").getId(), id);
        Assertions.assertEquals(customerDao.get("zelya@ukr.net").getFullName(), "Volodymyr Zelenskiy");
    }

    @Test
    void GET_CUSTOMER_BY_EMAIL_NEGATIVE_TEST(){
        Assertions.assertNotEquals(customerDao.get("zelya@ukr.net").getEmail(), "admin@gmail.com");
        Assertions.assertNotEquals(customerDao.get("zelya@ukr.net").getId(), 1);
        Assertions.assertNotEquals(customerDao.get("zelya@ukr.net").getFullName(), "Admin");
    }

    @Test
    void GET_CUSTOMER_BY_PHONE_NUMBER_POSITIVE_TEST(){
        Assertions.assertEquals(customerDao.getByPhoneNumber("+380956312456").getPhoneNumber(), "+380956312456");
        Assertions.assertEquals(customerDao.getByPhoneNumber("+380956312456").getId(), id);
        Assertions.assertEquals(customerDao.getByPhoneNumber("+380956312456").getFullName(), "Volodymyr Zelenskiy");
    }

    @Test
    void GET_CUSTOMER_BY_PHONE_NUMBER_NEGATIVE_TEST(){
        Assertions.assertNotEquals(customerDao.getByPhoneNumber("+380956312456").getEmail(), "admin@gmail.com");
        Assertions.assertNotEquals(customerDao.getByPhoneNumber("+380956312456").getId(), 1);
        Assertions.assertNotEquals(customerDao.getByPhoneNumber("+380956312456").getFullName(), "Admin");
    }

    @Test
    void DELETE_AND_GET_ALL_CUSTOMER_POSITIVE_TEST(){
        Customer newCustomer = new Customer("Biba Bibovych", LocalDate.of(1965, 3, 3), "+3806578459", "biba@gmail.com", "123456Q@q");
        int id = customerDao.signUp(newCustomer);
        List<Customer> userList = customerDao.getAll();
        int firstSize = userList.size();
        customerDao.delete(id);
        userList = customerDao.getAll();
        int secondSize = userList.size();
        Assertions.assertEquals(secondSize+1, firstSize);
    }

    @Test
    void DELETE_AND_GET_ALL_CUSTOMER_NEGATIVE_TEST(){
        Customer newCustomer = new Customer("Biba Bibovych", LocalDate.of(1965, 3, 3), "+3806578459", "biba@gmail.com", "123456Q@q");
        int id = customerDao.signUp(newCustomer);
        List<Customer> userList = customerDao.getAll();
        int firstSize = userList.size();
        customerDao.delete(id);
        userList = customerDao.getAll();
        int secondSize = userList.size();
        Assertions.assertNotEquals(secondSize, firstSize);
    }

}