package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Logging;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggingDao implements Dao<Integer, Logging> {

    private static final LoggingDao INSTANCE = new LoggingDao();

    private LoggingDao() {
    }

    public static LoggingDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.logging
            SET users_id = ?,
                users_authentication_time = ?,
                users_log_out_time = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, users_id, users_authentication_time, users_log_out_time
            FROM users.logging
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.logging(id, users_id, users_authentication_time, users_log_out_time)
            VALUES (?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.logging
            WHERE id = ?
            """;

    @Override
    public boolean update(Logging logging) {
        boolean updateResult = false;
        if (logging != null) {
            if (logging.getUsersId() > 0 & logging.getUsersAuthenticationTime() != null
                & logging.getUsersLogOutTime() != null) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setInt(1, logging.getUsersId());
                    statement.setTimestamp(2, Timestamp.valueOf(logging.getUsersAuthenticationTime()));
                    statement.setTimestamp(3, Timestamp.valueOf(logging.getUsersLogOutTime()));
                    statement.setInt(4, logging.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Logging> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Logging logging = null;

            var result = statement.executeQuery();
            while (result.next()) {
                logging = buildLogging(result);
            }
            return Optional.ofNullable(logging);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Logging> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Logging> loggingList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                loggingList.add(
                        buildLogging(result)
                );
            }
            return loggingList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Logging buildLogging(ResultSet result) throws SQLException {
        return new Logging(result.getInt("id"),
                result.getInt("users_id"),
                result.getTimestamp("users_authentication_time").toLocalDateTime(),
                result.getTimestamp("users_log_out_time").toLocalDateTime());
    }

    @Override
    public Logging save(Logging logging) {
        if (logging != null) {
            if (logging.getUsersId() > 0 & logging.getUsersAuthenticationTime() != null
                & logging.getUsersLogOutTime() != null) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Logging::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setInt(2, logging.getUsersId());
                    statement.setTimestamp(3, Timestamp.valueOf(logging.getUsersAuthenticationTime()));
                    statement.setTimestamp(4, Timestamp.valueOf(logging.getUsersLogOutTime()));
                    statement.executeUpdate();
                    logging.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                logging = new Logging();
            }
        } else {
            logging = new Logging();
        }
        return logging;
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
