package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.dto.PublisherDto;
import ua.excaptions.UserException;
import ua.mapper.Mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherMySqlDao implements Dao<Publisher> {
    private static final String CREATE_QUERY = "insert into publishers (image, publisher_name, topic, price, publisher_description) values (?, ?, ?, ?, ?)";
    private static final String GET_ALL_QUERY = "SELECT * from publishers";
    private static final String SQL_CALC_FOUND_ROWS = "select SQL_CALC_FOUND_ROWS * from publishers limit ?, ?";
    private static final String DELETE_QUERY = "DELETE FROM publishers WHERE id = ?";
    private static final String GET_ALL_BY_TOPIC = "select * from publishers where topic = ? and is_active = true";
    private static final String ADD_NEW_VERSION = "update publishers set image = ?, version = ? where publisher_name = ?";
    private static final String EDIT_PUBLISHER = "update publishers set image = ?, topic = ?,price = ?, publisher_description = ?, updated = now() where publisher_name = ?";
    private static final String GET_PUBLISHER_BY_NAME = "select * from publishers where publisher_name = ? and is_active = true";
    private static final String SEARCH_BY_NAME = "SELECT * FROM publishers WHERE publisher_name LIKE ? and is_active = true";
    private static final String GET_ACTIVE_PUBLISHERS = "SELECT * from publishers where is_active = true";
    private static final String GET_SUBSCRIBERS = "select c.id, c.fullname, c.dob, c.phone_number, c.email, c.balance from publishers p inner join publisher_customer pc ON p.id = pc.pub_id INNER JOIN customers c ON c.id = pc.cus_id where p.publisher_name = ?";


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
            //todo exception
        }
        logger.debug("Publisher creating successfully");
        return item.getId();
    }

    @Override
    public Publisher get(String name) {
        Publisher publisher = new Publisher();
        logger.debug("Start getting Publisher");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(GET_PUBLISHER_BY_NAME)){
            pstm.setString(1, name);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String image = rs.getString("image");
                String publisherName = rs.getString("publisher_name");
                int version = rs.getInt("version");
                String publisherTopic = rs.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                double price = rs.getDouble("price");
                String description = rs.getString("publisher_description");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                publisher = new Publisher(id, image, publisherName, version, topic, price, description, created, updated, isActive);
            }

        } catch (SQLException ex){
            logger.debug("Problem with getting Publisher: " + ex.getMessage());
        }
        logger.debug("Publisher getting successfully");

        return publisher;
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


    public boolean addNewVersion(Publisher publisher) {
        logger.debug("Start adding new version of publisher");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( ADD_NEW_VERSION )) {

            stmt.setString(1, publisher.getImage());
            stmt.setInt(2, publisher.getVersion());
            stmt.setString(3, publisher.getName());
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.debug("Problem with adding new version of publisher: " + ex.getMessage());
        }
        logger.debug("updating version successfully" + publisher.toString());
        return false;
    }

    public void editPublisher(Publisher publisher) {
        logger.debug("Start edit publisher");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( EDIT_PUBLISHER )) {

            stmt.setString(1, publisher.getImage());
            stmt.setString(2, publisher.getTopic().toString());
            stmt.setDouble(3, publisher.getPrice());
            stmt.setString(4, publisher.getDescription());
            stmt.setString(5, publisher.getName());
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.debug("Problem with editing publisher: " + ex.getMessage());
        }
        logger.debug("editing publisher happened successfully");
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

    public List<Publisher> getByTopic(String topic) {
        logger.debug("Start getting all Publisher by topic");
        List<Publisher> publisherList = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
             PreparedStatement pstm = con.prepareStatement(GET_ALL_BY_TOPIC)){
             //Statement stmt = con.createStatement()){
            pstm.setString(1, topic);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String image = rs.getString("image");
                String publisherName = rs.getString("publisher_name");
                int version = rs.getInt("version");
                double price = rs.getDouble("price");
                String description = rs.getString("publisher_description");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, image, publisherName, version, Topics.valueOf(topic), price, description, created, updated, isActive);

                publisherList.add(publisher);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.debug("Problem with getting users: " + ex.getMessage());
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
                String image = rs.getString("image");
                String publisherName = rs.getString("publisher_name");
                int version = rs.getInt("version");
                String publisherTopic = rs.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                double price = rs.getDouble("price");
                String description = rs.getString("publisher_description");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, image, publisherName, version, topic, price, description, created, updated, isActive);

                publisherList.add(publisher);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.debug("Problem with getting users: " + ex.getMessage());
        }
        return publisherList;
    }

    public List<Publisher> getActivePublishers() {
        logger.debug("Start getting active publishers");
        List<Publisher> publisherList = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ACTIVE_PUBLISHERS)){

            while (rs.next()) {

                int id = rs.getInt("id");
                String image = rs.getString("image");
                String publisherName = rs.getString("publisher_name");
                int version = rs.getInt("version");
                String publisherTopic = rs.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                double price = rs.getDouble("price");
                String description = rs.getString("publisher_description");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, image, publisherName, version, topic, price, description, created, updated, isActive);

                publisherList.add(publisher);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.debug("Problem with getting active publishers: " + ex.getMessage());
        }
        return publisherList;
    }

    public List<Customer> getSubscribers(String publisherName){
        logger.debug("Start getting subscribers");
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();

        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_SUBSCRIBERS)){
            stmt.setString(1, publisherName);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                LocalDate dob = rs.getDate("dob").toLocalDate(); //fixme login doesn't work without dob
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");

                customer = new Customer(id, fullName, dob, phoneNumber, email, balance);
                customerList.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("Problem with getting subscribers: " + ex.getMessage());
        }
        return customerList;
    }

    public List<Publisher> getByName(String searchPattern){
        logger.debug("start searching");
        List<Publisher> publisherList = new ArrayList<>();
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SEARCH_BY_NAME)) {
            pstm.setString(1, "%" + searchPattern + "%");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String image = rs.getString("image");
                String publisherName = rs.getString("publisher_name");
                int version = rs.getInt("version");
                String publisherTopic = rs.getString("topic");
                Topics topic = Topics.valueOf(publisherTopic);
                double price = rs.getDouble("price");
                String description = rs.getString("publisher_description");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();
                boolean isActive = rs.getBoolean("is_active");

                Publisher publisher = new Publisher(id, image, publisherName, version, topic, price, description, created, updated, isActive);

                publisherList.add(publisher);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publisherList;
    }



    @Override
    public void clearTable() {

    }


}
