package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.ProductGroup;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductGroupDao implements Dao<Integer, ProductGroup> {

    private static final ProductGroupDao INSTANCE = new ProductGroupDao();

    private ProductGroupDao() {
    }

    public static ProductGroupDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE catalog.product_group
            SET subcategory_id = ?,
                product_group = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, subcategory_id, product_group
            FROM catalog.product_group
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_ALL_BY_SUBCATEGORY_ID_SQL = FIND_ALL_SQL + """
            WHERE subcategory_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO catalog.product_group(id, subcategory_id, product_group)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM catalog.product_group
            WHERE id = ?
            """;


    @Override
    public boolean update(ProductGroup productGroup) {
        boolean updateResult = false;
        if (productGroup != null) {
            if (!productGroup.getProductGroup().trim().isEmpty() & productGroup.getSubcategoryId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setInt(1, productGroup.getSubcategoryId());
                    statement.setString(2, productGroup.getProductGroup());
                    statement.setInt(3, productGroup.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<ProductGroup> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            ProductGroup productGroup = null;

            var result = statement.executeQuery();
            while (result.next()) {
                productGroup = buildProductGroup(result);
            }
            return Optional.ofNullable(productGroup);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<ProductGroup> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<ProductGroup> productGroupList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                productGroupList.add(
                        buildProductGroup(result)
                );
            }
            return productGroupList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<ProductGroup> findAllBySubcategoryId(Integer subcategoryId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_SUBCATEGORY_ID_SQL)) {
            statement.setInt(1, subcategoryId);
            List<ProductGroup> productGroupList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                productGroupList.add(
                        buildProductGroup(result)
                );
            }
            return productGroupList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ProductGroup buildProductGroup(ResultSet result) throws SQLException {
        return new ProductGroup(result.getInt("id"),
                result.getInt("subcategory_id"),
                result.getString("product_group"));
    }

    @Override
    public ProductGroup save(ProductGroup productGroup) {
        if (productGroup != null) {
            if (!productGroup.getProductGroup().trim().isEmpty() & productGroup.getSubcategoryId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(ProductGroup::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setInt(2, productGroup.getSubcategoryId());
                    statement.setString(3, productGroup.getProductGroup());

                    statement.executeUpdate();
                    productGroup.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                productGroup = new ProductGroup();
            }
        } else {
            productGroup = new ProductGroup();
        }
        return productGroup;
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
