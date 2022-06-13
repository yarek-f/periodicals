package ua.servlets;

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

@WebServlet
public class LoginController extends HttpServlet {

    private UserService userService;
    private JWTService jwtService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getAttribute("email");
        String password = (String) req.getAttribute("password");

        if (userService.valid(email, password)) {
            Map<String, String> userInfo = createUserInfoMap(email, password);
            String token = jwtService.createToken(userInfo, 1);

            ServletContext context = req.getServletContext();
            context.setAttribute(token, token);
           // log.info("token:" + token);
        }


    }

    private Map<String, String> createUserInfoMap(String loginName, String password) {
        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("loginName", loginName);
        userInfo.put("password", password);
        return userInfo;
    }


}
