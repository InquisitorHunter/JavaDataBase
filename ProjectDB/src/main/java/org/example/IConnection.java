package org.example;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnection {
    void connect();
    ICursor getTables() throws SQLException;
    ICursor getTable(String tableName) throws SQLException, IOException;
    ICursor getTableInfo(String tableName) throws SQLException, IOException;
    ResultSet execute(String query) throws SQLException, IOException;
    ITable getTableInterface(String name);
}
