package by.alst.project.jdbc.mapper;

import by.alst.project.jdbc.dto.UserDto;
import by.alst.project.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDtoMapper implements Mapper<UserDto, User> {
    private static final CreateUserDtoMapper INSTANCE = new CreateUserDtoMapper();

    public static CreateUserDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .registrationDate(user.getRegistrationDate())
                .roleId(user.getRoleId())
                .genderId(user.getGenderId())
                .build();
    }
}
