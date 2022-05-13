package ua.servlets;

import ua.domain.User;
import ua.dto.CustomerSignUpDto;
import ua.dto.UserSignUpDto;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "signup", urlPatterns = {"/signUp"})
public class UserSignUpServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        //UserService userService = new UserServiceImpl();
        PrintWriter out = resp.getWriter();

        // Set response content type
        resp.setContentType("text/html");

        String fullName = req.getParameter("inputFullName");
        System.out.println(fullName);
        String dob = req.getParameter("inputDob");
        System.out.println(dob);
        String phoneNumber = req.getParameter("inputPhoneNumber");
        System.out.println(phoneNumber);
        String email = req.getParameter("inputEmail");
        System.out.println(email);
        String password = req.getParameter("inputPassword");
        System.out.println(password);
        String confirmPassword = req.getParameter("inputConfirmPassword");
        System.out.println(confirmPassword);


        UserSignUpDto userSignUpDto = new UserSignUpDto(email, password, confirmPassword);
        CustomerSignUpDto customerSignUpDto = new CustomerSignUpDto(fullName, dob, phoneNumber, email, password, confirmPassword);
        Map<String, String> userResponse = userService.signUp(userSignUpDto);
        Map<String, String> customerResponse = userService.signUp(customerSignUpDto);

        if (!userResponse.isEmpty() || !customerResponse.isEmpty()) {
//            userService.delete(customerSignUpDto);
            session.setAttribute("errorMessages", userResponse);
            session.setAttribute("userSignUpDto", userSignUpDto);
            session.setAttribute("customerErrorMessages", customerResponse);
            session.setAttribute("customerSignUpDto", customerSignUpDto);
            resp.sendRedirect(req.getContextPath() + "/signUp.jsp");
//
        }
//        if (!customerResponse.isEmpty()) {
//            userService.delete(userSignUpDto);
//            session.setAttribute("customerErrorMessages", customerResponse);
//            session.setAttribute("customerSignUpDto", customerSignUpDto);
//            resp.sendRedirect(req.getContextPath() + "/signUp.jsp");
//        }
        else {
            session.setAttribute("registrationMessage", "user with " + userSignUpDto.getEmail() + " successful registered");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
