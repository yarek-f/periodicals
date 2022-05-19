package ua.servlets;

import ua.dto.UserSignUpDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "registration_result", urlPatterns = "/rrs")
public class RegistrationResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        List<String> userResponse = (List<String>) session.getAttribute("errorMessages");
        UserSignUpDto userSignUpDto = (UserSignUpDto) session.getAttribute("signUpDTO");

        if (!userResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/signUp.jsp");
        }
        else {
            session.setAttribute("registrationMessage", "user with " + userSignUpDto.getEmail() + " successful registered");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
