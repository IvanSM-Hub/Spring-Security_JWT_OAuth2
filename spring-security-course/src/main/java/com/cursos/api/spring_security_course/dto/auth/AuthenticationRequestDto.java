package com.cursos.api.spring_security_course.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthenticationRequestDto implements Serializable {

    private String username;
    private String password;

}
