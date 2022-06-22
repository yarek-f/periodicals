package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;
import ua.services.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "periodicals", urlPatterns = {"/periodicals"})
public class MainPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        PublisherServiceImpl publisherService = new PublisherServiceImpl();
        UserService userService = new UserServiceImpl();
        JWTService jwtService = new JWTService();


        List<Publisher> resultList = publisherService.getAll();
        session.setAttribute("publishers", resultList);

        List<String> publishersByTopic =  userService.getTopicsByPublishers(resultList);

        List<String> allTopics = userService.getAllTopics(); //todo move this to publishersServlet

        String topic = request.getParameter("topic");
        request.setAttribute("topic", topic);

        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort); //fixme: replace session scope to request in jsp page
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

        String token = (String)request.getSession().getAttribute("token");
        String price = request.getParameter("price");
        System.out.println("publisher id for subscriptions ==> " + publisherId);
        System.out.println("customer id for subscriptions ==> " + customerId);

        if(publisherId != null && token != null && price != null){
            String email = jwtService.verifyToken(token).getClaims().get("email");
            UserSignUpDto userSignUpDto = new UserSignUpDto(email, price);
            userService.addSubscription(customerId, Integer.valueOf(publisherId));
            userService.withdrawFromBalance(userSignUpDto);
        }
        //end subscription process

        session.removeAttribute("publishersByTopic");
        session.setAttribute("publishersByTopic", publishersByTopic);
        session.setAttribute("allTopics", allTopics);//todo move this to publishersServlet
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
