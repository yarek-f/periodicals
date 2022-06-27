import ua.dao.CustomerMySqlDao;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class MainTestClass {
    public static void main(String[] args) {
        PublisherService publisherService = new PublisherServiceImpl();
        UserService userService = new UserServiceImpl();
        boolean subscribeUser = userService.isSubscribed(3, 11);
        boolean unsubscribeUser = userService.isSubscribed(5, 2);

        List<Publisher> publisherList = isSubscribed(publisherService.getAll(), 2);

        for (Publisher p : publisherList) {
            System.out.println("id: " + p.getId() + " " + p.getName() + " " + p.isSubscribed());
        }
    }

    private static List<Publisher> isSubscribed(List<Publisher> list, int customerId) {
        UserService userService = new UserServiceImpl();
        for (Publisher p : list) {
            if (userService.isSubscribed(customerId, p.getId())) {
                p.setSubscribed(1);
            }
        }
        return list;
    }
}
