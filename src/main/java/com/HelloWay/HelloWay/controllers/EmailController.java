package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.EmailDetails;
import com.HelloWay.HelloWay.services.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestParam("to") String to,
                          @RequestParam("subject") String subject,
                          @RequestParam("body") String body) {
        EmailDetails details = new EmailDetails(to, body, subject);
        emailService.sendSimpleMail(details);
    }
}
