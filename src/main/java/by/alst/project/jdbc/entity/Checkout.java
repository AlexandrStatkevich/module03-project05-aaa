package by.alst.project.jdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Checkout {
    private Integer id;
    private Integer loggingId;
    private LocalDateTime checkoutTime;
}
