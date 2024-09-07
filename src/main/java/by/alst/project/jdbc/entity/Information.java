package by.alst.project.jdbc.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Information {
    private Integer usersId;
    private String firstName;
    private String secondName;
    private String lastName;
    private String address;
    private String phone;
}
