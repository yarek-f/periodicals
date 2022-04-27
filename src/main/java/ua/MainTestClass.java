package ua;

import ua.dao.UserMySqlDao;
import ua.domain.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTestClass {
    public static void main(String[] args) {
        UserMySqlDao userMySqlDao = new UserMySqlDao();
        List<User> userList = userMySqlDao.getAll();
        for (User users : userList) {
            System.out.println(users.toString());
        }

//        User getUser = userMySqlDao.get("bibob@ail.com");
//        System.out.println(getUser.toString());

//        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"); //todo
//        Matcher matcher = pattern.matcher("123Qwerty@");
//        System.out.println(matcher.matches());
    }
}
