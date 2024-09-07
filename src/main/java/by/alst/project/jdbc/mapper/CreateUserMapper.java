package by.alst.project.jdbc.mapper;

import by.alst.project.jdbc.dao.GenderDao;
import by.alst.project.jdbc.dao.RoleDao;
import by.alst.project.jdbc.dto.CreateUserDto;
import by.alst.project.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    private final RoleDao roleDao = RoleDao.getInstance();
    private final GenderDao genderDao = GenderDao.getInstance();

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .registrationDate(LocalDateTime.now())
                .roleId(roleDao.findByRoleName(createUserDto.getRole()).get().getId())
                .genderId(genderDao.findByGenderName(createUserDto.getGender()).get().getId())
                .build();
    }
}
