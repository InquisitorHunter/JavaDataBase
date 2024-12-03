package org.example;

import java.sql.*;

public class MyPostgreConnection implements IConnection {
    private Connection connection;
    private String url;
    private String user;
    private String password;

    public MyPostgreConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ICursor getTables() throws SQLException {
        return MyPostgreCursor.getTables(connection);
    }

    @Override
    public ICursor getTable(String tableName) throws SQLException {
        return MyPostgreCursor.getTable(tableName, connection);
    }

    @Override
    public ICursor getTableInfo(String tableName) throws SQLException {
        return MyPostgreCursor.getTableInfo(tableName, connection);
    }

    public ICursor execute(String query) throws SQLException {
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet resultSet = statement.executeQuery(query);
        return new MyPostgreCursor(resultSet);
    }

    @Override
    public ITable getTableInterface(String tableName) {
        try {
            return new MyPostgreTable(tableName, this);
        } catch (SQLException e) {
            System.err.println("Error creating PostgreSQL table interface: " + e.getMessage());
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