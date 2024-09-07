package by.alst.project.jdbc.mapper;

import by.alst.project.jdbc.dto.CreateInformationDto;
import by.alst.project.jdbc.entity.Information;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateInformationMapper implements Mapper<Information, CreateInformationDto> {

    private static final CreateInformationMapper INSTANCE = new CreateInformationMapper();

    public static CreateInformationMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Information mapFrom(CreateInformationDto createInformationDto) {
        return Information.builder()
                .usersId(createInformationDto.getUsersId())
                .firstName(createInformationDto.getFirstName())
                .secondName(createInformationDto.getSecondName())
                .lastName(createInformationDto.getLastName())
                .address(createInformationDto.getAddress())
                .phone(createInformationDto.getPhone())
                .build();
    }
}
