package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.dto.PublisherDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "publisherList", urlPatterns = {"/publishers"})
public class PublishersServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    public PublishersServlet() {
//        super();
//    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
        String id = request.getParameter("id");
        if (id != null && Integer.valueOf(id) >= 0){
            publisherMySqlDao.delete(Integer.valueOf(id));
        }
//        System.out.println("p for deleting ==> " + id);
        System.out.println("p for edit ==> " + request.getAttribute("editId"));

        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        PublisherMySqlDao dao = new PublisherMySqlDao();
        List<PublisherDto> list = dao.getAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = dao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("publisherList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher view = request.getRequestDispatcher("publishers.jsp");
//        RequestDispatcher view = request.getRequestDispatcher("publisherList.jsp");
        view.forward(request, response);
    }
}
