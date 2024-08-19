package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.persistence.entity.security.Role;
import com.cursos.api.spring_security_course.persistence.repository.RoleRepository;
import com.cursos.api.spring_security_course.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private static String defaultRole = "COSTUMER";

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
}
