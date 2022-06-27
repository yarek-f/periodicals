package ua.servlets;

import ua.dao.UserMySqlDao;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.dto.UserGetDto;
import ua.excaptions.UserVarificationException;
import ua.services.JWTService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "subscriptions", urlPatterns = {"/my-subscriptions"})
public class MySubscriptions extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        JWTService jwtService = new JWTService();
//        PublisherServiceImpl publisherService = new PublisherServiceImpl();
        UserService userService = new UserServiceImpl();

        String token = (String)request.getSession().getAttribute("token");

        String email = null;
        try {
            email = jwtService.verifyToken(token).getClaims().get("email");
        } catch (UserVarificationException e) {
            e.printStackTrace();
        }
        System.out.println("email ==> " + email);
        session.setAttribute("email", email);

        String publisherId = request.getParameter("publisherIdForUnsubscription");
        System.out.println(publisherId);
        if (publisherId != null){
            userService.unsubscribe(userService.getCustomer(email).getId(), Integer.valueOf(publisherId));
        }
        List<Publisher> resultList = userService.getAllSubscriptions(email);


        List<String> publishersByTopic =  userService.getTopicsByPublishers(resultList);

        String topic = request.getParameter("topic");
        request.setAttribute("topic", topic);

        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort); //fixme: replace session scope to request in jsp page
        resultList = userService.sortBy(sort, topic, resultList);


        //start pagination
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<PublisherDto> list = userService.getPagination((page-1)*recordsPerPage, recordsPerPage, resultList);

        int noOfRecords = resultList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("publisherList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        //end pagination

        session.removeAttribute("publishersByTopic");
        session.setAttribute("publishersByTopic", publishersByTopic);

        RequestDispatcher view = request.getRequestDispatcher("my-subscriptions.jsp");
        view.forward(request, response);
    }
}
