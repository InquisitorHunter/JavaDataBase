package org.example;

import java.util.ArrayList;

public interface ITable {
    public void insertRecord(ArrayList<String> values) throws Exception;
    public void updateRecord(Integer row, String column, String newValue);
    public void removeRecord(Integer row);
}
