package main.java.ru.skillbox.notification;

import java.util.Arrays;
import java.util.List;

public class EmailNotification implements Notification {

    public static String text = "Спасибо за регистрацию на сервисе!";
    public static String subject =  "Успешная регистрация";
    public static List<String> recipient = Arrays.asList("user1@gmail.com","user2@gmail.com", "user3@gmail.com");

    public EmailNotification(String text, String subject, List<String> recipient) {
        this.text = text;
        this.subject = subject;
        this.recipient = recipient;
    }
    @Override
    public String formattedMessage() {
        return  "Subject: " + subject + "\n" +
                "Receivers: " + String.join(", ", recipient) + '\n' +
                "Message: " + "<p>" + text + "</p>";
    }
}
