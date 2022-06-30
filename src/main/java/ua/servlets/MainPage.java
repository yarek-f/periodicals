package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;
import ua.excaptions.UserVarificationException;
import ua.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (session.getAttribute("profile") != null){
            resultList = isSubscribed(resultList, customerEmail);
        }

        session.setAttribute("publishers", resultList);

        List<String> publishersByTopic =  userService.getTopicsByPublishers(resultList);

        String topic = request.getParameter("topic");
        request.setAttribute("topic", topic);

        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        resultList = userService.sortBy(sort, topic, resultList);

        //todo ==> trying to change button color when customer is subscribed to publisher:
//        String publisherIdForButton = (String) session.getAttribute("pId");
//        System.out.println("publisherIdForButton => " + publisherIdForButton);
//        int customerIdForButton = userService.getCustomer((String)session.getAttribute("email")).getId();
//        if (publisherIdForButton != null){
//           boolean res = userService.isSubscribed(customerIdForButton, Integer.valueOf(publisherIdForButton));
//            System.out.println("is subscribed? ==> " + res);
//           session.setAttribute("res", res);
//           session.removeAttribute("pId");
//        }


        //start subscription process
        String publisherId = request.getParameter("subscribe");
        int customerId = userService.getCustomer((String) session.getAttribute("profile")).getId();
//        int customerId = userService.getCustomer(customerEmail).getId();

//        isSubscribed(resultList, customerId); fixme

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

        session.removeAttribute("publishersByTopic");
        session.setAttribute("publishersByTopic", publishersByTopic);

        double balance = userService.getCustomer((String)session.getAttribute("profile")).getBalance();
        session.setAttribute("balance", String.format("%.2f", balance));
//        session.setAttribute("cusId", customerId);

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

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }

    private List<Publisher> isSubscribed(List<Publisher> list, String email) {
        UserService userService = new UserServiceImpl();
        int customerId = userService.getCustomer(email).getId();
        for (Publisher p : list) {
            if (userService.isSubscribed(customerId, p.getId())) {
                p.setSubscribed(1);
            }
        }
        return list;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HttpSession session = req.getSession(true);

        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();

        String wantedPublisher = req.getParameter("search");
        System.out.println(wantedPublisher);

        List<Publisher> publisherList = publisherMySqlDao.getByName(wantedPublisher);
        if (publisherList == null || publisherList.isEmpty()){
            resp.sendRedirect("cant-found.jsp");
        } else{
            session.setAttribute("searchingPublisher", publisherList);
            RequestDispatcher view = req.getRequestDispatcher("wanted-publisher.jsp");
            view.forward(req, resp);
        }
    }
}
