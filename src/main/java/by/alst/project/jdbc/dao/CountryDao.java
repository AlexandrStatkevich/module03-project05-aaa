package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Country;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryDao implements Dao<Integer, Country> {

    private static final CountryDao INSTANCE = new CountryDao();

    private CountryDao() {
    }

    public static CountryDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE data.country
            SET country = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, country
            FROM data.country
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO data.country(id, country)
            VALUES (?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM data.country
            WHERE id = ?
            """;


    @Override
    public boolean update(Country country) {
        boolean updateResult = false;
        if (country != null) {
            if (!country.getCountry().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, country.getCountry());
                    statement.setInt(2, country.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Country> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Country country = null;

            var result = statement.executeQuery();
            while (result.next()) {
                country = buildCountry(result);
            }
            return Optional.ofNullable(country);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Country> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Country> countryList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                countryList.add(
                        buildCountry(result)
                );
            }
            return countryList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Country buildCountry(ResultSet result) throws SQLException {
        return new Country(result.getInt("id"),
                result.getString("country"));
    }

    @Override
    public Country save(Country country) {
        if (country != null) {
            if (!country.getCountry().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Country::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, country.getCountry());

                    statement.executeUpdate();
                    country.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                country = new Country();
            }
        } else {
            country = new Country();
        }
        return country;
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
