package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.dto.PublisherDto;
import ua.excaptions.UserException;
import ua.mapper.Mapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PublisherMySqlDao implements Dao<Publisher> {
    private static final String CREATE_QUERY = "insert into publishers (image, publisher_name, topic, price, publisher_description) values (?, ?, ?, ?, ?)";
    private static final String GET_ALL_QUERY = "SELECT * from publishers where is_active = true";
    private static final String SQL_CALC_FOUND_ROWS = "select SQL_CALC_FOUND_ROWS * from publishers limit ?, ?";
    private static final String DELETE_QUERY = "update publishers set is_active = false where id = ?";

    private int noOfRecords;

    private static Logger logger = LogManager.getLogger(PublisherMySqlDao.class);
    @Override
    public int signUp(Publisher item) {

        logger.debug("Start Publisher creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

            stmt.setString(1, item.getImage());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getTopic().toString());
            stmt.setDouble(4, item.getPrice());
            stmt.setString(5, item.getDescription());

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        }catch (Exception ex) {
            logger.debug("Problem with creating Publisher: " + ex.getMessage());
        }
        logger.debug("Publisher creating successfully");
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
    public boolean delete(int id) {
        logger.debug("Start Publisher deleting");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( DELETE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.debug("Problem with deleting Publisher: " + ex.getMessage());
        }
        logger.debug("Publisher deleting successfully");
        return false;
    }

    public List<PublisherDto> getAll(int offset, int noOfRecords) {
        Connection connection = null;
        Statement statement;
        PreparedStatement pstmt;
        ResultSet resultSet;

        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<PublisherDto> publisherList = new ArrayList<>();
        Publisher publisher = null;
        try {
            pstmt = connection.prepareStatement(SQL_CALC_FOUND_ROWS);
            statement = connection.createStatement();
            pstmt.setInt(1, offset);
            pstmt.setInt(2, noOfRecords);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String image = resultSet.getString("image");
                String publisherName = resultSet.getString("publisher_name");
                int version = resultSet.getInt("version");
                String publisherTopic = resultSet.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("publisher_description");
                LocalDateTime created = resultSet.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = resultSet.getTimestamp("updated").toLocalDateTime();
                boolean isActive = resultSet.getBoolean("is_active");

                publisher = new Publisher(id, image, publisherName, version, topic, price, description, created, updated, isActive);

                publisherList.add(Mapper.convertToPublisherDto(publisher));
            }
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return publisherList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
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
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, publisherName, topic, price, image, created, updated, isActive);

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
