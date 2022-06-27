package ua.servlets;

import ua.domain.Customer;
import ua.dto.UserSignUpDto;
import ua.mapper.Mapper;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "my-profile", urlPatterns = {"/my-profile"})
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String email = (String) req.getSession().getAttribute("profile");
        Customer customer = userService.getCustomer(email);
        UserSignUpDto userDto = Mapper.convertToUserDto(customer);
        req.getSession().setAttribute("customerDto", userDto);

        resp.sendRedirect("my-profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String customerEmail = (String) req.getSession().getAttribute("profile");

        String fullName = req.getParameter("inputFullName");
        if (fullName == null || fullName.equals("")){
            fullName = userService.getCustomer(customerEmail).getFullName();
        }
        System.out.println("fullName ==> " + fullName);
        String dob = req.getParameter("inputDob");
        if (dob == null || dob.equals("")){
            dob = userService.getCustomer(customerEmail).getDob().toString();
        }
        System.out.println("dob ==> " + dob);
        String phoneNumber = req.getParameter("inputPhoneNumber");
        if (phoneNumber == null || phoneNumber.equals("")){
            phoneNumber = userService.getCustomer(customerEmail).getPhoneNumber();
        }
        System.out.println("phoneNumber ==> " + phoneNumber);
        String email = req.getParameter("inputEmail");
        if (email == null || email.equals("")){
            email = userService.getCustomer(customerEmail).getEmail();
        }


        System.out.println("email ==> " + email);
        String password = req.getParameter("inputPassword");
        if (password == null || password.equals("")){
            password = userService.get(customerEmail).getPassword();
        }
        System.out.println("password ==> " + password);
        String confirmPassword = req.getParameter("inputConfirmPassword");
        if (confirmPassword == null || confirmPassword.equals("")){
            confirmPassword = userService.get(customerEmail).getPassword();
        }

        UserSignUpDto userDto = new UserSignUpDto(fullName, dob, phoneNumber, email, password, confirmPassword, null);

        List<String> userResponse = userService.edit(userDto, customerEmail);
        req.getSession().setAttribute("errorEditingMessages", userResponse);

        if (!userResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/edit-profile.jsp");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/my-profile.jsp");
            req.getSession().setAttribute("customerDto", userDto);
            req.getSession().removeAttribute("errorEditingMessages");
            req.getSession().setAttribute("profile", email);
        }



    }
}
