package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "publisherList", urlPatterns = {"/employee.do"})
public class PublishersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PublishersServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        PublisherMySqlDao dao = new PublisherMySqlDao();
        List<Publisher> list = dao.getAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = dao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        session.setAttribute("publisherList", list);
        session.setAttribute("publisherList", list);
        session.setAttribute("noOfPages", noOfPages);
        session.setAttribute("currentPage", page);
        RequestDispatcher view = request.getRequestDispatcher("publisherList.jsp");
        view.forward(request, response);
    }
}
