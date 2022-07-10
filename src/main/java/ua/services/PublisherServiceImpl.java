package ua.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.dao.CustomerMySqlDao;
import ua.dao.PublisherMySqlDao;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.dto.CustomerDto;
import ua.dto.PublisherDto;
import ua.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherServiceImpl implements PublisherService {
    PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
    CustomerMySqlDao customerMySqlDao = new CustomerMySqlDao();

    private static Logger logger = LogManager.getLogger(PublisherServiceImpl.class);
    @Override
    public List<String> create(PublisherDto publisherDto) {
        List<String> validation = validateAddPublisher(publisherDto);

        if (validation.isEmpty()) {
            Publisher publisher = Mapper.convertToPublisher(publisherDto);

            logger.info("publisher inside service -> " + publisher.toString());
            publisherMySqlDao.signUp(publisher);
        }

        return validation;
    }

    @Override
    public List<CustomerDto> getAllSubscribers(String publisherName) {
        return publisherMySqlDao.getSubscribers(publisherName).stream().map(Mapper::convertToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public List<PublisherDto> searchByName(String wantedPublisher) {
        return publisherMySqlDao.searchByName(wantedPublisher).stream().map(Mapper::convertToPublisherDto).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        publisherMySqlDao.delete(id);
    }

    @Override
    public List<String> addNewVersion(PublisherDto publisherDto) { //todo test
        List<String> validation = validateAddNewVersion(publisherDto);
        UserService userService = new UserServiceImpl();

        if (validation.isEmpty()) {
            Publisher publisher = Mapper.convertToAddNewVersionPublisher(publisherDto);
            int publisherId = publisherMySqlDao.get(publisherDto.getName()).getId();
            double price = publisherMySqlDao.get(publisherDto.getName()).getPrice();

            List<Customer> customerList = publisherMySqlDao.getSubscribers(publisherDto.getName());
            for (Customer customer : customerList){
                if (customer.getBalance() > price){
                    customerMySqlDao.withdrawFromBalance(customer.getEmail(), price);
                } else{
                    userService.unsubscribe(customer.getId(), publisherId);
                }
            }
            logger.info("publisher inside service -> " + publisher.toString());
            publisherMySqlDao.addNewVersion(publisher);
        }

        return validation;
    }

    @Override
    public List<String> editPublisher(PublisherDto publisherDto) {
        List<String> validation = validateEditPublisher(publisherDto);

        if (validation.isEmpty()) {
            Publisher publisher = Mapper.convertToPublisher(publisherDto);
            logger.info("publisher inside service -> " + publisher.toString());
            publisherMySqlDao.editPublisher(publisher);
        }

        return validation;
    }

    private List<String> validateEditPublisher(PublisherDto publisherDto) {
        List<String> checkResult = new ArrayList<>();
        if (validDescriptionLength(publisherDto.getDescription())){
            checkResult.add("publisherDescription");
        }
        return checkResult;
    }

    private List<String> validateAddPublisher(PublisherDto publisherDto) {
        List<String> checkResult = new ArrayList<>();

        if (validNameLength(publisherDto.getName())){
            checkResult.add("publisherName");
        }
        if (!validNameIfExist(publisherDto.getName())){
            checkResult.add("publisherNameExist");
        }
        if (validNotNull(publisherDto.getTopic())){
            checkResult.add("publisherTopicIsNull");
        }
        if (validNotNull(publisherDto.getPrice())){
            checkResult.add("publisherPriceIsNull");
        }
        if (validDescriptionLength(publisherDto.getDescription())){
            checkResult.add("publisherDescription");
        }
        return checkResult;
    }

    private List<String> validateAddNewVersion(PublisherDto publisherDto) {
        List<String> checkResult = new ArrayList<>();

        if (validVersion(publisherDto.getVersion(), publisherDto.getName())){
            checkResult.add("publisherVersion");
        }
        return checkResult;
    }

    private boolean validVersion(String version, String publisherName) {
        return publisherMySqlDao.get(publisherName).getVersion() >= Integer.valueOf(version);
    }

    private boolean validNameLength (String publisherName){
        return publisherName.length() > 40;
    }
    private boolean validNameIfExist (String publisherName){
        return publisherMySqlDao.get(publisherName).getName() == null;
    }
    private boolean validNotNull (String entity){ //for topic and price
        return entity == null || entity.equals("");
    }

    private boolean validDescriptionLength (String description){
        return description.length() > 500;
    }

    @Override
    public Publisher get(String name) {
        return publisherMySqlDao.get(name);
    }

    @Override
    public List<Publisher> getAll() {
        return publisherMySqlDao.getAll();
    }

    @Override
    public List<Publisher> getByTopic(String topic) {
        return publisherMySqlDao.getByTopic(topic);
    }

}
