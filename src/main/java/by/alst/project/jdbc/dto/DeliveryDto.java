package by.alst.project.jdbc.dto;

import java.time.LocalDateTime;

public record DeliveryDto(Integer checkoutId, String deliveryAddress, LocalDateTime deliveryDateTime) {
}
