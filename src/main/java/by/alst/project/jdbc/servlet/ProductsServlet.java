package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.service.ProductGroupService;
import by.alst.project.jdbc.service.ProductService;
import by.alst.project.jdbc.service.UnitService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.PRODUCTS;

@WebServlet(PRODUCTS)
public class ProductsServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();
    private final ProductGroupService productGroupService = ProductGroupService.getInstance();
    private final UnitService unitService = UnitService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer productGroupId = Integer.valueOf(req.getParameter("productGroupId"));

        req.setAttribute("productGroup", productGroupService.findById(productGroupId));
        req.setAttribute("products", productService.findAllByProductGroupId(productGroupId));
        req.setAttribute("unitService", unitService);
        req.getRequestDispatcher(JspHelper.getPath("products")).forward(req, resp);
    }
}