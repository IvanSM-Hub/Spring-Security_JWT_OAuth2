package com.cursos.api.spring_security_course.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Builder
public class RegisterUserDto implements Serializable {

    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;

}
