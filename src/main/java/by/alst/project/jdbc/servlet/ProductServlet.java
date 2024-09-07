package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.ProductDto;
import by.alst.project.jdbc.service.CountryService;
import by.alst.project.jdbc.service.ProducerService;
import by.alst.project.jdbc.service.ProductService;
import by.alst.project.jdbc.service.UnitService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.alst.project.jdbc.utils.UrlPath.PRODUCT;

@WebServlet(PRODUCT)
public class ProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();
    private final ProducerService producerService = ProducerService.getInstance();
    private final CountryService countryService = CountryService.getInstance();
    private final UnitService unitService = UnitService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        ProductDto productDto = productService.findById(productId);

        req.setAttribute("product", productDto);
        req.setAttribute("unitService", unitService);
        req.setAttribute("producerService", producerService);
        req.setAttribute("countryService", countryService);
        req.getRequestDispatcher(JspHelper.getPath("product")).forward(req, resp);
    }
}
