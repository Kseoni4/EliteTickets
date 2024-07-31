package ru.mirea.docker.elitetickets.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import com.vaadin.flow.component.html.Span;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.stream.Collectors;

@Route("/users")
@AnonymousAllowed
public class UserView extends VerticalLayout {

    private UserDao userDao;

    public UserView(@Autowired UserDao userDao) {

        Grid<UserModel> userList = new Grid<>(UserModel.class, false);

        userList.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);
        userList.addClassName("styling");
        userList.addColumn(UserModel::getFirstName).setHeader("Имя").setAutoWidth(true);
        userList.addColumn(UserModel::getLastName).setHeader("Фамилия").setAutoWidth(true);
        userList.addColumn(UserModel::getEmail).setHeader("Email").setAutoWidth(true);
        userList.addColumn(UserModel::getFormattedBirthDate).setHeader("Дата рождения").setAutoWidth(true);

        userList.setPartNameGenerator(user -> {
            if(user.getFormattedBirthDate().equals("N/A"))
                return "user-problem";
            return null;
        });

        userList.setItems(userDao
                .getAllUsers()
                .stream()
                .map(UserModel::fromEntity)
                .toList());
        add(userList);
    }
}
