package com.cursos.api.spring_security_course.config.security;

import com.cursos.api.spring_security_course.config.security.filter.JwtAuthenticationFilter;
import com.cursos.api.spring_security_course.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider daoAuthenticationProvider)
            throws Exception {

        SecurityFilterChain filterChain = http
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                /*.authorizeHttpRequests( authReqConfig -> buildRequestMartchersV2(authReqConfig) )*/
                .build();

        return filterChain;

    }

    private static void buildRequestMartchersV2
            (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        // Authorizations públicas
        authReqConfig.requestMatchers(HttpMethod.POST,"/costumers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }

    /*private static void buildRequestMartchers
            (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        // Authorization de endpoints de products
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

//        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(
                        Role.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(
                        Role.ADMINISTRATOR.name()
                );

        // Authorization de endpoints de categories
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoriesId}")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(
                        Role.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriesId}")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriesId}/disabled")
                .hasRole(
                        Role.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/auth/profile")
                .hasAnyRole(
                        Role.ADMINISTRATOR.name(),
                        Role.ASSISTANT_ADMINISTRATOR.name(),
                        Role.COSTUMER.name()
                );

        // Authorizations públicos
        authReqConfig.requestMatchers(HttpMethod.POST,"/costumers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }*/

}
