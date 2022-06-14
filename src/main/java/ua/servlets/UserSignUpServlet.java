package ua.servlets;

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
import java.util.List;

@WebServlet(name = "signup", urlPatterns = {"/signUp"})
public class UserSignUpServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true); //
        resp.setContentType("text/html"); //fixme


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
        String checkBox = req.getParameter("check");
        System.out.println(checkBox);

        UserSignUpDto userSignUpDto = new UserSignUpDto(fullName, dob, phoneNumber, email, password, confirmPassword, checkBox);
            session.setAttribute("signUpDTO", userSignUpDto);

            List<String> userResponse = userService.signUp(userSignUpDto);

            session.setAttribute("errorMessages", userResponse);

        resp.sendRedirect("/rrs");
    }
}
