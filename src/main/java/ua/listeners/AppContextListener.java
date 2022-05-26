package ua.listeners;

import ua.dao.DataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        String url = ctx.getInitParameter("DBURL");
        String u = ctx.getInitParameter("DBUSER");
        String p = ctx.getInitParameter("DBPWD");
        String d = ctx.getInitParameter("DBDRIVER");

        DataSource.inntConfig(d, url, u, p);

        //create database connection from init parameters and set it to context
        //DBConnectionManager dbManager = new DBConnectionManager(url, u, p);
       // ctx.setAttribute("DBManager", dbManager);
       // System.out.println("Database connection initialized for Application.");
    }
}
