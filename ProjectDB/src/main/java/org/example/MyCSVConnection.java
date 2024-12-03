package org.example;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;


public class MyCSVConnection implements IConnection {
    private String filePath;
    private String directoryPath;

    public MyCSVConnection(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void connect() {
        System.out.println("Opening CSV file at " + filePath);

    }

    public ICursor getTables() throws SQLException {
        return MyCSVCursor.getTables("information_schema.tables", directoryPath);
    }

    public ICursor getTable(String tableName) throws IOException, SQLException {
        return new MyCSVCursor(filePath);
    }

    @Override
    public ICursor getTableInfo(String tableName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath, tableName)));
            String headerLine = reader.readLine();
            return new MyCSVCursor(new BufferedReader(new StringReader(headerLine)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet execute(String query) throws IOException, SQLException {
        return getTable(null);
    }

    @Override
    public ITable getTableInterface(String filePath) {
        try {
            return new MyCSVTable(filePath);
        } catch (IOException | SQLException e) {
            System.err.println("Error creating CSV table interface: " + e.getMessage());
            return null;
        }
    }
}