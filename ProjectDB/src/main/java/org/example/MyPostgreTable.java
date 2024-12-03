package org.example;

import java.sql.SQLException;
import java.util.ArrayList;


public class MyPostgreTable extends MyPostgreCursor implements ITable {
    private final String tableName;
    private final MyPostgreConnection connection;
    

    public MyPostgreTable(String tableName, MyPostgreConnection connection) throws SQLException {
        super(connection.executeQuery("SELECT * FROM " + tableName + ";"));
        this.tableName = tableName;
        this.connection = connection;
    }
    

    @Override
    public void insertRecord(ArrayList<String> values) {
        ArrayList<String> formattedValues = new ArrayList<>();
        for (String value : values) {
            if (isNumeric(value)) {
                formattedValues.add(value);
            } else {
                formattedValues.add("'" + value.replace("'", "''") + "'");
            }
        }
        String valuesString = String.join(", ", formattedValues);
        String query = "INSERT INTO " + tableName + " VALUES (" + valuesString + ")";
        try {
            connection.executeUpdate(query);
            super.setResultSet(connection.executeQuery("SELECT * FROM " + tableName + ";"));
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void updateRecord(Integer row, String column, String newValue) {
        try {
            if (connection == null) {
                System.out.println("Database connection is not established.");
                return;
            }
            String query = "UPDATE \"" + tableName + "\" SET \"" + column + "\" = '" + newValue + "' WHERE id = " + row;
            connection.execute(query);
            System.out.println("Record successfully updated.");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    @Override
    public void removeRecord(Integer row) {
        try {
            if (connection == null) {
                System.out.println("Database connection is not established.");
                return;
            }
            String query = "DELETE FROM \"" + tableName + "\" WHERE id = " + row;
            connection.execute(query);
            System.out.println("Record successfully removed.");
        } catch (SQLException e) {
            System.out.println("Error removing record: " + e.getMessage());
        }
    }
}