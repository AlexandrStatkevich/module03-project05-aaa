package by.alst.project.jdbc.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class UrlPath {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/registration";
    public static final String LOGOUT = "/logout";
    public static final String CATEGORY = "/category";
    public static final String SUBCATEGORY = "/subcategory";
    public static final String PRODUCT_GROUP = "/productGroup";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT = "/product";
    public static final String USERS = "/users";
    public static final String USER = "/user";
    public static final String INFORMATION = "/information";
    public static final String ORDER = "/order";
    public static final String CHECKOUT = "/checkout";

    public static final Set<String> publicUrlPathSet = Set.of(LOGIN, REGISTRATION, CATEGORY, SUBCATEGORY, PRODUCT_GROUP,
            PRODUCTS, PRODUCT);
}
