package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.exception.InvalidPasswordException;
import com.cursos.api.spring_security_course.exception.ObjectNotFoundException;
import com.cursos.api.spring_security_course.persistence.entity.security.Role;
import com.cursos.api.spring_security_course.persistence.entity.security.User;
import com.cursos.api.spring_security_course.persistence.repository.UserRepository;
import com.cursos.api.spring_security_course.service.RoleService;
import com.cursos.api.spring_security_course.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User registerOneCostumer(SaveUserDto saveUserDto) {

//        Validación de contraseñas
        validatePassword(saveUserDto);

//        Creación de usuario
        User user = new User();
        user.setName(saveUserDto.getName());
        user.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        user.setUsername(saveUserDto.getUsername());
        Role defaultRole = roleService.findDefaultRole()
                .orElseThrow( () -> new ObjectNotFoundException("Role not found. Default role.") );
        user.setRole(defaultRole);
//        Guardado Usuario en BBDD Role COSTUMER
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUserDto saveUserDto) {

        System.out.println(saveUserDto);
//        Comprobación de que tanto el password como el repitedPassword no sean null
        if (saveUserDto.getPassword().isEmpty() || saveUserDto.getRepeatedPassword().isEmpty())
            throw new InvalidPasswordException("Password or repited password is empty");

//        Comprobación de que las contraseñas coincidan
        if (!saveUserDto.getPassword().matches(saveUserDto.getRepeatedPassword()))
            throw new InvalidPasswordException("Password don't match");

    }

}
