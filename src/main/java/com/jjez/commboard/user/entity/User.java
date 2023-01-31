package com.jjez.commboard.user.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class User {

    @Id
    private String email;
    private String name;
    private String password;
    private String birth;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private boolean emailAuthYn;
    private String emailAuthKey;

    private boolean adminYn;

}
