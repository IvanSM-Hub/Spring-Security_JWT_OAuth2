package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.RegisterUserDto;
import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.persistence.entity.security.User;
import com.cursos.api.spring_security_course.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping()
    @PreAuthorize("permitAll")
    public ResponseEntity<RegisterUserDto> registerOne(@RequestBody @Valid SaveUserDto saveUserDto) {
        RegisterUserDto registerUser = authenticationService.registerOneCostumer(saveUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

    @GetMapping()
    @PreAuthorize("denyAll")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(Arrays.asList());
    }

}
