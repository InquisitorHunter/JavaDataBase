package org.example;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyCSVTable extends MyCSVCursor implements ITable {
    private final String filePath;
    private List<String[]> data;

    public MyCSVTable(String filePath) throws IOException, SQLException {
        super(filePath);
        this.filePath = filePath;
        this.data = new ArrayList<>();
    }

    @Override
    public void insertRecord(ArrayList<String> values) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String record = String.join(",", values);
            writer.write(record);
            writer.newLine();
            writer.flush();
            super.loadFile(filePath);
        } catch (IOException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    @Override
    public void updateRecord(Integer row, String column, String newValue) {
        List<String[]> data = readCSVFile(filePath);
        int columnIndex = getColumnIndex(data, column);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column not found.");
        }
        if (row < 0 || row >= data.size()) {
            throw new IllegalArgumentException("Row index out of bounds.");
        }
        data.get(row)[columnIndex] = newValue;
        writeCSVFile(filePath, data);
        System.out.println("Record successfully updated in CSV.");
    }

    private int writeCSVFile(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private List<String[]> readCSVFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().map(line -> line.split(",")).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getColumnIndex(List<String[]> data, String column) {
        String[] header = data.get(0);
        for (int i = 0; i < header.length; i++) {
            if (header[i].equalsIgnoreCase(column)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void removeRecord(Integer row) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (row >= 0 && row < lines.size()) {
            lines.remove((int) row);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
                System.out.println("Record at index " + row + " removed from CSV file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid row index.");
        }
    }
}