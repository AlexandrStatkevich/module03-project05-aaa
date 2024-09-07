package by.alst.project.jdbc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(Integer id, Integer productGroupId, String productName, String productDescription,
                         Integer productProducerId, Integer countryOriginId, Integer productUnitId,
                         LocalDateTime productArrivalDate, BigDecimal productCost, BigDecimal productAmount,
                         LocalDateTime productCardDateCreation) {
}
