package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.mapper.Mapper;

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

@WebServlet(name = "publisherList", urlPatterns = {"/publishers"})
public class PublishersServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();

        String deletePublisher = request.getParameter("deletePublisher");
        publisherMySqlDao.delete(publisherMySqlDao.get(deletePublisher).getId());

        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }


        List<PublisherDto> list = getPagination((page-1)*recordsPerPage,
                recordsPerPage);

        int noOfRecords = publisherMySqlDao.getAll().size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("publisherList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        RequestDispatcher view = request.getRequestDispatcher("publishers.jsp");

        view.forward(request, response);
    }

    private List<PublisherDto> getPagination(int skip, int limit){
        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
        List<Publisher> publishers = publisherMySqlDao.getAll();

        List<PublisherDto> resultList = publishers.stream()
                .skip(skip)
                .limit(limit)
                .map(e -> Mapper.convertToPublisherDto(e))
                .collect(Collectors.toList());

        return resultList;
    }
}
