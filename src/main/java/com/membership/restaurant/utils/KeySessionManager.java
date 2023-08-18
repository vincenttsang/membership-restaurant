package com.membership.restaurant.utils;

import com.membership.restaurant.dtos.LoginSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class KeySessionManager {
    private static final Map<String, LoginSession> objects = new HashMap<>();

    public static LoginSession get(String key) {
        return objects.get(key);
    }

    public static void put(String key, LoginSession value) {
        objects.put(key, value);
    }

    public static void remove(String key) {
        objects.remove(key);
    }

    @Scheduled(fixedDelay = 1000 * 60) // triggered every 60s
    public void checkExpiration() {
        log.info("SessionManager: Checking session's expiration time at " + Date.valueOf(LocalDate.now()) + " " + Time.valueOf(LocalTime.now()));

        List<String> expiredList = new LinkedList<>();

        objects.forEach((key, value) -> {
            if (value.isExpired()) {
                log.info("SessionManager: Cleaning up expired session for user " + value.getUser_id());
                expiredList.add(key);
            }
        });

        for (String expiredKey : expiredList) {
            remove(expiredKey);
        }
    }
}
