import ua.dao.CustomerMySqlDao;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.dto.CustomerDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MainTestClass {
    public static void main(String[] args) {
        PublisherService publisherService = new PublisherServiceImpl();
        List<CustomerDto> list = publisherService.getAllSubscribers("Science");
        for (CustomerDto c : list){
            System.out.println(c);
        }


    }

}
