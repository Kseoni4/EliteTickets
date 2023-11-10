package ru.mirea.docker.elitetickets.services.mail;

import ru.mirea.docker.elitetickets.dto.models.EventModel;
import ru.mirea.docker.elitetickets.dto.models.UserModel;

public interface MailService {

    void sendEventReminder(UserModel userModel, EventModel eventModel);

    void sendRemindersForUsers();
}
