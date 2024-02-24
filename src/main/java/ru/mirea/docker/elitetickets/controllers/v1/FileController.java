package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.docker.elitetickets.services.ticket.TicketService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final TicketService ticketService;

    @GetMapping(path = "/t/{ticketId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTicketFile(@PathVariable String ticketId, @AuthenticationPrincipal String user) throws IOException {
        log.info("User {} trying to get ticket by id {}", user, ticketId);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        BufferedImage bufferedImage = ticketService.getTicketImage(ticketId);

        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return ResponseEntity.ok(byteArrayOutputStream.toByteArray());
    }

}
