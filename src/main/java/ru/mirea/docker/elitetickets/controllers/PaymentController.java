package ru.mirea.docker.elitetickets.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("successful_payment")
@RequiredArgsConstructor
public class PaymentController {

    @GetMapping("")
    public String succsesfulEndpoint(){
        return "Payment OK";
    }

}
