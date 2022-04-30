package ua.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/userList.jsp")
public class AuthenticationFilter implements Filter {
    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "Bearer "; // with trailing space to separate token

    private static final int STATUS_CODE_UNAUTHORIZED = 401;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        boolean loggedIn = false;
        try {

            String jwt = getBearerToken( httpRequest );

            if ( jwt != null && !jwt.isEmpty() ) {
                httpRequest.login( jwt, "" );
                loggedIn = true;
//                LOG.info( "Logged in using JWT" );
            } else {
//                LOG.info( "No JWT provided, go on unauthenticated" );
            }



            if ( loggedIn ) {
                filterChain.doFilter( servletRequest, servletResponse );

//                LOG.info( "Logged out" );
            } else{
                httpRequest.logout();
                RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/logIn.jsp");
                requestDispatcher.forward(servletRequest, servletResponse);
            }


        } catch ( final Exception e ) {
//            LOG.log( Level.WARNING, "Failed logging in with security token", e );
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentLength( 0 );
            httpResponse.setStatus( STATUS_CODE_UNAUTHORIZED );
        }
    }
    private String getBearerToken( HttpServletRequest request ) {
        String authHeader = request.getHeader( AUTH_HEADER_KEY );
        if ( authHeader != null && authHeader.startsWith( AUTH_HEADER_VALUE_PREFIX ) ) {
            return authHeader.substring( AUTH_HEADER_VALUE_PREFIX.length() );
        }
        return null;
    }
}
