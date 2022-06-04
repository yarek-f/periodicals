package ua.listeners;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.*;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/log4j2.log");
        System.setProperty("logFile", path);

        final Logger log = LogManager.getLogger(ContextListener.class);
        log.debug("path = " + path);

        // try to obtain a connection
        //Connection con = DBUtils.getInstance().getConnection())
    }

}

