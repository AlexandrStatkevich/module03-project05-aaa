package by.alst.project.jdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Delivery {
    private Integer checkoutId;
    private String deliveryAddress;
    private LocalDateTime deliveryDateTime;
}
