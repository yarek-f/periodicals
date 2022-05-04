package ua.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.domain.Role;
import ua.domain.User;

import java.util.List;

class UserMySqlDaoTest {
    private Dao<User> userDao = new UserMySqlDao();

    @BeforeEach
    public void init(){
        userDao.clearTable();
        userDao.signUp(new User("rusykorabel@mail.ru", "jhsrfbRr%sb7696"));
        userDao.signUp(new User("bonjovi@gmail.com", "jhgisQg$54"));
        userDao.signUp(new User("vasyastus@ukr.net", "isgiugsiu@Ts54687"));
        userDao.signUp(new User("bibob@gmail.com", "jydyioe9w9^Fweo"));
    }

    @Test
    void SIGN_UP_USER_POSITIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        Assertions.assertEquals(5, userDao.signUp(user));
    }

    @Test
    void SIGN_UP_USER_NEGATIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        Assertions.assertNotEquals(6, userDao.signUp(user));
    }

    @Test
    void GET_USER_EMAIL_POSITIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        userDao.signUp(user);
        Assertions.assertEquals("zelya@ukr.net", userDao.get(user.getEmail()).getEmail());
    }

    @Test
    void GET_USER_EMAIL_NEGATIVE_TEST() {
        User user = new User("zelya@ukr.net", "Ukr@vova22");
        //userDao.signUp(user);
        Assertions.assertNotEquals("zelya@ukr.net", userDao.get(user.getEmail()).getEmail());
    }

    @Test
    void UPDATE_USER_DATA_POSITIVE_TEST() {
        User updateUser = new User(Role.USER, "ya.wasFirst@gmail.com", "Pa5$word", false);
        userDao.update(updateUser, 1);
        Assertions.assertEquals(1, userDao.get("ya.wasFirst@gmail.com").getId());
    }

    @Test
    void UPDATE_USER_DATA_NEGATIVE_TEST() {
        User updateUser = new User(Role.USER, "ya.wasFirst@gmail.com", "Pa5$word", false);
        userDao.update(updateUser, 3);
        Assertions.assertNotEquals(1, userDao.get("ya.wasFirst@gmail.com").getId());
    }

    @Test
    void DELETE_USER_POSITIVE_TEST() {
        userDao.delete(3);
        Assertions.assertEquals(3, userDao.getAll().size());
    }

    @Test
    void DELETE_USER_NEGATIVE_TEST() {
        userDao.delete(3);
        Assertions.assertNotEquals(4, userDao.getAll().size());
    }

    @Test
    void GET_ALL_USER_POSITIVE_TEST() {
        List<User> userList = userDao.getAll();
        Assertions.assertEquals(4, userList.size());
    }

    @Test
    void GET_ALL_USER_NEGATIVE_TEST() {
        List<User> userList = userDao.getAll();
        Assertions.assertNotEquals(5, userList.size());
    }
}