package com.codeup.plantapp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class stuffTest {

    public static void main(String[] args) {
        long newYorkOne = (1688531528L + 3600) * 1000;
        long newYorkTwo = (1688583951L + 3600) * 1000;
        long antonioOne = (1688557155L + -18000) * 1000;
        long antonioTwo = (1688607447L + -18000) * 1000;

        Date date = new Date((1688531528L + 3600) * 1000);
        System.out.println(date);
    }

    public static LocalDateTime convertUnixTimestampToLocalDateTime(long unixTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneId.systemDefault());
    }
}