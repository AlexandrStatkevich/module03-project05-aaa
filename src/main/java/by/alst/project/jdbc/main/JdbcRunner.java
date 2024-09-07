package by.alst.project.jdbc.main;

import by.alst.project.jdbc.dao.CategoryDao;
import by.alst.project.jdbc.dao.InformationDao;
import by.alst.project.jdbc.dao.UserDao;
import by.alst.project.jdbc.dto.InformationDto;
import by.alst.project.jdbc.entity.Category;
import by.alst.project.jdbc.entity.Information;
import by.alst.project.jdbc.entity.User;
import by.alst.project.jdbc.service.InformationService;
import by.alst.project.jdbc.service.UserService;

import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) {

//  Table catalog.category

        var categoryDao = CategoryDao.getInstance();
        Category category1 = new Category(100, "Мебель");
        Category category2 = new Category(56, "Товары для животных");
        Category category3 = new Category(33, "Дача, сад");
        Category category4 = new Category(17, "Красота, здоровье");
        System.out.println(categoryDao.save(category1));
        System.out.println(categoryDao.save(category2));
        System.out.println(categoryDao.save(category3));
        category1.setCategory("Новогодние товары");
        System.out.println(categoryDao.update(category1));
        System.out.println(categoryDao.findById(category1.getId()).orElse(new Category()));
        System.out.println(categoryDao.save(category4));
        category2.setCategory("");
        System.out.println(categoryDao.update(category2));
        System.out.println(categoryDao.delete(category1.getId()));
        System.out.println(categoryDao.findById(category1.getId()).orElse(new Category()));
        categoryDao.findAll().forEach(System.out::println);
        System.out.println(categoryDao.update(null));
        System.out.println(categoryDao.findById(-5).orElse(new Category()));
        System.out.println(categoryDao.save(null));
        System.out.println(categoryDao.save(new Category(100, "")));
        System.out.println(categoryDao.delete(-12));
        System.out.println(categoryDao.delete(category1.getId()));
        System.out.println(categoryDao.delete(category2.getId()));
        System.out.println(categoryDao.delete(category3.getId()));
        System.out.println(categoryDao.delete(category4.getId()));


//  Table catalog.subcategory

//        var subcategoryDao = SubcategoryDao.getInstance();
//        Subcategory subcategory1 = new Subcategory(100, 1, "Сахар, соль, специи");
//        Subcategory subcategory2 = new Subcategory(100, 1, "Вино");
//        Subcategory subcategory3 = new Subcategory(100, 1, "Заморозка");
//        Subcategory subcategory4 = new Subcategory(100, 1, "Специи");
//        System.out.println(subcategoryDao.save(subcategory1));
//        System.out.println(subcategoryDao.save(subcategory2));
//        System.out.println(subcategoryDao.save(subcategory3));
//        System.out.println(subcategoryDao.delete(subcategory1.getId()));
//        System.out.println(subcategoryDao.save(subcategory4));
//        subcategory1.setSubcategory("Консервированная продукция");
//        System.out.println(subcategoryDao.update(subcategory1));
//        System.out.println(subcategoryDao.findById(subcategory1.getId()).orElse(new Subcategory()));
//        subcategory1.setSubcategory("   ");
//        System.out.println(subcategoryDao.update(subcategory1));
//        System.out.println(subcategoryDao.delete(subcategory1.getId()));
//        System.out.println(subcategoryDao.findById(subcategory1.getId()).orElse(new Subcategory()));
//        subcategoryDao.findAll().forEach(System.out::println);
//        System.out.println(subcategoryDao.update(null));
//        System.out.println(subcategoryDao.findById(-5).orElse(new Subcategory()));
//        System.out.println(subcategoryDao.save(null));
//        System.out.println(subcategoryDao.save(new Subcategory(100, 1, "   ")));
//        System.out.println(subcategoryDao.delete(-12));
//        System.out.println(subcategoryDao.delete(subcategory1.getId()));
//        System.out.println(subcategoryDao.delete(subcategory2.getId()));
//        System.out.println(subcategoryDao.delete(subcategory3.getId()));
//        System.out.println(subcategoryDao.delete(subcategory4.getId()));


//  Table catalog.product_group

//        var productGroupDao = ProductGroupDao.getInstance();
//        ProductGroup productGroup1 = new ProductGroup(100, 3, "Мороженное");
//        ProductGroup productGroup2 = new ProductGroup(100, 3, "Курага");
//        ProductGroup productGroup3 = new ProductGroup(100, 3, "Изюм");
//        ProductGroup productGroup4 = new ProductGroup(100, 3, "Фундук");
//        System.out.println(productGroupDao.save(productGroup1));
//        System.out.println(productGroupDao.save(productGroup2));
//        System.out.println(productGroupDao.save(productGroup3));
//        System.out.println(productGroupDao.delete(productGroup1.getId()));
//        System.out.println(productGroupDao.save(productGroup4));
//        productGroup1.setProductGroup("Чернослив");
//        System.out.println(productGroupDao.update(productGroup1));
//        System.out.println(productGroupDao.findById(productGroup1.getId()).orElse(new ProductGroup()));
//        productGroup1.setProductGroup("   ");
//        System.out.println(productGroupDao.update(productGroup1));
//        System.out.println(productGroupDao.delete(productGroup1.getId()));
//        System.out.println(productGroupDao.findById(productGroup1.getId()).orElse(new ProductGroup()));
//        productGroupDao.findAll().forEach(System.out::println);
//        System.out.println(productGroupDao.update(null));
//        System.out.println(productGroupDao.findById(-5).orElse(new ProductGroup()));
//        System.out.println(productGroupDao.save(null));
//        System.out.println(productGroupDao.save(new ProductGroup(100, 1, "   ")));
//        System.out.println(productGroupDao.delete(-12));
//        System.out.println(productGroupDao.delete(productGroup1.getId()));
//        System.out.println(productGroupDao.delete(productGroup2.getId()));
//        System.out.println(productGroupDao.delete(productGroup3.getId()));
//        System.out.println(productGroupDao.delete(productGroup4.getId()));


//  Table catalog.product

//        var productGroupDao = ProductDao.getInstance();
//        Product product1 = new Product(1, 1, "Мороженное 1", "Пачка",
//                2, 1, 1, LocalDateTime.of(2024, 6, 2, 10, 15),
//                BigDecimal.valueOf(10.13), BigDecimal.valueOf(20), LocalDateTime.of(2020, 1, 9, 17, 20));
//        Product product2 = new Product(1, 1, "Мороженное 2", "Стакан с крышкой",
//                4, 2, 1, LocalDateTime.of(2024, 6, 3, 17, 15),
//                BigDecimal.valueOf(12.15), BigDecimal.valueOf(7), LocalDateTime.of(2021, 1, 9, 17, 20));
//        Product product3 = new Product(1, 1, "Мороженное 3", "Рожок",
//                6, 1, 1, LocalDateTime.of(2024, 6, 1, 12, 35),
//                BigDecimal.valueOf(3.13), BigDecimal.valueOf(8), LocalDateTime.of(2020, 1, 9, 17, 00));
//        Product product4 = new Product(1, 1, "Мороженное 4", "Брикет",
//                3, 3, 1, LocalDateTime.of(2024, 5, 25, 10, 35),
//                BigDecimal.valueOf(7.16), BigDecimal.valueOf(3), LocalDateTime.of(2023, 4, 12, 10, 40));
//        System.out.println(productGroupDao.save(product1));
//        System.out.println(productGroupDao.save(product2));
//        System.out.println(productGroupDao.save(product3));
//        System.out.println(productGroupDao.delete(product1.getId()));
//        System.out.println(productGroupDao.save(product4));
//        product1.setProductName("Чернослив крупный");
//        product1.setProductCost(BigDecimal.valueOf(25));
//        System.out.println(productGroupDao.update(product1));
//        System.out.println(productGroupDao.findById(product1.getId()).orElse(new Product()));
//        product1.setProductName("   ");
//        System.out.println(productGroupDao.update(product1));
//        System.out.println(productGroupDao.delete(product1.getId()));
//        System.out.println(productGroupDao.findById(product1.getId()).orElse(new Product()));
//        productGroupDao.findAll().forEach(System.out::println);
//        System.out.println(productGroupDao.update(null));
//        System.out.println(productGroupDao.findById(-5).orElse(new Product()));
//        System.out.println(productGroupDao.save(null));
//        System.out.println(productGroupDao.save(new Product(1, 1, "    ", "Брикет",
//                3, 3, 1, LocalDateTime.of(2024, 5, 25, 10, 35),
//                BigDecimal.valueOf(7.16), BigDecimal.valueOf(3), LocalDateTime.of(2023, 4, 12, 10, 40))));
//        System.out.println(productGroupDao.delete(-12));
//        System.out.println(productGroupDao.delete(product1.getId()));
//        System.out.println(productGroupDao.delete(product2.getId()));
//        System.out.println(productGroupDao.delete(product3.getId()));
//        System.out.println(productGroupDao.delete(product4.getId()));


//  Table data.country

//        var countryDao = CountryDao.getInstance();
//        Country country1 = new Country(100, "Япония");
//        Country country2 = new Country(100, "Аргентина");
//        Country country3 = new Country(100, "Венгрия");
//        Country country4 = new Country(100, "Гондурас");
//        System.out.println(countryDao.save(country1));
//        System.out.println(countryDao.save(country2));
//        System.out.println(countryDao.save(country3));
//        System.out.println(countryDao.delete(country1.getId()));
//        System.out.println(countryDao.save(country4));
//        country1.setCountry("Исландия");
//        System.out.println(countryDao.update(country1));
//        System.out.println(countryDao.findById(country1.getId()).orElse(new Country()));
//        country1.setCountry("   ");
//        System.out.println(countryDao.update(country1));
//        System.out.println(countryDao.delete(country1.getId()));
//        System.out.println(countryDao.findById(country1.getId()).orElse(new Country()));
//        countryDao.findAll().forEach(System.out::println);
//        System.out.println(countryDao.update(null));
//        System.out.println(countryDao.findById(-5).orElse(new Country()));
//        System.out.println(countryDao.save(null));
//        System.out.println(countryDao.save(new Country(1, "    ")));
//        System.out.println(countryDao.delete(-12));
//        System.out.println(countryDao.delete(country1.getId()));
//        System.out.println(countryDao.delete(country2.getId()));
//        System.out.println(countryDao.delete(country3.getId()));
//        System.out.println(countryDao.delete(country4.getId()));


//  Table data.producer

//        var producerDao = ProducerDao.getInstance();
//        Producer producer1 = new Producer(100, "КУП «Витебский кондитерский комбинат «Витьба»",
//                "210038 г. Витебск, ул. Короткевича, 3, Республика Беларусь");
//        Producer producer2 = new Producer(100, "ТОО «Корпорация Караганды-Нан»",
//                "г. Караганда, ул. Камская, 91, Казахстан");
//        Producer producer3 = new Producer(100, "ООО «Крупяной Завод»",
//                "Алтайский край, г.Барнаул, ул.Трактовая, 41/3, Россия");
//        Producer producer4 = new Producer(100, "ООО «Елизавета+»",
//                "Смоленская обл., г. Смоленск, ул. Губенко, д. 26, Россия");
//        System.out.println(producerDao.save(producer1));
//        System.out.println(producerDao.save(producer2));
//        System.out.println(producerDao.save(producer3));
//        System.out.println(producerDao.delete(producer1.getId()));
//        System.out.println(producerDao.save(producer4));
//        producer1.setProducerName("ООО «ЗападБытХим»");
//        producer1.setProducerAddress("230003, г. Гродно, ул. Аульская, 38, Республика Беларусь");
//        System.out.println(producerDao.update(producer1));
//        System.out.println(producerDao.findById(producer1.getId()).orElse(new Producer()));
//        producer1.setProducerName("   ");
//        System.out.println(producerDao.update(producer1));
//        System.out.println(producerDao.delete(producer1.getId()));
//        System.out.println(producerDao.findById(producer1.getId()).orElse(new Producer()));
//        producerDao.findAll().forEach(System.out::println);
//        System.out.println(producerDao.update(null));
//        System.out.println(producerDao.findById(-5).orElse(new Producer()));
//        System.out.println(producerDao.save(null));
//        System.out.println(producerDao.save(new Producer(1, "    ",
//                "225406, г. Барановичи, ул. 50 лет БССР, 21, Республика")));
//        System.out.println(producerDao.delete(-12));
//        System.out.println(producerDao.delete(producer1.getId()));
//        System.out.println(producerDao.delete(producer2.getId()));
//        System.out.println(producerDao.delete(producer3.getId()));
//        System.out.println(producerDao.delete(producer4.getId()));


//  Table data.unit

//        var unitDao = UnitDao.getInstance();
//        Unit unit1 = new Unit(100, "компл");
//        Unit unit2 = new Unit(100, "100 упак");
//        Unit unit3 = new Unit(100, "100 шт");
//        Unit unit4 = new Unit(100, "пог. м");
//        System.out.println(unitDao.save(unit1));
//        System.out.println(unitDao.save(unit2));
//        System.out.println(unitDao.save(unit3));
//        System.out.println(unitDao.delete(unit1.getId()));
//        System.out.println(unitDao.save(unit4));
//        unit1.setUnit("кор");
//        System.out.println(unitDao.update(unit1));
//        System.out.println(unitDao.findById(unit1.getId()).orElse(new Unit()));
//        unit1.setUnit("   ");
//        System.out.println(unitDao.update(unit1));
//        System.out.println(unitDao.delete(unit1.getId()));
//        System.out.println(unitDao.findById(unit1.getId()).orElse(new Unit()));
//        unitDao.findAll().forEach(System.out::println);
//        System.out.println(unitDao.update(null));
//        System.out.println(unitDao.findById(-5).orElse(new Unit()));
//        System.out.println(unitDao.save(null));
//        System.out.println(unitDao.save(new Unit(1, "    ")));
//        System.out.println(unitDao.delete(-12));
//        System.out.println(unitDao.delete(unit1.getId()));
//        System.out.println(unitDao.delete(unit2.getId()));
//        System.out.println(unitDao.delete(unit3.getId()));
//        System.out.println(unitDao.delete(unit4.getId()));


//  Table users.gender

//        var genderDao = GenderDao.getInstance();
//        Gender gender1 = new Gender(100, "GENDER1");
//        Gender gender2 = new Gender(100, "GENDER2");
//        Gender gender3 = new Gender(100, "GENDER3");
//        Gender gender4 = new Gender(100, "GENDER4");
//        System.out.println(genderDao.save(gender1));
//        System.out.println(genderDao.save(gender2));
//        System.out.println(genderDao.save(gender3));
//        System.out.println(genderDao.delete(gender1.getId()));
//        System.out.println(genderDao.save(gender4));
//        gender1.setGender("GENDER5");
//        System.out.println(genderDao.update(gender1));
//        System.out.println(genderDao.findById(gender1.getId()).orElse(new Gender()));
//        gender1.setGender("   ");
//        System.out.println(genderDao.update(gender1));
//        System.out.println(genderDao.delete(gender1.getId()));
//        System.out.println(genderDao.findById(gender1.getId()).orElse(new Gender()));
//        genderDao.findAll().forEach(System.out::println);
//        System.out.println(genderDao.update(null));
//        System.out.println(genderDao.findById(-5).orElse(new Gender()));
//        System.out.println(genderDao.save(null));
//        System.out.println(genderDao.save(new Gender(1, "    ")));
//        System.out.println(genderDao.delete(-12));
//        System.out.println(genderDao.delete(gender1.getId()));
//        System.out.println(genderDao.delete(gender2.getId()));
//        System.out.println(genderDao.delete(gender3.getId()));
//        System.out.println(genderDao.delete(gender4.getId()));


//  Table users.role

//        var roleDao = RoleDao.getInstance();
//        Role role1 = new Role(100, "ROLE1");
//        Role role2 = new Role(100, "ROLE2");
//        Role role3 = new Role(100, "ROLE3");
//        Role role4 = new Role(100, "ROLE4");
//        System.out.println(roleDao.save(role1));
//        System.out.println(roleDao.save(role2));
//        System.out.println(roleDao.save(role3));
//        System.out.println(roleDao.delete(role1.getId()));
//        System.out.println(roleDao.save(role4));
//        role1.setRole("ROLE5");
//        System.out.println(roleDao.update(role1));
//        System.out.println(roleDao.findById(role1.getId()).orElse(new Role()));
//        role1.setRole("   ");
//        System.out.println(roleDao.update(role1));
//        System.out.println(roleDao.delete(role1.getId()));
//        System.out.println(roleDao.findById(role1.getId()).orElse(new Role()));
//        roleDao.findAll().forEach(System.out::println);
//        System.out.println(roleDao.update(null));
//        System.out.println(roleDao.findById(-5).orElse(new Role()));
//        System.out.println(roleDao.save(null));
//        System.out.println(roleDao.save(new Role(1, "    ")));
//        System.out.println(roleDao.delete(-12));
//        System.out.println(roleDao.delete(role1.getId()));
//        System.out.println(roleDao.delete(role2.getId()));
//        System.out.println(roleDao.delete(role3.getId()));
//        System.out.println(roleDao.delete(role4.getId()));


//  Table users.users

//        var userDao = UserDao.getInstance();
//        User user1 = new User(100, "mail2.yahoo.com", "10101010",
//                LocalDateTime.of(2023, 7, 20, 9, 0), 2, 2);
//        User user2 = new User(100, "mail2@yandex.ru", "11111111",
//                LocalDateTime.of(2023, 7, 22, 19, 30), 2, 2);
//        User user3 = new User(100, "mail2@gmail.com", "12121212",
//                LocalDateTime.of(2023, 8, 1, 3, 25), 2, 2);
//        User user4 = new User(100, "mail2@icloud.com", "13131313",
//                LocalDateTime.of(2023, 8, 4, 15, 45), 2, 2);
//        System.out.println(userDao.save(user1));
//        System.out.println(userDao.save(user2));
//        System.out.println(userDao.save(user3));
//        System.out.println(userDao.delete(user1.getId()));
//        System.out.println(userDao.save(user4));
//        user1.setEmail("mail3.yahoo.com");
//        user1.setPassword("15151515");
//        System.out.println(userDao.update(user1));
//        System.out.println(userDao.findById(user1.getId()).orElse(new User()));
//        user1.setEmail("   ");
//        System.out.println(userDao.update(user1));
//        System.out.println(userDao.delete(user1.getId()));
//        System.out.println(userDao.findById(user1.getId()).orElse(new User()));
//        userDao.findAll().forEach(System.out::println);
//        System.out.println(userDao.update(null));
//        System.out.println(userDao.findById(-5).orElse(new User()));
//        System.out.println(userDao.save(null));
//        System.out.println(userDao.save(new User(1, "mail3@icloud.com", "       ",
//                LocalDateTime.of(2023, 8, 15, 14, 20), 2, 2)));
//        System.out.println(userDao.delete(-12));
//        System.out.println(userDao.delete(user1.getId()));
//        System.out.println(userDao.delete(user2.getId()));
//        System.out.println(userDao.delete(user3.getId()));
//        System.out.println(userDao.delete(user4.getId()));
//        for (int i = 7; i < 8; i++) {
//            System.out.println(userDao.delete(i));
//        }
//        var userDto = userDao.findByEmail("info@site.com");
//        var userService = UserService.getInstance();
//        System.out.println(userDto.isPresent());
//        System.out.println(userDto);
//        String email = "info@site.com";
//        System.out.println(userService.findByEmail(email));

//  Table users.logging

//        var loggingDao = LoggingDao.getInstance();
//        Logging logging1 = new Logging(100, 2, LocalDateTime.of(2024, 6, 18, 5, 25),
//                LocalDateTime.of(2024, 6, 18, 7, 25));
//        Logging logging2 = new Logging(100, 5, LocalDateTime.of(2024, 6, 18, 10, 50),
//                LocalDateTime.of(2024, 6, 18, 15, 0));
//        Logging logging3 = new Logging(100, 3, LocalDateTime.of(2024, 6, 18, 10, 55),
//                LocalDateTime.of(2024, 6, 18, 11, 40));
//        Logging logging4 = new Logging(100, 2, LocalDateTime.of(2024, 6, 18, 11, 0),
//                LocalDateTime.of(2024, 6, 18, 15, 17));
//        System.out.println(loggingDao.save(logging1));
//        System.out.println(loggingDao.save(logging2));
//        System.out.println(loggingDao.save(logging3));
//        System.out.println(loggingDao.delete(logging1.getId()));
//        System.out.println(loggingDao.save(logging4));
//        logging1.setUsersLogOutTime(LocalDateTime.of(2024, 6, 18, 7, 30));
//        System.out.println(loggingDao.update(logging1));
//        System.out.println(loggingDao.findById(logging1.getId()).orElse(new Logging()));
//        logging1.setUsersLogOutTime(null);
//        System.out.println(loggingDao.update(logging1));
//        System.out.println(loggingDao.delete(logging1.getId()));
//        System.out.println(loggingDao.findById(logging1.getId()).orElse(new Logging()));
//        loggingDao.findAll().forEach(System.out::println);
//        System.out.println(loggingDao.update(null));
//        System.out.println(loggingDao.findById(-5).orElse(new Logging()));
//        System.out.println(loggingDao.save(null));
//        System.out.println(loggingDao.save(new Logging(100, 6,
//                LocalDateTime.of(2024, 6, 18, 17, 10), null)));;
//        System.out.println(loggingDao.delete(-12));
//        System.out.println(loggingDao.delete(logging1.getId()));
//        System.out.println(loggingDao.delete(logging2.getId()));
//        System.out.println(loggingDao.delete(logging3.getId()));
//        System.out.println(loggingDao.delete(logging4.getId()));

//  Table users.checkout

//        var checkoutDao = CheckoutDao.getInstance();
//        Checkout checkout1 = new Checkout(100, 13, LocalDateTime.of(2024, 6, 18, 5, 50));
//        Checkout checkout2 = new Checkout(100, 15, LocalDateTime.of(2024, 6, 18, 11, 35));
//        Checkout checkout3 = new Checkout(100, 14, LocalDateTime.of(2024, 6, 18, 11, 35));
//        Checkout checkout4 = new Checkout(100, 18, LocalDateTime.of(2024, 6, 18, 14, 43));
//        System.out.println(checkoutDao.save(checkout1));
//        System.out.println(checkoutDao.save(checkout2));
//        System.out.println(checkoutDao.save(checkout3));
//        System.out.println(checkoutDao.delete(checkout1.getId()));
//        System.out.println(checkoutDao.save(checkout4));
//        checkout1.setCheckoutTime(LocalDateTime.of(2024, 6, 18, 6, 10));
//        System.out.println(checkoutDao.update(checkout1));
//        System.out.println(checkoutDao.findById(checkout1.getId()).orElse(new Checkout()));
//        checkout1.setCheckoutTime(null);
//        System.out.println(checkoutDao.update(checkout1));
//        System.out.println(checkoutDao.delete(checkout1.getId()));
//        System.out.println(checkoutDao.findById(checkout1.getId()).orElse(new Checkout()));
//        checkoutDao.findAll().forEach(System.out::println);
//        System.out.println(checkoutDao.update(null));
//        System.out.println(checkoutDao.findById(-5).orElse(new Checkout()));
//        System.out.println(checkoutDao.save(null));
//        System.out.println(checkoutDao.save(new Checkout(1, 15, null)));;
//        System.out.println(checkoutDao.delete(-12));
//        System.out.println(checkoutDao.delete(checkout1.getId()));
//        System.out.println(checkoutDao.delete(checkout2.getId()));
//        System.out.println(checkoutDao.delete(checkout3.getId()));
//        System.out.println(checkoutDao.delete(checkout4.getId()));


//  Table users.information

//        var informationDao = InformationDao.getInstance();
//        var informationService = InformationService.getInstance();
//        var userDao = UserDao.getInstance();
//        User user = new User(100, "mail2.yahoo.com", "10101010",
//                LocalDateTime.of(2023, 7, 20, 9, 0), 2, 2);
//        System.out.println(userDao.save(user));
//        Information information1 = new Information(7, "Иван", "Сергеевич", "Петров",
//                "г. Минск, улица Первая", "+375 29 123 45 67");
//        Information information2 = new Information(8, "Сергей", "Петрович", "Николаев",
//                "г. Минск, улица Вторая", "+375 29 890 12 34");
//        Information information3 = new Information(9, "Петр", "Николанвич", "Александров",
//                "г. Минск, улица Третья", "+375 29 567 90 12");
//        Information information4 = new Information(10, "Николай", "Александрович", "Борисов",
//                "г. Минск, улица Четвертая", "+375 33 345 67 89");
//        System.out.println(informationDao.save(information1));
//        System.out.println(informationDao.save(information2));
//        System.out.println(informationDao.save(information3));
//        System.out.println(informationDao.save(information4));
//        information1.setFirstName("Георгий");
//        information1.setSecondName("Григорьевич");
//        System.out.println(informationDao.update(information1));
//        System.out.println(informationDao.findById(information1.getUsersId()).orElse(new Information()));
//        information1.setAddress("      ");
//        System.out.println(informationDao.update(information1));
//        System.out.println(informationDao.delete(information1.getUsersId()));
//        System.out.println(informationDao.findById(information1.getUsersId()).orElse(new Information()));
//        informationDao.findAll().forEach(System.out::println);
//        System.out.println(informationDao.update(null));
//        System.out.println(informationDao.findById(-5).orElse(new Information()));
//        System.out.println(informationDao.save(null));
//        System.out.println(informationDao.save(new Information(2, "Иван", "Сергеевич",
//                "Петров", "г. Минск, улица Первая", "+375 29 123 45 67")));
//        System.out.println(informationDao.delete(-12));
//        System.out.println(informationDao.delete(information1.getUsersId()));
//        System.out.println(informationDao.delete(information2.getUsersId()));
//        System.out.println(informationDao.delete(information3.getUsersId()));
//        System.out.println(informationDao.delete(information4.getUsersId()));
//        System.out.println(userDao.delete(user.getId()));
//        System.out.println(informationDao.delete(7));
//        var information = informationService.findById(7);
//        System.out.println(information);
//        System.out.println(InformationDto.builder().build());
//        System.out.println(informationService.findById(7).equals(InformationDto.builder().build()));


//  Table shopping.orders

//        var orderDao = OrderDao.getInstance();
//        Order order1 = new Order(100, 24, 35, BigDecimal.valueOf(3));
//        Order order2 = new Order(100, 26, 41, BigDecimal.valueOf(15));
//        Order order3 = new Order(100, 27, 27, BigDecimal.valueOf(1.6));
//        Order order4 = new Order(100, 16, 3, BigDecimal.valueOf(5));
//        System.out.println(orderDao.save(order1));
//        System.out.println(orderDao.save(order2));
//        System.out.println(orderDao.save(order3));
//        order1.setCheckoutId(19);
//        order1.setProductId(54);
//        System.out.println(orderDao.update(order1));
//        order1.setProductId(16);
//        order1.setProductAmount(BigDecimal.valueOf(7));
//        System.out.println(orderDao.update(order1));
//        System.out.println(orderDao.findById(order1.getId()).orElse(new Order()));
//        System.out.println(orderDao.save(order4));
//        order1.setProductId(null);
//        System.out.println(orderDao.update(order1));
//        System.out.println(orderDao.delete(order1.getId()));
//        System.out.println(orderDao.findById(order1.getId()).orElse(new Order()));
//        orderDao.findAll().forEach(System.out::println);
//        System.out.println(orderDao.update(null));
//        System.out.println(orderDao.findById(-5).orElse(new Order()));
//        System.out.println(orderDao.save(null));
//        System.out.println(orderDao.save(new Order(100, 21, 12, null)));
//        System.out.println(orderDao.delete(-12));
//        System.out.println(orderDao.delete(order1.getId()));
//        System.out.println(orderDao.delete(order2.getId()));
//        System.out.println(orderDao.delete(order3.getId()));
//        System.out.println(orderDao.delete(order4.getId()));


//  Table shopping.delivery

//        var deliveryDao = DeliveryDao.getInstance();
//        var checkoutDao = CheckoutDao.getInstance();
//        Checkout checkout = new Checkout(100, 20,
//                LocalDateTime.of(2024, 6, 20, 10, 50));
//        System.out.println(checkoutDao.save(checkout));
//        Delivery delivery1 = new Delivery(checkout.getId(), "г. Минск, ул. Седьмая",
//                LocalDateTime.of(2024, 6, 20, 10, 50));
//        Delivery delivery2 = new Delivery(35, "г. Минск, ул. Восьмая",
//                LocalDateTime.of(2024, 6, 20, 12, 0));
//        Delivery delivery3 = new Delivery(41, "г. Минск, ул. Девятая",
//                LocalDateTime.of(2024, 6, 20, 14, 30));
//        Delivery delivery4 = new Delivery(50, "г. Минск, ул. Десятая",
//                LocalDateTime.of(2024, 6, 20, 16, 16));
//        System.out.println(deliveryDao.save(delivery1));
//        System.out.println(deliveryDao.save(delivery2));
//        System.out.println(deliveryDao.save(delivery3));
//        System.out.println(deliveryDao.save(delivery4));
//        delivery1.setDeliveryAddress("г. Минск, ул. Одиннадцатая");
//        delivery1.setDeliveryDateTime(LocalDateTime.of(2024, 6, 20, 12, 50));
//        System.out.println(deliveryDao.update(delivery1));
//        System.out.println(deliveryDao.findById(delivery1.getCheckoutId()).orElse(new Delivery()));
//        delivery1.setDeliveryAddress("      ");
//        System.out.println(deliveryDao.update(delivery1));
//        System.out.println(deliveryDao.delete(delivery1.getCheckoutId()));
//        System.out.println(deliveryDao.findById(delivery1.getCheckoutId()).orElse(new Delivery()));
//        deliveryDao.findAll().forEach(System.out::println);
//        System.out.println(deliveryDao.update(null));
//        System.out.println(deliveryDao.findById(-5).orElse(new Delivery()));
//        System.out.println(deliveryDao.save(null));
//        System.out.println(deliveryDao.save(new Delivery(20, "г. Минск, ул. Десятая", null)));
//        System.out.println(deliveryDao.delete(-12));
//        System.out.println(deliveryDao.delete(delivery1.getCheckoutId()));
//        System.out.println(deliveryDao.delete(delivery2.getCheckoutId()));
//        System.out.println(deliveryDao.delete(delivery3.getCheckoutId()));
//        System.out.println(deliveryDao.delete(delivery4.getCheckoutId()));
//        System.out.println(checkoutDao.delete(checkout.getId()));
    }
}
