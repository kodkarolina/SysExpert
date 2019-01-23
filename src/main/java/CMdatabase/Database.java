package CMdatabase;

import java.sql.*;

public class Database {

    private String connectionString = "jdbc:sqlite:data/ucDatabase.db";
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
        String createTableStatement = "CREATE TABLE IF NOT EXISTS 'uc_database' (" +
                "'id'	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "'manufacturer'	TEXT," +
                "'product_name'	TEXT," +
                "'price'	REAL," +
                "'core'	TEXT," +
                "'flash_kb'	INTEGER," +
                "'sram_bytes'	INTEGER," +
                "'pin_count'	INTEGER," +
                "'cpu_speed'	INTEGER," +
                "'comparators'	INTEGER," +
                "'ADC_input'	INTEGER," +
                "'ADC_resolution'	INTEGER," +
                "'DAC_output'	INTEGER," +
                "'DAC_resolution'	INTEGER," +
                "'counters'	INTEGER," +
                "'UART'	INTEGER," +
                "'SPI'	INTEGER," +
                "'I2C'	INTEGER," +
                "'CAN'	INTEGER," +
                "'USB'	INTEGER," +
                "'temp_min'	INTEGER," +
                "'temp_max'	INTEGER," +
                "'voltage_min'	INTEGER," +
                "'voltage_max'	INTEGER," +
                "'power_consumption'	REAL," +
                "'FPU'	INTEGER," +
                "'graphics_support'	INTEGER," +
                "'external_ram_support'	INTEGER," +
                "'parallel_interfaces'	TEXT," +
                "'serial_interfaces'	TEXT," +
                "'general_description'	TEXT," +
                "'packages'	TEXT," +
                "'package_tht'	INTEGER," +
                "'package_easy'	INTEGER," +
                "'package_hard'	INTEGER," +
                "'package_bga'	INTEGER);";

        Statement statement = connection.createStatement();
        statement.execute("SELECT name FROM sqlite_master WHERE type='table' AND name='uc_database';");
        if (!statement.getResultSet().next()) {
            statement.execute(createTableStatement);
            System.out.println("Potato");
        }
        statement.close();
    }

}
