package ua.servlets;

import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.excaptions.UserVarificationException;
import ua.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "periodicals", urlPatterns = {"/periodicals"})
public class MainPage extends HttpServlet {
    private static Logger logger = LogManager.getLogger(MainPage.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        PublisherServiceImpl publisherService = new PublisherServiceImpl();
        UserService userService = new UserServiceImpl();
        JWTService jwtService = new JWTService();
        String customerEmail = (String) session.getAttribute("profile");

        List<Publisher> resultList = publisherService.getAll();
        session.setAttribute("publishers", resultList);

        List<String> publishersByTopic =  userService.getTopicsByPublishers(resultList);

        String topic = request.getParameter("topic");
        request.setAttribute("topic", topic);

        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        resultList = userService.sortBy(sort, topic, resultList);

        String publisherUnsubscribeId = request.getParameter("publisherIdForUnsubscription");
        System.out.println(publisherUnsubscribeId);
        if (publisherUnsubscribeId != null){
            userService.unsubscribe(userService.getCustomer(customerEmail).getId(), Integer.valueOf(publisherUnsubscribeId));
        }

        //start subscription process
        String publisherId = request.getParameter("subscribe");
        int customerId = userService.getCustomer(customerEmail).getId();

        String token = (String)request.getSession().getAttribute("token");
        String price = request.getParameter("price");
        System.out.println("publisher id for subscriptions ==> " + publisherId);
        System.out.println("customer id for subscriptions ==> " + customerId);

        try {
            if(publisherId != null && token != null && price != null){
                int pubId = Integer.valueOf(publisherId);
                String email = jwtService.verifyToken(token).getClaims().get("email");

                List<String> res = userService.withdrawFromBalance(email, pubId, Double.valueOf(price));

                if (res.isEmpty()){
                    userService.addSubscription(customerId, Integer.valueOf(publisherId));
                }  else{
                    session.setAttribute("subscriptionErrorMessage", res);
                }
            }
        } catch (UserVarificationException e) {
            logger.error(""+e.getMessage());
        }
        //end subscription process



        //start pagination
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null && !request.getParameter("page").equals("")){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<PublisherDto> list = userService.getPagination((page-1)*recordsPerPage, recordsPerPage, resultList);

        int noOfRecords = resultList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("publisherList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        //end pagination

//        session.removeAttribute("publishersByTopic");
        session.setAttribute("publishersByTopic", publishersByTopic);

        double balance = userService.getCustomer(customerEmail).getBalance();
        session.setAttribute("balance", String.format("%.2f", balance));

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HttpSession session = req.getSession();
        PublisherService publisherService = new PublisherServiceImpl();

        String wantedPublisher = req.getParameter("search");
        System.out.println(wantedPublisher);

        List<PublisherDto> publisherList = publisherService.searchByName(wantedPublisher);
        if (publisherList == null || publisherList.isEmpty()){
            resp.sendRedirect("cant-found.jsp");
        } else{
            session.setAttribute("searchingPublisher", publisherList);
            RequestDispatcher view = req.getRequestDispatcher("wanted-publisher.jsp");
            view.forward(req, resp);
        }
    }
}
