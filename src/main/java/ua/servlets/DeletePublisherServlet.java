package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig
@WebServlet(name = "delete-publisher", urlPatterns = {"/delete"})
public class DeletePublisherServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        String name = request.getParameter("name");
        String page = request.getParameter("page");

        request.getSession().setAttribute("sessionName", name);
        request.getSession().setAttribute("page", page);

        RequestDispatcher view = request.getRequestDispatcher("confirm-deleting.jsp");
        view.forward(request, response);
    }
}
