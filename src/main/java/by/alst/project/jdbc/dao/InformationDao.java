package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Information;
import by.alst.project.jdbc.entity.User;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InformationDao implements Dao<Integer, Information> {

    private static final InformationDao INSTANCE = new InformationDao();

    private InformationDao() {
    }

    public static InformationDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.information
            SET first_name = ?,
                second_name = ?,
                last_name = ?,
                address = ?,
                phone = ?
            WHERE users_id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT users_id, first_name, second_name, last_name, address, phone
            FROM users.information
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE users_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.information(users_id, first_name, second_name, last_name, address, phone)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.information
            WHERE users_id = ?
            """;

    @Override
    public boolean update(Information information) {
        boolean updateResult = false;
        if (information != null) {
            if (!information.getFirstName().trim().isEmpty() & !information.getSecondName().trim().isEmpty()
                & !information.getLastName().trim().isEmpty() & !information.getAddress().trim().isEmpty()
                & !information.getPhone().trim().isEmpty() & information.getUsersId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, information.getFirstName());
                    statement.setString(2, information.getSecondName());
                    statement.setString(3, information.getLastName());
                    statement.setString(4, information.getAddress());
                    statement.setString(5, information.getPhone());
                    statement.setInt(6, information.getUsersId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Information> findById(Integer usersId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, usersId);
            Information information = null;

            var result = statement.executeQuery();
            while (result.next()) {
                information = buildInformation(result);
            }
            return Optional.ofNullable(information);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Information> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Information> informationList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                informationList.add(
                        buildInformation(result)
                );
            }
            return informationList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Information buildInformation(ResultSet result) throws SQLException {
        return new Information(result.getInt("users_id"),
                result.getString("first_name"),
                result.getString("second_name"),
                result.getString("last_name"),
                result.getString("address"),
                result.getString("phone"));
    }


    @Override
    public Information save(Information information) {
        var userDao = UserDao.getInstance();
        if (information != null
            && !findAll().stream().map(Information::getUsersId).toList().contains(information.getUsersId())
            && userDao.findAll().stream().map(User::getId).toList().contains(information.getUsersId())) {
            if (!information.getFirstName().trim().isEmpty() & !information.getSecondName().trim().isEmpty()
                & !information.getLastName().trim().isEmpty() & !information.getAddress().trim().isEmpty()
                & !information.getPhone().trim().isEmpty() & information.getUsersId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    statement.setInt(1, information.getUsersId());
                    statement.setString(2, information.getFirstName());
                    statement.setString(3, information.getSecondName());
                    statement.setString(4, information.getLastName());
                    statement.setString(5, information.getAddress());
                    statement.setString(6, information.getPhone());
                    statement.executeUpdate();
                    information = findById(information.getUsersId()).orElse(new Information());
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                information = new Information();
            }
        } else {
            information = new Information();
        }
        return information;
    }

    @Override
    public boolean delete(Integer usersId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, usersId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
