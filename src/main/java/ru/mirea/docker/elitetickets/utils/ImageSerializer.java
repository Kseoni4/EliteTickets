package ru.mirea.docker.elitetickets.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ImageSerializer implements RedisSerializer<BufferedImage> {
    @Override
    public byte[] serialize(BufferedImage bufferedImage) throws SerializationException {
        try {
            log.info("===ImageSerializer===");
            log.info("===serialize===");
            log.info("{}", bufferedImage);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BufferedImage deserialize(byte[] bytes) throws SerializationException {
        try {
            log.info("===ImageSerializer===");
            log.info("===deserialize===");
            InputStream is = new ByteArrayInputStream(bytes);
         return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
