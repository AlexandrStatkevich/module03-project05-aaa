package by.alst.project.jdbc.dto;

import java.math.BigDecimal;

public record OrderDto(Integer id, Integer checkoutId, Integer productId, BigDecimal productAmount) {
}
