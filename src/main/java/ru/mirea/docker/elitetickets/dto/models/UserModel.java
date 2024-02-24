package ru.mirea.docker.elitetickets.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.TypeReg;
import ru.mirea.docker.elitetickets.enums.UserRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails {

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private LocalDate birthDate;

    private UserRole userRole;

    private TypeReg provider;

    public static UserModel fromEntity(UserEntity user){
        return UserModel.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .userRole(user.getRole())
                .password(user.getPassword())
                .provider(user.getRegType())
                .build();
    }

/*
    public static UserModel fromOAuth2User(OAuth2User oAuth2User){
        Map attribs = oAuth2User.getAttributes();
        return UserModel.builder()
                .email((String) attribs.get("default_email"))
                .firstName((String) attribs.get("first_name"))
                .lastName((String) attribs.get("last_name"))
                .birthDate(LocalDate.parse((String) attribs.get("birthday")))
                .userRole(UserRole.USER)
                .provider(TypeReg.YANDEX)
                .build();
    }
*/


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(userRole);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
