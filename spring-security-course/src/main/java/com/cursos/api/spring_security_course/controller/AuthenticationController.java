package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.auth.AuthenticationRequestDto;
import com.cursos.api.spring_security_course.dto.auth.AuthenticationResponseDto;
import com.cursos.api.spring_security_course.persistence.entity.security.User;
import com.cursos.api.spring_security_course.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @PreAuthorize("permitAll")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
        @RequestBody @Valid AuthenticationRequestDto authenticationRequestDto
    ) {
        AuthenticationResponseDto resp = authenticationService.login(authenticationRequestDto);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/validate-token")
    @PreAuthorize("permitAll")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    public ResponseEntity<User> findMyProfile() {
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }

}
