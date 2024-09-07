package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Product;
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

public class ProductDao implements Dao<Integer, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE catalog.product
            SET product_group_id = ?,
                product_name = ?,
                product_description = ?,
                product_producer_id = ?,
                country_origin_id = ?,
                product_unit_id = ?,
                product_arrival_date = ?,
                product_cost = ?,
                product_amount = ?,
                product_card_date_creation = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, product_group_id, product_name, product_description, product_producer_id, country_origin_id,
            product_unit_id, product_arrival_date, product_cost, product_amount, product_card_date_creation
            FROM catalog.product
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_ALL_BY_PRODUCT_GROUP_ID_SQL = FIND_ALL_SQL + """
            WHERE product_group_id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO catalog.product(product_group_id, product_name, product_description, product_producer_id,
            country_origin_id, product_unit_id, product_arrival_date, product_cost, product_amount,
            product_card_date_creation, id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM catalog.product
            WHERE id = ?
            """;

    @Override
    public boolean update(Product product) {
        boolean updateResult = false;
        if (product != null) {
            if (!product.getProductName().trim().isEmpty() & product.getProductGroupId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    setProductStatement(product, statement);
                    statement.setInt(11, product.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Product product = null;

            var result = statement.executeQuery();
            while (result.next()) {
                product = buildProduct(result);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Product> productList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                productList.add(
                        buildProduct(result)
                );
            }
            return productList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Product> findAllByProductGroupId(Integer productGroupId) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_BY_PRODUCT_GROUP_ID_SQL)) {
            statement.setInt(1, productGroupId);
            List<Product> productList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                productList.add(
                        buildProduct(result)
                );
            }
            return productList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Product buildProduct(ResultSet result) throws SQLException {
        return new Product(result.getInt("id"),
                result.getInt("product_group_id"),
                result.getString("product_name"),
                result.getString("product_description"),
                result.getInt("product_producer_id"),
                result.getInt("country_origin_id"),
                result.getInt("product_unit_id"),
                result.getTimestamp("product_arrival_date").toLocalDateTime(),
                result.getBigDecimal("product_cost"),
                result.getBigDecimal("product_amount"),
                result.getTimestamp("product_card_date_creation").toLocalDateTime());
    }

    @Override
    public Product save(Product product) {
        if (product != null) {
            if (!product.getProductName().trim().isEmpty() & product.getProductGroupId() > 0) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Product::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(11, id);
                    setProductStatement(product, statement);

                    statement.executeUpdate();
                    product.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                product = new Product();
            }
        } else {
            product = new Product();
        }
        return product;
    }

    private void setProductStatement(Product product, PreparedStatement statement) throws SQLException {
        statement.setInt(1, product.getProductGroupId());
        statement.setString(2, product.getProductName());
        statement.setString(3, product.getProductDescription());
        statement.setInt(4, product.getProductProducerId());
        statement.setInt(5, product.getCountryOriginId());
        statement.setInt(6, product.getProductUnitId());
        statement.setTimestamp(7, Timestamp.valueOf(product.getProductArrivalDate()));
        statement.setBigDecimal(8, product.getProductCost());
        statement.setBigDecimal(9, product.getProductAmount());
        statement.setTimestamp(10, Timestamp.valueOf(product.getProductCardDateCreation()));
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
