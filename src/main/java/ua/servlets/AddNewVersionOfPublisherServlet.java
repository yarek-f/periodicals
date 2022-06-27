package ua.servlets;

import ua.dao.PublisherMySqlDao;
import ua.domain.Publisher;
import ua.domain.Topics;
import ua.dto.PublisherDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig
@WebServlet(name = "addNeVersion", urlPatterns = {"/new-version"})
public class AddNewVersionOfPublisherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PublisherService publisherService = new PublisherServiceImpl();
        HttpSession session = req.getSession(true);
        resp.setContentType("text/html");

        String publisherName = req.getParameter("inputPublisherName");
        Publisher currentPublisher = publisherService.get(publisherName);
        System.out.println(publisherName);
        String publisherVersion = req.getParameter("inputPublisherVersion");
        System.out.println(publisherVersion);

        Part filePart = req.getPart("file");
        String fileName = Paths.get(
                filePart.getSubmittedFileName()).getFileName().toString();

        if (!fileName.equals("")){

            System.out.println("file publisherName ==> " + fileName);

            String servletAddress = getServletContext().getRealPath("images/").concat(fileName);
            String projectAddress = "C:\\ITprojects\\Periodicals_web\\src\\main\\webapp\\images\\" + fileName;
            System.out.println("address ==> " + projectAddress);

            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent, Paths.get(projectAddress), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(projectAddress), Paths.get(servletAddress), StandardCopyOption.REPLACE_EXISTING);

        } else {
            fileName = currentPublisher.getImage();
        }


        PublisherDto createPublisherDto = new PublisherDto(fileName, publisherName, publisherVersion);
        session.setAttribute("publisherAddNewVersionDto", createPublisherDto);

        List<String> publisherResponse = publisherService.addNewVersion(createPublisherDto);
        //addNewVersion(createPublisherDto, List<CustomerDto> list)

        session.setAttribute("publisherErrorMessages", publisherResponse);

        if (!publisherResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/add-new-version.jsp");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/publishers");
            session.removeAttribute("publisherErrorMessages");
            session.removeAttribute("publisherAddNewVersionDto");
        }


//        PublisherDto publisherDto = new PublisherDto(pictures, publisherName, topic, price, description);
//            session.setAttribute("createDTO", publisherDto);
//
//            List<String> userResponse = publisherService.create(publisherDto);
//
//            session.setAttribute("errorMessages", userResponse);
//
//        resp.sendRedirect("/rrs");
    }
}
