package com.example.diplom.Server.Postgre;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.diplom.Server.Postgre.Postgre.statmt;

public class UserDB {
    public UserDB(String login, String pass) throws SQLException {
        super(); // Вызов конструктора Postgre
    }
    // Пример использования Statement
    public void executeQuery(String query) throws SQLException {
        if (statmt != null) {
            ResultSet rs = statmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Результат: " + rs.getString(1));
            }
        } else {
            System.out.println("Statement не инициализирован.");
        }
    }
}
