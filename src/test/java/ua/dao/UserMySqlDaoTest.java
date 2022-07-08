package ua.dao;

import org.junit.jupiter.api.*;
import ua.domain.User;

import java.util.List;

class UserMySqlDaoTest {
    private UserMySqlDao userDao = new UserMySqlDao();
    private User user = new User();
    private int id = 0;

    @BeforeAll
    static void dbInit() {
        DataSource.inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
    }

    @BeforeEach
    void before() {
        user = new User("zelya@ukr.net", "Ukr@vova22");
        id = userDao.signUp(user);
    }

    @AfterEach
    void after(){
        userDao.delete(id);
    }

    @Test
    void SIGN_UP_USER_POSITIVE_TEST() {
        Assertions.assertEquals(userDao.get(user.getEmail()).getId(), id);
    }

    @Test
    void SIGN_UP_USER_NEGATIVE_TEST() {
        Assertions.assertNotEquals(1, id);
    }

    @Test
    void GET_USER_BY_EMAIL_POSITIVE_TEST() {
        Assertions.assertEquals("zelya@ukr.net", userDao.get("zelya@ukr.net").getEmail());
        Assertions.assertEquals(id, userDao.get("zelya@ukr.net").getId());
    }

    @Test
    void GET_USER_BY_EMAIL_NEGATIVE_TEST() {
        Assertions.assertNotEquals("cat@gmail.com", userDao.get("zelya@ukr.net").getEmail());
        Assertions.assertNotEquals(1, userDao.get("zelya@ukr.net").getId());
    }

    @Test
    void GET_ALL_USER_POSITIVE_TEST() {
        List<User> userList = userDao.getAll();
        int firstSize = userList.size();
        User user = new User("tyui@ukr.net", "Ukr@tyui22");
        int id = userDao.signUp(user);

        userList = userDao.getAll();
        int secondSize = userList.size();
        Assertions.assertEquals(firstSize + 1, secondSize);
        userDao.delete(id);
    }

    @Test
    void GET_ALL_USER_NEGATIVE_TEST() {
        List<User> userList = userDao.getAll();
        int firstSize = userList.size();
        User user = new User("tyui@ukr.net", "Ukr@tyui22");
        int id = userDao.signUp(user);

        userList = userDao.getAll();
        int secondSize = userList.size();
        Assertions.assertNotEquals(firstSize, secondSize);
        userDao.delete(id);
    }

    @Test
    void EDIT_USER_DATA_POSITIVE_TEST() {
        User Updateuser = new User("milliebobbybrown@gmail.com", "Pa5$word");
        userDao.edit(Updateuser, user.getEmail());
        Assertions.assertEquals(id, userDao.get("milliebobbybrown@gmail.com").getId());
    }

    @Test
    void EDIT_USER_DATA_NEGATIVE_TEST() {
        User UpdateUser = new User("milliebobbybrown@gmail.com", "Pa5$word");
        userDao.edit(UpdateUser, user.getEmail());
        Assertions.assertNotEquals("zelya@ukr.net", userDao.get(UpdateUser.getEmail()).getEmail());
    }

    @Test
    void DELETE_USER_POSITIVE_TEST() {
        User user = new User("rtjw@ukr.net", "Ukr@vova22");
        int id = userDao.signUp(user);

        int listSizeBeforeDeleting = userDao.getAll().size();
        userDao.delete(id);
        int listSizeAfterDeleting = userDao.getAll().size();
        Assertions.assertEquals(listSizeAfterDeleting, listSizeBeforeDeleting - 1);
    }

    @Test
    void DELETE_USER_NEGATIVE_TEST() {
        User user = new User("rtjw@ukr.net", "Ukr@vova22");
        int id = userDao.signUp(user);

        int listSizeBeforeDeleting = userDao.getAll().size();
        userDao.delete(id);
        int listSizeAfterDeleting = userDao.getAll().size();
        Assertions.assertNotEquals(listSizeAfterDeleting, listSizeBeforeDeleting);
    }

    @Test
    void DEACTIVATE_USER_POSITIVE_TEST() {
        userDao.deactivate(id);
        Assertions.assertFalse(userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
    }

    @Test
    void DEACTIVATE_USER_NEGATIVE_TEST() {
        userDao.deactivate(id);
        Assertions.assertNotEquals(true, userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
    }

    @Test
    void ACTIVATE_USER_POSITIVE_TEST() {
        userDao.deactivate(id);
        Assertions.assertFalse(userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
        userDao.activate(id);
        Assertions.assertTrue(userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
    }

    @Test
    void ACTIVATE_USER_NEGATIVE_TEST() {
        userDao.deactivate(id);
        Assertions.assertFalse(userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
        userDao.activate(id);
        Assertions.assertNotEquals(false, userDao.getUserIgnoringFieldIsactibe("zelya@ukr.net").isActive());
    }
}