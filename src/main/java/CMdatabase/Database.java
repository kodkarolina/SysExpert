package CMdatabase;

import java.sql.*;

public class Database {

    private String connectionString = "jdbc:sqlite:data/cmDatabase.db";
    private Connection connection;

    public void init() throws SQLException {
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Connection to SQLite has been established");
            createTable();
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS 'cm_database' (" +
                "'id'	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "'name'	TEXT," +
                "'bluetooth'	INTEGER," +
                "'wifi'	INTEGER," +
                "'radio'	INTEGER," +
                "'range'	INTEGER," +
                "'voltage_min'	INTEGER," +
                "'voltage_max'	INTEGER," +
                "'power_saving'	INTEGER," +
                "'direction'	INTEGER," +
                "'UART'	INTEGER," +
                "'SPI'	INTEGER," +
                "'I2C'	INTEGER," +
                "'USB'	INTEGER," +
                "'programmable'	INTEGER," +
                "'package'	INTEGER," +
                "'communication_speed' REAL, " +
                "'power_consumption'	INTEGER," +
                "'work_freq'	REAL," +
                "'encryption'	INTEGER," +
                "'price'	REAL," +
                "'arduino_support'	INTEGER);";

        Statement statement = connection.createStatement();
        statement.execute("SELECT name FROM sqlite_master WHERE type='table' AND name='cm_database';");
        if (!statement.getResultSet().next()) {
            statement.execute(createTableStatement);
            System.out.println("Potato");
        }
        statement.close();
    }

}
