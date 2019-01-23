import java.util.HashMap;

public class CModuleModel {

    public final static int BLUETOOTH_2_0_EDR = 1;
    public final static int PACKAGE_SIMPLE_SMD = 2;
    public final static int PACKAGE_ADVANCED_SMD = 4;
    public final static int PACKAGE_BGA = 8;

    //values
    public final static String BLUETOOTH = "bluetooth";
    public final static String COMMUNICATION_STANDARD = "communication_standard";
    public final static String RANGE = "range";
    public final static String OPTIMAL_VOLTAGE = "optimal_voltage";
    public final static String MAXIMAL_VOLTAGE = "maximal_voltage";
    public final static String COMMUNICATION_DIRECTION = "communication_direction";
    public final static String COMMUNICATION_SPEED = "communication_speed";
    public final static String PACKAGE = "package";
    public final static String COMMUNICATION_INTERFACE = "communication_interface";

    //flags
    public final static String LOW_PRICE = "low_price";
    public final static String OPEN_SPACE = "open_space";
    public final static String POWER_SAVING = "power_saving";
    public final static String PROGRAMMABLE = "programmable";
    public final static String ENCRYPTION = "encryption";
    public final static String ARDUINO_SUPPORT = "arduino_support";



    public HashMap<String, Boolean> parametersFlags;
    public HashMap<String, Integer> parametersValues;

    public CModuleModel(){
        parametersFlags = new HashMap<>();
        parametersValues = new HashMap<>();

        //main

        parametersValues.put(BLUETOOTH, BLUETOOTH_2_0_EDR);
        parametersFlags.put(ARDUINO_SUPPORT, false);




    }

    @Override
    public String toString() {
        return "CModuleModel{\n\r" +
                "\tparametersFlags=" + parametersFlags + ",\n\r" +
                "\tparametersValues=" + parametersValues + "\n\r" +
                '}';
    }
}
