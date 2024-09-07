package by.alst.project.jdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class User {
    private Integer id;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Integer roleId;
    private Integer genderId;
}
