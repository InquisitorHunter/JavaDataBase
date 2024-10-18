package org.example;

import java.io.*;
import java.util.Scanner;

public class MyCSVConnection {
    private String filePath;
    private File file;
    public void openConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до CSV-файлу: ");
        filePath = scanner.nextLine() + ".csv";
        file = new File(filePath);
        if (file.exists()) {
            System.out.println("Файл знайдено: " + filePath);
        } else {
            System.out.println("Файл не знайдено, створюється новий файл: " + filePath);
            createCSVFile();
        }
    }

    public void createCSVFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Name,Email,Phone");  // Заголовки для нового файлу
            writer.newLine();
            System.out.println("Новий CSV-файл '" + filePath + "' створено з заголовками.");
        } catch (IOException e) {
            System.out.println("Помилка при створенні файлу: " + e.getMessage());
        }
    }

    public void writeCSV() {
        if (file == null || !file.exists()) {
            System.out.println("Файл не підключено. Спочатку підключіться до CSV-файлу.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ім'я: ");
        String name = scanner.nextLine();
        System.out.print("Введіть електронну пошту: ");
        String email = scanner.nextLine();
        System.out.print("Введіть номер телефону: ");
        String phone = scanner.nextLine();
        String data = name + "," + email + "," + phone;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
            System.out.println("Дані успішно записані у файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }

    public void readCSV() {
        if (file == null || !file.exists()) {
            System.out.println("Файл не підключено. Спочатку підключіться до CSV-файлу.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Вміст файлу " + filePath + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу: " + e.getMessage());
        }
    }
}