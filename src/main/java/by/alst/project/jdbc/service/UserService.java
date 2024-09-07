package by.alst.project.jdbc.service;

import by.alst.project.jdbc.dao.UserDao;
import by.alst.project.jdbc.dto.CreateUserDto;
import by.alst.project.jdbc.dto.UserDto;
import by.alst.project.jdbc.entity.User;
import by.alst.project.jdbc.exception.ValidationException;
import by.alst.project.jdbc.mapper.CreateUserDtoMapper;
import by.alst.project.jdbc.mapper.CreateUserMapper;
import by.alst.project.jdbc.validator.CreateUserValidator;

import java.util.List;
import java.util.Optional;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final CreateUserDtoMapper createUserDtoMapper = CreateUserDtoMapper.getInstance();

    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(user -> new UserDto(user.getId(), user.getEmail(),
                user.getPassword(), user.getRegistrationDate(), user.getRoleId(), user.getGenderId())).toList();
    }

    public UserDto findById(Integer usersId) {
        User user = userDao.findById(usersId).orElse(new User());
        return new UserDto(user.getId(), user.getEmail(),
                user.getPassword(), user.getRegistrationDate(), user.getRoleId(), user.getGenderId());
    }

    public UserDto findByEmail(String email) {
        User user = userDao.findByEmail(email).orElse(new User());
        return new UserDto(user.getId(), user.getEmail(),
                user.getPassword(), user.getRegistrationDate(), user.getRoleId(), user.getGenderId());
    }

    public void create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(createUserDtoMapper::mapFrom);
    }
}
