package ua.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@WebServlet(name = "create", urlPatterns = {"/create-publisher"})
public class CreatePublisherServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger(CreatePublisherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = req.getParameter("page");
        req.getSession().setAttribute("page", page);

        resp.sendRedirect("/add-publisher.jsp");
    }

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
            fileName = manageImage(fileName, filePart);
        } else {
            fileName = "defaultPicture.png";
        }

        PublisherDto createPublisherDto = new PublisherDto(fileName, publisherName, topic, price, description);
        session.setAttribute("publisherCreateDto", createPublisherDto);

        List<String> publisherResponse = publisherService.create(createPublisherDto);

        session.setAttribute("publisherErrorMessages", publisherResponse);

        if (!publisherResponse.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/add-publisher.jsp");
        }
        else {
            resp.sendRedirect(req.getContextPath() + "/publishers");
            session.removeAttribute("publisherErrorMessages");
            session.removeAttribute("publisherCreateDto");
        }
    }

    private String manageImage(String fileName, Part filePart){
        try {
            System.out.println("file name ==> " + fileName);
            File currentPicture = new File("C:\\\\ITprojects\\\\Periodicals_web\\\\src\\\\main\\\\webapp\\\\images\\" + fileName);
            if (currentPicture.exists()){
                int random = (int) (1000+Math.random() * 9000);
                fileName = random + "_" + fileName;
            }
            String servletAddress = getServletContext().getRealPath("images/").concat(fileName);
            System.out.println("servlet address ==> " + servletAddress);

            String projectAddress = "C:\\ITprojects\\Periodicals_web\\src\\main\\webapp\\images\\" + fileName;
            System.out.println("project address ==> " + projectAddress);

            InputStream fileContent = filePart.getInputStream();

            Files.copy(fileContent, Paths.get(projectAddress));
            Files.copy(Paths.get(projectAddress), Paths.get(servletAddress));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return fileName;
    }
}
