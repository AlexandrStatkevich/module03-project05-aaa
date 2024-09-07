package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.SubcategoryDao;
import by.alst.project.jdbc.dto.SubcategoryDto;
import by.alst.project.jdbc.entity.Subcategory;

import java.util.List;

public class SubcategoryService {

    private static final SubcategoryService INSTANCE = new SubcategoryService();

    private SubcategoryService() {
    }

    public static SubcategoryService getInstance() {
        return INSTANCE;
    }

    private final SubcategoryDao subcategoryDao = SubcategoryDao.getInstance();

    public List<SubcategoryDto> findAll() {
        return subcategoryDao.findAll().stream().map(subcategory -> new SubcategoryDto(subcategory.getId(),
                subcategory.getCategoryId(), subcategory.getSubcategory())).toList();
    }

    public List<SubcategoryDto> findAllByCategoryId(Integer categoryId) {
        return subcategoryDao.findAllByCategoryId(categoryId).stream().map(subcategory
                -> new SubcategoryDto(subcategory.getId(), subcategory.getCategoryId(),
                subcategory.getSubcategory())).toList();
    }

    public SubcategoryDto findById(Integer subcategoryId) {
        Subcategory subcategory = subcategoryDao.findById(subcategoryId).orElse(new Subcategory());
        return new SubcategoryDto(subcategory.getId(), subcategory.getCategoryId(), subcategory.getSubcategory());
    }
}
