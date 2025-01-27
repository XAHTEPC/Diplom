package com.example.diplom.Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main_Client {
    public static String login;
    public static String generateSaltFromLogin(String login) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] loginBytes = login.getBytes();
            byte[] salt = digest.digest(loginBytes);
            return Base64.getEncoder().encodeToString(salt); // Кодируем соль в Base64
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при создании соли", e);
        }
    }
    public static String hashPasswordWithSalt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            // Комбинируем пароль и соль
            String saltedPassword = password + salt;
            byte[] hash = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash); // Кодируем хэш в Base64
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при хэшировании пароля", e);
        }
    }
}
