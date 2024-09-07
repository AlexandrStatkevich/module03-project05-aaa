package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.UserDto;
import by.alst.project.jdbc.service.InformationService;
import by.alst.project.jdbc.service.RoleService;
import by.alst.project.jdbc.service.UserService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import static by.alst.project.jdbc.utils.UrlPath.*;

@WebServlet(USERS)
public class UsersServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final InformationService informationService = InformationService.getInstance();
    private final RoleService roleService = RoleService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserDto) req.getSession().getAttribute("user");
        var role = roleService.findById(user.roleId()).role();
        String administratorRole = "ADMINISTRATOR";
        if (Objects.equals(role, administratorRole)) {
            req.setAttribute("users", userService.findAll());
            req.setAttribute("informationService", informationService);
            req.getRequestDispatcher(JspHelper.getPath("users")).forward(req, resp);
        } else {
            resp.sendRedirect("/user?usersId=" + user.id());
        }
    }
}