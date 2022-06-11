package ua.servlets;

import ua.domain.Publisher;
import ua.domain.Publishers;
import ua.domain.Topics;
import ua.services.PublisherServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "periodicals", urlPatterns = {"/periodicals"})
public class MainPage extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
//        int page = 1;
//        int recordsPerPage = 5;
//        if(request.getParameter("page") != null){
//            page = Integer.parseInt(request.getParameter("page"));
//        }
//        UserMySqlDao dao = new UserMySqlDao();
//
//        List<UserGetDto> list = dao.getAll((page-1)*recordsPerPage,
//                recordsPerPage);
//        int noOfRecords = dao.getNoOfRecords();
//        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//        request.setAttribute("userList", list);
//        request.setAttribute("noOfPages", noOfPages);
//        request.setAttribute("currentPage", page);

        PublisherServiceImpl publisherService = new PublisherServiceImpl();

        List<Publisher> publishersList = publisherService.getAll();
        List<Publisher> sortedPublishersByName =  publishersList.stream()
                .sorted(Comparator.comparing(Publisher::getName))
                .collect(Collectors.toList());

        List<Publisher> sortedPublishersByPrice =  publishersList.stream()
                .sorted(Comparator.comparing(Publisher::getPrice))
                .collect(Collectors.toList());

        List<String> publishersByTopic =  publishersList.stream()
                .map(e -> e.getTopic().toString())
                .distinct()
                .collect(Collectors.toList());


        String topic = request.getParameter("topic");

        if (topic != null){
            List<Publisher> publisherByTopioc = publisherService.getByTopic(topic);
            session.setAttribute("publishers", publisherByTopioc);
        } else {
            session.setAttribute("publishers", publishersList);
        }
        String sort = request.getParameter("sort");
        if(sort != null && sort.equals("byName")){
            session.setAttribute("publishers", sortedPublishersByName);
        } else if(sort != null && sort.equals("byPrice")){
            session.setAttribute("publishers", sortedPublishersByPrice);
        }



        session.setAttribute("publishersByName", sortedPublishersByName);
        session.setAttribute("publishersByPrice", sortedPublishersByPrice);
        session.setAttribute("publishersByTopic", publishersByTopic);

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }
}
