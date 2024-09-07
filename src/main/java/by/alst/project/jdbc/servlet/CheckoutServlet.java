package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.InformationDto;
import by.alst.project.jdbc.service.CheckoutService;
import by.alst.project.jdbc.service.InformationService;
import by.alst.project.jdbc.service.OrderService;
import by.alst.project.jdbc.service.ProductService;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static by.alst.project.jdbc.utils.UrlPath.CHECKOUT;

@WebServlet(CHECKOUT)
public class CheckoutServlet extends HttpServlet {

    private final CheckoutService checkoutService = CheckoutService.getInstance();
    private final InformationService informationService = InformationService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer usersId = Integer.valueOf(req.getParameter("usersId"));
        InformationDto informationDto = informationService.findById(usersId);
        var checkoutDtoList = checkoutService.findAllByUsersId(usersId);
        var totalCheckoutsCost = checkoutService.findAllByUsersId(usersId).stream().map(checkoutDto
                        -> orderService.findAllByCheckoutId(checkoutDto.id())).flatMap(orderDtoList
                        -> orderDtoList.stream().map(orderDto -> orderDto.productAmount()
                        .multiply(productService.findById(orderDto.productId()).productCost()))).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).toString();


        req.setAttribute("information", informationDto);
        req.setAttribute("checkouts", checkoutDtoList);
        req.setAttribute("totalCheckoutsCost", totalCheckoutsCost);
        req.setAttribute("orderService", orderService);
        req.setAttribute("productService", productService);
        req.getRequestDispatcher(JspHelper.getPath("checkout")).forward(req, resp);
    }
}
