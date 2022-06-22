package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Customer;
import ua.excaptions.UserException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class CustomerMySqlDao implements Dao<Customer> {
    private static final String CREATE_QUERY = "insert into customers (fullname, dob, phone_number, email, user_password) values (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from customers where email = ?";
    private static final String GET_PHONE_NUMBER_QUERY = "select * from customers where phone_number = ?";
    private static final String GET_CUSTOMER = "select * from customers where email = ?";
    private static final String SUBSCRIBE_USER = "insert into publisher_customer (cus_id, pub_id) values(?, ?)";
    private static final String IS_SUBSCRIBE_USER = "select * from publisher_customer where cus_id = ? and pub_id = ?";
    private static final String UNSUBSCRIBE_USER = "delete from publisher_customer where cus_id = ? and pub_id = ?";
    private static final String UPDATE_BALANCE = "update customers set balance = ? where email = ?";

    private Connection con;

    private static Logger logger = LogManager.getLogger(CustomerMySqlDao.class);

    public CustomerMySqlDao() {
    }

    public CustomerMySqlDao(Connection con) {
        this.con = con;
    }
    @Override
    public int signUp(Customer item) {
        try (PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

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
            logger.debug("Problem with creating user: " + ex.getMessage());
        }
        return 0;
    }

    public void withdrawFromBalance(Customer customer){
        logger.debug("Start withdraw money from balance");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UPDATE_BALANCE)) {

            double newBalance = get(customer.getEmail()).getBalance() - customer.getBalance();

            pstm.setDouble(1, newBalance);
            pstm.setString(2, customer.getEmail());

            int status = pstm.executeUpdate();
            if(status!=1) throw new UserException("Update more than one record!!!");
        } catch (SQLException e) {
            logger.debug("Problem with withdraw user money from balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void replenishBalance(Customer customer) {
        logger.debug("Start replenish the balance");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UPDATE_BALANCE)) {

//            DecimalFormat df = new DecimalFormat("0.00");
            double oldBalance = get(customer.getEmail()).getBalance();
//            double currentBalance = Double.valueOf(df.format(customer.getBalance() + oldBalance));

            pstm.setDouble(1, customer.getBalance()+oldBalance);
            pstm.setString(2, customer.getEmail());

            int status = pstm.executeUpdate();
            if(status!=1) throw new UserException("Update more than one record!!!");
        } catch (SQLException e) {
            logger.debug("Problem with replenish user balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addSubscription(int cus_id, int pub_id){
        logger.debug("Start subscribing user");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(SUBSCRIBE_USER)) {
            pstm.setInt(1, cus_id);
            pstm.setInt(2, pub_id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            logger.debug("Problem with subscribing user" + e.getMessage());
        }
        logger.debug("User subscribed successfully!");
    }

    public void deleteSubscritpion(int cus_id, int pub_id){
        logger.debug("Starting unsubscribe user");
        try(Connection con = DataSource.getConnection();
            PreparedStatement pstm = con.prepareStatement(UNSUBSCRIBE_USER)) {
            pstm.setInt(1, cus_id);
            pstm.setInt(2, pub_id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            logger.debug("Problem with unsubscribe user" + e.getMessage());
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
            logger.debug("Problem with checking if user is subscribed: " + e.getMessage());
            res = false;
        }
        logger.debug("User is checked!");
        return res;
    }

    @Override
    public Customer get(String email) {
        logger.debug("Start getting customer by phone number");
        Customer customer = new Customer();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_CUSTOMER)){

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("fullname");
                LocalDate dob = rs.getDate("dob").toLocalDate(); //fixme login doesn't work without dob
                String phoneNumber = rs.getString("phone_number");
                email = rs.getString("email");
                double balance = rs.getDouble("balance");

                customer = new Customer(id, fullName, dob, phoneNumber, email, balance);
            }
        } catch (SQLException e) {
            logger.debug("Problem with pulling user data from database: " + e.getMessage());
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
                String fullName = rs.getString("fullname");
                LocalDate dob = rs.getDate("dob").toLocalDate();
                phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String password = rs.getString("user_password");

                customer = new Customer(fullName, dob, phoneNumber, email, password);
            }
        } catch (SQLException e) {
            logger.debug("Problem with pulling user data from database: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public int update(Customer items, int id) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
//    @Override
//    public boolean delete(String email) {
//        logger.debug("Start deleting customer");
//        boolean result = false;
//
//        try (PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)){
//
//            stmt.setString(1, email);
//
//            if (stmt.executeUpdate() == 1) {
//                result = true;
//            }
//
//        } catch (SQLException ex) {
//            logger.debug("Problem with deleting user: " + ex.getMessage());
//        }
//
//        logger.debug("User deleted");
//
//        return result;
//    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void clearTable() {

    }


}
