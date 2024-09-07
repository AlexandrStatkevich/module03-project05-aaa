package by.alst.project.jdbc.servlet;

import by.alst.project.jdbc.dto.CheckoutDto;
import by.alst.project.jdbc.dto.DeliveryDto;
import by.alst.project.jdbc.dto.InformationDto;
import by.alst.project.jdbc.service.*;
import by.alst.project.jdbc.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static by.alst.project.jdbc.utils.UrlPath.ORDER;

@WebServlet(ORDER)
public class OrderServlet extends HttpServlet {

    private final CheckoutService checkoutService = CheckoutService.getInstance();
    private final InformationService informationService = InformationService.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final DeliveryService deliveryService = DeliveryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer usersId = Integer.valueOf(req.getParameter("usersId"));
        Integer checkoutId = Integer.valueOf(req.getParameter("checkoutId"));
        InformationDto informationDto = informationService.findById(usersId);
        CheckoutDto checkoutDto = checkoutService.findById(checkoutId);
        DeliveryDto deliveryDto = deliveryService.findById(checkoutId);
        var checkoutCost = orderService.findAllByCheckoutId(checkoutId).stream().map(orderDto ->
                        orderDto.productAmount().multiply(productService.findById(orderDto.productId()).productCost()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();

        req.setAttribute("information", informationDto);
        req.setAttribute("checkout", checkoutDto);
        req.setAttribute("delivery", deliveryDto);
        req.setAttribute("orders", orderService.findAllByCheckoutId(checkoutId));
        req.setAttribute("productService", productService);
        req.setAttribute("checkoutCost", checkoutCost);
        req.getRequestDispatcher(JspHelper.getPath("order")).forward(req, resp);
    }
}