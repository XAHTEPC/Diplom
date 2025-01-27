package com.example.diplom.Client;

import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "localhost"; // Адрес сервера
        int port = 12345; // Порт сервера

        // Отключение проверки сертификатов (для тестирования)
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Создание SSL-сокета
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(host, port)) {
            System.out.println("Подключено к серверу " + host + " на порту " + port);

            // Отправка логина и пароля на сервер
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String login = "user1";
            String password = "password124";
            String salt = generateSaltFromLogin(login);
            String hashedPassword = hashPasswordWithSalt(password, salt);

            // Хэширование пароля с использованием Argon2

//            String hashedPassword = hashPassword(password, login);
            // Отправка данных
            out.println(login);
            out.println(hashedPassword);
            System.out.println("pass:" + password + " hash:" + hashedPassword);
            System.out.println("Отправлены данные: Логин = " + login + ", Хэш пароля = " + hashedPassword);

            // Получение ответа от сервера
            String response = in.readLine();
            System.out.println("Ответ от сервера: " + response);
        }
    }

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
