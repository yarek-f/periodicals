package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Role;
import ua.domain.User;
import ua.excaptions.UserException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMySqlDao implements Dao<User> {
    private static final String CREATE_QUERY = "insert into users(email, user_password) values (?,?)";
    private static final String GET_QUERY = "select * from users where email = ?";
    private static final String DELETE_QUERY = "delete from users where id = ?";
    private static final String GET_ALL_QUERY = "SELECT * from users";
    private static final String TRANCATE_QUERY = "TRUNCATE users";
    /*5 >?*/private static final String UPDATE_QUERY = "update users set role = ?, email = ?, user_password = ?, isActive = ? where id = ?";

    private static Logger logger = LogManager.getLogger(UserMySqlDao.class);

    @Override
    public int signUp(User item) {
        logger.debug("Start user creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

            stmt.setString(1, item.getEmail());
            stmt.setString(2, item.getPassword());

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
            logger.debug("Problem with creating user: " + ex.getMessage());
        }

        logger.debug("User created");

        return get(item.getEmail()).getId();
    }

    @Override
    public User get(String email) { //todo
        logger.debug("Start getting user");
        User requiredUser = new User();
        try (Connection con = DataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(GET_QUERY)){

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userRole = rs.getString("role");
                Role role = Role.valueOf(userRole);
                email = rs.getString("email");
                String password = rs.getString("user_password");
                boolean isActive = rs.getBoolean("isActive");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                requiredUser = new User(id, role, email, password, isActive, created, updated);
            }
        } catch (SQLException e) {
            logger.debug("Problem with pulling user data from database: " + e.getMessage());
        } catch (Exception ex) {
            logger.debug("Problem with getting user: " + ex.getMessage());
        }
        return requiredUser;
    }

    @Override
    public int update(User items, int id) { //todo ????
        logger.debug("Start user creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( UPDATE_QUERY )) {

            stmt.setString(1, items.getRole().name()); //todo
            stmt.setString(2, items.getEmail());
            stmt.setString(3, items.getPassword());
            stmt.setBoolean(4, items.isActive());
            stmt.setInt(5, id);
//private static final String UPDATE_QUERY = "update users set role = ?, email = ?, user_password = ?, isActive = ? where id = ?";
            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
//            logger.debug("Problem with creating user: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }

        logger.debug("User created");

        return get(items.getEmail()).getId();
    }

    @Override
    public boolean delete(int id) {
        logger.debug("Start deleting user");
        boolean result = false;

        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)){

            stmt.setInt(1, id);

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
    public List<User> getAll() { //todo
        logger.debug("Start getting all users");
        List<User> userList = new ArrayList<>();

        try (Connection con = DataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_QUERY)){

            while (rs.next()) {
                int id = rs.getInt("id");
                String userRole = rs.getString("role");
                Role role = Role.valueOf(userRole);
                String email = rs.getString("email");
                String password = rs.getString("user_password");
                boolean isActive = rs.getBoolean("isActive");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                User user = new User(id, role, email, password, isActive, created, updated);

                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.debug("Problem with getting users: " + ex.getMessage());
        }

        logger.debug("Got all users");
        return userList;
    }

    @Override
    public void clearTable(){
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()){
            stmt.executeUpdate(TRANCATE_QUERY);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
//            logger.debug("Problem with getting users: " + ex.getMessage());
        }
    }
}
