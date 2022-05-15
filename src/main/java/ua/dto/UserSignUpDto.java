package ua.dto;

import ua.domain.User;
import ua.excaptions.UserInvalidDataExcaption;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUpDto {
    private String fullName;
    private String dob;
    private String phoneNumber;
    private String email;
    private String password;
    private String confirmPassword;

    public UserSignUpDto() {
    }

    public UserSignUpDto(String fullName, String dob, String phoneNumber, String email, String password, String confirmPassword) {
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

//    public UserSignUpDto(String email, String password, String confirmPassword) {
//        this.email = email;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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
