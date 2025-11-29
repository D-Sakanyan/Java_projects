package main.java.ru.skillbox.notification;

import java.util.Arrays;
import java.util.List;

public class SmsNotification implements Notification{
    public static String text = "Спасибо за регистрацию на сервисе!";
    public static List<String> phoneNums = Arrays.asList("+1111111");

    public SmsNotification(String text, List<String> phoneNums){
        this.text = text;
        this.phoneNums = phoneNums;
    }

    @Override
    public String formattedMessage() {
        return  "Message: " + text + "\n" +
                "Receivers: " + String.join(", ", phoneNums);
    }
}
