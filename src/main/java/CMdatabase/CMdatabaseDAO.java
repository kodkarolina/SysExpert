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


    public int insertAll(Collection<CModuleEntity> ucCollection) {
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
            for (CModuleEntity uc : ucCollection) {
                statement.setString(1, uc.getName());
                statement.setInt(2, uc.getBluetooth());
                statement.setInt(3, uc.getWifi());
                statement.setInt(4, uc.getRadio());
                statement.setInt(5, uc.getRange());
                statement.setInt(6, uc.getVoltage_min());
                statement.setInt(7, uc.getVoltage_max());
                statement.setInt(8, uc.getPower_saving());
                statement.setInt(9, uc.getConnection_direction());
                statement.setInt(10, uc.getUART());
                statement.setInt(11, uc.getSPI());
                statement.setInt(12, uc.getI2C());
                statement.setInt(13, uc.getUSB());
                statement.setInt(14, uc.getProgrammable());
                statement.setInt(15, uc.getModule_package());
                statement.setFloat(16, uc.getCommunicationSpeed());
                statement.setInt(17, uc.getCurrent_consumption());
                statement.setFloat(18, uc.getWork_freq());
                statement.setInt(19, uc.getEncryption());
                statement.setFloat(20, uc.getPrice());
                statement.setInt(21, uc.getArduino_support());
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
        List<CModuleEntity> ucList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ucList.add(new CModuleEntity(
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
        return ucList;
    }

}
