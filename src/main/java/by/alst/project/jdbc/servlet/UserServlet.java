package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.InformationDto;
import by.alst.project.jdbc.dto.UserDto;
import by.alst.project.jdbc.service.GenderService;
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

import static by.alst.project.jdbc.utils.UrlPath.USER;

@WebServlet(USER)
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final InformationService informationService = InformationService.getInstance();
    private final RoleService roleService = RoleService.getInstance();
    private final GenderService genderService = GenderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var getUsersId = req.getParameter("usersId");
        if (getUsersId != null) {
            Integer usersId = Integer.valueOf(req.getParameter("usersId"));
            UserDto userDto = userService.findById(usersId);
            InformationDto informationDto = informationService.findById(usersId);

            req.setAttribute("user", userDto);
            req.setAttribute("information", informationDto);
            req.setAttribute("roleService", roleService);
            req.setAttribute("genderService", genderService);
            req.getRequestDispatcher(JspHelper.getPath("user")).forward(req, resp);
        } else {
            var user = (UserDto) req.getSession().getAttribute("user");
            resp.sendRedirect("/user?usersId=" + user.id());
        }
    }
}
