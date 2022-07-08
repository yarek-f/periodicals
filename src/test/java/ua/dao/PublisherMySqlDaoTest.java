package ua.dao;


import org.junit.jupiter.api.*;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Topics;

import java.time.LocalDate;
import java.util.List;

class PublisherMySqlDaoTest {
    private PublisherMySqlDao publisherDao = new PublisherMySqlDao();
    private CustomerMySqlDao customerDao = new CustomerMySqlDao();
    private Publisher publisher = new Publisher();
    private int id = 0;

    @BeforeAll
    static void dbInit(){
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

    @BeforeEach
    void before(){
        publisher = new Publisher("defaultPicture.png", "New news", Topics.NEWS, 99.54, "Some description");
        id = publisherDao.signUp(publisher);
    }

    @AfterEach
    void after(){
        publisherDao.delete(id);
    }

    @Test
    void SIGN_UP_PUBLISHER_POSITIVE_TEST(){
        Assertions.assertEquals(publisherDao.get(publisher.getName()).getId(), id);
    }

    @Test
    void SIGN_UP_PUBLISHER_NEGATIVE_TEST(){
        Assertions.assertNotEquals(publisherDao.get(publisher.getName()).getId(), 1);
    }

    @Test
    void GET_PUBLISHER_BY_NAME_POSITIVE_TEST(){
        Assertions.assertEquals(publisherDao.get(publisher.getName()).getTopic(), publisher.getTopic());
        Assertions.assertEquals(publisherDao.get(publisher.getName()).getPrice(), publisher.getPrice());
        Assertions.assertEquals(publisherDao.get(publisher.getName()).getDescription(), publisher.getDescription());
    }

    @Test
    void GET_PUBLISHER_BY_NAME_NEGATIVE_TEST(){
        Assertions.assertNotEquals(publisherDao.get(publisher.getName()).getTopic(), Topics.ECONOMY);
        Assertions.assertNotEquals(publisherDao.get(publisher.getName()).getId(), 1);
    }

    @Test
    void DELETE_AND_GET_ALL_PUBLISHER_POSITIVE_TEST(){
        Publisher publisher = new Publisher("picture.jpg", "Coll news", Topics.OTHER, 55.23, "Some other description");
        int id = publisherDao.signUp(publisher);
        int listSizeBeforeDeleting = publisherDao.getAll().size();
        publisherDao.delete(id);
        int listSizeAfterDeleting = publisherDao.getAll().size();
        Assertions.assertEquals(listSizeAfterDeleting, listSizeBeforeDeleting - 1);
    }

    @Test
    void DELETE_AND_GET_ALL_PUBLISHER_NEGATIVE_TEST(){
        Publisher publisher = new Publisher("picture.jpg", "Coll news", Topics.OTHER, 55.23, "Some other description");
        int id = publisherDao.signUp(publisher);
        int listSizeBeforeDeleting = publisherDao.getAll().size();
        publisherDao.delete(id);
        int listSizeAfterDeleting = publisherDao.getAll().size();
        Assertions.assertNotEquals(listSizeAfterDeleting, listSizeBeforeDeleting);
    }

    @Test
    void GET_BY_ID_POSITIVE_TEST(){
        Assertions.assertEquals(id, publisherDao.getById(id).getId());
        Assertions.assertEquals(publisher.getName(), publisherDao.getById(id).getName());
        Assertions.assertEquals(publisher.getDescription(), publisherDao.getById(id).getDescription());
    }

    @Test
    void GET_BY_ID_NEGATIVE_TEST(){
        Assertions.assertNotEquals(1, publisherDao.getById(id).getId());
        Assertions.assertNotEquals("Time", publisherDao.getById(id).getName());
        Assertions.assertNotEquals("Description", publisherDao.getById(id).getDescription());
    }

    @Test
    void ADD_NEW_VERSION_POSITIVE_TEST(){
        Publisher newPublisher = new Publisher(publisher.getName(), publisher.getImage(), 2);
        publisherDao.addNewVersion(newPublisher);
        Assertions.assertEquals(2, publisherDao.get(publisher.getName()).getVersion());
    }

    @Test
    void ADD_NEW_VERSION_NEGATIVE_TEST(){
        Publisher newPublisher = new Publisher(publisher.getName(), publisher.getImage(), 2);
        publisherDao.addNewVersion(newPublisher);
        Assertions.assertNotEquals(1, publisherDao.get(publisher.getName()).getVersion());
    }

    @Test
    void EDIT_PUBLISHER_POSITIVE_TEST(){
        Publisher editedPublisher = new Publisher(publisher.getImage(), publisher.getName(), Topics.ECONOMY, 33.33, "New description");
        publisherDao.editPublisher(editedPublisher);
        Assertions.assertEquals(editedPublisher.getTopic(), publisherDao.get(publisher.getName()).getTopic());
        Assertions.assertEquals(editedPublisher.getPrice(), publisherDao.get(publisher.getName()).getPrice());
        Assertions.assertEquals(editedPublisher.getDescription(), publisherDao.get(publisher.getName()).getDescription());
    }

    @Test
    void EDIT_PUBLISHER_NEGATIVE_TEST(){
        Publisher editedPublisher = new Publisher(publisher.getImage(), publisher.getName(), Topics.ECONOMY, 33.33, "New description");
        publisherDao.editPublisher(editedPublisher);
        Assertions.assertNotEquals(editedPublisher.getTopic(), publisher.getTopic());
        Assertions.assertNotEquals(editedPublisher.getPrice(), publisher.getPrice());
        Assertions.assertNotEquals(editedPublisher.getDescription(), publisher.getDescription());
    }

    @Test
    void GET_BY_TOPIC_POSITIVE_TEST(){
        List<Publisher> list = publisherDao.getByTopic("FASHION");
        int counter = 0;
        for (Publisher p : list){
            if (p.getTopic().equals(Topics.FASHION)) counter++;
        }
        Assertions.assertEquals(counter, list.size());
    }

    @Test
    void GET_BY_TOPIC_NEGATIVE_TEST(){
        List<Publisher> list = publisherDao.getByTopic("FASHION");
        int counter = 0;
        for (Publisher p : list){
            if (p.getTopic().equals(Topics.FASHION)) counter++;
        }
        Assertions.assertNotEquals(counter-1, list.size());
    }

    @Test
    void SEARCH_BY_NAME_POSITIVE_TEST(){
       Publisher publisher2 = new Publisher("picture.png", "Daily news", Topics.NEWS, 99.54, "Some description");
       List<Publisher> beforeCreatingNewPublisherList = publisherDao.searchByName("new");
       int id = publisherDao.signUp(publisher2);
       Assertions.assertEquals(beforeCreatingNewPublisherList.size()+1, publisherDao.searchByName("new").size());
       publisherDao.delete(id);
    }

    @Test
    void SEARCH_BY_NAME_NEGATIVE_TEST(){
       Publisher publisher2 = new Publisher("picture.png", "Daily news", Topics.NEWS, 99.54, "Some description");
       List<Publisher> beforeCreatingNewPublisherList = publisherDao.searchByName("new");
       int id = publisherDao.signUp(publisher2);
       Assertions.assertNotEquals(beforeCreatingNewPublisherList.size(), publisherDao.searchByName("new").size());
       publisherDao.delete(id);
    }

    @Test
    void GET_SUBSCRIBERS_POSITIVE_TEST(){
        Customer customer1 = new Customer("Volodymyr Zelenskiy", LocalDate.of(1986, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        Customer customer2 = new Customer("Olena Zelenska", LocalDate.of(1982, 3, 3), "+380995612357", "zelenska@ukr.net", "Ukr@ole55");
        int customerId1 = customerDao.signUp(customer1);
        int customerId2 = customerDao.signUp(customer2);
        customerDao.addSubscription(customerId1, publisherDao.get(publisher.getName()).getId());
        customerDao.addSubscription(customerId2, publisherDao.get(publisher.getName()).getId());

        List<Customer> subscribers = publisherDao.getSubscribers(publisher.getName());
        Assertions.assertEquals(subscribers.size(), 2);

        customerDao.deleteSubscription(customerId1, publisherDao.get(publisher.getName()).getId());
        customerDao.deleteSubscription(customerId2, publisherDao.get(publisher.getName()).getId());
        customerDao.delete(customerId1);
        customerDao.delete(customerId2);
    }

    @Test
    void GET_SUBSCRIBERS_NEGATIVE_TEST(){
        Customer customer1 = new Customer("Volodymyr Zelenskiy", LocalDate.of(1986, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        Customer customer2 = new Customer("Olena Zelenska", LocalDate.of(1982, 3, 3), "+380995612357", "zelenska@ukr.net", "Ukr@ole55");
        int customerId1 = customerDao.signUp(customer1);
        int customerId2 = customerDao.signUp(customer2);
        customerDao.addSubscription(customerId1, publisherDao.get(publisher.getName()).getId());
        customerDao.addSubscription(customerId2, publisherDao.get(publisher.getName()).getId());

        List<Customer> subscribers = publisherDao.getSubscribers(publisher.getName());
        Assertions.assertNotEquals(subscribers.size(), 1);
        Assertions.assertNotEquals(subscribers.size(), 0);

        customerDao.deleteSubscription(customerId1, publisherDao.get(publisher.getName()).getId());
        customerDao.deleteSubscription(customerId2, publisherDao.get(publisher.getName()).getId());
        customerDao.delete(customerId1);
        customerDao.delete(customerId2);
    }

}