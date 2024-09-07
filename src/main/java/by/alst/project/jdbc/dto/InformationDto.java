package by.alst.project.jdbc.dto;

import lombok.Builder;

@Builder
public record InformationDto(Integer usersId, String firstName, String secondName, String lastName,
                             String address, String phone) {
}
