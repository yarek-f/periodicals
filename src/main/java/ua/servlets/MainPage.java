package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.domain.Publishers;
import ua.domain.Topics;
import ua.dto.PublisherDto;
import ua.mapper.Mapper;
import ua.services.PublisherServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "periodicals", urlPatterns = {"/periodicals"})
public class MainPage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        PublisherServiceImpl publisherService = new PublisherServiceImpl();

        List<Publisher> publishersList = publisherService.getAll();
        session.setAttribute("publishers", publishersList);

        List<Publisher> resultList = publishersList;


        List<String> publishersByTopic =  publishersList.stream()
                .map(e -> e.getTopic().toString())
                .distinct()
                .collect(Collectors.toList());

        List<String> allTopics = Stream.of(Topics.values())
                .map(Topics::name)
                .collect(Collectors.toList());


        String topic = request.getParameter("topic");
        request.setAttribute("topic", topic);

        if (topic != null){
            resultList = publisherService.getByTopic(topic);
        }

        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);

         if(sort != null && sort.equals("byName") && topic != null){
            resultList = sortByName(publisherService.getByTopic(topic));
        } else if(sort != null && sort.equals("byName")){
            resultList = sortByName(publishersList);
        }

         if(sort != null && sort.equals("byPrice") && topic != null){
            resultList = sortByPrice(publisherService.getByTopic(topic));
        } else if(sort != null && sort.equals("byPrice")){
             resultList = sortByPrice(publishersList);
        }

        session.setAttribute("publishersByTopic", publishersByTopic);
        session.setAttribute("allTopics", allTopics);

        //start pagination
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<PublisherDto> list = getPagination((page-1)*recordsPerPage,
                recordsPerPage, resultList);

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

    private List<PublisherDto> getPagination(int skip, int limit, List<Publisher> currentList){
        return currentList.stream()
                .skip(skip)
                .limit(limit)
                .map(e -> Mapper.convertToPublisherDto(e))
                .collect(Collectors.toList());
    }

    private List<Publisher> sortByName(List<Publisher> publishersList){
        return publishersList.stream()
                .sorted(Comparator.comparing(Publisher::getName))
                .collect(Collectors.toList());
    }

    private List<Publisher> sortByPrice(List<Publisher> publishersList){
        return publishersList.stream()
                .sorted(Comparator.comparing(Publisher::getPrice))
                .collect(Collectors.toList());
    }
}
