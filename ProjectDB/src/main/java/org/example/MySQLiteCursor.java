package org.example;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MySQLiteCursor implements ICursor {
    private ResultSet resultSet;
    private List<String[]> data = new ArrayList<>();
    private int currentRow = -1;
    private List<String> columnNames = new ArrayList<>();

    public MySQLiteCursor(ResultSet resultSet) throws SQLException {
        this.resultSet = resultSet;
        loadResultSetData(resultSet);
    }

    private void loadResultSetData(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        while (resultSet.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = resultSet.getString(i + 1);
            }
            data.add(row);
        }
    }

    public static ICursor getTables(Connection connection) throws SQLException {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return new MySQLiteCursor(resultSet);
    }

    public static ICursor getTable(String tableName, Connection connection) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return new MySQLiteCursor(resultSet);
    }


    public static ICursor getTableInfo(String tableName, Connection connection) throws SQLException {
        String query = "PRAGMA table_info(" + tableName + ")";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return new MySQLiteCursor(resultSet);
    }


    public static ICursor execute(String query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);
        return new MySQLiteCursor(resultSet);
    }

    @Override
    public List<String[]> getData() {
        return data;
    }

    @Override
    public Integer getRecordCount() {
        return data.size();
    }

    @Override
    public Integer getColumnsCount() {
        return columnNames.size();
    }

    @Override
    public boolean first() {
        if (!data.isEmpty()) {
            currentRow = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean last() {
        if (!data.isEmpty()) {
            currentRow = data.size() - 1;
            return true;
        }
        return false;
    }

    @Override
    public int getRow() {
        return currentRow + 1;
    }

    @Override
    public boolean absolute(int row) throws SQLException {return false;}

    @Override
    public boolean relative(int rows) throws SQLException {return false;}

    @Override
    public boolean next() {
        try {
            if (resultSet != null) {
                return resultSet.next();
            } else {
                System.out.println("ResultSet is not initialized.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error moving to the next record: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public boolean wasNull() throws SQLException {return false;}

    @Override
    public String getString(int columnIndex) throws SQLException {return "";}

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {return false;}

    @Override
    public byte getByte(int columnIndex) throws SQLException {return 0;}

    @Override
    public short getShort(int columnIndex) throws SQLException {return 0;}

    @Override
    public int getInt(int columnIndex) throws SQLException {return 0;}

    @Override
    public long getLong(int columnIndex) throws SQLException {return 0;}

    @Override
    public float getFloat(int columnIndex) throws SQLException {return 0;}

    @Override
    public double getDouble(int columnIndex) throws SQLException {return 0;}

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {return null;}

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {return new byte[0];}

    @Override
    public Date getDate(int columnIndex) throws SQLException {return null;}

    @Override
    public Time getTime(int columnIndex) throws SQLException {return null;}

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {return null;}

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {return null;}

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {return null;}

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {return null;}

    @Override
    public String getString(String columnLabel) throws SQLException {return "";}

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {return false;}

    @Override
    public byte getByte(String columnLabel) throws SQLException {return 0;}

    @Override
    public short getShort(String columnLabel) throws SQLException {return 0;}

    @Override
    public int getInt(String columnLabel) throws SQLException {return 0;}

    @Override
    public long getLong(String columnLabel) throws SQLException {return 0;}

    @Override
    public float getFloat(String columnLabel) throws SQLException {return 0;}

    @Override
    public double getDouble(String columnLabel) throws SQLException {return 0;}

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {return null;}

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {return new byte[0];}

    @Override
    public Date getDate(String columnLabel) throws SQLException {return null;}

    @Override
    public Time getTime(String columnLabel) throws SQLException {return null;}

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {return null;}

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {return null;}

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {return null;}

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {return null;}

    @Override
    public SQLWarning getWarnings() throws SQLException {return null;}

    @Override
    public void clearWarnings() throws SQLException {}

    @Override
    public String getCursorName() throws SQLException {return "";}

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {return null;}

    @Override
    public Object getObject(int columnIndex) throws SQLException {return null;}

    @Override
    public Object getObject(String columnLabel) throws SQLException {return null;}

    @Override
    public int findColumn(String columnLabel) throws SQLException {return 0;}

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {return null;}

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {return null;}

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {return null;}

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {return null;}

    @Override
    public boolean isBeforeFirst() throws SQLException {return false;}

    @Override
    public boolean isAfterLast() throws SQLException {return false;}

    @Override
    public boolean isFirst() throws SQLException {return false;}

    @Override
    public boolean isLast() throws SQLException {return false;}

    @Override
    public void beforeFirst() throws SQLException {}

    @Override
    public void afterLast() throws SQLException {}

    @Override
    public boolean previous() {
        try {
            if (resultSet != null && resultSet.getType() != ResultSet.TYPE_FORWARD_ONLY) {
                if (resultSet.previous()) {
                    return true;
                } else {
                    System.out.println("No previous record available.");
                }
            } else if (resultSet == null) {
                System.out.println("ResultSet is not initialized.");
            } else {
                System.out.println("ResultSet does not support backward scrolling.");
            }
        } catch (SQLException e) {
            System.out.println("Error moving to the previous record: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {}

    @Override
    public int getFetchDirection() throws SQLException {return 0;}

    @Override
    public void setFetchSize(int rows) throws SQLException {}

    @Override
    public int getFetchSize() throws SQLException {return 0;}

    @Override
    public int getType() throws SQLException {return 0;}

    @Override
    public int getConcurrency() throws SQLException {return 0;}

    @Override
    public boolean rowUpdated() throws SQLException {return false;}

    @Override
    public boolean rowInserted() throws SQLException {return false;}

    @Override
    public boolean rowDeleted() throws SQLException {return false;}

    @Override
    public void updateNull(int columnIndex) throws SQLException {}

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {}

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {}

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {}

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {}

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {}

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {}

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {}

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {}

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {}

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {}

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {}

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {}

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {}

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {}

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {}

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {}

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {}

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {}

    @Override
    public void updateNull(String columnLabel) throws SQLException {}

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {}

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {}

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {}

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {}

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {}

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {}

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {}

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {}

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {}

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {}

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {}

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {}

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {}

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {}

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {}

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {}

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {}

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {}

    @Override
    public void insertRow() throws SQLException {}

    @Override
    public void updateRow() throws SQLException {}

    @Override
    public void deleteRow() throws SQLException {}

    @Override
    public void refreshRow() throws SQLException {}

    @Override
    public void cancelRowUpdates() throws SQLException {}

    @Override
    public void moveToInsertRow() throws SQLException {}

    @Override
    public void moveToCurrentRow() throws SQLException {}

    @Override
    public Statement getStatement() throws SQLException {return null;}

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {return null;}

    @Override
    public Ref getRef(int columnIndex) throws SQLException {return null;}

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {return null;}

    @Override
    public Clob getClob(int columnIndex) throws SQLException {return null;}

    @Override
    public Array getArray(int columnIndex) throws SQLException {return null;}

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {return null;}

    @Override
    public Ref getRef(String columnLabel) throws SQLException {return null;}

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {return null;}

    @Override
    public Clob getClob(String columnLabel) throws SQLException {return null;}

    @Override
    public Array getArray(String columnLabel) throws SQLException {return null;}

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {return null;}

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {return null;}

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {return null;}

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {return null;}

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {return null;}

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {return null;}

    @Override
    public URL getURL(int columnIndex) throws SQLException {return null;}

    @Override
    public URL getURL(String columnLabel) throws SQLException {return null;}

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {}

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {}

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {}

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {}

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {}

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {}

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {}

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {}

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {return null;}

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {return null;}

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {}

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {}

    @Override
    public int getHoldability() throws SQLException {return 0;}

    @Override
    public boolean isClosed() throws SQLException {return false;}

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {}

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {}

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {}

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {}

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {return null;}

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {return null;}

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {return null;}

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {return null;}

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {}

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {}

    @Override
    public String getNString(int columnIndex) throws SQLException {return "";}

    @Override
    public String getNString(String columnLabel) throws SQLException {return "";}

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {return null;}

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {return null;}

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {}

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {}

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {}

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {}

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {}

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {}

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {}

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {}

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {}

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {}

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {}

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {}

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {}

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {}

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {}

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {}

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {}

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {}

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {}

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {}

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {}

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {}

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {}

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {}

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {}

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {}

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {}

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {}

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {return null;}

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {return null;}

    @Override
    public String getColumnName(int index) {
        if (index >= 1 && index <= columnNames.size()) {
            return columnNames.get(index - 1);
        }
        return null;
    }

    @Override
    public String getValue(int column) {
        if (currentRow >= 0 && currentRow < data.size() && column >= 1 && column <= columnNames.size()) {
            return data.get(currentRow)[column - 1];
        }
        return null;
    }

    @Override
    public String getValue(String column) {
        try {
            return resultSet.getString(column);
        } catch (SQLException e) {
            System.err.println("Error retrieving value for column: " + column);
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ICursor getTables() {
        return null;
    }

    @Override
    public ICursor getTable(String tableName) {
        return null;
    }

    @Override
    public ICursor getTableInfo(String tableName) {
        return null;
    }

    @Override
    public ICursor execute(String query) {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {return null;}

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {return false;}

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}