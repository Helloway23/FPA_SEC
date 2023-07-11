package com.HelloWay.HelloWay.controllers;


import com.HelloWay.HelloWay.entities.Notification;
import com.HelloWay.HelloWay.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


    @Autowired
    NotificationService notificationService;
    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }

    @GetMapping("/providers/{userId}/notifications")
    public List<Notification> getNotificationsForProvider(@PathVariable Long userId) {
        return notificationService.getNotificationsForRecipient(userId);
    }
}
