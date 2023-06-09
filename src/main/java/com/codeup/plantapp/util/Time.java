package com.codeup.plantapp.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Time {

    public static LocalDateTime convertTimestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

//      String weekDay = Weather.getWeekday(usersLocalWeather.getSunset()); // FRIDAY
//      String month = Weather.getMonth(usersLocalWeather.getSunset()); // JUNE
//      int day = Weather.getDay(usersLocalWeather.getSunset()); // 9
//      int hour = Weather.getHour(usersLocalWeather.getSunset()); // 8
//      int minute = Weather.getMinute(usersLocalWeather.getSunset()); // 32

    public static String getWeekday(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().toString();
    }

    public static String getMonth(LocalDateTime dateTime) {
        return dateTime.getMonth().toString();
    }

    public static int getDay(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    //  To make calculations by hour
    public static int getHour(LocalDateTime dateTime) {
        if (dateTime.getHour() > 12) {
            return dateTime.getHour() - 12;
        } else {
            return dateTime.getHour();
        }
    }
    //  To make calculations by minute
    public static int getMinute(LocalDateTime dateTime) {
        return dateTime.getMinute();
    }
    //  To print time for USER view
    public static String printTime(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minutes = dateTime.getMinute();
        if (hour > 12) {
            return (hour - 12) + " : " + minutes + " PM";
        } else {
            return hour + " : " + minutes + " AM";
        }
    }

}