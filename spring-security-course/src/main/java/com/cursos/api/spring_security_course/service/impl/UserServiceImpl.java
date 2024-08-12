package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveUserDto;
import com.cursos.api.spring_security_course.exception.InvalidPasswordException;
import com.cursos.api.spring_security_course.persistence.entity.User;
import com.cursos.api.spring_security_course.persistence.repository.UserRepository;
import com.cursos.api.spring_security_course.service.UserService;
import com.cursos.api.spring_security_course.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCostumer(SaveUserDto saveUserDto) {

//        Validación de contraseñas
        validatePassword(saveUserDto);

//        Creación de usuario
        User user = new User();
        user.setName(saveUserDto.getName());
        user.setPassword(passwordEncoder.encode(saveUserDto.getPassword()));
        user.setUsername(saveUserDto.getUsername());
        user.setRole(Role.COSTUMER);

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
