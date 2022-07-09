package ua.servlets;

import ua.dao.UserMySqlDao;
import ua.dto.UserGetDto;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "usersList", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        UserMySqlDao dao = new UserMySqlDao();

        String email = request.getParameter("email");
        String isActive = request.getParameter("isActive");
        if (email != null && isActive.equals("true")){
            userService.deactivateUser(email);
        }if (email != null && isActive.equals("false")){
            userService.activate(email);
        }
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<UserGetDto> list = dao.getAll((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = dao.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("userList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher view = request.getRequestDispatcher("userList.jsp");
        view.forward(request, response);
    }
}
