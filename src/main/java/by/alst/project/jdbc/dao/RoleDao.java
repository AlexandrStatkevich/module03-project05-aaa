package by.alst.project.jdbc.dao;

import by.alst.project.jdbc.entity.Role;
import by.alst.project.jdbc.exception.DaoException;
import by.alst.project.jdbc.utils.ConnectionManager;
import by.alst.project.jdbc.utils.FindId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDao implements Dao<Integer, Role> {

    private static final RoleDao INSTANCE = new RoleDao();

    private RoleDao() {
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE users.role
            SET role = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, role
            FROM users.role
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private final static String FIND_BY_ROLE_NAME_SQL = FIND_ALL_SQL + """
            WHERE role = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO users.role(id, role)
            VALUES (?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM users.role
            WHERE id = ?
            """;

    @Override
    public boolean update(Role role) {
        boolean updateResult = false;
        if (role != null) {
            if (!role.getRole().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(UPDATE_SQL)) {
                    statement.setString(1, role.getRole());
                    statement.setInt(2, role.getId());
                    updateResult = statement.executeUpdate() > 0;
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
        }
        return updateResult;
    }

    @Override
    public Optional<Role> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            Role role = null;

            var result = statement.executeQuery();
            while (result.next()) {
                role = buildRole(result);
            }
            return Optional.ofNullable(role);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Role> findByRoleName(String roleName) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ROLE_NAME_SQL)) {
            statement.setString(1, roleName);
            Role role = null;

            var result = statement.executeQuery();
            while (result.next()) {
                role = buildRole(result);
            }
            return Optional.ofNullable(role);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Role> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Role> roleList = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                roleList.add(
                        buildRole(result)
                );
            }
            return roleList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Role buildRole(ResultSet result) throws SQLException {
        return new Role(result.getInt("id"),
                result.getString("role"));
    }

    @Override
    public Role save(Role role) {
        if (role != null) {
            if (!role.getRole().trim().isEmpty()) {
                try (var connection = ConnectionManager.get();
                     var statement = connection.prepareStatement(SAVE_SQL)) {
                    List<Integer> idList = findAll().stream().map(Role::getId).sorted().toList();
                    int id = FindId.findId(idList);
                    statement.setInt(1, id);
                    statement.setString(2, role.getRole());

                    statement.executeUpdate();
                    role.setId(id);
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            } else {
                role = new Role();
            }
        } else {
            role = new Role();
        }
        return role;
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
