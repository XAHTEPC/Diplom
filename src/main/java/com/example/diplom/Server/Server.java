package com.example.diplom.Server;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class Server {
    private static final String PASSWORD_HASH = "yiW7qUCcIG0LOux4ncUw7pfDDH5YbcGJIaLwgEi7gtB6mqTSTnHEIEALsvXtkSfmYPHfXK1qxCCaVMkoGs/RiA=="; // Захэшированный пароль
    private static final String LOGIN = "user";
    private static final String COMPUTER_HASH = "uJxkEHtbJmQ77cDrZ6OIPyWmT+qibqomAMJUCn+QsbcWjrmVdHo4CU4TAZsNm2o/cfegOD25mH5JoPHPBhDcjA==";

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
                    String uniqueComputerIdentifier = in.readLine();
                    System.out.println("Получены данные от клиента: Логин = " + login + ", Хэш пароля = " + hashedPassword);
                    boolean ans = check(login, hashedPassword,uniqueComputerIdentifier);
                    out.println(ans);
                    System.out.println("Ответ клиенту: " + ans);
                }
            }
        }
    }
    public static boolean check (String login, String pass_hash, String computerID) {
        if(LOGIN.equals(login)){
            if(PASSWORD_HASH.equals(pass_hash)){
                if(COMPUTER_HASH.equals(COMPUTER_HASH)){
                    return true;
                }
                System.out.println("error comp");
            }
            System.out.println("error pass");
        }
        System.out.println("error login");
        return false;
    }

}