import UCdatabase.MicroControllerEntity;
import UCdatabase.UCDatabaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UCModelMatcher {

    private Connection dbConnection;


    public UCModelMatcher(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<MicroControllerEntity> matchUCModel(MicroControllerModel ucModel) {
        List<MicroControllerEntity> ucList = filterUCBase(ucModel);

        ucList.sort(new UCComparator(ucModel, ucList));

        return ucList;
    }


    private class UCComparator implements Comparator {

        private MicroControllerModel ucModel;
        private float priceMin = Float.MAX_VALUE;
        private float priceMax = Float.MIN_VALUE;
        private float powerMin = Float.MAX_VALUE;
        private float powerMax = Float.MIN_VALUE;
        private int speedMin = Integer.MAX_VALUE;
        private int speedMax = Integer.MIN_VALUE;
        private int ramMin = Integer.MAX_VALUE;
        private int ramMax = Integer.MIN_VALUE;
        private int pinMin = Integer.MAX_VALUE;
        private int pinMax = Integer.MIN_VALUE;

        UCComparator(MicroControllerModel ucModel, List<MicroControllerEntity> ucList) {
            this.ucModel = ucModel;
            for (MicroControllerEntity uc : ucList) {
                priceMax = priceMax < uc.getPrice() ? uc.getPrice() : priceMax;
                priceMin = priceMin > uc.getPrice() ? uc.getPrice() : priceMin;
                powerMax = powerMax < uc.getPower_consumption() ? uc.getPower_consumption() : powerMax;
                powerMin = powerMin > uc.getPower_consumption() ? uc.getPower_consumption() : powerMin;
                speedMax = speedMax < uc.getCpu_speed() ? uc.getCpu_speed() : speedMax;
                speedMin = speedMin > uc.getCpu_speed() ? uc.getCpu_speed() : speedMin;
                ramMax = ramMax < uc.getSram_bytes() ? uc.getSram_bytes() : ramMax;
                ramMin = ramMin > uc.getSram_bytes() ? uc.getSram_bytes() : ramMin;
                pinMax = pinMax < uc.getPin_count() ? uc.getPin_count() : pinMax;
                pinMin = pinMin > uc.getPin_count() ? uc.getPin_count() : pinMin;
            }
        }

        @Override
        public int compare(Object uc1, Object uc2) {

            float pointsUC1 = calculatePoints((MicroControllerEntity) uc1);
            float pointsUC2 = calculatePoints((MicroControllerEntity) uc2);


            if (pointsUC2 - pointsUC1 > 0) {
                return 1;
            } else if (pointsUC2 - pointsUC1 < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        private float calculatePoints(MicroControllerEntity uc) {
            float points = 0;

            //price
            float pricePoints = 1 - ((uc.getPrice() - priceMin) / (priceMax - priceMin));
            points += ucModel.parametersFlags.get(MicroControllerModel.SMALL_SERIES) ? 2 * pricePoints : 4 * pricePoints;

            //power consumption
            if (ucModel.parametersFlags.get(MicroControllerModel.POWER_SAVING)) {
                points += 1 - ((uc.getPower_consumption() - powerMin) / (powerMax - powerMin));
            }

            //OS support
            if (ucModel.parametersFlags.get(MicroControllerModel.OS_SUPPORT)) {
                points += uc.getManufacturer().equals("STM") ? 1 : 0;
            }

            //graphics features support
            if (ucModel.parametersFlags.get(MicroControllerModel.GRAPHICS_FEATURES)) {
                points += uc.getGraphics_support() > 0 ? 1 : 0;
            }

            //performance
            if (ucModel.parametersFlags.get(MicroControllerModel.HIGH_PERFORMANCE)) {
                points += (((float) uc.getCpu_speed() - speedMin) / (speedMax - speedMin));
            }
            if (ucModel.parametersFlags.get(MicroControllerModel.FPU)) {
                points += uc.getFPU() > 0 ? 2 : 0;
            }

            //IO
            if (ucModel.parametersFlags.get(MicroControllerModel.IO_EXPANDERS)) {
                if (uc.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER)) {
                    if (uc.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) * 1.75f) {
                        points += 1;
                    } else {
                        points += 2;
                    }
                } else if (uc.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) * 0.5f) {
                    points += 1;
                }

            } else {
                if (uc.getPin_count() >= ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) * 1.75f) {
                    points += (((float) uc.getPin_count() - pinMin) / (pinMax - pinMin));
                } else {
                    points += 1 + (((float) uc.getPin_count() - pinMin) / (pinMax - pinMin));
                }
            }

            //RAM
            if (ucModel.parametersFlags.get(MicroControllerModel.EXTERNAL_RAM)) {
                points += uc.getExternal_ram_support() > 0 ? 1 : 0;
            }
            points += (((float) uc.getSram_bytes() - ramMin) / (ramMax - ramMin));

            //DAC - using "handmade" DAC
            int numberOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_NUMBER);
            int resolutionOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_RESOLUTION);

            if (uc.getDAC_output() >= numberOfDAC) {
                points += 2;
            } else if ((numberOfDAC - uc.getDAC_output() == 1) && resolutionOfDAC <= 8) {
                if (uc.getPin_count() > ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER) + 10) {
                    points += 1;
                }
            }

            return points;
        }

    }

    private List<MicroControllerEntity> filterUCBase(MicroControllerModel ucModel) {
        ResultSet resultSet = null;
        String sqlStatement = "SELECT * FROM uc_database WHERE ";
        StringBuilder stringBuilder = new StringBuilder(sqlStatement);

        stringBuilder.append(prepareManufacturerFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(preparePackageFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareVoltageFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareInterfacesFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareIOFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareADCFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareDACFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareFlashFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareSramFilter(ucModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareCountersFilter(ucModel));

        try {
            String statementSQL = stringBuilder.toString();
            System.out.println(statementSQL);
            PreparedStatement statement = dbConnection.prepareStatement(statementSQL);
            statement.execute();
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet != null ? UCDatabaseDAO.rowsToObject(resultSet) : Collections.emptyList();
    }

    private String prepareManufacturerFilter(MicroControllerModel ucModel) {
        StringBuilder sb = new StringBuilder("( ");
        boolean firstCondition = true;

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_STM)) {
            sb.append("manufacturer is \'STM\'");
            firstCondition = false;
        }

        if (ucModel.parametersFlags.get(MicroControllerModel.MANUFACTURER_ATMEL)) {
            if (!firstCondition) sb.append(" OR ");
            sb.append("manufacturer is \'ATMEL\'");
            firstCondition = false;
        }

        if (firstCondition) {
            sb.append("0");
        }
        sb.append(") ");
        return sb.toString();
    }

    private String preparePackageFilter(MicroControllerModel ucModel) {
        int package_lvl = ucModel.parametersValues.get(MicroControllerModel.UC_PACKAGE);
        StringBuilder sb = new StringBuilder("( package_tht = 1");

        if (package_lvl >= MicroControllerModel.PACKAGE_SIMPLE_SMD) {
            sb.append(" OR package_easy = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_ADVANCED_SMD) {
            sb.append(" OR package_hard = 1");
        }

        if (package_lvl >= MicroControllerModel.PACKAGE_BGA) {
            sb.append(" OR package_bga = 1");
        }

        sb.append(") ");
        return sb.toString();
    }

    private String prepareVoltageFilter(MicroControllerModel ucModel) {
        int optimalVoltage = ucModel.parametersValues.get(MicroControllerModel.OPTIMAL_VOLTAGE);
        int minimalVoltage = ucModel.parametersValues.get(MicroControllerModel.MINIMAL_VOLTAGE);
        if (minimalVoltage > optimalVoltage) {
            minimalVoltage = optimalVoltage;
        }
        return String.format("( voltage_min <= %d AND voltage_max >= %d ) ", minimalVoltage, optimalVoltage);
    }

    private String prepareInterfacesFilter(MicroControllerModel ucModel) {
        StringBuilder sb = new StringBuilder("( ");
        sb.append(String.format("UART >= %d AND SPI >= %d AND I2C >= %d AND CAN >= %d",
                ucModel.parametersValues.get(MicroControllerModel.UART_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.SPI_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.I2C_INTERFACES),
                ucModel.parametersValues.get(MicroControllerModel.CAN_INTERFACES)
        ));

        if (ucModel.parametersFlags.get(MicroControllerModel.USB_INTERFACES)) {
            sb.append(" AND USB >= 1");
        }

        sb.append(") ");
        return sb.toString();
    }

    private String prepareIOFilter(MicroControllerModel ucModel) {
        String preparedSQL = null;
        int minimalIO = ucModel.parametersValues.get(MicroControllerModel.IO_PORTS_NUMBER);
        if (!ucModel.parametersFlags.get(MicroControllerModel.IO_EXPANDERS)) {
            preparedSQL = String.format("( pin_count >= %d ) ", minimalIO);
        } else {
            preparedSQL = "( pin_count >= 0 )";
        }
        return preparedSQL;
    }

    private String prepareADCFilter(MicroControllerModel ucModel) {
        int numberOfADC = ucModel.parametersValues.get(MicroControllerModel.ADC_NUMBER);
        int resolutionOfADC = ucModel.parametersValues.get(MicroControllerModel.ADC_RESOLUTION);
        return String.format(" ( ADC_input >= %d AND ADC_resolution >= %d ) ", numberOfADC, resolutionOfADC);
    }

    private String prepareDACFilter(MicroControllerModel ucModel) {
        int numberOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_NUMBER);
        int resolutionOfDAC = ucModel.parametersValues.get(MicroControllerModel.DAC_RESOLUTION);
        String preparedSQL;

        if (numberOfDAC >= 1 && (resolutionOfDAC > 8)) {
            preparedSQL = String.format(" ( DAC_output >= %d AND DAC_resolution >= %d ) ", numberOfDAC, resolutionOfDAC);
        } else {
            preparedSQL = " ( DAC_output >= 0 ) ";
        }

        return preparedSQL;
    }

    private String prepareFlashFilter(MicroControllerModel ucModel) {
        return String.format(" ( flash_kb >= %d ) ", ucModel.parametersValues.get(MicroControllerModel.FLASH_SIZE));
    }

    private String prepareSramFilter(MicroControllerModel ucModel) {
        String preparedSQL;
        int ramUsage = ucModel.parametersValues.get(MicroControllerModel.RAM_SIZE);
        if (!ucModel.parametersFlags.get(MicroControllerModel.EXTERNAL_RAM)) {
            preparedSQL = String.format("( sram_bytes >= %d ) ", ramUsage);
        } else {
            preparedSQL = "( sram_bytes >= 0 )";
        }
        return preparedSQL;
    }

    private String prepareCountersFilter(MicroControllerModel ucModel) {
        return String.format(" ( counters >= %d ) ", ucModel.parametersValues.get(MicroControllerModel.COUNTERS));
    }

}
