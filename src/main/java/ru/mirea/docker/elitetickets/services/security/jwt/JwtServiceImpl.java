package ru.mirea.docker.elitetickets.services.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.enums.UserRole;
import ru.mirea.docker.elitetickets.properties.TokenProperties;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final TokenProperties tokenProperties;

    @Override
    public UserModel parseToken(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build();

            Jws<Claims> parsedJwt = jwtParser.parseClaimsJws(jwt);

            Claims claims = parsedJwt.getBody();

            return UserModel.builder()
                    .email(claims.getSubject())
                    .userRole(UserRole.valueOf((String) claims.get("role")))
                    .build();
        } catch (ExpiredJwtException e){
            log.info("Jwt is expired: {}", e.getMessage());
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        }
    }

    @Override
    public String generateToken(UserModel userModel) {
        Claims claims = Jwts.claims();
        claims.setSubject(userModel.getEmail());
        claims.put("role", userModel.getUserRole().getAuthority());
        claims.setExpiration(getExpirationDate());

        return Jwts.builder()
                .addClaims(claims)
                .signWith(getSigninKey())
                .compact();
    }


    private Date getExpirationDate() {
        Date now = new Date();
        now.setTime((now.getTime() + 100) * tokenProperties.getTimeToLive());
        return now;
    }

    private Key getSigninKey() {
        byte[] keyBytes = Base64.getDecoder().decode(tokenProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
