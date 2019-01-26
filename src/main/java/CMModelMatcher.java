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
        private float speedMin = Float.MAX_VALUE;
        private float speedMax = Float.MIN_VALUE;
        private int rangeMin = Integer.MAX_VALUE;
        private int rangeMax = Integer.MIN_VALUE;

        CMComparator(CModuleModel cmModel, List<CModuleEntity> cmList) {
            this.cmModel = cmModel;
            for (CModuleEntity cm : cmList) {
                priceMax = priceMax < cm.getPrice() ? cm.getPrice() : priceMax;
                priceMin = priceMin > cm.getPrice() ? cm.getPrice() : priceMin;
                powerMax = powerMax < cm.getCurrent_consumption() ? cm.getCurrent_consumption() : powerMax;
                powerMin = powerMin > cm.getCurrent_consumption() ? cm.getCurrent_consumption() : powerMin;
                speedMax = speedMax < cm.getCommunicationSpeed() ? cm.getCommunicationSpeed() : speedMax;
                speedMin = speedMin > cm.getCommunicationSpeed() ? cm.getCommunicationSpeed() : speedMin;
                rangeMax = rangeMax < cm.getRange() ? cm.getRange() : rangeMax;
                rangeMin = rangeMin > cm.getRange() ? cm.getRange() : rangeMin;
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

            //price
            float pricePoints = 1 - ((cm.getPrice() - priceMin) / (priceMax - priceMin));
            points += cmModel.parametersFlags.get(CModuleModel.LOW_PRICE) ? 3 * pricePoints : 2 * pricePoints;

            //power consumption
            if (cmModel.parametersFlags.get(CModuleModel.POWER_SAVING)) {
                points += 1 - ((cm.getCurrent_consumption() - powerMin) / (powerMax - powerMin));
                points += cm.getPower_saving() > 0 ? 1 : 0;
            }

            //communication speed
            points += ((cm.getCommunicationSpeed() - speedMin) / (speedMax - speedMin));


            // range
            points += ((cm.getRange() - rangeMin) / (rangeMax - rangeMin)) *
                    (cmModel.parametersFlags.get(CModuleModel.OPEN_SPACE) ? 1 : 2);

            //package
            if (cmModel.parametersValues.get(CModuleModel.PACKAGE)>0){
                points+=cm.getModule_package()>0 ? 0.5 : 0;
            }

            // arduino
            if (cmModel.parametersFlags.get(CModuleModel.ARDUINO_SUPPORT)) {
                points += cm.getArduino_support() > 0 ? 1 : 0;
            }

            // programmable
            if (cmModel.parametersFlags.get(CModuleModel.PROGRAMMABLE)) {
                points += cm.getProgrammable() > 0 ? 1 : 0;
            }

            // encryption
            if (cmModel.parametersFlags.get(CModuleModel.ENCRYPTION)) {
                points += cm.getEncryption() > 0 ? 1 : 0;
            }

            //low latency
            if (cmModel.parametersFlags.get(CModuleModel.LOW_LATENCY)){
                if(cm.getRadio()>0){
                    points+=1;
                }else if(cm.getBluetooth()>0){
                    points+=0.5;
                }
            }

            if(cmModel.parametersFlags.get(CModuleModel.POINT2POINT)){
                if(cm.getRadio()>0 || cm.getBluetooth()>0 ){
                    points+=1;
                }

            }

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
            prepSQL = "( 1 )";
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
        switch (direction) {
            case 0:
                return "( direction = 0)";
            case 1:
                return "( direction = 1)";
            default:
                return "( direction = 2)";
        }

    }


}
