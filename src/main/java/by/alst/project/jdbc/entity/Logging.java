package by.alst.project.jdbc.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Logging {
    private Integer id;
    private Integer usersId;
    private LocalDateTime usersAuthenticationTime;
    private LocalDateTime usersLogOutTime;
}
