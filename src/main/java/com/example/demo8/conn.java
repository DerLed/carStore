package com.example.demo8;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:CAR.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'vehicle' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'brand' VARCHAR(50), 'model' VARCHAR(50), 'category' VARCHAR(50), 'registrationNumber' VARCHAR(15)," +
                "'typeVehicle' VARCHAR(15), 'year' INT, hasTrailer INT(1));");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statmt.execute("INSERT INTO 'vehicle' ('brand', 'model', 'category', 'registrationNumber', 'typeVehicle'," +
                "'year', 'hasTrailer')" +
                "VALUES ('ffff', 'f57', 'Кабриолет', 'Б111ББ11', 'Легковая', 2010, 0 ) ; ");
        statmt.execute("INSERT INTO 'vehicle' ('brand', 'model', 'category', 'registrationNumber', 'typeVehicle'," +
                "'year', 'hasTrailer')" +
                "VALUES ('Опель', 'Инсигния', 'Кабриолет', 'В111ВВ11', 'Легковая', 2010, 0 ) ; ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM vehicle");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  brand = resSet.getString("brand");
            String  model = resSet.getString("model");
            String category = resSet.getString("category");
            String registrationNumber = resSet.getString("registrationNumber");
            String typeVehicle = resSet.getString("typeVehicle");
            int year = resSet.getInt("year");
            int hasTrailer = resSet.getInt("hasTrailer");
            System.out.println( "ID = " + id );
            System.out.println( "Марка = " + brand);
            System.out.println( "Модель = " + model);
            System.out.println( "Категория = " + category);
            System.out.println( "Номер ТС = " + registrationNumber);
            System.out.println( "Тип ТС = " + typeVehicle);
            System.out.println( "Год выпуска = " + year);
            System.out.println( "Наличие прицепа = " + hasTrailer);

            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}

