package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ICursor extends ResultSet {
    public ICursor getTables();
    ICursor getTable(String tableName);
    ICursor getTableInfo(String tableName);
    ICursor execute(String query);
    List<String[]> getData();
    public Integer getRecordCount();
    public Integer getColumnsCount();
    public String getColumnName(int index);
    boolean first();
    boolean last();
    int getRow();
    boolean next();
    boolean previous();
    public String getValue(int column);
    public String getValue(String column) throws SQLException;
}