import ua.dao.CustomerMySqlDao;
import ua.dao.DataSource;
import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.Topics;
import ua.domain.User;
import ua.dto.PublisherGetDto;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;
import ua.services.PublisherServiceImpl;
import ua.services.UserServiceImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTestClass {
    public static void main(String[] args) {
        PublisherServiceImpl service = new PublisherServiceImpl();
        List<Publisher> publisherList = service.getAll();
        int p = 0;
        publisherList.iterator().hasNext();
        for (Publisher publisher : publisherList) {
            System.out.println();
            for (int i = 1; i <= 4; i++) {
                System.out.print(i + ". " + publisher.getName() + " ");
            }
        }
    }
}
