package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.GenderDao;
import by.alst.project.jdbc.dto.GenderDto;
import by.alst.project.jdbc.entity.Gender;

import java.util.List;

public class GenderService {

    private static final GenderService INSTANCE = new GenderService();

    private GenderService() {
    }

    public static GenderService getInstance() {
        return INSTANCE;
    }

    private final GenderDao genderDao = GenderDao.getInstance();

    public List<GenderDto> findAll() {
        return genderDao.findAll().stream().map(gender -> new GenderDto(gender.getId(), gender.getGender())).toList();
    }

    public GenderDto findById(Integer genderId) {
        Gender gender = genderDao.findById(genderId).orElse(new Gender());
        return new GenderDto(gender.getId(), gender.getGender());
    }
}
