package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Category;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao implements Dao<Integer, Category> {

    private static final CategoryDao INSTANCE = new CategoryDao();

    private CategoryDao() {
    }

    public static CategoryDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE catalog.category
            SET category_name = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, category_name
            FROM catalog.category
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO catalog.category(id, category_name)
            VALUES (?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM catalog.category
            WHERE id = ?
            """;

    @Override
    public boolean update(Category category) {
        boolean updateResult = false;
        if (category != null) {
            if (!category.getCategory().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, category.getCategory());
                    statement.setInt(2, category.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Category category = null;

            var result = statement.executeQuery();
            while (result.next()) {
                category = buildCategory(result);
            }
            return Optional.ofNullable(category);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Category> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Category> categoryList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                categoryList.add(
                        buildCategory(result)
                );
            }
            return categoryList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Category buildCategory(ResultSet result) throws SQLException {
        return new Category(result.getInt("id"),
                result.getString("category_name"));
    }

    @Override
    public Category save(Category category) {
        if (category != null) {
            if (!category.getCategory().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Category::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, category.getCategory());
                    statement.executeUpdate();
                    category.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                category = new Category();
            }
        } else {
            category = new Category();
        }
        return category;
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
