package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.CountryDao;
import by.alst.project.jdbc.dto.CountryDto;
import by.alst.project.jdbc.entity.Country;

import java.util.List;

public class CountryService {

    private static final CountryService INSTANCE = new CountryService();

    private CountryService() {
    }

    public static CountryService getInstance() {
        return INSTANCE;
    }

    private final CountryDao categoryDao = CountryDao.getInstance();

    public List<CountryDto> findAll() {
        return categoryDao.findAll().stream().map(country -> new CountryDto(country.getId(),
                country.getCountry())).toList();
    }

    public CountryDto findById(Integer countryId) {
        Country country = categoryDao.findById(countryId).orElse(new Country());
        return new CountryDto(country.getId(), country.getCountry());
    }
}
