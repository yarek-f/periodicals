package ua.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "delete-publisher", urlPatterns = {"/delete"})
public class DeletePublisherServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String page = request.getParameter("page");

        request.getSession().setAttribute("sessionName", name);
        request.getSession().setAttribute("page", page);

        RequestDispatcher view = request.getRequestDispatcher("confirm-deleting.jsp");
        view.forward(request, response);
    }
}
