package ua.services;

import ua.dao.UserMySqlDao;
import ua.domain.User;
import ua.dto.UserSignUpDto;
import ua.excaptions.UserInvalidDataExcaption;
import ua.mapper.Mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    private UserMySqlDao userMySqlDao = new UserMySqlDao();
    private Mapper mapper = new Mapper();
    private UserSignUpDto userSignUpDto = new UserSignUpDto();

    @Override
    public Map<String, String> signUp(UserSignUpDto userSignUpDto) {
//        Map<String, String> validation = userSignUpDto.validate();
        Map<String, String> validation = validate(userSignUpDto);

        if (validation.isEmpty()){
//            User user = userSignUpDto.convertToUser();
            User user = Mapper.convertToUser(userSignUpDto);

            System.out.println("INSIDE SERVICE -> " + user.toString());
            userMySqlDao.signUp(user);
        }

        return validation;
    }

    public Map<String, String> validate(UserSignUpDto userSignUpDto){
        Map<String, String> checkResult = new TreeMap<>();

        try{
            validEmail(userSignUpDto.getEmail());
        } catch (Exception e){
            checkResult.put("email", e.getMessage());
        }try{
            validPassword(userSignUpDto.getPassword());
        } catch (Exception e){
            checkResult.put("password", e.getMessage());
        }try{
            validConfirmPassword(userSignUpDto.getPassword(), userSignUpDto.getConfirmPassword());
        } catch (Exception e){
            checkResult.put("confirmPassword", e.getMessage());
        }

        return checkResult;
    }



    private void validEmail(String email){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
//        if(userMySqlDao.get(email).getEmail().equals(email)){
//            throw new UserInvalidDataExcaption("It looks like this email address has already been registered");
//        }
        if (!matcher.matches()) {
            throw new UserInvalidDataExcaption("You enter invalid email");
        }

    }

    private void validPassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"); //todo
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new UserInvalidDataExcaption("You enter invalid password");
        }
    }

    private void validConfirmPassword(String password, String confirmPassword){
        if (!password.equals(confirmPassword)){
            throw new UserInvalidDataExcaption("You enter invalid confirm password");
        }
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
