package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.Topics;
import ua.domain.User;
import ua.excaptions.UserException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PublisherMySqlDao implements Dao<Publisher> {
    private static final String CREATE_QUERY = "insert into publisher(publisher_name, topic) values (?,?)";
    private static final String GET_ALL_QUERY = "SELECT * from publisher";

    private static Logger logger = LogManager.getLogger(PublisherMySqlDao.class);
    @Override
    public int signUp(Publisher item) {
        logger.debug("Start user creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getTopic().name());

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        }catch (Exception ex) {
            logger.debug("Problem with creating user: " + ex.getMessage());
        }
        return item.getId();
    }

    @Override
    public Publisher get(String email) {
        return null;
    }

    @Override
    public int update(Publisher items, int id) {
        return 0;
    }

    @Override
    public boolean delete(String email) {
        return false;
    }

    @Override
    public List<Publisher> getAll() {
        logger.debug("Start getting all users");
        List<Publisher> publisherList = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)){

            while (rs.next()) {
                int id = rs.getInt("id");
                String publisherName = rs.getString("publisher_name");
                String publisherTopic = rs.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, publisherName, topic, created, updated, isActive);

                publisherList.add(publisher);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.debug("Problem with getting users: " + ex.getMessage());
        }
        return publisherList;
    }

    @Override
    public void clearTable() {

    }
}
