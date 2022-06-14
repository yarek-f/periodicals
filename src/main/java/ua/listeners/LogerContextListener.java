package ua.listeners;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.*;

@WebListener
public class LogerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/log4j2.log");
        System.setProperty("C:\\ITprojects\\Periodicals_web\\logFile.log", path);

        final Logger log = LogManager.getLogger(LogerContextListener.class);
        log.debug("path = " + path);
    }

}

