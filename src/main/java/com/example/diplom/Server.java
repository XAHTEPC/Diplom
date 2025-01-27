package com.example.diplom;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Server {
    private static final String STORED_HASH = "yiW7qUCcIG0LOux4ncUw7pfDDH5YbcGJIaLwgEi7gtB6mqTSTnHEIEALsvXtkSfmYPHfXK1qxCCaVMkoGs/RiA=="; // Захэшированный пароль

    public static void main(String[] args) throws Exception {
        int port = 12345; // Порт сервера

        // Загрузка хранилища ключей (keystore)
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreStream = new FileInputStream("server.keystore")) {
            keyStore.load(keyStoreStream, "password".toCharArray());
        }

        // Настройка SSL контекста
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "password".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Создание SSL-сервера
        SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
        try (SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                try (SSLSocket clientSocket = (SSLSocket) serverSocket.accept()) {
                    System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

                    // Работа с клиентом
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Получение логина и пароля
                    String login = in.readLine();
                    String hashedPassword = in.readLine();
                    System.out.println("Получены данные от клиента: Логин = " + login + ", Хэш пароля = " + hashedPassword);
                    boolean pass = STORED_HASH.equals(hashedPassword);
                    out.println(pass);
                    System.out.println("Ответ клиенту: " + pass);
                }
            }
        }
    }
//    public static boolean verifyPassword(String enteredPassword, String storedHash, String login) {
//        Argon2 argon2 = Argon2Factory.create();
//
//        // Генерация фиксированной соли на основе логина
//        String fixedSalt = login + "_fixed_salt";  // Использование той же фиксированной соли
//
//        // Генерация параметров
//        int iterations = getIterationsFromLogin(login);
//        int memory = getMemoryFromLogin(login);
//        int parallelism = getParallelismFromLogin(login);
//
//        // Проверяем введённый пароль с хэшем
//        return argon2.verify(storedHash, enteredPassword.toCharArray());
//    }

}