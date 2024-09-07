package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Checkout;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckoutDao implements Dao<Integer, Checkout> {

    private static final CheckoutDao INSTANCE = new CheckoutDao();

    private CheckoutDao() {
    }

    public static CheckoutDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.checkout
            SET logging_id = ?,
                checkout_time = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, logging_id, checkout_time
            FROM users.checkout
            """;

    private final static String FIND_BY_USERS_ID_SQL = """
            SELECT ch.id, ch.logging_id, ch.checkout_time
            FROM users.checkout ch, users.logging lo
            WHERE ch.logging_id = lo.id AND lo.users_id = ?
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.checkout(id, logging_id, checkout_time)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.checkout
            WHERE id = ?
            """;

    @Override
    public boolean update(Checkout checkout) {
        boolean updateResult = false;
        if (checkout != null) {
            if (checkout.getLoggingId() > 0 & checkout.getCheckoutTime() != null) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setInt(1, checkout.getLoggingId());
                    statement.setTimestamp(2, Timestamp.valueOf(checkout.getCheckoutTime()));
                    statement.setInt(3, checkout.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Checkout> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Checkout checkout = null;

            var result = statement.executeQuery();
            while (result.next()) {
                checkout = buildCheckout(result);
            }
            return Optional.ofNullable(checkout);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Checkout> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Checkout> checkoutList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                checkoutList.add(
                        buildCheckout(result)
                );
            }
            return checkoutList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Checkout> findAllByUsersId(Integer usersId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USERS_ID_SQL)) {
            List<Checkout> checkoutList = new ArrayList<>();

            statement.setInt(1, usersId);
            var result = statement.executeQuery();
            while (result.next()) {
                checkoutList.add(
                        buildCheckout(result)
                );
            }
            return checkoutList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Checkout buildCheckout(ResultSet result) throws SQLException {
        return new Checkout(result.getInt("id"),
                result.getInt("logging_id"),
                result.getTimestamp("checkout_time").toLocalDateTime());
    }

    @Override
    public Checkout save(Checkout checkout) {
        if (checkout != null) {
            if (checkout.getLoggingId() > 0 & checkout.getCheckoutTime() != null) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Checkout::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setInt(2, checkout.getLoggingId());
                    statement.setTimestamp(3, Timestamp.valueOf(checkout.getCheckoutTime()));
                    statement.executeUpdate();
                    checkout.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                checkout = new Checkout();
            }
        } else {
            checkout = new Checkout();
        }
        return checkout;
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
