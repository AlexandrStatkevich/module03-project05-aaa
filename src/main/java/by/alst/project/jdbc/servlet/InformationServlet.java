package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.*;
import by.alst.project.jdbc.exception.ValidationException;
import by.alst.project.jdbc.service.InformationService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.INFORMATION;
import static by.alst.project.jdbc.utils.UrlPath.LOGIN;

@WebServlet(INFORMATION)
public class InformationServlet extends HttpServlet {

    private final InformationService informationService = InformationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("information"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createInformationDto = CreateInformationDto.builder()
                .usersId(((UserDto) req.getSession().getAttribute("user")).id())
                .firstName(req.getParameter("firstName"))
                .secondName(req.getParameter("secondName"))
                .lastName(req.getParameter("lastName"))
                .address(req.getParameter("address"))
                .phone(req.getParameter("phone"))
                .build();

        try {
            var createResult = informationService.create(createInformationDto);
            if (createResult) {
                resp.sendRedirect(LOGIN);
            } else {
                doGet(req, resp);
            }
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
