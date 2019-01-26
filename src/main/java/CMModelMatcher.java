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

        stringBuilder.append(preparePackageFilter(cmModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareVoltageFilter(cmModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareInterfaceFilter(cmModel));
        stringBuilder.append(" AND ");
        stringBuilder.append(prepareCommunicationDirectionFilter(cmModel));


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

    private String preparePackageFilter(CModuleModel ucModel) {
        int package_lvl = ucModel.parametersValues.get(CModuleModel.PACKAGE);
        String prepSQL;

        if (package_lvl == 0) {
            prepSQL = "( package = 0 )";
        } else {
            prepSQL = "( package = 1 )";
        }

        return prepSQL;
    }

    private String prepareVoltageFilter(CModuleModel cmModel) {

        int minimalVoltage = cmModel.parametersValues.get(CModuleModel.MINIMAL_VOLTAGE);
        int optimalVoltage = cmModel.parametersValues.get(CModuleModel.OPTIMAL_VOLTAGE);

        return String.format("( voltage_min >= %d AND voltage_max <= %d ) ", minimalVoltage, optimalVoltage);
    }

    private String prepareInterfaceFilter(CModuleModel cmModel) {
        int interfaceType = cmModel.parametersValues.get(CModuleModel.COMMUNICATION_INTERFACE);
        switch (interfaceType) {
            case 0:
                return "( SPI = 1 )";
            case 1:
                return "( I2C = 1 )";
            case 2:
                return "( UART = 1 )";
            default:
                return "( 1 )";
        }
    }

    private String prepareCommunicationDirectionFilter(CModuleModel cmModel) {
        int direction = cmModel.parametersValues.get(CModuleModel.COMMUNICATION_DIRECTION);
        switch(direction){
            case 0:
                return "( direction = 0)";
            case 1:
                return "( direction = 1)";
            default:
                return "( direction = 2)";
        }

    }


}
