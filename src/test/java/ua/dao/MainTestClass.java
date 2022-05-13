package ua.dao;

import ua.dao.PublisherMySqlDao;
import ua.dao.UserMySqlDao;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.Topics;
import ua.domain.User;
import ua.services.PublisherServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTestClass {
    public static void main(String[] args) {
        UserMySqlDao userMySqlDao = null;
        CustomerMySqlDao customerMySqlDao = null;
        try {
            userMySqlDao = new UserMySqlDao(DataSource.getConnection());
            customerMySqlDao = new CustomerMySqlDao(DataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerMySqlDao.delete("kakui@ukt.net");
    }
}
