package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.ProductDao;
import by.alst.project.jdbc.dto.ProductDto;
import by.alst.project.jdbc.entity.Product;

import java.util.List;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();

    private ProductService() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    private final ProductDao productDao = ProductDao.getInstance();

    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(product -> new ProductDto(product.getId(),
                product.getProductGroupId(), product.getProductName(), product.getProductDescription(),
                product.getProductProducerId(), product.getCountryOriginId(), product.getProductUnitId(),
                product.getProductArrivalDate(), product.getProductCost(), product.getProductAmount(),
                product.getProductCardDateCreation())).toList();
    }

    public List<ProductDto> findAllByProductGroupId(Integer productGroupId) {
        return productDao.findAllByProductGroupId(productGroupId).stream().map(product -> new ProductDto(product.getId(),
                product.getProductGroupId(), product.getProductName(), product.getProductDescription(),
                product.getProductProducerId(), product.getCountryOriginId(), product.getProductUnitId(),
                product.getProductArrivalDate(), product.getProductCost(), product.getProductAmount(),
                product.getProductCardDateCreation())).toList();
    }

    public ProductDto findById(Integer productId) {
        Product product = productDao.findById(productId).orElse(new Product());
        return new ProductDto(product.getId(),
                product.getProductGroupId(), product.getProductName(), product.getProductDescription(),
                product.getProductProducerId(), product.getCountryOriginId(), product.getProductUnitId(),
                product.getProductArrivalDate(), product.getProductCost(), product.getProductAmount(),
                product.getProductCardDateCreation());
    }
}
