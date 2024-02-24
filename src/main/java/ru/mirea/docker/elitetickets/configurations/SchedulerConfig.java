package ru.mirea.docker.elitetickets.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.mirea.docker.elitetickets.services.mail.MailService;

@Configuration
//@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final MailService mailService;

    @Async
    @Scheduled(cron = "* */1 * * * *")
    public void sendReminders(){
        mailService.sendRemindersForUsers();
    }

}
