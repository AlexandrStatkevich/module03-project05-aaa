package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Gender;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenderDao implements Dao<Integer, Gender> {

    private static final GenderDao INSTANCE = new GenderDao();

    private GenderDao() {
    }

    public static GenderDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.gender
            SET gender = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, gender
            FROM users.gender
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_BY_GENDER_NAME_SQL = FIND_ALL_SQL + """
            WHERE gender.gender = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.gender(id, gender)
            VALUES (?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.gender
            WHERE id = ?
            """;

    @Override
    public boolean update(Gender gender) {
        boolean updateResult = false;
        if (gender != null) {
            if (!gender.getGender().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, gender.getGender());
                    statement.setInt(2, gender.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Gender> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Gender gender = null;

            var result = statement.executeQuery();
            while (result.next()) {
                gender = buildGender(result);
            }
            return Optional.ofNullable(gender);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Gender> findByGenderName(String genderName) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_GENDER_NAME_SQL)) {
            statement.setString(1, genderName);
            Gender gender = null;

            var result = statement.executeQuery();
            while (result.next()) {
                gender = buildGender(result);
            }
            return Optional.ofNullable(gender);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Gender> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Gender> genderList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                genderList.add(
                        buildGender(result)
                );
            }
            return genderList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Gender buildGender(ResultSet result) throws SQLException {
        return new Gender(result.getInt("id"),
                result.getString("gender"));
    }

    @Override
    public Gender save(Gender gender) {
        if (gender != null) {
            if (!gender.getGender().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Gender::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, gender.getGender());

                    statement.executeUpdate();
                    gender.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                gender = new Gender();
            }
        } else {
            gender = new Gender();
        }
        return gender;
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
