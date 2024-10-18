package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;

public class MySQLiteConnection {
    private Connection connection;
    private String url;

    public void openConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL підключення: ");
        url = scanner.nextLine();
        try {
            // Підключення до бази даних або створення нової, якщо вона не існує
            connection = DriverManager.getConnection(url);
            System.out.println("Підключено до бази даних: " + url);
        } catch (SQLException e) {
            System.out.println("Помилка підключення: " + e.getMessage());
        }
    }

    public void createTable(String tableName, String columns) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columns + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблиця " + tableName + " успішно створена.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(String tableName, String columns, String values) {
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Дані успішно вставлені.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewData(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}