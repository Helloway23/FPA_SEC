package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Notification;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(String title, String message, User user) {
        Notification notification = new Notification();
        notification.setNotificationTitle(title);
        notification.setMessage(message);
        notification.setRecipient(user);
        notificationRepository.save(notification);
        return notification;
    }

    public List<Notification> getNotificationsForRecipient(Long userId) {
        return notificationRepository.findByRecipientId(userId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
