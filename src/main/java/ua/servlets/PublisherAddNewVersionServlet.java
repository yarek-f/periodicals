package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet(name = "addNeVersion", urlPatterns = {"/new-version"})
public class PublisherAddNewVersionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PublisherService publisherService = new PublisherServiceImpl();
        HttpSession session = req.getSession(true);
        resp.setContentType("text/html");

        PublisherMySqlDao publisherMySqlDao = new PublisherMySqlDao();
        String name = req.getParameter("inputPublisherName");
        Publisher curentPublisher = publisherMySqlDao.get(name);
        System.out.println(name);
        String publisherVersion = req.getParameter("inputPublisherVersion");
        System.out.println(publisherVersion);

        Part filePart = req.getPart("file");
        String fileName = Paths.get(
                filePart.getSubmittedFileName()).getFileName().toString();

        if (!fileName.equals("")){

            System.out.println("file name ==> " + fileName);

            String address = getServletContext().getRealPath("images/").concat(fileName);
            System.out.println("address ==> " + address);

            InputStream fileContent = filePart.getInputStream(); //fixme
            Files.copy(fileContent, Paths.get(address));
        } else {
            fileName = curentPublisher.getImage();
        }


        Publisher publisher = new Publisher(name, fileName, Integer.valueOf(publisherVersion));
        publisherMySqlDao.addNewVersion(publisher);

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
