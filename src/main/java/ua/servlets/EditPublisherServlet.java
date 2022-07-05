package ua.servlets;

import ua.domain.Publisher;
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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig
@WebServlet(name = "edit", urlPatterns = {"/edit-publisher"})
public class EditPublisherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("publisherName");
        String page = req.getParameter("page");
        System.out.println("publisher name for edit: "+name);

        req.getSession().setAttribute("publisherName", name);
        req.getSession().setAttribute("page", page);

        resp.sendRedirect("/edit-publisher.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PublisherService publisherService = new PublisherServiceImpl();
        HttpSession session = req.getSession(true);
        resp.setContentType("text/html");


        String publisherName = (String) session.getAttribute("publisherName");
        if (publisherName == null || publisherName.equals("")){
            resp.sendRedirect("/publishers");
        } else{
            System.out.println(publisherName);
            Publisher curentPublisher = publisherService.get(publisherName);

            String topic = req.getParameter("inputTopic");
            System.out.println(topic);
            if(topic == null || topic.equals("")){
                topic = curentPublisher.getTopic().toString();
            }
            String price = req.getParameter("inputPrice");
            System.out.println(price);
            if(price == null || price.equals("")){
                price = curentPublisher.getPrice().toString();
            }
            String description = req.getParameter("inputDescription");
            System.out.println(description);
            if(description == null || description.equals("")){
                description = curentPublisher.getDescription();
            }

            Part filePart = req.getPart("file");
            System.out.println("file part ==> "+ filePart.toString());
            String fileName = "";
            fileName = Paths.get(
                    filePart.getSubmittedFileName()).getFileName().toString();
            if (!fileName.equals("")){

                System.out.println("file name ==> " + fileName);

                String servletAddress = getServletContext().getRealPath("images/").concat(fileName);
                String projectAddress = "C:\\ITprojects\\Periodicals_web\\src\\main\\webapp\\images\\" + fileName;
                System.out.println("address ==> " + projectAddress);

                InputStream fileContent = filePart.getInputStream();
                Files.copy(fileContent, Paths.get(projectAddress), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(projectAddress), Paths.get(servletAddress), StandardCopyOption.REPLACE_EXISTING);
            } else {
                fileName = curentPublisher.getImage();
            }

            PublisherDto publisherEditDto = new PublisherDto(fileName, publisherName, topic, price, description);
            session.setAttribute("publisherEditDto", publisherEditDto);

            List<String> publisherResponse = publisherService.editPublisher(publisherEditDto);

            session.setAttribute("publisherErrorMessages", publisherResponse);

            if (!publisherResponse.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/edit-publisher.jsp");
            }
            else {
                resp.sendRedirect(req.getContextPath() + "/publishers?page="+req.getSession().getAttribute("page"));
                session.removeAttribute("publisherErrorMessages");
                session.removeAttribute("publisherEditDto");
            }
        }
    }
}
