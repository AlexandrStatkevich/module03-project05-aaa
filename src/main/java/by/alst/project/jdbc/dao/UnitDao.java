package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Unit;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnitDao implements Dao<Integer, Unit> {

    private static final UnitDao INSTANCE = new UnitDao();

    private UnitDao() {
    }

    public static UnitDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE data.unit
            SET unit = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, unit
            FROM data.unit
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO data.unit(id, unit)
            VALUES (?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM data.unit
            WHERE id = ?
            """;

    @Override
    public boolean update(Unit unit) {
        boolean updateResult = false;
        if (unit != null) {
            if (!unit.getUnit().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, unit.getUnit());
                    statement.setInt(2, unit.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Unit> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Unit unit = null;

            var result = statement.executeQuery();
            while (result.next()) {
                unit = buildUnit(result);
            }
            return Optional.ofNullable(unit);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Unit> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Unit> unitList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                unitList.add(
                        buildUnit(result)
                );
            }
            return unitList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Unit buildUnit(ResultSet result) throws SQLException {
        return new Unit(result.getInt("id"),
                result.getString("unit"));
    }

    @Override
    public Unit save(Unit unit) {
        if (unit != null) {
            if (!unit.getUnit().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Unit::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, unit.getUnit());

                    statement.executeUpdate();
                    unit.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                unit = new Unit();
            }
        } else {
            unit = new Unit();
        }
        return unit;
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
