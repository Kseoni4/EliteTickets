package ru.mirea.docker.elitetickets.services.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.services.security.jwt.JwtService;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String jwt = getToken(request);

        if(Objects.isNull(jwt)){
            log.info("No jwt in header");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Get jwt {}", jwt);

        UserModel user;

        user = jwtService.parseToken(jwt);

        if(Objects.isNull(user)){
            log.info("No valid user in jwt {}", jwt);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Get user from jwt {}", user);


        if(!userDao.userExistByEmail(user.getEmail())){
            log.info("User {} does not exists", user);
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), user.getAuthorities()
        );

        log.info("Authenticate user {}", user.getUsername());

        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            return null;
        }

        final String[] strs = header.split(" ");
        if (strs.length != 2) {
            return null;
        }

        return strs[1].trim();
    }
}
