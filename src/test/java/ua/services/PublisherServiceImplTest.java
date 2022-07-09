package ua.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.dao.PublisherMySqlDao;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.dto.PublisherDto;

import java.time.LocalDate;
import java.util.List;

class PublisherServiceImplTest {
    PublisherServiceImpl publisherService = new PublisherServiceImpl();
    PublisherMySqlDao publisherDao = new PublisherMySqlDao();
    CustomerMySqlDao customerDao = new CustomerMySqlDao();

    @BeforeAll
    static void dbInit() {
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

    @Test
    void CREATE_PUBLISHER_POSITIVE_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        List<String> validation = publisherService.create(publisherDto);
        Assertions.assertTrue(validation.isEmpty());
        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void CREATE_PUBLISHER_NEGATIVE_TEST(){
        PublisherDto existedPublisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(existedPublisherDto);
        PublisherDto firstPublisherDto = new PublisherDto("defaultPicture.png", "New newsNew newsNew newsNew newsNew newsNew newsNew newsNew newsNew newsNew news", null, null, "Some descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome descriptionSome description");
        List<String> validation = publisherService.create(firstPublisherDto);
        Assertions.assertFalse(validation.isEmpty());
        Assertions.assertEquals(validation.size(), 4);

        PublisherDto theSamePublisher = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        List<String> additionalValidation = publisherService.create(theSamePublisher);
        Assertions.assertFalse(additionalValidation.isEmpty());
        Assertions.assertEquals(additionalValidation.size(), 1);
        publisherService.delete(publisherDao.get(existedPublisherDto.getName()).getId());
    }

    @Test
    void GET_ALL_SUBSCRIBERS_TEST(){
        Customer customer = new Customer("Volodymyr Zelenskiy", LocalDate.of(1965, 3, 3), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        int cusId = customerDao.signUp(customer);
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        int pubId = publisherDao.get(publisherDto.getName()).getId();

        Assertions.assertEquals(publisherService.getAllSubscribers(publisherDto.getName()).size(), 0);
        customerDao.addSubscription(cusId, pubId);

        Assertions.assertEquals(publisherService.getAllSubscribers(publisherDto.getName()).size(), 1);
        customerDao.deleteSubscription(cusId, pubId);
        customerDao.delete(cusId);
        publisherService.delete(pubId);
    }

    @Test
    void SEARCH_BY_NAME_TEST(){
        Publisher publisher2 = new Publisher("picture.png", "Daily news", Topics.NEWS, 99.54, "Some description");
        List<Publisher> beforeCreatingNewPublisher = publisherDao.searchByName("new");
        int id = publisherDao.signUp(publisher2);
        Assertions.assertEquals(beforeCreatingNewPublisher.size()+1, publisherDao.searchByName("new").size());
        publisherDao.delete(id);
    }

    @Test
    void GET_BY_NAME_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        Assertions.assertEquals("New news", publisherService.get(publisherDto.getName()).getName());
        Assertions.assertEquals("Some description", publisherService.get(publisherDto.getName()).getDescription());
        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void GET_ALL_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        List<Publisher> beforeCreating = publisherService.getAll();
        publisherService.create(publisherDto);
        List<Publisher> afterCreating = publisherService.getAll();
        Assertions.assertEquals(beforeCreating.size()+1, afterCreating.size());
        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void GET_BY_TOPIC_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        List<Publisher> beforeCreating = publisherService.getByTopic("NEWS");
        publisherService.create(publisherDto);
        List<Publisher> afterCreating = publisherService.getByTopic("NEWS");
        Assertions.assertEquals(beforeCreating.size()+1, afterCreating.size());
        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void EDIT_PUBLISHER_POSITIVE_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        PublisherDto editedPublisher = new PublisherDto("picture.png", "New news", "OTHER", "44.85", "Some other description");
        List<String> validation = publisherService.editPublisher(editedPublisher);

        Assertions.assertTrue(validation.isEmpty());
        Assertions.assertEquals(editedPublisher.getDescription(), publisherService.get(publisherDto.getName()).getDescription());
        Assertions.assertEquals(Double.valueOf(editedPublisher.getPrice()), publisherService.get(publisherDto.getName()).getPrice());

        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void EDIT_PUBLISHER_NEGATIVE_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        PublisherDto editedPublisher = new PublisherDto("picture.png", "New news", "OTHER", "44.85", "Some other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionscriptionSome other descriptionSome other descriptionSome other descriptionSome other descriptionSome other description");
        List<String> validation = publisherService.editPublisher(editedPublisher);

        Assertions.assertFalse(validation.isEmpty());
        Assertions.assertNotEquals(editedPublisher.getDescription(), publisherService.get(publisherDto.getName()).getDescription());
        Assertions.assertNotEquals(Double.valueOf(editedPublisher.getPrice()), publisherService.get(publisherDto.getName()).getPrice());
        publisherService.delete(publisherDao.get(publisherDto.getName()).getId());
    }

    @Test
    void ADD_NEW_VERSION_POSITIVE_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        int publisherId = publisherService.get(publisherDto.getName()).getId();
        PublisherDto newVersion = new PublisherDto("newDefaultPicture.jpg", "New news", "2");

        Customer customer1 = new Customer("Volodymyr Zelenskiy", LocalDate.of(1965, 1, 23), "+380956312456", "zelya@ukr.net", "Ukr@vova22");
        Customer customer2 = new Customer("Olena Zelenska", LocalDate.of(1988, 3, 3), "+380956317845", "olena@ukr.net", "Ukr@ole22");
        int cusId1 = customerDao.signUp(customer1);
        int cusId2 = customerDao.signUp(customer2);
        customerDao.addSubscription(cusId1, publisherId);
        customerDao.addSubscription(cusId2, publisherId);
        customerDao.replenishBalance(new Customer("zelya@ukr.net", 100.55));
        customerDao.replenishBalance(new Customer("olena@ukr.net", 98.45));

        List<String> validation = publisherService.addNewVersion(newVersion);
        Assertions.assertTrue(validation.isEmpty());
        Assertions.assertEquals(customerDao.get(customer1.getEmail()).getBalance(), 100.55-99.54);
        Assertions.assertEquals(customerDao.get(customer2.getEmail()).getBalance(), 98.45);
        Assertions.assertTrue(customerDao.isSubscribed(cusId1, publisherId));
        Assertions.assertFalse(customerDao.isSubscribed(cusId2, publisherId));

        publisherService.delete(publisherId);
        customerDao.delete(cusId1);
        customerDao.delete(cusId2);
    }

    @Test
    void ADD_NEW_VERSION_NEGATIVE_TEST(){
        PublisherDto publisherDto = new PublisherDto("defaultPicture.png", "New news", "NEWS", "99.54", "Some description");
        publisherService.create(publisherDto);
        int publisherId = publisherService.get(publisherDto.getName()).getId();
        PublisherDto newVersion = new PublisherDto("newDefaultPicture.jpg", "New news", "0");

        List<String> validation = publisherService.addNewVersion(newVersion);
        Assertions.assertFalse(validation.isEmpty());

        publisherService.delete(publisherId);
    }



}