package ua.servlets;

import ua.domain.Role;
import ua.services.JWTService;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private UserService userService;
    private JWTService jwtService;


    @Override
    public void init() throws ServletException {
        jwtService = new JWTService();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/logIn.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Role role = userService.valid(email, password);
        if (role != null) {
            Map<String, String> userInfo = createUserInfoMap(email, password);
            String token = jwtService.createToken(userInfo, 1);

            ServletContext context = req.getServletContext();
            context.setAttribute(token, token);
            req.getSession().setAttribute("token", token);

            if (role.equals(Role.ADMIN)) {
                resp.sendRedirect("/publishers");
            } else if (role.equals(Role.USER)) {
                resp.sendRedirect("/periodicals");
            }
        } else {
            req.getSession().setAttribute("loginError", "Wrong email or password");
            resp.sendRedirect("/login");
        }


    }

    private Map<String, String> createUserInfoMap(String loginName, String password) {
        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("loginName", loginName);
        userInfo.put("password", password);
        return userInfo;
    }


}
