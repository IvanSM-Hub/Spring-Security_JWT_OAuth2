package com.cursos.api.spring_security_course.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SaveUserDto implements Serializable {

    @Size(min = 4)
    private String name;

    private String username;

    @Size(min = 8)
    private String password;

    @Size(min = 8)
    private String repeatedPassword;

}
