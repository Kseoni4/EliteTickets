package ru.mirea.docker.elitetickets.services.mail;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.mirea.docker.elitetickets.dao.TicketDao;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.EventModel;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.properties.MailProperties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final MailProperties properties;

    private Session session;

    private String sender;

    private final UserDao userDao;

    private final TicketDao ticketDao;

    @PostConstruct
    public void openSession() throws FileNotFoundException {
        String host = properties.getHost();

        String port = properties.getPort();

        sender = properties.getSender();

        Properties prop = new Properties();

        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);

        session = Session.getDefaultInstance(prop);
    }

    @Override
    public void sendEventReminder(UserModel userModel, EventModel eventModel) {
        try {
            File file = ResourceUtils.getFile("classpath:/static/mail-template/mail-template.html");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String reminderText = bufferedReader.lines().collect(Collectors.joining());

            bufferedReader.close();

            reminderText = reminderText.formatted(userModel.getFirstName(), eventModel.getEventDate(), eventModel.getEventName());

            sendEmail(userModel.getEmail(), "Напоминание о событии %s".formatted(eventModel.getEventName()), reminderText);

        } catch (IOException e) {

        }
    }

    @Override
    public void sendRemindersForUsers() {
        //TODO: сделать так, чтобы выбирались только те пользователи, у которых дата мероприятия за день/два/неделю до текущей
        List<UserEntity> users = userDao.getAllUsers();

        for(UserEntity user : users){
            List<TicketEntity> list = ticketDao.getAllTicketEntitiesByUser(user.getEmail());

            for(TicketEntity ticket : list){

                sendEventReminder(UserModel.fromEntity(user),
                        EventModel.fromEntity(ticket.getEvent()));

            }
        }

    }

    @Override
    public void sendTicketLinkToUser(UserModel userModel, TicketModel ticketModel) {
        try {
            File file = ResourceUtils.getFile("classpath:static/mail-template/mail-ticket-template.html");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String ticketLinkText = bufferedReader.lines().collect(Collectors.joining());

            bufferedReader.close();

            ticketLinkText = ticketLinkText.formatted(
                        userModel.getFirstName(),
                        ticketModel.getEventName(),
                        "http://localhost:8080/api/v1/file/t/"+ticketModel.getTicketId()
                    );

            sendEmail(userModel.getEmail(), "Покупка билета на событие %s".formatted(ticketModel.getEventName()), ticketLinkText);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendEmail(String receiver, String subject, String text){
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(sender));

            mimeMessage.setSubject(subject);

            mimeMessage.setText(text);

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
