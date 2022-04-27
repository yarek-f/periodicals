package ua.dao;

import org.junit.jupiter.api.Test;
import ua.domain.User;

class UserMySqlDaoTest {
    private Dao<User> userDao = new UserMySqlDao();

    @Test
    void SIGN_UP_USER_POSITIVE_TEST() {
        User user = new User("jsjhvs@ukr.net", "1235465");
        userDao.signUp(user);
    }

    @Test
    void get() {
        String email = "bonjovi@gmail.com";
        userDao.get(email);
    }

    @Test
    void getAll() {
    }
}