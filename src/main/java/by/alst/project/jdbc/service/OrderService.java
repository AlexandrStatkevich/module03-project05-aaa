package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.OrderDao;
import by.alst.project.jdbc.dto.OrderDto;
import by.alst.project.jdbc.entity.Order;

import java.util.List;

public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    private OrderService() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    private final OrderDao orderDao = OrderDao.getInstance();

    public List<OrderDto> findAll() {
        return orderDao.findAll().stream().map(order -> new OrderDto(order.getId(), order.getCheckoutId(),
                order.getProductId(), order.getProductAmount())).toList();
    }

    public OrderDto findById(Integer ordersId) {
        Order order = orderDao.findById(ordersId).orElse(new Order());
        return new OrderDto(order.getId(), order.getCheckoutId(), order.getProductId(), order.getProductAmount());
    }

    public List<OrderDto> findAllByCheckoutId(Integer checkoutId) {
        return orderDao.findAllByCheckoutId(checkoutId).stream().map(order -> new OrderDto(order.getId(), order.getCheckoutId(),
                order.getProductId(), order.getProductAmount())).toList();
    }
}
