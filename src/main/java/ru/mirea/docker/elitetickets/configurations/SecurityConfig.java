package ru.mirea.docker.elitetickets.configurations;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ru.mirea.docker.elitetickets.services.security.UserDetailsServiceImpl;
import ru.mirea.docker.elitetickets.services.security.filter.ExceptionFilter;
import ru.mirea.docker.elitetickets.services.security.filter.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends VaadinWebSecurity {

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
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                                   UserDetailsServiceImpl userDetailsService, MvcRequestMatcher.Builder mvc) throws Exception {
        http
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
                        config.requestMatchers(mvc.pattern("/VAADIN/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/v1/orders")).authenticated()
                                .requestMatchers(mvc.pattern("/orders")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/error**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v1/auth/**", HttpMethod.POST.name())).permitAll()
                                .requestMatchers(mvc.pattern("/static/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/login**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/ping")).permitAll()
                )
                // Отключаем логин через форму
                .formLogin(AbstractHttpConfigurer::disable)
                // Добавляем JWT Filter перед фильтром аутентификации по Username/Password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionFilter, JwtFilter.class);
        return super.filterChain(http);
    }

}