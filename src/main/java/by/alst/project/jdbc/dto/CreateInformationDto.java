package by.alst.project.jdbc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateInformationDto {
    Integer usersId;
    String firstName;
    String secondName;
    String lastName;
    String address;
    String phone;
}
