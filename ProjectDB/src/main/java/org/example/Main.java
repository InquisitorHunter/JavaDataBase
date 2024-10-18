package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyCSVConnection csvConnection = new MyCSVConnection();
        MyPostgreConnection pgConnection = new MyPostgreConnection();
        MySQLiteConnection sqlConnection = new MySQLiteConnection();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("Головне меню");
            System.out.println("1. Підключитися до CSV-файлу");
            System.out.println("2. Записати дані у CSV-файл");
            System.out.println("3. Прочитати дані з CSV-файлу");
            System.out.println("4. Підключитися до PostgreSQL");
            System.out.println("5. Створити базу даних PostgreSQL");
            System.out.println("6. Підключитися до SQLite");
            System.out.println("7. Створити таблицю SQLite");
            System.out.println("8. Додати дані SQLite");
            System.out.println("9. Переглянути таблицю SQLite");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    csvConnection.openConnection();
                    break;
                case 2:
                    csvConnection.writeCSV();
                    break;
                case 3:
                    csvConnection.readCSV();
                    break;
                case 4:
                    pgConnection.openConnection();
                    break;
                case 5:
                    pgConnection.createDatabase();
                    break;
                case 6:
                    sqlConnection.openConnection();
                    break;
                case 7:
                    System.out.print("Введіть назву таблиці: ");
                    String tableName = scanner.nextLine();
                    System.out.print("Введіть кількість стовпців: ");
                    int columnCount = scanner.nextInt();
                    scanner.nextLine();

                    StringBuilder columns = new StringBuilder();
                    for (int i = 0; i < columnCount; i++) {
                        System.out.print("Введіть назву стовпця " + (i + 1) + ": ");
                        String columnName = scanner.nextLine();
                        System.out.print("Введіть тип даних для стовпця " + columnName + " (TEXT, INTEGER, REAL): ");
                        String columnType = scanner.nextLine();

                        columns.append(columnName).append(" ").append(columnType);
                        if (i < columnCount - 1) {
                            columns.append(", ");
                        }
                    }

                    columns.insert(0, "id INTEGER PRIMARY KEY AUTOINCREMENT, ");
                    sqlConnection.createTable(tableName, columns.toString());
                    break;
                case 8:
                    System.out.print("Введіть назву таблиці для додавання даних: ");
                    String insertTableName = scanner.nextLine();
                    System.out.print("Введіть кількість стовпців для вставки даних: ");
                    int insertColumnCount = scanner.nextInt();
                    scanner.nextLine();  // Очищення буфера
                    StringBuilder insertColumns = new StringBuilder();
                    StringBuilder insertValues = new StringBuilder();
                    for (int i = 0; i < insertColumnCount; i++) {
                        System.out.print("Введіть назву стовпця: ");
                        String insertColumnName = scanner.nextLine();
                        System.out.print("Введіть значення для стовпця " + insertColumnName + ": ");
                        String insertValue = scanner.nextLine();

                        insertColumns.append(insertColumnName);
                        insertValues.append("'").append(insertValue).append("'");
                        if (i < insertColumnCount - 1) {
                            insertColumns.append(", ");
                            insertValues.append(", ");
                        }
                    }
                    sqlConnection.insertData(insertTableName, insertColumns.toString(), insertValues.toString());
                    break;
                case 9:
                    System.out.print("Введіть назву таблиці для перегляду: ");
                    String viewTableName = scanner.nextLine();
                    sqlConnection.viewData(viewTableName);
                    break;
                case 0:
                    System.out.println("Вихід з програми.");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        } while (choice != 0);
        scanner.close();
    }
}