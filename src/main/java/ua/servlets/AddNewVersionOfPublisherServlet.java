package ua.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.domain.Publisher;
import ua.dto.PublisherDto;
import ua.services.PublisherService;
import ua.services.PublisherServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig
@WebServlet(name = "addNeVersion", urlPatterns = {"/new-version"})
public class AddNewVersionOfPublisherServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AddNewVersionOfPublisherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = req.getParameter("page");
        req.getSession().setAttribute("page", page);

        resp.sendRedirect("/add-new-version.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PublisherService publisherService = new PublisherServiceImpl();
        HttpSession session = req.getSession();
        resp.setContentType("text/html");
        String page = req.getParameter("page");
        req.getSession().setAttribute("page", page);

        String publisherName = req.getParameter("inputPublisherName");
        Publisher currentPublisher = publisherService.get(publisherName);
        System.out.println(publisherName);
        String publisherVersion = req.getParameter("inputPublisherVersion");
        System.out.println(publisherVersion);

        Part filePart = req.getPart("file");
        String fileName = Paths.get(
                filePart.getSubmittedFileName()).getFileName().toString();

        if (!fileName.equals("")){
            fileName = manageImage(fileName, filePart, publisherName);
        } else {
            fileName = currentPublisher.getImage();
        }
        PublisherDto createPublisherDto = new PublisherDto(fileName, publisherName, publisherVersion);
        session.setAttribute("publisherAddNewVersionDto", createPublisherDto);

        List<String> publisherResponse = publisherService.addNewVersion(createPublisherDto);

        session.setAttribute("publisherErrorMessages", publisherResponse);

        if (!publisherResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/add-new-version.jsp");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/publishers");
            session.removeAttribute("publisherErrorMessages");
            session.removeAttribute("publisherAddNewVersionDto");
        }
    }

    private String manageImage(String fileName, Part filePart, String publisherName){
        try {
        PublisherService publisherService = new PublisherServiceImpl();
        System.out.println("file publisherName ==> " + fileName);
        File currentPicture = new File("C:\\\\ITprojects\\\\Periodicals_web\\\\src\\\\main\\\\webapp\\\\images\\" + fileName);
            if (currentPicture.exists()){
                int random = (int) (1000+Math.random() * 9000);
                fileName = random + "_" + fileName;
            }
        File previousPicture = new File("C:\\\\ITprojects\\\\Periodicals_web\\\\src\\\\main\\\\webapp\\\\images\\" + publisherService.get(publisherName).getImage());
        File previousPicture2 = new File(getServletContext().getRealPath("images/").concat(publisherService.get(publisherName).getImage()));
        previousPicture.delete();
        previousPicture2.delete();
        String servletAddress = getServletContext().getRealPath("images/").concat(fileName);
        String projectAddress = "C:\\ITprojects\\Periodicals_web\\src\\main\\webapp\\images\\" + fileName;
        System.out.println("address ==> " + projectAddress);

        InputStream fileContent = filePart.getInputStream();
        Files.copy(fileContent, Paths.get(projectAddress));
        Files.copy(Paths.get(projectAddress), Paths.get(servletAddress));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return fileName;
    }
}
