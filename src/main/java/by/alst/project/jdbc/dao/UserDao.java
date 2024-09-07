package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.User;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {

    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.users
            SET email = ?,
                password = ?,
                registration_date = ?,
                role_id = ?,
                gender_id = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, email, password, registration_date, role_id, gender_id
            FROM users.users
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private final static String FIND_BY_EMAIL_SQL = FIND_ALL_SQL + """
            WHERE email = ?
            """;

    private final static String FIND_BY_EMAIL_AND_PASSWORD_SQL = FIND_ALL_SQL + """
            WHERE email = ? AND password = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.users(email, password, registration_date, role_id, gender_id, id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.users
            WHERE id = ?
            """;

    @Override
    public boolean update(User user) {
        boolean updateResult = false;
        if (user != null) {
            if (!user.getEmail().trim().isEmpty() & !user.getPassword().trim().isEmpty()
                & user.getRegistrationDate() != null & user.getRoleId() > 0 & user.getGenderId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    setUserStatement(user, statement);
                    statement.setInt(6, user.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            User user = null;

            var result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            User user = null;

            var result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            User user = null;

            var result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> userList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                userList.add(
                        buildUser(result)
                );
            }
            return userList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUser(ResultSet result) throws SQLException {
        return new User(result.getInt("id"),
                result.getString("email"),
                result.getString("password"),
                result.getTimestamp("registration_date").toLocalDateTime(),
                result.getInt("role_id"),
                result.getInt("gender_id"));
    }

    @Override
    public User save(User user) {
        if (user != null) {
            if (!user.getEmail().trim().isEmpty() & !user.getPassword().trim().isEmpty()
                & user.getRegistrationDate() != null & user.getRoleId() > 0 & user.getGenderId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(User::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(6, id);
                    setUserStatement(user, statement);
                    statement.executeUpdate();
                    user.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                user = new User();
            }
        } else {
            user = new User();
        }
        return user;
    }

    private void setUserStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setTimestamp(3, Timestamp.valueOf(user.getRegistrationDate()));
        statement.setInt(4, user.getRoleId());
        statement.setInt(5, user.getGenderId());
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
