package ua.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.domain.Role;
import ua.domain.User;

import java.sql.SQLException;
import java.util.List;

class UserMySqlDaoTest {
    private Dao<User> userDao = new UserMySqlDao();

    @BeforeEach
    void init(){
        userDao.clearTable();
        userDao.signUp(new User(Role.ADMIN, "admin@gmail.com", "123456Q@q"));
        userDao.signUp(new User("jonsonyuk@gmail.com", "123456Q@q"));
        userDao.signUp(new User("biden@gmail.com", "123456Q@q"));
        userDao.signUp(new User("bob@gmail.com", "123456Q@q"));
        userDao.signUp(new User("cat@gmail.com", "123456Q@q"));
    }

    @Test
    void SIGN_UP_USER_POSITIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        Assertions.assertEquals(6, userDao.signUp(user));
    }

    @Test
    void SIGN_UP_USER_NEGATIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        Assertions.assertNotEquals(1, userDao.signUp(user));
    }

    @Test
    void GET_USER_EMAIL_POSITIVE_TEST() {
        Assertions.assertEquals("cat@gmail.com", userDao.get("cat@gmail.com").getEmail());
        Assertions.assertEquals(5, userDao.get("cat@gmail.com").getId());
    }

    @Test
    void GET_USER_EMAIL_NEGATIVE_TEST() {
        Assertions.assertNotEquals("zelya@ukr.net", userDao.get("cat@gmail.com").getEmail());
        Assertions.assertNotEquals(3, userDao.get("cat@gmail.com").getId());
    }

//    @Test
//    void UPDATE_USER_DATA_POSITIVE_TEST() {
//        User updateUser = new User("ya.wasSecond@gmail.com", "Pa5$word");
//        userDao.edit(updateUser, "jonsonyuk@gmail.com");
//        Assertions.assertEquals(2, userDao.get("ya.wasFirst@gmail.com").getId());
//    }
//
//    @Test
//    void UPDATE_USER_DATA_NEGATIVE_TEST() {
//        User updateUser = new User(Role.USER, "ya.wasFirst@gmail.com", "Pa5$word", false);
//        userDao.update(updateUser, 3);
//        Assertions.assertNotEquals(1, userDao.get("ya.wasFirst@gmail.com").getId());
//    }

//    @Test
//    void DELETE_USER_POSITIVE_TEST() {
//        userDao.delete(3);
//        Assertions.assertEquals(3, userDao.getAll().size());
//    }
//
//    @Test
//    void DELETE_USER_NEGATIVE_TEST() {
//        userDao.delete(3);
//        Assertions.assertNotEquals(4, userDao.getAll().size());
//    }

    @Test
    void GET_ALL_USER_POSITIVE_TEST() {
        List<User> userList = userDao.getAll();
        Assertions.assertEquals(5, userList.size());
    }

    @Test
    void GET_ALL_USER_NEGATIVE_TEST() {
        List<User> userList = userDao.getAll();
        Assertions.assertNotEquals(3, userList.size());
    }
}