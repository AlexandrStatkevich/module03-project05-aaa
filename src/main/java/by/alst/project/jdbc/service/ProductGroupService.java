package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.ProductGroupDao;
import by.alst.project.jdbc.dto.ProductGroupDto;
import by.alst.project.jdbc.entity.ProductGroup;

import java.util.List;

public class ProductGroupService {

    private static final ProductGroupService INSTANCE = new ProductGroupService();

    private ProductGroupService() {
    }

    public static ProductGroupService getInstance() {
        return INSTANCE;
    }

    private final ProductGroupDao productGroupDao = ProductGroupDao.getInstance();

    public List<ProductGroupDto> findAll() {
        return productGroupDao.findAll().stream().map(productGroup -> new ProductGroupDto(productGroup.getId(),
                productGroup.getSubcategoryId(), productGroup.getProductGroup())).toList();
    }

    public List<ProductGroupDto> findAllBySubcategoryId(Integer subcategoryId) {
        return productGroupDao.findAllBySubcategoryId(subcategoryId).stream().map(productGroup ->
                new ProductGroupDto(productGroup.getId(),
                        productGroup.getSubcategoryId(), productGroup.getProductGroup())).toList();
    }

    public ProductGroupDto findById(Integer productGroupId) {
        ProductGroup productGroup = productGroupDao.findById(productGroupId).orElse(new ProductGroup());
        return new ProductGroupDto(productGroup.getId(), productGroup.getSubcategoryId(), productGroup.getProductGroup());
    }
}
