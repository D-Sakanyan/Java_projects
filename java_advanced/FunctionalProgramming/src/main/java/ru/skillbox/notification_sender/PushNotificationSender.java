package main.java.ru.skillbox.notification_sender;

import main.java.ru.skillbox.notification.Notification;

import java.util.List;

public class PushNotificationSender implements NotificationSender<Notification>{
    @Override
    public void send(Notification notification) {
        System.out.println(notification.formattedMessage() + '\n');
    }

    @Override
    public void send(List<Notification> notifications) {
        for (Notification n : notifications) {
            System.out.println(n.formattedMessage() + '\n');
        }
    }
}
