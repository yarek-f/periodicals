package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Customer;
import ua.domain.Publisher;
import ua.excaptions.UserException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerMySqlDao implements Dao<Customer> {
    private static final String CREATE_QUERY = "insert into customers (fullname, dob, phone_number, email, user_password) values (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from customers where id = ?";
    private static final String GET_PHONE_NUMBER_QUERY = "select * from customers where phone_number = ?";
    private static final String GET_CUSTOMER = "select * from customers where email = ?";
    private static final String SUBSCRIBE_USER = "insert into publisher_customer (cus_id, pub_id) values(?, ?)";
    private static final String IS_SUBSCRIBE_USER = "select * from publisher_customer where cus_id = ? and pub_id = ?";
    private static final String UNSUBSCRIBE_USER = "delete from publisher_customer where cus_id = ? and pub_id = ?";
    private static final String UPDATE_BALANCE = "update customers set balance = ? where email = ?";
    private static final String EDIT_QUERY = "update customers set fullname = ?, dob = ?, phone_number = ?, email = ?, user_password = ? where email = ?";
    private static final String DEACTIVATE_QUERY = "update customers set is_active = false where id = ?";
    private static final String ACTIVATE_QUERY = "update customers set is_active = true where id = ?";
    private static final String GET_ALL_QUERY = "select * from customers";
    private static final String GET_ALL_SUBSCRIPTIONS = "SELECT p.publisher_name FROM customers c INNER JOIN publisher_customer pc ON c.id = pc.cus_id INNER JOIN publishers p ON p.id = pc.pub_id where c.email = ?";

    private static Logger logger = LogManager.getLogger(CustomerMySqlDao.class);

    public CustomerMySqlDao() {
    }

    @Override
    public int signUp(Customer item) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

            stmt.setString(1, item.getFullName());
            if (item.getDob() != null){
                stmt.setDate(2, Date.valueOf(item.getDob()));
            }else{
                stmt.setDate(2, null);
            }
            stmt.setString(3, item.getPhoneNumber());
            stmt.setString(4, item.getEmail());
            stmt.setString(5, item.getPassword());

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
            logger.error("Problem with creating user: " + ex.getMessage());
        }
        return get(item.getEmail()).getId();
    }

    @Override
    public void edit(Customer customer, String currentEmail) {
        logger.debug("Start customer editing");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( EDIT_QUERY )) {

            stmt.setString(1, customer.getFullName());
            if (customer.getDob() != null){
                stmt.setDate(2, Date.valueOf(customer.getDob()));
            }else{
                stmt.setDate(2, null);
            }
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getPassword());
            stmt.setString(6, currentEmail);

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
            logger.error("Problem with editing customer: " + ex.getMessage());
        }
    }

    public void withdrawFromBalance(String email, double price){
        logger.debug("Start withdraw money from balance");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UPDATE_BALANCE)) {

            double newBalance = get(email).getBalance() - price;

            pstm.setDouble(1, newBalance);
            pstm.setString(2, email);

            int status = pstm.executeUpdate();
            if(status!=1) throw new UserException("Update more than one record!!!");
        } catch (SQLException e) {
            logger.error("Problem with withdraw user money from balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void replenishBalance(Customer customer) {
        logger.debug("Start replenish the balance");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UPDATE_BALANCE)) {

            double oldBalance = get(customer.getEmail()).getBalance();

            pstm.setDouble(1, customer.getBalance()+oldBalance);
            pstm.setString(2, customer.getEmail());

            int status = pstm.executeUpdate();
            if(status!=1) throw new UserException("Update more than one record!!!");
        } catch (SQLException e) {
            logger.error("Problem with replenish user balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate(int id) {
        logger.debug("Start customer deactivating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( DEACTIVATE_QUERY )) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.error("Problem with deactivating customer: " + ex.getMessage());
        }
    }

    public void activate(int id) {
        logger.debug("Start user activating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( ACTIVATE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.error("Problem with activating user: " + ex.getMessage());
        }
        logger.debug("User activating successfully");
    }


    public List<Publisher> getAllSubscriptions(String email) {
        logger.debug("Start getting all subscriptions");
        List<Publisher> subscriptions = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_SUBSCRIPTIONS)){
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String publisherName = rs.getString("publisher_name");

                PublisherMySqlDao publisherDao = new PublisherMySqlDao();

                subscriptions.add(publisherDao.get(publisherName));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("Problem with getting subscriptions: " + ex.getMessage());
        }

        logger.debug("Got all subscriptions");
        return subscriptions;
    }

    public void addSubscription(int cus_id, int pub_id){
        logger.debug("Start subscribing user");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SUBSCRIBE_USER)) {
            pstm.setInt(1, cus_id);
            pstm.setInt(2, pub_id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            logger.error("Problem with subscribing user" + e.getMessage());
        }
        logger.debug("User subscribed successfully!");
    }

    public void deleteSubscription(int cus_id, int pub_id){
        logger.debug("Starting unsubscribe user");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UNSUBSCRIBE_USER)) {
            pstm.setInt(1, cus_id);
            pstm.setInt(2, pub_id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            logger.error("Problem with unsubscribe user" + e.getMessage());
        }
        logger.debug("User unsubscribed successfully!");
    }




    public boolean isSubscribed(int cus_id, int pub_id){
        logger.debug("Start checking if user is subscribed");
        boolean res = true;
        int a = 0;
        int b = 0;
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(IS_SUBSCRIBE_USER)) {
            pstm.setInt(1, cus_id);
            pstm.setInt(2, pub_id);

            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                 a = rs.getInt("cus_id");
                 b = rs.getInt("pub_id");
            }

            if (a < 1 || b < 1) res = false;

        } catch (SQLException e) {
            logger.error("Problem with checking if user is subscribed: " + e.getMessage());
            res = false;
        }
        logger.debug("User is checked!");
        return res;
    }

    @Override
    public Customer get(String email) {
        logger.debug("Start getting customer by email");
        Customer customer = new Customer();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_CUSTOMER)){

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                Date dob = rs.getDate("dob");
                LocalDate dateOfBirth = null;
                if (dob != null) {
                    dateOfBirth = rs.getDate("dob").toLocalDate();
                }

                String phoneNumber = rs.getString("phone_number");
                email = rs.getString("email");
                double balance = rs.getDouble("balance");
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                customer = new Customer(id, fullName, dateOfBirth, phoneNumber, email, balance, isActive, created, updated);
            }
        } catch (SQLException e) {
            logger.error("Problem with pulling user data from database: " + e.getMessage());
        }
        return customer;
    }


    public Customer getByPhoneNumber(String phoneNumber){
        logger.debug("Start getting customer by phone number");
        Customer customer = new Customer();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_PHONE_NUMBER_QUERY)){

            stmt.setString(1, phoneNumber);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                Date dob = rs.getDate("dob");
                LocalDate dateOfBirth = null;
                if (dob != null) {
                    dateOfBirth = rs.getDate("dob").toLocalDate();
                }

                phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                customer = new Customer(id, fullName, dateOfBirth, phoneNumber, email, balance, isActive, created, updated);
            }
        } catch (SQLException e) {
            logger.error("Problem with pulling user data from database: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public boolean delete(int id) {
        logger.debug("Start deleting customer");
        boolean res = true;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( DELETE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.debug("Customer deleting successfully");
        }catch (Exception ex) {
            res = false;
            logger.error("Problem with deleting customer: " + ex.getMessage());
        }
        return res;
    }

    @Override
    public List<Customer> getAll() {
        logger.debug("Start getting all customers");
        List<Customer> customerList = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)){

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                Date dob = rs.getDate("dob");
                LocalDate dateOfBirth = null;
                if (dob != null) {
                    dateOfBirth = rs.getDate("dob").toLocalDate();
                }
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");

                Customer customer = new Customer(id, fullName, dateOfBirth, phoneNumber, email, balance);

                customerList.add(customer);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("Problem with getting all customers: " + ex.getMessage());
        }
        return customerList;
    }

}
