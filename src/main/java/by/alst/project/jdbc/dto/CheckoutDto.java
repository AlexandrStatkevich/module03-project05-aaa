package by.alst.project.jdbc.dto;

import java.time.LocalDateTime;

public record CheckoutDto(Integer id, Integer loggingId, LocalDateTime checkoutTime) {
}
