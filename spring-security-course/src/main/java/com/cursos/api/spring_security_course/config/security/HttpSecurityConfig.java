package com.cursos.api.spring_security_course.config.security;

import com.cursos.api.spring_security_course.config.security.filter.JwtAuthenticationFilter;
import com.cursos.api.spring_security_course.util.RoleEnum;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@Data
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider daoAuthenticationProvider)
            throws Exception {

        SecurityFilterChain filterChain = http
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> authReqConfig.anyRequest().access(authorizationManager) )
                .exceptionHandling( exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                } )
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

    private static void buildRequestMartchers
            (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        // Authorization de endpoints de products
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

//        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(
                        RoleEnum.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(
                        RoleEnum.ADMINISTRATOR.name()
                );

        // Authorization de endpoints de categories
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoriesId}")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(
                        RoleEnum.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriesId}")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoriesId}/disabled")
                .hasRole(
                        RoleEnum.ADMINISTRATOR.name()
                );

        authReqConfig.requestMatchers(HttpMethod.PUT, "/auth/profile")
                .hasAnyRole(
                        RoleEnum.ADMINISTRATOR.name(),
                        RoleEnum.ASSISTANT_ADMINISTRATOR.name(),
                        RoleEnum.COSTUMER.name()
                );

        // Authorizations públicos
        authReqConfig.requestMatchers(HttpMethod.POST,"/costumers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

        authReqConfig.anyRequest().authenticated();
    }

}
