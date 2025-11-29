package main.java.ru.skillbox.notification;

import java.util.List;

public class PushNotification implements Notification{
    public static String text = "Спасибо за регистрацию на сервисе!";
    public static String title = "Успешная регистрация!";
    public static String recipient = "User@gmail.com";

    public PushNotification(String text, String title, String recipient) {
        this.text = text;
        this.title = title;
        this.recipient = recipient;
    }

    @Override
    public String formattedMessage() {
        return  "Subject: " + title + "\n" +
                "Receivers: " + recipient + "\n" +
                "Message: \ud83d\udc4b" + text;
    }
}
