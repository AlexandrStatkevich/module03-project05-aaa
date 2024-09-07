package by.alst.project.jdbc.validator;

import by.alst.project.jdbc.dto.CreateInformationDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateInformationValidator implements Validator<CreateInformationDto> {

    private final static CreateInformationValidator INSTANCE = new CreateInformationValidator();

    public static CreateInformationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateInformationDto createInformationDto) {
        var validationResult = new ValidationResult();

        if (createInformationDto.getFirstName().isEmpty()) {
            validationResult.add(Error.of("invalid.firstName", "FirstName is invalid"));
        }
        if (createInformationDto.getSecondName().isEmpty()) {
            validationResult.add(Error.of("invalid.secondName", "SecondName is invalid"));
        }
        if (createInformationDto.getLastName().isEmpty()) {
            validationResult.add(Error.of("invalid.lastName", "LastName is invalid"));
        }
        if (createInformationDto.getAddress().isEmpty()) {
            validationResult.add(Error.of("invalid.address", "Address is invalid"));
        }
        if (createInformationDto.getPhone().isEmpty()) {
            validationResult.add(Error.of("invalid.phone", "Phone is invalid"));
        }
        return validationResult;
    }
}
