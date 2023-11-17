package ru.mirea.docker.elitetickets.services.security.jwt;

import ru.mirea.docker.elitetickets.dto.models.UserModel;

public interface JwtService {

    UserModel parseToken(String jwt);

    String generateToken(UserModel userModel);


}
