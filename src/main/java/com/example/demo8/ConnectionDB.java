package com.example.demo8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo8.Settings.PATHSQLDB;

public class ConnectionDB {
    public static Connection conn;
    public static Statement statmt;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws SQLException, ClassNotFoundException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        Connection connectionDB = DriverManager.getConnection(PATHSQLDB);
        System.out.println("База Подключена!");
    }




    public static void CreateDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'vehicle' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'brand' VARCHAR(50), 'model' VARCHAR(50), 'category' VARCHAR(50), 'registrationNumber' VARCHAR(15)," +
                "'typeVehicle' VARCHAR(15), 'year' INT, hasTrailer INT(1));");

        System.out.println("Таблица создана или уже существует.");
    }
}
