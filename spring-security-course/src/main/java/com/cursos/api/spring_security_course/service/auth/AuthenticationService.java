package com.cursos.api.spring_security_course.service.auth;

import com.cursos.api.spring_security_course.dto.auth.AuthenticationRequestDto;
import com.cursos.api.spring_security_course.dto.auth.AuthenticationResponseDto;
import com.cursos.api.spring_security_course.dto.RegisterUserDto;
import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.exception.ObjectNotFoundException;
import com.cursos.api.spring_security_course.persistence.entity.User;
import com.cursos.api.spring_security_course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterUserDto registerOneCostumer(SaveUserDto saveUserDto) {

//        Guardado el la BBDD
        User user = userService.registerOneCostumer(saveUserDto);

//        DTO que se le va a devolver a la respuesta
        RegisterUserDto userDto = RegisterUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();

//        Generación del Token
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);

        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("name",user.getName());
        extraClaims.put("role",user.getRole().name());
        extraClaims.put("authorities",user.getAuthorities());

        return extraClaims;

    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
          authenticationRequestDto.getUsername(),
          authenticationRequestDto.getPassword()
        );

        authenticationManager.authenticate(auth);

        UserDetails user = userService.findOneByUsername(authenticationRequestDto.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        return AuthenticationResponseDto.builder()
                .jwt(jwt)
                .build();

    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public User findLoggedInUser() {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();

        String username = (String) auth.getPrincipal();

        return userService.findOneByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException("User not found username: " + username)
        );

    }
}
