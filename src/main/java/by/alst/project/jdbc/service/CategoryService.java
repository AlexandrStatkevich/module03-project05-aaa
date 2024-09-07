package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.CategoryDao;
import by.alst.project.jdbc.dto.CategoryDto;
import by.alst.project.jdbc.entity.Category;

import java.util.List;

public class CategoryService {

    private static final CategoryService INSTANCE = new CategoryService();

    private CategoryService() {
    }

    public static CategoryService getInstance() {
        return INSTANCE;
    }

    private final CategoryDao categoryDao = CategoryDao.getInstance();

    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(category -> new CategoryDto(category.getId(),
                category.getCategory())).toList();
    }

    public CategoryDto findById(Integer categoryId) {
        Category category = categoryDao.findById(categoryId).orElse(new Category());
        return new CategoryDto(category.getId(), category.getCategory());
    }
}
