package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.RoleDao;
import by.alst.project.jdbc.dto.RoleDto;
import by.alst.project.jdbc.entity.Role;

import java.util.List;

public class RoleService {

    private static final RoleService INSTANCE = new RoleService();

    private RoleService() {
    }

    public static RoleService getInstance() {
        return INSTANCE;
    }

    private final RoleDao roleDao = RoleDao.getInstance();

    public List<RoleDto> findAll() {
        return roleDao.findAll().stream().map(role -> new RoleDto(role.getId(), role.getRole())).toList();
    }

    public RoleDto findById(Integer roleId) {
        Role role = roleDao.findById(roleId).orElse(new Role());
        return new RoleDto(role.getId(), role.getRole());
    }
}
