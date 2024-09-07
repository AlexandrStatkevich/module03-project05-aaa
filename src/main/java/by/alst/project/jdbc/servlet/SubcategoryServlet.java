package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.service.CategoryService;
import by.alst.project.jdbc.service.SubcategoryService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.SUBCATEGORY;

@WebServlet(SUBCATEGORY)
public class SubcategoryServlet extends HttpServlet {

    private final SubcategoryService subcategoryService = SubcategoryService.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer categoryId = Integer.valueOf(req.getParameter("categoryId"));

        req.setAttribute("category", categoryService.findById(categoryId));
        req.setAttribute("subcategories", subcategoryService.findAllByCategoryId(categoryId));
        req.getRequestDispatcher(JspHelper.getPath("subcategory")).forward(req, resp);
    }
}
