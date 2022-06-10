package ua.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final String CREATE_QUERY = "insert into users(email, user_password) values (?,?)";
    private static final String GET_QUERY = "select * from users where email = ?";
    private static final String DELETE_QUERY = "delete from users where email = ?";
    private static final String GET_ALL_QUERY = "SELECT * from users";
    private static final String TRANCATE_QUERY = "TRUNCATE users";
    private static final String UPDATE_QUERY = "update users set role = ?, email = ?, user_password = ?, isActive = ? where id = ?";
    private static final String SQL_CALC_FOUND_ROWS = "select SQL_CALC_FOUND_ROWS * from users limit ?, ?";

    private int noOfRecords;

    private static Logger logger = LogManager.getLogger(UserMySqlDao.class);
    private Connection con;

    public UserMySqlDao() {
    }

    public UserMySqlDao(Connection con){
        this.con = con;
    }

    @Override
    public int signUp(User item) {
        logger.debug("Start user creating");
        try (PreparedStatement stmt = con.prepareStatement( CREATE_QUERY )) {

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
//        logger.debug("Start getting user");
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
    public boolean delete(int id) { //fixme
        return false;
    }

//    @Override
//    public boolean delete(String email) {
//        logger.debug("Start deleting user");
//        boolean result = false;
//
//        try (
////                Connection con = DataSource.getConnection();
//             PreparedStatement stmt = con.prepareStatement(DELETE_QUERY)){
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

    public List<UserGetDto> getAll(int offset, int noOfRecords) {
//        Connection connection = null;
//        Statement statement;
//        PreparedStatement pstmt;
//        ResultSet resultSet;

//        try {
//            connection = DataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
                boolean isActive = resultSet.getBoolean("isActive");
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
