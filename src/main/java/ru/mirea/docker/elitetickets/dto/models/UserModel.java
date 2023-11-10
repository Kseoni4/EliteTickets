package ru.mirea.docker.elitetickets.dto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.UserRole;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

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

    public static UserModel fromEntity(UserEntity user){
        return UserModel.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .userRole(user.getRole())
                .password(user.getPassword())
                .build();
    }


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
