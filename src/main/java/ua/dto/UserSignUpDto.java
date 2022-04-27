package ua.dto;

import ua.domain.User;
import ua.excaptions.UserInvalidDataExcaption;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUpDto {
    private String email;
    private String password;
    private String confirmPassword;

    public UserSignUpDto() {
    }

    public UserSignUpDto(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

//    public User convertToUser(){
//        User user = new User(email, password);
//        return user;
//    }
//
//    public Map<String, String> validate(){
//        Map<String, String> checkResult = new TreeMap<>();
//
//        try{
//            validEmail();
//        } catch (Exception e){
//            checkResult.put("email", e.getMessage());
//        }try{
//            validPassword();
//        } catch (Exception e){
//            checkResult.put("password", e.getMessage());
//        }try{
//            validConfirmPassword();
//        } catch (Exception e){
//            checkResult.put("confirmPassword", e.getMessage());
//        }
//
//        return checkResult;
//    }
//
//
//
//    private void validEmail(){
//        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
//        Matcher matcher = pattern.matcher(email);
//        if (!matcher.matches()) {
//            throw new UserInvalidDataExcaption("You enter invalid email");
//        }
//    }
//
//    private void validPassword(){
//        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"); //todo
//        Matcher matcher = pattern.matcher(password);
//        if (!matcher.matches()) {
//            throw new UserInvalidDataExcaption("You enter invalid password");
//        }
//    }
//
//    private void validConfirmPassword(){
//        if (!password.equals(confirmPassword)){
//            throw new UserInvalidDataExcaption("You enter invalid confirm password");
//        }
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }




}
