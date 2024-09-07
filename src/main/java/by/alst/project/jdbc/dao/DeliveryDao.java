package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Checkout;
import by.alst.project.jdbc.entity.Delivery;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryDao implements Dao<Integer, Delivery> {

    private static final DeliveryDao INSTANCE = new DeliveryDao();

    private DeliveryDao() {
    }

    public static DeliveryDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE shopping.delivery
            SET delivery_address = ?,
                delivery_date_time = ?
            WHERE checkout_id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT checkout_id, delivery_address, delivery_date_time
            FROM shopping.delivery
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE checkout_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO shopping.delivery(checkout_id, delivery_address, delivery_date_time)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM shopping.delivery
            WHERE checkout_id = ?
            """;

    @Override
    public boolean update(Delivery delivery) {
        boolean updateResult = false;
        if (delivery != null) {
            var deliveryDateTime = Optional.ofNullable(delivery.getDeliveryDateTime()).isPresent()
                    ? delivery.getDeliveryDateTime() : LocalDateTime.MIN;
            if (!delivery.getDeliveryAddress().trim().isEmpty() & deliveryDateTime != LocalDateTime.MIN
                & delivery.getCheckoutId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, delivery.getDeliveryAddress());
                    statement.setTimestamp(2, Timestamp.valueOf(delivery.getDeliveryDateTime()));
                    statement.setInt(3, delivery.getCheckoutId());

                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Delivery> findById(Integer checkoutId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, checkoutId);
            Delivery delivery = null;

            var result = statement.executeQuery();
            while (result.next()) {
                delivery = buildDelivery(result);
            }
            return Optional.ofNullable(delivery);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Delivery> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Delivery> deliveryList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                deliveryList.add(
                        buildDelivery(result)
                );
            }
            return deliveryList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Delivery buildDelivery(ResultSet result) throws SQLException {
        return new Delivery(result.getInt("checkout_id"),
                result.getString("delivery_address"),
                result.getTimestamp("delivery_date_time").toLocalDateTime());
    }

    @Override
    public Delivery save(Delivery delivery) {
        var checkoutDao = CheckoutDao.getInstance();
        if (delivery != null && !findAll().stream().map(Delivery::getCheckoutId).toList().contains(delivery.getCheckoutId())
            && checkoutDao.findAll().stream().map(Checkout::getId).toList().contains(delivery.getCheckoutId())) {
            var deliveryDateTime = Optional.ofNullable(delivery.getDeliveryDateTime()).isPresent()
                    ? delivery.getDeliveryDateTime() : LocalDateTime.MIN;
            if (!delivery.getDeliveryAddress().trim().isEmpty() & deliveryDateTime != LocalDateTime.MIN
                & delivery.getCheckoutId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    statement.setInt(1, delivery.getCheckoutId());
                    statement.setString(2, delivery.getDeliveryAddress());
                    statement.setTimestamp(3, Timestamp.valueOf(delivery.getDeliveryDateTime()));

                    statement.executeUpdate();
                    delivery = findById(delivery.getCheckoutId()).orElse(new Delivery());
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                delivery = new Delivery();
            }
        } else {
            delivery = new Delivery();
        }
        return delivery;
    }

    @Override
    public boolean delete(Integer checkoutId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, checkoutId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
