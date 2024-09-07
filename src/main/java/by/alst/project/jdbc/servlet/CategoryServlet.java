package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.service.CategoryService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.CATEGORY;

@WebServlet(CATEGORY)
public class CategoryServlet extends HttpServlet {

    private final CategoryService categoryService = CategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("category")).forward(req, resp);
    }
}
