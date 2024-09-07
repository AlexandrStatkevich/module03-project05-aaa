package by.alst.project.jdbc.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductGroup {
    private Integer id;
    private Integer subcategoryId;
    private String productGroup;
}
