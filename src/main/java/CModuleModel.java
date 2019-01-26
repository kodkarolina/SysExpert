import java.util.HashMap;

public class CModuleModel {

    //values


    public final static String RANGE = "range";
    public final static String OPTIMAL_VOLTAGE = "optimal_voltage";
    public final static String COMMUNICATION_DIRECTION = "communication_direction";

    public final static String PACKAGE = "package";
    public final static String COMMUNICATION_INTERFACE = "communication_interface";

    //flags
    public final static String LOW_PRICE = "low_price";
    public final static String OPEN_SPACE = "open_space";
    public final static String POWER_SAVING = "power_saving";
    public final static String PROGRAMMABLE = "programmable";
    public final static String ENCRYPTION = "encryption";
    public final static String ARDUINO_SUPPORT = "arduino_support";
    public final static String COMMUNICATION_SPEED = "communication_speed";
    public final static String LOW_LATENCY  = "low_latency";
    public final static String POINT2POINT = "point_to_point";


    public HashMap<String, Boolean> parametersFlags;
    public HashMap<String, Integer> parametersValues;

    public CModuleModel() {
        parametersFlags = new HashMap<>();
        parametersValues = new HashMap<>();

        //main
        parametersFlags.put(LOW_PRICE, false);
        parametersValues.put(RANGE, 0);
        parametersValues.put(COMMUNICATION_INTERFACE, 3);
        parametersValues.put(PACKAGE, 0);


        //COMUNNICATION
        parametersFlags.put(POINT2POINT, false);
        parametersFlags.put(LOW_LATENCY, false);
        parametersValues.put(COMMUNICATION_DIRECTION, 2);
        parametersFlags.put(COMMUNICATION_SPEED, true);
        parametersFlags.put(ARDUINO_SUPPORT, false);



        //area
        parametersFlags.put(OPEN_SPACE, true);

        //Power
        parametersValues.put(OPTIMAL_VOLTAGE, 1);
        parametersFlags.put(POWER_SAVING, false);

        //Features
        parametersFlags.put(PROGRAMMABLE, false);
        parametersFlags.put(ENCRYPTION, false);

    }

    @Override
    public String toString() {
        return "CModuleModel{\n\r" +
                "\tparametersFlags=" + parametersFlags + ",\n\r" +
                "\tparametersValues=" + parametersValues + "\n\r" +
                '}';
    }
}
