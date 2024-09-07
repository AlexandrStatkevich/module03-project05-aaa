package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.UnitDao;
import by.alst.project.jdbc.dto.UnitDto;
import by.alst.project.jdbc.entity.Unit;

import java.util.List;

public class UnitService {

    private static final UnitService INSTANCE = new UnitService();

    private UnitService() {
    }

    public static UnitService getInstance() {
        return INSTANCE;
    }

    private final UnitDao unitDao = UnitDao.getInstance();

    public List<UnitDto> findAll() {
        return unitDao.findAll().stream().map(unit -> new UnitDto(unit.getId(), unit.getUnit())).toList();
    }

    public UnitDto findById(Integer unitId) {
        Unit unit = unitDao.findById(unitId).orElse(new Unit());
        return new UnitDto(unit.getId(), unit.getUnit());
    }
}
