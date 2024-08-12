package com.cursos.api.spring_security_course.config.security.filter;

import com.cursos.api.spring_security_course.exception.ObjectNotFoundException;
import com.cursos.api.spring_security_course.service.UserService;
import com.cursos.api.spring_security_course.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("ENTRO EN EL FILTRO JWT AUTHENTICATION FILTER");

//        1. Obtener el encabezado http llamado Authorization
        String authorizationHeader = req.getHeader("Authorization");

        System.out.println(authorizationHeader);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(req, res);
            return;
        }

//        2. Obtener token JWT desde el Header
        String jwt = authorizationHeader.split(" ")[1];

//        3. Obtener el subject/username desde el token esta acción a su vez valida el formato del token,
//        firma y fecha de expedición.
        String username = jwtService.extractUsername(jwt);

//        4. Setear objeto authentication dentro de SecurityContextHolder
        UserDetails userDetails = userService.findOneByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException("User not found Username: " + username)
        );

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetails(req));

        SecurityContextHolder.getContext().setAuthentication( authToken );

//        5. Ejecutar el registro de filtros
        filterChain.doFilter(req, res);

    }
}
