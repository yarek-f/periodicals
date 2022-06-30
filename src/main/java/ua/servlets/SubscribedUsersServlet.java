package ua.servlets;

import ua.dao.UserMySqlDao;
import ua.dto.CustomerDto;
import ua.dto.UserGetDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "subscribedUsersList", urlPatterns = {"/subscribed-users"})
public class SubscribedUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PublisherService publisherService = new PublisherServiceImpl();

        String publisherName = request.getParameter("publisherName");
        if (publisherName != null){
            request.getSession().setAttribute("publisherName", publisherName);
            List<CustomerDto> subscribersList = publisherService.getAllSubscribers(publisherName);
            request.setAttribute("subscribersList", subscribersList);
        }

        RequestDispatcher view = request.getRequestDispatcher("/subscribed-users.jsp");
        view.forward(request, response);
    }
}
