package by.alst.project.jdbc.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Subcategory {
    private Integer id;
    private Integer categoryId;
    private String subcategory;
}
