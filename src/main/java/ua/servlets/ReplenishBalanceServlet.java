package ua.servlets;

import ua.dto.CustomerSignUpDto;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;
import ua.excaptions.UserVarificationException;
import ua.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig
@WebServlet(name = "balance", urlPatterns = {"/replenish-balance"})
public class ReplenishBalanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = new UserServiceImpl();
        HttpSession session = req.getSession(true);
        resp.setContentType("text/html");
        JWTService jwtService = new JWTService();

        String balance = req.getParameter("balance");
        System.out.println(balance);

        String token = (String)req.getSession().getAttribute("token");
        String email = null;
        try {
            email = jwtService.verifyToken(token).getClaims().get("email");
        } catch (UserVarificationException e) {
            e.printStackTrace();
        }
        System.out.println("email ==> " + email);

        UserSignUpDto customerDto = new UserSignUpDto(email, balance);
        session.setAttribute("customerDto", customerDto);

        List<String> customerResponse = userService.replenishBalance(customerDto);

        session.setAttribute("balanceErrorMessages", customerResponse);

//        resp.sendRedirect(req.getContextPath() + "/periodicals");

        if (!customerResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/balance.jsp");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/periodicals");
            session.removeAttribute("balanceErrorMessages");
            session.removeAttribute("withdrawBalance");
            session.removeAttribute("customerDto");
        }
    }
}
