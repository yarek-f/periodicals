package ua.services;

import ua.dao.UserMySqlDao;
import ua.domain.User;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private UserMySqlDao userMySqlDao = new UserMySqlDao();

    @Override
    public Map<String, String> signUp(UserSignUpDto userSignUpDto) {
        Map<String, String> validation = validate(userSignUpDto);

        if (validation.isEmpty()){
            User user = Mapper.convertToUser(userSignUpDto);

            System.out.println("INSIDE SERVICE -> " + user.toString());
            userMySqlDao.signUp(user);
        }

        return validation;
    }

    public Map<String, String> validate(UserSignUpDto userSignUpDto){
        Map<String, String> checkResult = new TreeMap<>();

        if (!validEmail(userSignUpDto.getEmail())){
            checkResult.put("email", "false");
        } if(!validPassword(userSignUpDto.getPassword())){
            checkResult.put("password", "false");
        } if(!validConfirmPassword(userSignUpDto.getPassword(), userSignUpDto.getConfirmPassword())){
            checkResult.put("confirmPassword", "false");
        }
        return checkResult;
    }



    private boolean validEmail(String email){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validPassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"); //todo
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validConfirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    @Override
    public List<User> getAll() {

        return userMySqlDao.getAll();
    }

    @Override
    public User get(String email) {
        return userMySqlDao.get(email);
    }

    @Override
    public boolean updateRole(int id) {
        return false;
    }


}
