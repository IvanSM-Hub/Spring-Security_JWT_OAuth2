package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.persistence.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User registerOneCostumer(SaveUserDto saveUserDto);
    Optional<User> findOneByUsername(String username);
}
