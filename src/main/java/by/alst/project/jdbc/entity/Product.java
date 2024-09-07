package by.alst.project.jdbc.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {
    private Integer id;
    private Integer productGroupId;
    private String productName;
    private String productDescription;
    private Integer productProducerId;
    private Integer countryOriginId;
    private Integer productUnitId;
    private LocalDateTime productArrivalDate;
    private BigDecimal productCost;
    private BigDecimal productAmount;
    private LocalDateTime productCardDateCreation;
}
