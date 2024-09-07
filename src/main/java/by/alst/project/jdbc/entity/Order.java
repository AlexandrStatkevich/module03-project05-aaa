package by.alst.project.jdbc.entity;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {
    private Integer id;
    private Integer checkoutId;
    private Integer productId;
    private BigDecimal productAmount;
}
