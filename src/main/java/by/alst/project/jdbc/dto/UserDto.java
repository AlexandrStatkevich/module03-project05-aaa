package by.alst.project.jdbc.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(Integer id, String email, String password, LocalDateTime registrationDate, Integer roleId,
                      Integer genderId) {
}
