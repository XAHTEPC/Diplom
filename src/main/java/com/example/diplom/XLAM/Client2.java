package com.example.diplom.XLAM;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Client2 {
    public static void main(String[] args) throws Exception {
        String login = "user";  // Пример логина
        String password = "password124";  // Пример пароля
        String hash_pass = "$argon2i$v=19$m=196608,t=9,p=4$iJfVCEKixzM5Xt0zl+TJkg$1kuXzjQwVllYnZqD9gKpCOG21Z2UkqqLenyUe1rHPhg";
        // Хэшируем пароль с использованием фиксированной соли
        String hashedPassword = hashPassword(password, login);
        System.out.println("Хэш пароля: " + hashedPassword);

        // Пример проверки пароля
        boolean isCorrect = verifyPassword(password, hash_pass, login);
        System.out.println("Пароль верный? " + isCorrect);
    }

    // Функция для хэширования пароля
    public static String hashPassword(String pass, String login) {
        Argon2 argon2 = Argon2Factory.create();
        String hashedPassword;

        // Генерация фиксированной соли на основе логина
//        String fixedSalt = login + "_fixed_salt";  // Использование фиксированной соли для каждого логина

        // Генерация уникальных параметров для каждого пользователя
        int iterations = getIterationsFromLogin(login);
        int memory = getMemoryFromLogin(login);
        int parallelism = getParallelismFromLogin(login);

        // Преобразуем соль в массив байтов с использованием StandardCharsets.UTF_8
//        byte[] saltBytes = fixedSalt.getBytes(StandardCharsets.UTF_8);

        try {
            // Хэшируем пароль с фиксированной солью
            hashedPassword = argon2.hash(iterations, memory, parallelism, pass.toCharArray());
        } finally {
            argon2.wipeArray(pass.toCharArray());  // Очищаем массив символов пароля
        }

        return hashedPassword;
    }

    // Функция для проверки пароля
    public static boolean verifyPassword(String enteredPassword, String storedHash, String login) {
        Argon2 argon2 = Argon2Factory.create();

        // Генерация фиксированной соли на основе логина
        String fixedSalt = login + "_fixed_salt";  // Использование той же фиксированной соли

        // Генерация параметров
        int iterations = getIterationsFromLogin(login);
        int memory = getMemoryFromLogin(login);
        int parallelism = getParallelismFromLogin(login);

        // Проверяем введённый пароль с хэшем
        return argon2.verify(storedHash, enteredPassword.toCharArray());
    }

    // Пример функций для генерации параметров на основе логина
    private static int getIterationsFromLogin(String login) {
        return Math.abs(login.hashCode() % 10) + 2;  // Пример: значение от 2 до 12
    }

    private static int getMemoryFromLogin(String login) {
        return 65536 * (Math.abs(login.hashCode() % 5) + 1);  // Пример: от 65536 до 327680
    }

    private static int getParallelismFromLogin(String login) {
        return Math.abs(login.hashCode() % 4) + 1;  // Пример: от 1 до 4
    }
}