package ru.mirea.docker.elitetickets;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Обязательная аннотация для работы проекта
public class Application {
    public static void main(String[] args) {
        // Метод для запуска проекта
        // Входящий аргумент сообщает спрингу относительно какого класса сканировать проект на компоненты
        SpringApplication.run(Application.class);
    }
}