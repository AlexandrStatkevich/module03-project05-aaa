package by.alst.project.jdbc.validator;

import by.alst.project.jdbc.dto.CreateUserDto;
import by.alst.project.jdbc.dto.UserDto;
import by.alst.project.jdbc.service.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();
        var userService = UserService.getInstance();
        if (!EmailValidator.getInstance().isValid(createUserDto.getEmail())) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (!userService.findByEmail(createUserDto.getEmail()).equals(UserDto.builder().build())) {
            validationResult.add(Error.of("invalid.email", "Email is present"));
        }
        if (createUserDto.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        if (Optional.ofNullable(createUserDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Optional.ofNullable(createUserDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }
}


