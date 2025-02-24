package me.adityabanerjee.kafkart_backend.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptUtils {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plaintext, String hash) {
        return BCrypt.checkpw(plaintext, hash);
    }
}

