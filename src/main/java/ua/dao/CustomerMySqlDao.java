package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Customer;
import ua.excaptions.UserException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerMySqlDao implements Dao<Customer> {
    private static final String CREATE_QUERY = "insert into customers (fullname, dob, phone_number, email, user_password) values (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "delete from customers where email = ?";

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
            stmt.setDate(2, Date.valueOf(item.getDob()));
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
