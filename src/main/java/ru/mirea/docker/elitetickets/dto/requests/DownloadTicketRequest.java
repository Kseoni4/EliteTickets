package ru.mirea.docker.elitetickets.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTicketRequest {

    private String email;

    private String ticketId;

}
