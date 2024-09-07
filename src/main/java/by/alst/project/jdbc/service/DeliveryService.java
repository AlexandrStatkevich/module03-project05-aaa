package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.DeliveryDao;
import by.alst.project.jdbc.dto.DeliveryDto;
import by.alst.project.jdbc.entity.Delivery;

import java.util.List;

public class DeliveryService {

    private final static DeliveryService INSTANCE = new DeliveryService();

    private DeliveryService() {
    }

    public static DeliveryService getInstance() {
        return INSTANCE;
    }

    private final DeliveryDao deliveryDao = DeliveryDao.getInstance();

    public List<DeliveryDto> findAll() {
        return deliveryDao.findAll().stream().map(delivery -> new DeliveryDto(delivery.getCheckoutId(),
                delivery.getDeliveryAddress(), delivery.getDeliveryDateTime())).toList();
    }

    public DeliveryDto findById(Integer checkoutId) {
        Delivery delivery = deliveryDao.findById(checkoutId).orElse(new Delivery());
        return new DeliveryDto(delivery.getCheckoutId(), delivery.getDeliveryAddress(), delivery.getDeliveryDateTime());
    }

}
