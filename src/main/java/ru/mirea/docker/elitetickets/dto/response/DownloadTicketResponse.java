package ru.mirea.docker.elitetickets.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTicketResponse {

    private TicketModel ticketModel;

    private String downloadUrl;

}
