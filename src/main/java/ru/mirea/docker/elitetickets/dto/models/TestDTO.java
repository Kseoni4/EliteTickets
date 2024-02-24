package ru.mirea.docker.elitetickets.dto.models;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TestDTO implements Converter<Tuple, TestDTO> {

    private UUID id;

    private int price;

    @Override
    public TestDTO convert(Tuple source) {
        UUID id = (UUID) source.get(0);
        int price = (int) source.get(1);
        log.info("{}, {}", id, price);
        return new TestDTO(id, price);
    }
}
