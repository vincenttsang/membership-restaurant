package com.membership.restaurant.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashGenerator {
    public static String generateHash(String inputString) {
        String salt = BCrypt.gensalt();
        String hashedString = BCrypt.hashpw(inputString, salt);
        return hashedString;
    }

    public static boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}