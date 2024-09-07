package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Checkout;
import by.alst.project.jdbc.entity.Order;
import by.alst.project.jdbc.entity.Product;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Integer, Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    private OrderDao() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE shopping.orders
            SET checkout_id = ?,
                product_id = ?,
                product_amount = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, checkout_id, product_id, product_amount
            FROM shopping.orders
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_ALL_BY_CHECKOUT_ID_SQL = FIND_ALL_SQL + """
            WHERE checkout_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO shopping.orders(id, checkout_id, product_id, product_amount)
            VALUES (?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM shopping.orders
            WHERE id = ?
            """;

    @Override
    public boolean update(Order order) {
        boolean updateResult = false;
        if (order != null) {
            var productAmount = Optional.ofNullable(order.getProductAmount()).isPresent() ? order.getProductAmount() : 0;
            var productDao = ProductDao.getInstance();
            var checkoutDao = CheckoutDao.getInstance();
            List<Integer> productIdList = productDao.findAll().stream().map(Product::getId).toList();
            List<Integer> checkoutIdList = checkoutDao.findAll().stream().map((Checkout::getId)).toList();
            if (productIdList.contains(order.getProductId()) & checkoutIdList.contains(order.getCheckoutId())
                & productAmount.doubleValue() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setInt(1, order.getCheckoutId());
                    statement.setInt(2, order.getProductId());
                    statement.setBigDecimal(3, order.getProductAmount());
                    statement.setInt(4, order.getId());
                    return statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Order order = null;

            var result = statement.executeQuery();
            while (result.next()) {
                order = buildOrder(result);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Order> orderList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                orderList.add(
                        buildOrder(result)
                );
            }
            return orderList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Order> findAllByCheckoutId(Integer checkoutId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_CHECKOUT_ID_SQL)) {
            statement.setInt(1, checkoutId);
            List<Order> orderList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                orderList.add(
                        buildOrder(result)
                );
            }
            return orderList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Order buildOrder(ResultSet result) throws SQLException {
        return new Order(result.getInt("id"),
                result.getInt("checkout_id"),
                result.getInt("product_id"),
                result.getBigDecimal("product_amount"));
    }

    @Override
    public Order save(Order order) {
        if (order != null) {
            var productAmount = Optional.ofNullable(order.getProductAmount()).isPresent() ? order.getProductAmount() : 0;
            var productDao = ProductDao.getInstance();
            var checkoutDao = CheckoutDao.getInstance();
            List<Integer> productIdList = productDao.findAll().stream().map(Product::getId).toList();
            List<Integer> checkoutIdList = checkoutDao.findAll().stream().map((Checkout::getId)).toList();
            if (productIdList.contains(order.getProductId()) & checkoutIdList.contains(order.getCheckoutId())
                & productAmount.doubleValue() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Order::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setInt(2, order.getCheckoutId());
                    statement.setInt(3, order.getProductId());
                    statement.setBigDecimal(4, order.getProductAmount());

                    statement.executeUpdate();
                    order.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                order = new Order();
            }
        } else {
            order = new Order();
        }
        return order;
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
