package by.alst.project.jdbc.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Producer {
    private Integer id;
    private String producerName;
    private String producerAddress;
}
