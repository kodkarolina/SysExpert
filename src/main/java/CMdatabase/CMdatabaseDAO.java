package CMdatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CMdatabaseDAO {


    private Connection connection;

    public CMdatabaseDAO(Connection connection) {
        this.connection = connection;
    }


    public int insertAll(Collection<CModuleEntity> cmCollection) {
        int[] results = new int[0];
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO 'cm_database'" +
                            "('name','bluetooth','wifi','radio','range','voltage_min','voltage_max'," +
                            "'power_saving','direction','UART','SPI','I2C','USB'," +
                            "'programmable','package','communication_speed','power_consumption','work_freq'," +
                            "'encryption','price','arduino_support') " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
            );
            for (CModuleEntity cm : cmCollection) {
                statement.setString(1, cm.getName());
                statement.setInt(2, cm.getBluetooth());
                statement.setInt(3, cm.getWifi());
                statement.setInt(4, cm.getRadio());
                statement.setInt(5, cm.getRange());
                statement.setInt(6, cm.getVoltage_min());
                statement.setInt(7, cm.getVoltage_max());
                statement.setInt(8, cm.getPower_saving());
                statement.setInt(9, cm.getConnection_direction());
                statement.setInt(10, cm.getUART());
                statement.setInt(11, cm.getSPI());
                statement.setInt(12, cm.getI2C());
                statement.setInt(13, cm.getUSB());
                statement.setInt(14, cm.getProgrammable());
                statement.setInt(15, cm.getModule_package());
                statement.setFloat(16, cm.getCommunicationSpeed());
                statement.setInt(17, cm.getCurrent_consumption());
                statement.setFloat(18, cm.getWork_freq());
                statement.setInt(19, cm.getEncryption());
                statement.setFloat(20, cm.getPrice());
                statement.setInt(21, cm.getArduino_support());
                statement.addBatch();
            }
            results = statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int resultCounter = 0;
        for (int result : results) {
            resultCounter += result > 0 ? result : 0;
        }

        return resultCounter;
    }

    public int tableSize() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as row_numbers FROM cm_database");
            statement.execute();
            return statement.getResultSet().getInt("row_numbers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<CModuleEntity> rowsToObject(ResultSet resultSet) {
        List<CModuleEntity> cmList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                cmList.add(new CModuleEntity(
                        resultSet.getString("name"),
                        resultSet.getInt("bluetooth"),
                        resultSet.getInt("wifi"),
                        resultSet.getInt("radio"),
                        resultSet.getInt("range"),
                        resultSet.getInt("voltage_min"),
                        resultSet.getInt("voltage_max"),
                        resultSet.getInt("power_saving"),
                        resultSet.getInt("direction"),
                        resultSet.getInt("UART"),
                        resultSet.getInt("SPI"),
                        resultSet.getInt("I2C"),
                        resultSet.getInt("USB"),
                        resultSet.getInt("programmable"),
                        resultSet.getInt("package"),
                        resultSet.getFloat("communication_speed"),
                        resultSet.getInt("power_consumption"),
                        resultSet.getFloat("work_freq"),
                        resultSet.getInt("encryption"),
                        resultSet.getFloat("price"),
                        resultSet.getInt("arduino_support")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cmList;
    }

}
