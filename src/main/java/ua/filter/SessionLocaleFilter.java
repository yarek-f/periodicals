package ua.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession currentSession = req.getSession();
        String langReq = req.getParameter("lang");

        if (langReq != null) {
            currentSession.setAttribute("lang", langReq);
        } else {
            if(currentSession.getAttribute("lang")==null){
                currentSession.setAttribute("lang", "en");
            }
        }
//        if (req.getParameter("sessionLocale") != null) {
//            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
//        }

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}