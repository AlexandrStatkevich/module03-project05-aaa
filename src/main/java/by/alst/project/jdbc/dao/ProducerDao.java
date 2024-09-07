package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Producer;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerDao implements Dao<Integer, Producer> {

    private static final ProducerDao INSTANCE = new ProducerDao();

    private ProducerDao() {
    }

    public static ProducerDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE data.producer
            SET producer = ?,
                address = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, producer, address
            FROM data.producer
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO data.producer(id, producer, address)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM data.producer
            WHERE id = ?
            """;

    @Override
    public boolean update(Producer producer) {
        boolean updateResult = false;
        if (producer != null) {
            if (!producer.getProducerName().trim().isEmpty() & !producer.getProducerAddress().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, producer.getProducerName());
                    statement.setString(2, producer.getProducerAddress());
                    statement.setInt(3, producer.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Producer> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Producer producer = null;

            var result = statement.executeQuery();
            while (result.next()) {
                producer = buildProducer(result);
            }
            return Optional.ofNullable(producer);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Producer> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Producer> producerList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                producerList.add(
                        buildProducer(result)
                );
            }
            return producerList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Producer buildProducer(ResultSet result) throws SQLException {
        return new Producer(result.getInt("id"),
                result.getString("producer"),
                result.getString("address"));
    }

    @Override
    public Producer save(Producer producer) {
        if (producer != null) {
            if (!producer.getProducerName().trim().isEmpty() & !producer.getProducerAddress().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Producer::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, producer.getProducerName());
                    statement.setString(3, producer.getProducerAddress());

                    statement.executeUpdate();
                    producer.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                producer = new Producer();
            }
        } else {
            producer = new Producer();
        }
        return producer;
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
