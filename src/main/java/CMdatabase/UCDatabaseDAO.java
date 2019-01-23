package CMdatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UCDatabaseDAO {


    private Connection connection;

    public UCDatabaseDAO(Connection connection) {
        this.connection = connection;
    }


    public int insertAll(Collection<MicroControllerEntity> ucCollection) {
        int[] results = new int[0];
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO 'uc_database'" +
                            "('manufacturer','product_name','price','core','flash_kb','sram_bytes','pin_count'," +
                            "'cpu_speed','comparators','ADC_input','ADC_resolution','DAC_output','DAC_resolution'," +
                            "'counters','UART','SPI','I2C','CAN','USB','temp_min','temp_max','voltage_min'," +
                            "'voltage_max','power_consumption','FPU','graphics_support','external_ram_support'," +
                            "'parallel_interfaces','serial_interfaces','general_description','packages','package_tht'," +
                            "'package_easy','package_hard','package_bga') " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
            );
            for (MicroControllerEntity uc : ucCollection) {
                statement.setString(1, uc.getManufacturer().toUpperCase());
                statement.setString(2, uc.getProduct_name());
                statement.setFloat(3, uc.getPrice());
                statement.setString(4, uc.getCore());
                statement.setInt(5, uc.getFlash_kb());
                statement.setInt(6, uc.getSram_bytes());
                statement.setInt(7, uc.getPin_count());
                statement.setInt(8, uc.getCpu_speed());
                statement.setInt(9, uc.getComparators());
                statement.setInt(10, uc.getADC_input());
                statement.setInt(11, uc.getADC_resolution());
                statement.setInt(12, uc.getDAC_output());
                statement.setInt(13, uc.getDAC_resolution());
                statement.setInt(14, uc.getCounters());
                statement.setInt(15, uc.getUART());
                statement.setInt(16, uc.getSPI());
                statement.setInt(17, uc.getI2C());
                statement.setInt(18, uc.getCAN());
                statement.setInt(19, uc.getUSB());
                statement.setInt(20, uc.getTemp_min());
                statement.setInt(21, uc.getTemp_max());
                statement.setInt(22, uc.getVoltage_min());
                statement.setInt(23, uc.getVoltage_max());
                statement.setFloat(24, uc.getPower_consumption());
                statement.setInt(25, uc.getFPU());
                statement.setInt(26, uc.getGraphics_support());
                statement.setInt(27, uc.getExternal_ram_support());
                statement.setString(28, uc.getParallel_interfaces());
                statement.setString(29, uc.getSerial_interfaces());
                statement.setString(30, uc.getGeneral_description());
                statement.setString(31, uc.getPackages());
                statement.setInt(32, uc.getPackage_tht());
                statement.setInt(33, uc.getPackage_easy());
                statement.setInt(34, uc.getPackage_hard());
                statement.setInt(35, uc.getPackage_bga());
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
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) as row_numbers FROM uc_database");
            statement.execute();
            return statement.getResultSet().getInt("row_numbers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<MicroControllerEntity> rowsToObject(ResultSet resultSet) {
        List<MicroControllerEntity> ucList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                ucList.add(new MicroControllerEntity(
                        resultSet.getString("manufacturer"),
                        resultSet.getString("product_name"),
                        resultSet.getFloat("price"),
                        resultSet.getString("core"),
                        resultSet.getInt("flash_kb"),
                        resultSet.getInt("sram_bytes"),
                        resultSet.getInt("pin_count"),
                        resultSet.getInt("cpu_speed"),
                        resultSet.getInt("comparators"),
                        resultSet.getInt("ADC_input"),
                        resultSet.getInt("ADC_resolution"),
                        resultSet.getInt("DAC_output"),
                        resultSet.getInt("DAC_resolution"),
                        resultSet.getInt("counters"),
                        resultSet.getInt("UART"),
                        resultSet.getInt("SPI"),
                        resultSet.getInt("I2C"),
                        resultSet.getInt("CAN"),
                        resultSet.getInt("USB"),
                        resultSet.getInt("temp_min"),
                        resultSet.getInt("temp_max"),
                        resultSet.getInt("voltage_min"),
                        resultSet.getInt("voltage_max"),
                        resultSet.getFloat("power_consumption"),
                        resultSet.getInt("FPU"),
                        resultSet.getInt("graphics_support"),
                        resultSet.getInt("external_ram_support"),
                        resultSet.getString("parallel_interfaces"),
                        resultSet.getString("serial_interfaces"),
                        resultSet.getString("general_description"),
                        resultSet.getString("packages"),
                        resultSet.getInt("package_tht"),
                        resultSet.getInt("package_easy"),
                        resultSet.getInt("package_hard"),
                        resultSet.getInt("package_bga")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ucList;
    }

}
