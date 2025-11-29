package main.java.ru.skillbox;

import main.java.ru.skillbox.notification.EmailNotification;
import main.java.ru.skillbox.notification.PushNotification;
import main.java.ru.skillbox.notification.SmsNotification;
import main.java.ru.skillbox.notification_sender.EmailNotificationSender;
import main.java.ru.skillbox.notification_sender.PushNotificationSender;
import main.java.ru.skillbox.notification_sender.SmsNotificationSender;

public class Main {
    public void main(String[] args) {

        System.out.println("Email Notification");
        EmailNotification emailNotification = new EmailNotification(EmailNotification.text, EmailNotification.subject, EmailNotification.recipient);
        EmailNotificationSender emailNotificationSender = new EmailNotificationSender();
        emailNotificationSender.send(emailNotification);

        System.out.println("Sms Notification");
        SmsNotification smsNotification = new SmsNotification(SmsNotification.text, SmsNotification.phoneNums);
        SmsNotificationSender smsNotificationSender = new SmsNotificationSender();
        smsNotificationSender.send(smsNotification);

        System.out.println("Push Notification");
        PushNotification pushNotification = new PushNotification(PushNotification.text, PushNotification.title, PushNotification.recipient);
        PushNotificationSender pushNotificationSender = new PushNotificationSender();
        pushNotificationSender.send(pushNotification);
    }
}
