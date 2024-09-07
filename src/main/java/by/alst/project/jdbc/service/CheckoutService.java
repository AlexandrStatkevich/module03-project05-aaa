package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.CheckoutDao;
import by.alst.project.jdbc.dto.CheckoutDto;
import by.alst.project.jdbc.entity.Checkout;

import java.util.List;

public class CheckoutService {

    private static final CheckoutService INSTANCE = new CheckoutService();

    private CheckoutService() {
    }

    public static CheckoutService getInstance() {
        return INSTANCE;
    }

    private final CheckoutDao checkoutDao = CheckoutDao.getInstance();

    public List<CheckoutDto> findAll() {
        return checkoutDao.findAll().stream().map(checkout -> new CheckoutDto(checkout.getId(),
                checkout.getLoggingId(), checkout.getCheckoutTime())).toList();
    }

    public CheckoutDto findById(Integer checkoutId) {
        Checkout checkout = checkoutDao.findById(checkoutId).orElse(new Checkout());
        return new CheckoutDto(checkout.getId(), checkout.getLoggingId(), checkout.getCheckoutTime());
    }

    public List<CheckoutDto> findAllByUsersId(Integer usersId) {
        return checkoutDao.findAllByUsersId(usersId).stream().map(checkout -> new CheckoutDto(checkout.getId(),
                checkout.getLoggingId(), checkout.getCheckoutTime())).toList();
    }
}
