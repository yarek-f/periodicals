package ua.servlets;

import ua.dto.CustomerDto;
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
import java.util.stream.Collectors;

@WebServlet(name = "usersList", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();

        String email = request.getParameter("email");
        String isActive = request.getParameter("isActive");
        if (email != null && !email.equals("") && isActive.equals("true")){
            userService.deactivateUser(email);
        }if (email != null && !email.equals("") && isActive.equals("false")){
            userService.activate(email);
        }

        List<CustomerDto> customerList = userService.getAllCustomer();
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<CustomerDto> list = getPagination((page-1)*recordsPerPage, recordsPerPage, customerList);
        int noOfRecords = customerList.size();

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("userList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        RequestDispatcher view = request.getRequestDispatcher("/userList.jsp");
        view.forward(request, response);
    }

    private List<CustomerDto> getPagination(int skip, int limit, List<CustomerDto> currentList) {
        return currentList.stream()
                .skip(skip)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
