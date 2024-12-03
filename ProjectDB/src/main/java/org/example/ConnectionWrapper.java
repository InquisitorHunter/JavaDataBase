package org.example;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class ConnectionWrapper {
    private IConnection connection;

    public void openConnection() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. CSV");
        System.out.println("2. PostgreSQL");
        System.out.println("3. SQLite");
        System.out.println("4. Exit");
        System.out.print("Select the connection type: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the path to the CSV: ");
                String csvPath = scanner.nextLine();
                connection = new MyCSVConnection(csvPath);
                break;
            case 2:
                System.out.print("Enter the PostgreSQL URL: ");
                String postgreUrl = scanner.nextLine();
                System.out.print("Enter a user: ");
                String postgreUser = scanner.nextLine();
                System.out.print("Enter a password: ");
                String postgrePassword = scanner.nextLine();
                connection = new MyPostgreConnection(postgreUrl, postgreUser, postgrePassword);
                break;
            case 3:
                System.out.print("Enter the SQLite URL: ");
                String sqliteUrl = scanner.nextLine();
                connection = new MySQLiteConnection(sqliteUrl);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Wrong choice.");
                return;
        }
        connection.connect();
        menu();
    }

    private void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Get tables");
            System.out.println("2. Get data from a table");
            System.out.println("3. Get information about table attributes");
            System.out.println("4. Execute the request");
            System.out.println("5. Get record count");
            System.out.println("6. Get column count");
            System.out.println("7. Get column name by index");
            System.out.println("8. Move cursor to first record");
            System.out.println("9. Move cursor to the last record");
            System.out.println("10. Move cursor to the current cursor record");
            System.out.println("11. Move cursor to the current next record");
            System.out.println("12. Move cursor to the current next entry");
            System.out.println("13. Get value by column index");
            System.out.println("14. Get value by column name");
            System.out.println("15. Insert a record into a table");
            System.out.println("16. Update a record in a table");
            System.out.println("17. Remove a record from a table");
            System.out.println("18. Exit the program");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    try {
                        ICursor tablesCursor = connection.getTables();
                        if (tablesCursor == null) {
                            System.out.println("No tables available.");
                        } else {
                            displayCursorData(tablesCursor);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving tables: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter the table name: ");
                    String tableName = scanner.nextLine();
                    try {
                        ICursor tableCursor = connection.getTable(tableName);
                        if (tableCursor == null) {
                            System.out.println("Table not found or no data.");
                        } else {
                            displayCursorData(tableCursor);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving table data: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter the table name: ");
                    String infoTableName = scanner.nextLine();
                    try {
                        ICursor infoCursor = connection.getTableInfo(infoTableName);
                        displayCursorData(infoCursor);
                    } catch (SQLException e) {
                        System.out.println("Error retrieving table info: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter the SQL query: ");
                    String query = scanner.nextLine();
                    try {
                        ICursor queryCursor = (ICursor) connection.execute(query);
                        displayCursorData(queryCursor);
                    } catch (SQLException e) {
                        System.out.println("Error executing query: " + e.getMessage());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    System.out.print("Enter the table name to count records: ");
                    String countTableName = scanner.nextLine();
                    try {
                        ICursor countCursor = connection.getTable(countTableName);
                        if (countCursor != null) {
                            int recordCount = countCursor.getRecordCount();
                            System.out.println("Record count: " + recordCount);
                        } else {
                            System.out.println("Unable to retrieve record count.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving record count: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Enter the table name to get column count: ");
                    String columnTableName = scanner.nextLine();
                    try {
                        ICursor columnCursor = connection.getTable(columnTableName);
                        if (columnCursor != null) {
                            int columnCount = columnCursor.getColumnsCount();
                            System.out.println("Column count: " + columnCount);
                        } else {
                            System.out.println("Unable to retrieve column count.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving column count: " + e.getMessage());
                    }
                    break;
                case 7:
                    System.out.print("Enter the table name to get column name by index: ");
                    String columnNameTableName = scanner.nextLine();
                    try {
                        ICursor columnNameCursor = connection.getTable(columnNameTableName);
                        if (columnNameCursor != null) {
                            System.out.print("Enter the column index: ");
                            int columnIndex = scanner.nextInt();
                            scanner.nextLine();
                            String columnName = columnNameCursor.getColumnName(columnIndex);
                            if (columnName != null) {
                                System.out.println("Column name at index " + columnIndex + ": " + columnName);
                            } else {
                                System.out.println("Invalid column index.");
                            }
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving column name: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Enter the table name to move cursor to the first record: ");
                    String firstRecordTableName = scanner.nextLine();
                    try {
                        ICursor firstRecordCursor = connection.getTable(firstRecordTableName);
                        if (firstRecordCursor != null) {
                            boolean isFirst = firstRecordCursor.first();
                            if (isFirst) {
                                System.out.println("Cursor moved to the first record.");
                            } else {
                                System.out.println("Unable to move cursor to the first record.");
                            }
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error moving cursor to first record: " + e.getMessage());
                    }
                    break;
                case 9:
                    System.out.print("Enter the table name to move cursor to the last record: ");
                    String lastRecordTableName = scanner.nextLine();
                    try {
                        ICursor lastRecordCursor = connection.getTable(lastRecordTableName);
                        if (lastRecordCursor != null) {
                            boolean isLast = lastRecordCursor.last();
                            if (isLast) {
                                System.out.println("Cursor moved to the last record.");
                            } else {
                                System.out.println("Unable to move cursor to the last record.");
                            }
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error moving cursor to last record: " + e.getMessage());
                    }
                    break;
                case 10:
                    System.out.print("Enter the table name to get current row number: ");
                    String rowTableName = scanner.nextLine();
                    try {
                        ICursor rowCursor = connection.getTable(rowTableName);
                        if (rowCursor != null) {
                            int currentRow = rowCursor.getRow();
                            System.out.println("Current row number: " + currentRow);
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving current row number: " + e.getMessage());
                    }
                    break;
                case 11:
                    System.out.print("Enter the table name to move cursor to the next record: ");
                    String nextRecordTableName = scanner.nextLine();
                    try {
                        ICursor nextRecordCursor = connection.getTable(nextRecordTableName);
                        if (nextRecordCursor != null) {
                            boolean hasNext = nextRecordCursor.next();
                            if (hasNext) {
                                System.out.println("Cursor moved to the next record.");
                            } else {
                                System.out.println("No more records available.");
                            }
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error moving cursor to next record: " + e.getMessage());
                    }
                    break;
                case 12:
                    System.out.print("Enter the table name to move cursor to the previous record: ");
                    String prevRecordTableName = scanner.nextLine();
                    try {
                        ICursor prevRecordCursor = connection.getTable(prevRecordTableName);
                        if (prevRecordCursor != null) {
                            boolean hasPrevious = prevRecordCursor.previous();
                            if (hasPrevious) {
                                System.out.println("Cursor moved to the previous record.");
                            } else {
                                System.out.println("No previous records available.");
                            }
                        } else {
                            System.out.println("Unable to retrieve table data.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error moving cursor to previous record: " + e.getMessage());
                    }
                    break;
                case 13:
                    System.out.print("Enter the table name to get value by column index: ");
                    String valueTableName = scanner.nextLine();
                    ICursor cursor = null;
                    try {
                        cursor = connection.getTable(valueTableName);
                    } catch (SQLException e) {
                        System.out.println("Error while retrieving table: " + e.getMessage());
                        return;
                    }
                    if (cursor != null) {
                        if (cursor.first()) {
                            System.out.print("Enter the column index: ");
                            int columnIndex = scanner.nextInt();
                            scanner.nextLine();
                            String value = cursor.getValue(columnIndex);
                            if (value != null) {
                                System.out.println("Value at column index " + columnIndex + ": " + value);
                            } else {
                                System.out.println("Invalid column index or no data in current row.");
                            }
                        } else {
                            System.out.println("No data available.");
                        }
                    } else {
                        System.out.println("Table not found.");
                    }
                    break;
                case 14:
                    System.out.print("Enter the table name to get value by column name: ");
                    String tableNameForColumn = scanner.nextLine();
                    ICursor columnCursor = null;
                    try {
                        columnCursor = connection.getTable(tableNameForColumn);
                    } catch (SQLException e) {
                        System.out.println("Error retrieving table: " + e.getMessage());
                        break;
                    }
                    if (columnCursor != null) {
                        if (columnCursor.first()) {
                            System.out.print("Enter the column name: ");
                            String columnName = scanner.nextLine();

                            try {
                                String value = columnCursor.getValue(columnName);
                                if (value != null) {
                                    System.out.println("Value in column '" + columnName + "': " + value);
                                } else {
                                    System.out.println("Invalid column name or no data in current row.");
                                }
                            } catch (Exception e) {
                                System.out.println("Error retrieving value: " + e.getMessage());
                            }
                        } else {
                            System.out.println("No data available.");
                        }
                    } else {
                        System.out.println("Table not found.");
                    }
                    break;
                case 15:
                    System.out.print("Enter the table name or CSV file path: ");
                    String tableOrFileName = scanner.nextLine();
                    System.out.print("Enter the number of values to insert: ");
                    int valueCount = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<String> valuesToInsert = new ArrayList<>();
                    for (int i = 0; i < valueCount; i++) {
                        System.out.print("Enter value " + (i + 1) + ": ");
                        valuesToInsert.add(scanner.nextLine());
                    }
                    try {
                        ITable table = connection.getTableInterface(tableOrFileName);
                        if (table != null) {
                            table.insertRecord(valuesToInsert);
                            System.out.println("Record successfully inserted.");
                        } else {
                            System.out.println("Table or file not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error inserting record: " + e.getMessage());
                    }
                    break;
                case 16:
                    System.out.print("Enter the table name or CSV file path: ");
                    String updateTableName = scanner.nextLine();
                    try {
                        ITable updateTable = connection.getTableInterface(updateTableName);
                        if (updateTable != null) {
                            System.out.print("Enter the row number to update: ");
                            int row = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter the column name: ");
                            String column = scanner.nextLine();
                            System.out.print("Enter the new value: ");
                            String newValue = scanner.nextLine();
                            updateTable.updateRecord(row, column, newValue);
                            System.out.println("Record successfully updated.");
                        } else {
                            System.out.println("Table or file not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error updating record: " + e.getMessage());
                    }
                    break;
                case 17:
                    System.out.print("Enter the table name or CSV file path: ");
                    String deleteTableName = scanner.nextLine();
                    try {
                        ITable deleteTable = connection.getTableInterface(deleteTableName);
                        if (deleteTable != null) {
                            System.out.print("Enter the row number to delete: ");
                            int row = scanner.nextInt();
                            scanner.nextLine();
                            deleteTable.removeRecord(row);
                            System.out.println("Record successfully deleted.");
                        } else {
                            System.out.println("Table or file not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error deleting record: " + e.getMessage());
                    }
                    break;
                case 18:
                    return;
                default:
                    System.out.println("Wrong choice.");
            }
        }
    }

    private void displayCursorData(ICursor cursor) {
        if (cursor != null) {
            List<String[]> data = cursor.getData();
            if (data != null && !data.isEmpty()) {
                for (String[] row : data) {
                    System.out.println(Arrays.toString(row));
                }
            } else {
                System.out.println("No data available.");
            }
        } else {
            System.out.println("Cursor is null.");
        }
    }
}
