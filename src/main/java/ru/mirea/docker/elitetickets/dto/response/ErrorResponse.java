package ru.mirea.docker.elitetickets.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

    private final String message;
    private final Object payload;

    public ErrorResponse(String message) {
        this(message, null);
    }

    public ErrorResponse(String message, Object payload) {
        this.message = message;
        this.payload = payload;
    }
}