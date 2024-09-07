package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Subcategory;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubcategoryDao implements Dao<Integer, Subcategory> {

    private static final SubcategoryDao INSTANCE = new SubcategoryDao();

    private SubcategoryDao() {
    }

    public static SubcategoryDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE catalog.subcategory
            SET category_id = ?,
                subcategory = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, category_id, subcategory.subcategory
            FROM catalog.subcategory
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_ALL_BY_CATEGORY_ID_SQL = FIND_ALL_SQL + """
            WHERE category_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO catalog.subcategory(id, category_id, subcategory)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM catalog.subcategory
            WHERE id = ?
            """;

    @Override
    public boolean update(Subcategory subcategory) {
        boolean updateResult = false;
        if (subcategory != null) {
            if (!subcategory.getSubcategory().trim().isEmpty() & subcategory.getCategoryId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setInt(1, subcategory.getCategoryId());
                    statement.setString(2, subcategory.getSubcategory());
                    statement.setInt(3, subcategory.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Subcategory> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Subcategory subcategory = null;

            var result = statement.executeQuery();
            while (result.next()) {
                subcategory = buildSubcategory(result);
            }
            return Optional.ofNullable(subcategory);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Subcategory> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Subcategory> subcategoryList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                subcategoryList.add(
                        buildSubcategory(result)
                );
            }
            return subcategoryList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Subcategory> findAllByCategoryId(Integer categoryId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_CATEGORY_ID_SQL)) {
            statement.setInt(1, categoryId);
            List<Subcategory> subcategoryList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                subcategoryList.add(
                        buildSubcategory(result)
                );
            }
            return subcategoryList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Subcategory buildSubcategory(ResultSet result) throws SQLException {
        return new Subcategory(result.getInt("id"),
                result.getInt("category_id"),
                result.getString("subcategory"));
    }

    @Override
    public Subcategory save(Subcategory subcategory) {
        if (subcategory != null) {
            if (!subcategory.getSubcategory().trim().isEmpty() & subcategory.getCategoryId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Subcategory::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setInt(2, subcategory.getCategoryId());
                    statement.setString(3, subcategory.getSubcategory());

                    statement.executeUpdate();
                    subcategory.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                subcategory = new Subcategory();
            }
        } else {
            subcategory = new Subcategory();
        }
        return subcategory;
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
