package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.service.ProductGroupService;
import by.alst.project.jdbc.service.SubcategoryService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.PRODUCT_GROUP;

@WebServlet(PRODUCT_GROUP)
public class ProductGroupServlet extends HttpServlet {

    private final ProductGroupService productGroupService = ProductGroupService.getInstance();
    private final SubcategoryService subcategoryService = SubcategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer subcategoryId = Integer.valueOf(req.getParameter("subcategoryId"));
        req.setAttribute("subcategory", subcategoryService.findById(subcategoryId));
        req.setAttribute("productGroups", productGroupService.findAllBySubcategoryId(subcategoryId));
        req.getRequestDispatcher(JspHelper.getPath("productGroup")).forward(req, resp);
    }
}