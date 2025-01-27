package com.example.diplom.Server.Postgre;

import java.sql.*;

public class Postgre {
    static Connection connection;
    static Statement statmt;
    static ResultSet data_resSet;
    public Postgre(String login, String pass) throws SQLException {
//        System.out.println("В контрукторе до коннекта: "+login+"|"+pass);
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Diplom1",login,pass);
        statmt = connection.createStatement();
//        System.out.println(login+"|+|"+pass);
//        getUserID(login,pass);
    }
}
