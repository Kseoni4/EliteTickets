package ru.mirea.docker.elitetickets.services.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dao.TicketDao;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.dto.requests.UserTicketRequest;
import ru.mirea.docker.elitetickets.dto.response.TicketRedeemResponse;
import ru.mirea.docker.elitetickets.enums.TicketType;
import ru.mirea.docker.elitetickets.repositories.redis.RedisTicketImageRepository;
import ru.mirea.docker.elitetickets.utils.ImageProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketDao ticketDao;

    private final RedisTicketImageRepository ticketImageRepository;

    @Override
    public TicketRedeemResponse redeemTicket(String ticketId) {
        ticketDao.redeemTicket(ticketId);
        return new TicketRedeemResponse();
    }

    @Override
    public BufferedImage getTicketImage(String ticketId) {
        return ticketImageRepository.findValueByHash(ticketId);
    }

    @Override
    public BufferedImage createTicketImage(TicketModel ticketModel) {
        TicketType ticketType = ticketModel.getTicketType();

        String fileName = "classpath:static/img/" + switch (ticketType) {
            case COMMON -> {
                yield "b-tier-ticket-blank.png";
            }
            case VIP, GOLD -> {
                yield "s-tier-ticket_blank.png";
            }
        };

        BufferedImage image = new ImageProcessor().processImage(fileName, ticketModel);

        ticketImageRepository.add(ticketModel.getTicketId().toString(), image);

        return image;
    }

    @Override
    public List<TicketModel> getTicketsOfUser(UserTicketRequest userTicketRequest) {
        return ticketDao.getAllTicketsByUser(userTicketRequest.getEmail());
    }
}
