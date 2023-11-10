package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.docker.elitetickets.dao.EventDao;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.EventModel;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.UserReminderRequest;
import ru.mirea.docker.elitetickets.services.mail.MailService;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("")
    public String sendReminderTo(@RequestBody UserReminderRequest reminderRequest){
        UserModel user = UserModel.builder()
                .email(reminderRequest.getEmail())
                .firstName(reminderRequest.getFirstName())
                .build();

        EventModel event = EventModel.builder()
                .eventName(reminderRequest.getEventName())
                .eventDate(LocalDate.parse(reminderRequest.getEventDate()))
                .isActive(true)
                .build();

        mailService.sendEventReminder(user, event);
        return "Email sent";
    }

}
