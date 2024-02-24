package ru.mirea.docker.elitetickets.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mirea.docker.elitetickets.services.security.UserDetailsServiceImpl;
import ru.mirea.docker.elitetickets.services.security.filter.ExceptionFilter;
import ru.mirea.docker.elitetickets.services.security.filter.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final ExceptionFilter exceptionFilter;

/*    @Bean
    public OAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistration){
        return new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistration,
                "/oauth2/authorization"
        );
    }*/


    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http,
                                                          UserDetailsServiceImpl userDetailsService) throws Exception {
        return http
                // Отключаем аутентификацию по HTTP
                .httpBasic(AbstractHttpConfigurer::disable)
                // Отключаем проверку CSRF токена
                .csrf(AbstractHttpConfigurer::disable)
                // Устанавливаем политику сессий как STATELESS – не храним сессии
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Обрабатываем исключения
                .exceptionHandling(
                        c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                // Устанавливаем политики доступа к end-point-ам
                .authorizeHttpRequests(config ->
                        config.requestMatchers("/v1/orders").authenticated()
                            .requestMatchers("/error**").permitAll()
                            .requestMatchers("/error/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/v1/auth/**").permitAll()
                                .requestMatchers("/static/**").permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .requestMatchers("/login**").permitAll()
                                .requestMatchers("/ping").permitAll()
                            .anyRequest().authenticated()
                )
                // Отключаем логин через форму
                .formLogin(AbstractHttpConfigurer::disable)
                // Добавляем JWT Filter перед фильтром аутентификации по Username/Password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionFilter, JwtFilter.class)
                // Инициализируем полученный объект политик и возвращаем из метода
                .build();

    }

}