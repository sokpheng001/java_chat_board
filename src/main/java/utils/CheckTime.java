package utils;

import java.time.LocalTime;

public class CheckTime {
    public static String checkTimeOfDay(String message) {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        if (hour >= 5 && hour < 12) {
            return "🌅 Good Morning!, " + message;
        } else if (hour >= 12 && hour < 18) {
            return "☀️ Good Afternoon!, " + message;
        } else {
            return "🌙 Good Evening!, " + message;
        }
    }
}
