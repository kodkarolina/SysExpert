import CMdatabase.CModuleEntity;
import CMdatabase.CMdatabaseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CMModelMatcher {

    private Connection dbConnection;


    public CMModelMatcher(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<CModuleEntity> matchCMModel(CModuleModel cmModel) {
        List<CModuleEntity> cmList = filterCMBase(cmModel);

        cmList.sort(new CMComparator(cmModel, cmList));

        return cmList;
    }


    private class CMComparator implements Comparator {

        private CModuleModel cmModel;
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

        CMComparator(CModuleModel cmModel, List<CModuleEntity> cmList) {
            this.cmModel = cmModel;
            for (CModuleEntity cm : cmList) {
//                priceMax = priceMax < cm.getPrice() ? cm.getPrice() : priceMax;
//                priceMin = priceMin > cm.getPrice() ? cm.getPrice() : priceMin;
//                powerMax = powerMax < cm.getPower_consumption() ? cm.getPower_consumption() : powerMax;
//                powerMin = powerMin > cm.getPower_consumption() ? cm.getPower_consumption() : powerMin;
//                speedMax = speedMax < cm.getCpu_speed() ? cm.getCpu_speed() : speedMax;
//                speedMin = speedMin > cm.getCpu_speed() ? cm.getCpu_speed() : speedMin;
//                ramMax = ramMax < cm.getSram_bytes() ? cm.getSram_bytes() : ramMax;
//                ramMin = ramMin > cm.getSram_bytes() ? cm.getSram_bytes() : ramMin;
//                pinMax = pinMax < cm.getPin_count() ? cm.getPin_count() : pinMax;
//                pinMin = pinMin > cm.getPin_count() ? cm.getPin_count() : pinMin;
            }
        }

        @Override
        public int compare(Object cm1, Object cm2) {

            float pointsCM1 = calculatePoints((CModuleEntity) cm1);
            float pointsCM2 = calculatePoints((CModuleEntity) cm2);


            if (pointsCM2 - pointsCM1 > 0) {
                return 1;
            } else if (pointsCM2 - pointsCM1 < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        //TODO change this methof for new subject of systme
        private float calculatePoints(CModuleEntity cm) {
            float points = 0;

//            //price
//            float pricePoints = 1 - ((cm.getPrice() - priceMin) / (priceMax - priceMin));
//            points += cmModel.parametersFlags.get(CModuleModel.SMALL_SERIES) ? 2 * pricePoints : 4 * pricePoints;
//
//            //power consumption
//            if (cmModel.parametersFlags.get(CModuleModel.POWER_SAVING)) {
//                points += 1 - ((cm.getPower_consumption() - powerMin) / (powerMax - powerMin));
//            }
//
//            //OS support
//            if (cmModel.parametersFlags.get(CModuleModel.OS_SUPPORT)) {
//                points += cm.getManufacturer().equals("STM") ? 1 : 0;
//            }
//
//            //graphics features support
//            if (cmModel.parametersFlags.get(CModuleModel.GRAPHICS_FEATURES)) {
//                points += cm.getGraphics_support() > 0 ? 1 : 0;
//            }
//
//            //performance
//            if (cmModel.parametersFlags.get(CModuleModel.HIGH_PERFORMANCE)) {
//                points += (((float) cm.getCpu_speed() - speedMin) / (speedMax - speedMin));
//            }
//            if (cmModel.parametersFlags.get(CModuleModel.FPU)) {
//                points += cm.getFPU() > 0 ? 2 : 0;
//            }
//
//            //IO
//            if (cmModel.parametersFlags.get(CModuleModel.IO_EXPANDERS)) {
//                if (cm.getPin_count() >= cmModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER)) {
//                    if (cm.getPin_count() >= cmModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER) * 1.75f) {
//                        points += 1;
//                    } else {
//                        points += 2;
//                    }
//                } else if (cm.getPin_count() >= cmModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER) * 0.5f) {
//                    points += 1;
//                }
//
//            } else {
//                if (cm.getPin_count() >= cmModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER) * 1.75f) {
//                    points += (((float) cm.getPin_count() - pinMin) / (pinMax - pinMin));
//                } else {
//                    points += 1 + (((float) cm.getPin_count() - pinMin) / (pinMax - pinMin));
//                }
//            }
//
//            //RAM
//            if (cmModel.parametersFlags.get(CModuleModel.EXTERNAL_RAM)) {
//                points += cm.getExternal_ram_support() > 0 ? 1 : 0;
//            }
//            points += (((float) cm.getSram_bytes() - ramMin) / (ramMax - ramMin));
//
//            //DAC - using "handmade" DAC
//            int numberOfDAC = cmModel.parametersValues.get(CModuleModel.DAC_NUMBER);
//            int resolutionOfDAC = cmModel.parametersValues.get(CModuleModel.DAC_RESOLUTION);
//
//            if (cm.getDAC_output() >= numberOfDAC) {
//                points += 2;
//            } else if ((numberOfDAC - cm.getDAC_output() == 1) && resolutionOfDAC <= 8) {
//                if (cm.getPin_count() > cmModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER) + 10) {
//                    points += 1;
//                }
//            }

            return points;
        }

    }

    private List<CModuleEntity> filterCMBase(CModuleModel cmModel) {
        ResultSet resultSet = null;
        String sqlStatement = "SELECT * FROM cm_database WHERE ";
        StringBuilder stringBuilder = new StringBuilder(sqlStatement);

//        stringBuilder.append(prepareManufacturerFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(preparePackageFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareVoltageFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareInterfacesFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareIOFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareADCFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareDACFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareFlashFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareSramFilter(cmModel));
//        stringBuilder.append(" AND ");
//        stringBuilder.append(prepareCountersFilter(cmModel));

        try {
            String statementSQL = stringBuilder.toString();
            System.out.println(statementSQL);
            PreparedStatement statement = dbConnection.prepareStatement(statementSQL);
            statement.execute();
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet != null ? CMdatabaseDAO.rowsToObject(resultSet) : Collections.emptyList();
    }

//    private String prepareManufacturerFilter(CModuleModel cmModel) {
//        StringBuilder sb = new StringBuilder("( ");
//        boolean firstCondition = true;
//
//        if (cmModel.parametersFlags.get(CModuleModel.MANUFACTURER_STM)) {
//            sb.append("manufacturer is \'STM\'");
//            firstCondition = false;
//        }
//
//        if (cmModel.parametersFlags.get(CModuleModel.MANUFACTURER_ATMEL)) {
//            if (!firstCondition) sb.append(" OR ");
//            sb.append("manufacturer is \'ATMEL\'");
//            firstCondition = false;
//        }
//
//        if (firstCondition) {
//            sb.append("0");
//        }
//        sb.append(") ");
//        return sb.toString();
//    }
//
//    private String preparePackageFilter(CModuleModel ucModel) {
//        int package_lvl = ucModel.parametersValues.get(CModuleModel.UC_PACKAGE);
//        StringBuilder sb = new StringBuilder("( package_tht = 1");
//
//        if (package_lvl >= CModuleModel.PACKAGE_SIMPLE_SMD) {
//            sb.append(" OR package_easy = 1");
//        }
//
//        if (package_lvl >= CModuleModel.PACKAGE_ADVANCED_SMD) {
//            sb.append(" OR package_hard = 1");
//        }
//
//        if (package_lvl >= CModuleModel.PACKAGE_BGA) {
//            sb.append(" OR package_bga = 1");
//        }
//
//        sb.append(") ");
//        return sb.toString();
//    }
//
//    private String prepareVoltageFilter(CModuleModel ucModel) {
//        int optimalVoltage = ucModel.parametersValues.get(CModuleModel.OPTIMAL_VOLTAGE);
//        int minimalVoltage = ucModel.parametersValues.get(CModuleModel.MINIMAL_VOLTAGE);
//        if (minimalVoltage > optimalVoltage) {
//            minimalVoltage = optimalVoltage;
//        }
//        return String.format("( voltage_min <= %d AND voltage_max >= %d ) ", minimalVoltage, optimalVoltage);
//    }
//
//    private String prepareInterfacesFilter(CModuleModel ucModel) {
//        StringBuilder sb = new StringBuilder("( ");
//        sb.append(String.format("UART >= %d AND SPI >= %d AND I2C >= %d AND CAN >= %d",
//                ucModel.parametersValues.get(CModuleModel.UART_INTERFACES),
//                ucModel.parametersValues.get(CModuleModel.SPI_INTERFACES),
//                ucModel.parametersValues.get(CModuleModel.I2C_INTERFACES),
//                ucModel.parametersValues.get(CModuleModel.CAN_INTERFACES)
//        ));
//
//        if (ucModel.parametersFlags.get(CModuleModel.USB_INTERFACES)) {
//            sb.append(" AND USB >= 1");
//        }
//
//        sb.append(") ");
//        return sb.toString();
//    }
//
//    private String prepareIOFilter(CModuleModel ucModel) {
//        String preparedSQL = null;
//        int minimalIO = ucModel.parametersValues.get(CModuleModel.IO_PORTS_NUMBER);
//        if (!ucModel.parametersFlags.get(CModuleModel.IO_EXPANDERS)) {
//            preparedSQL = String.format("( pin_count >= %d ) ", minimalIO);
//        } else {
//            preparedSQL = "( pin_count >= 0 )";
//        }
//        return preparedSQL;
//    }
//
//    private String prepareADCFilter(CModuleModel ucModel) {
//        int numberOfADC = ucModel.parametersValues.get(CModuleModel.ADC_NUMBER);
//        int resolutionOfADC = ucModel.parametersValues.get(CModuleModel.ADC_RESOLUTION);
//        return String.format(" ( ADC_input >= %d AND ADC_resolution >= %d ) ", numberOfADC, resolutionOfADC);
//    }
//
//    private String prepareDACFilter(CModuleModel ucModel) {
//        int numberOfDAC = ucModel.parametersValues.get(CModuleModel.DAC_NUMBER);
//        int resolutionOfDAC = ucModel.parametersValues.get(CModuleModel.DAC_RESOLUTION);
//        String preparedSQL;
//
//        if (numberOfDAC >= 1 && (resolutionOfDAC > 8)) {
//            preparedSQL = String.format(" ( DAC_output >= %d AND DAC_resolution >= %d ) ", numberOfDAC, resolutionOfDAC);
//        } else {
//            preparedSQL = " ( DAC_output >= 0 ) ";
//        }
//
//        return preparedSQL;
//    }
//
//    private String prepareFlashFilter(CModuleModel ucModel) {
//        return String.format(" ( flash_kb >= %d ) ", ucModel.parametersValues.get(CModuleModel.FLASH_SIZE));
//    }
//
//    private String prepareSramFilter(CModuleModel ucModel) {
//        String preparedSQL;
//        int ramUsage = ucModel.parametersValues.get(CModuleModel.RAM_SIZE);
//        if (!ucModel.parametersFlags.get(CModuleModel.EXTERNAL_RAM)) {
//            preparedSQL = String.format("( sram_bytes >= %d ) ", ramUsage);
//        } else {
//            preparedSQL = "( sram_bytes >= 0 )";
//        }
//        return preparedSQL;
//    }
//
//    private String prepareCountersFilter(CModuleModel ucModel) {
//        return String.format(" ( counters >= %d ) ", ucModel.parametersValues.get(CModuleModel.COUNTERS));
//    }

}
