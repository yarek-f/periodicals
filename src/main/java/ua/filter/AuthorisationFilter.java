package ua.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.services.JWTService;
import ua.services.Payload;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns ={"/my-subscriptions", "/my-profile", "/balance.jsp", "/edit-profile.jsp"})
public class AuthorisationFilter implements Filter {
    private static Logger logger = LogManager.getLogger(AuthorisationFilter.class);
//    private static final int STATUS_CODE_UNAUTHORIZED = 401;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        JWTService jwtService = new JWTService();
        UserService userService = new UserServiceImpl();

        try {

            String jwt = (String) session.getAttribute("token");

            if (jwt != null && !jwt.isEmpty()) {
                Payload payload = jwtService.verifyToken(jwt);
                String email = payload.getClaims().get("email");

                filterChain.doFilter(servletRequest, servletResponse);

            } else {
                httpResponse.sendRedirect("/logIn.jsp");
            }


        } catch (final Exception e) {
            logger.debug("Failed logging in with security token");
            httpResponse.sendRedirect("/logIn.jsp");
        }
    }
}
