import ua.dao.CustomerMySqlDao;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Publisher;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MainTestClass {
    public static void main(String[] args) {
//        CustomerMySqlDao customerMySqlDao = new CustomerMySqlDao();
//        customerMySqlDao.addSubscription(4, 5);
//        boolean res = customerMySqlDao.isSubscribed(10, 4);
//        System.out.println(res);
        PublisherService publisherService = new PublisherServiceImpl();
        isSubscribed(publisherService.getAll(), 4);
        List<Publisher> res = publisherService.getAll();
        for (Publisher p : res) {
            System.out.println(p);
        }
    }

    private static void isSubscribed(List<Publisher> list, int customerId){
        UserService userService = new UserServiceImpl();
        for (Publisher p : list) {
            if (userService.isSubscribed(customerId, p.getId())){
                p.setSubscribed(true);
            }
        }
    }
}
