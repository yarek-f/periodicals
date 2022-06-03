package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Customer;
import ua.excaptions.UserException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerMySqlDao implements Dao<Customer> {
    private static final String CREATE_QUERY = "insert into customers (fullname, dob, phone_number, email, user_password) values (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from customers where email = ?";
    private static final String GET_PHONE_NUMBER_QUERY = "select * from customers where phone_number = ?";

    private Connection con;

    private static Logger logger = LogManager.getLogger(CustomerMySqlDao.class);

    public CustomerMySqlDao() throws SQLException {
    }

    public CustomerMySqlDao(Connection con) throws SQLException {
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

    @Override
    public Customer get(String email) {
        return null;
    }


    public Customer getByPhoneNumber(String phoneNumber){
        logger.debug("Start getting user");
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
        } catch (Exception ex) {
            logger.debug("Problem with getting user: " + ex.getMessage());
        }
        return customer;
    }

    @Override
    public int update(Customer items, int id) {
        return 0;
    }

    @Override
    public boolean delete(String email) {
        logger.debug("Start deleting user");
        boolean result = false;

        try (PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)){

            stmt.setString(1, email);

            if (stmt.executeUpdate() == 1) {
                result = true;
            }

        } catch (SQLException ex) {
            logger.debug("Problem with deleting user: " + ex.getMessage());
        }

        logger.debug("User deleted");

        return result;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void clearTable() {

    }
}
