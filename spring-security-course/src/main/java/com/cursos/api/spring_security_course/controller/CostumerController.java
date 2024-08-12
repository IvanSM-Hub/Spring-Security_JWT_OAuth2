package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.RegisterUserDto;
import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<RegisterUserDto> registerOne(@RequestBody @Valid SaveUserDto saveUserDto) {
        RegisterUserDto registerUser = authenticationService.registerOneCostumer(saveUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

}
