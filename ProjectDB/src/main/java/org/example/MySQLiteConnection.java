package org.example;

import java.sql.*;

public class MySQLiteConnection implements IConnection {
    private String url;
    private Connection connection;

    public MySQLiteConnection(String url) {
        this.url = url;
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to SQLite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ICursor getTables() throws SQLException {
        return MySQLiteCursor.getTables(connection);
    }

    public ICursor getTable(String tableName) throws SQLException {
        return MySQLiteCursor.getTable(tableName, connection);
    }

    public ICursor getTableInfo(String tableName) throws SQLException {
        return MySQLiteCursor.getTableInfo(tableName, connection);
    }

    @Override
    public ResultSet execute(String query) throws SQLException {
        return MySQLiteCursor.execute(query, connection);
    }

    @Override
    public ITable getTableInterface(String tableName) {
        try {
            return new MySQLiteTable(tableName, this);
        } catch (SQLException e) {
            System.err.println("Error creating SQLite table interface: " + e.getMessage());
            return null;
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}