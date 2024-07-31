package ru.mirea.docker.elitetickets;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Обязательная аннотация для работы проекта
@Theme("elite-tickets-theme")
public class Application implements AppShellConfigurator {
    public static void main(String[] args) {
        // Метод для запуска проекта
        // Входящий аргумент сообщает спрингу относительно какого класса сканировать проект на компоненты
        SpringApplication.run(Application.class);
    }
}