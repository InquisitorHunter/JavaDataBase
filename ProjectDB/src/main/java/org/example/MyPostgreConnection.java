package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MyPostgreConnection {
    private Connection connection;
    private String url;
    private String user;
    private String password;

    public void openConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL підключення: ");
        url = scanner.nextLine();
        System.out.print("Введіть ім'я користувача: ");
        user = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        password = scanner.nextLine();
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Підключено до бази даних PostgreSQL.");
        } catch (SQLException e) {
            System.out.println("Не вдалося підключитися до бази даних: " + e.getMessage());
        }
    }


    public void createDatabase() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву бази даних для створення: ");
        String dbName = scanner.nextLine();
        String sql = "CREATE DATABASE " + dbName;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Базу даних '" + dbName + "' успішно створено.");
        } catch (SQLException e) {
            System.out.println("Помилка при створенні бази даних: " + e.getMessage());
        }
    }

}
