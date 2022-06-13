package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.dto.PublisherDto;
import ua.dto.UserSignUpDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;
import ua.services.UserService;
import ua.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig
@WebServlet(name = "create", urlPatterns = {"/create-publisher"})
public class PublisherCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PublisherService publisherService = new PublisherServiceImpl();
        HttpSession session = req.getSession(true);
        resp.setContentType("text/html");


        String publisherName = req.getParameter("inputPublisherName");
        System.out.println(publisherName);
        String topic = req.getParameter("inputTopic");
        System.out.println(topic);
        String price = req.getParameter("inputPrice");
        System.out.println(price);
        String description = req.getParameter("inputDescription");
        System.out.println(description);

        Part filePart = req.getPart("file");
        String fileName = Paths.get(
                filePart.getSubmittedFileName()).getFileName().toString();
        if (!fileName.equals("")){

            System.out.println("file name ==> " + fileName);

            String address = getServletContext().getRealPath("images/").concat(fileName);
            System.out.println("address ==> " + address);

            InputStream fileContent = filePart.getInputStream();//fixme
            Files.copy(fileContent, Paths.get(address));
        } else {
            fileName = "defaultPicture.png";
        }



        Publisher publisher = new Publisher(fileName, publisherName, Topics.valueOf(topic), Double.valueOf(price), description);
        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
        publisherMySqlDao.signUp(publisher);

        resp.sendRedirect("/publishers");
//        PublisherDto publisherDto = new PublisherDto(pictures, publisherName, topic, price, description);
//            session.setAttribute("createDTO", publisherDto);
//
//            List<String> userResponse = publisherService.signUp(publisherDto);
//
//            session.setAttribute("errorMessages", userResponse);
//
//        resp.sendRedirect("/rrs");
    }
}
