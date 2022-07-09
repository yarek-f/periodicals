package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Publisher;
import ua.domain.Role;
import ua.domain.User;
import ua.dto.UserGetDto;
import ua.excaptions.UserException;
import ua.mapper.Mapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMySqlDao implements Dao<User> {
    private static final String CREATE_QUERY = "insert into users(role, email, user_password) values (?, ?,?)";
    private static final String GET_QUERY = "select * from users where email = ? and is_active = true";
    private static final String GET_ALL_QUERY = "SELECT * from users where is_active = true";
    private static final String SQL_CALC_FOUND_ROWS = "select SQL_CALC_FOUND_ROWS * from users  where role = 'USER' limit ?, ?";
    private static final String DEACTIVATE_QUERY = "update users set is_active = false where id = ?";
    private static final String ACTIVATE_QUERY = "update users set is_active = true where id = ?";
    private static final String EDIT_QUERY = "update users set email = ?, user_password = ? where email = ?";
    private static final String DELETE_QUERY = "delete from users where id = ?";
    private static final String GET_QUERY_NEW = "select * from users where email = ?";

    private int noOfRecords;

    private static Logger logger = LogManager.getLogger(UserMySqlDao.class);

    public UserMySqlDao() {

    }

    @Override
    public int signUp(User item) {
        logger.debug("Start user creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

            if (item.getRole() == null || item.getRole().toString().equals("")){
                stmt.setString(1, Role.USER.toString());
            } else {
                stmt.setString(1, item.getRole().toString());
            }
            stmt.setString(2, item.getEmail());
            stmt.setString(3, item.getPassword());

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
            logger.error("Problem with creating user: " + ex.getMessage());
        }

        logger.debug("User created");

        return get(item.getEmail()).getId();
    }



    @Override
    public void edit(User user, String currentEmail) {
        logger.debug("Start user creating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( EDIT_QUERY )) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, currentEmail);

            int status = stmt.executeUpdate();
            if(status!=1) throw new UserException("Created more than one record!!!");

        } catch (Exception ex) {
            logger.error("Problem with creating user: " + ex.getMessage());
        }

        logger.debug("User created");
    }

    @Override
    public User get(String email) {
        logger.debug("Start getting user");
        User requiredUser = null;
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
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                requiredUser = new User(id, role, email, password, isActive, created, updated);
            }
        } catch (SQLException e) {
            logger.error("Problem with pulling user data from database: " + e.getMessage());
        }
        return requiredUser;
    }

    public User getUserIgnoringFieldIsActive(String email) { //only for testing
        logger.debug("Start getting user");
        User requiredUser = null;
        try (Connection con = DataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(GET_QUERY_NEW)){

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userRole = rs.getString("role");
                Role role = Role.valueOf(userRole);
                email = rs.getString("email");
                String password = rs.getString("user_password");
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                requiredUser = new User(id, role, email, password, isActive, created, updated);
            }
        } catch (SQLException e) {
            logger.error("Problem with pulling user data from database: " + e.getMessage());
        }
        return requiredUser;
    }

    @Override
    public boolean delete(int id) {
        logger.debug("Start user deleting");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( DELETE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.error("Problem with deleting user: " + ex.getMessage());
        }
        logger.debug("User deleting successfully");
        return false;
    }

    @Override
    public void deactivate(int id) {
        logger.debug("Start user deactivating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( DEACTIVATE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.error("Problem with deactivating user: " + ex.getMessage());
        }
    }


    public boolean activate(int id) {
        logger.debug("Start user activating");
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement( ACTIVATE_QUERY )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception ex) {
            logger.error("Problem with activating user: " + ex.getMessage());
        }
        logger.debug("User activating successfully");
        return false;
    }


    @Override
    public List<User> getAll() {
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
                boolean isActive = rs.getBoolean("is_active");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = rs.getTimestamp("updated").toLocalDateTime();

                User user = new User(id, role, email, password, isActive, created, updated);

                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.error("Problem with getting users: " + ex.getMessage());
        }

        logger.debug("Got all users");
        return userList;
    }

    public List<UserGetDto> getAll(int offset, int noOfRecords) {
        logger.debug("Start getting all users");
        List<UserGetDto> userList = new ArrayList<>();
        User user = null;
        try (Connection connection = DataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_CALC_FOUND_ROWS);
            Statement statement = connection.createStatement()){

            pstmt.setInt(1, offset);
            pstmt.setInt(2, noOfRecords);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userRole = resultSet.getString("role");
                Role role = Role.valueOf(userRole);
                String email = resultSet.getString("email");
                String password = resultSet.getString("user_password");
                boolean isActive = resultSet.getBoolean("is_active");
                LocalDateTime created = resultSet.getTimestamp("created").toLocalDateTime();
                LocalDateTime updated = resultSet.getTimestamp("updated").toLocalDateTime();

                user = new User(id, role, email, password, isActive, created, updated);

                userList.add(Mapper.convertToUserDto(user));
            }
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next()){
                this.noOfRecords = resultSet.getInt(1);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        logger.debug("Got all users");
        return userList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

}
