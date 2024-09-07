package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.CreateUserDto;
import by.alst.project.jdbc.dto.GenderDto;
import by.alst.project.jdbc.dto.RoleDto;
import by.alst.project.jdbc.exception.ValidationException;
import by.alst.project.jdbc.service.GenderService;
import by.alst.project.jdbc.service.RoleService;
import by.alst.project.jdbc.service.UserService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.LOGIN;
import static by.alst.project.jdbc.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final RoleService roleService = RoleService.getInstance();
    private final GenderService genderService = GenderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roleService.findAll().stream().map(RoleDto::role).toList());
        req.setAttribute("genders", genderService.findAll().stream().map(GenderDto::gender).toList());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createUserDto = CreateUserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("pwd"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.create(createUserDto);
            resp.sendRedirect(LOGIN);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}

