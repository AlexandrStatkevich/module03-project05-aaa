package by.alst.project.jdbc.dto;

import java.time.LocalDateTime;

public record LoggingDto(Integer id, Integer usersId, LocalDateTime usersAuthenticationTime,
                         LocalDateTime usersLogOutTime) {
}
