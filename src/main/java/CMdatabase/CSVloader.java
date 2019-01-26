package CMdatabase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class CSVloader {
    public static final String NAME = "Name";
    public static final String BLUETOOTH = "Bluetooth";
    public static final String WIFI = "WiFi";
    public static final String RADIO = "Radio";
    public static final String RANGE = "Range";
    public static final String VOLTAGE_MIN = "Minimal_voltage";
    public static final String VOLTAGE_MAX = "Maximal_voltage";
    public static final String POWER_SAVING = "Power_saving";
    public static final String DIRECTION = "Communincation_direction";
    public static final String UART = "UART";
    public static final String SPI = "SPI";
    public static final String I2C = "I2C";
    public static final String USB = "USB";
    public static final String PROGRAMMABLE = "Programmable";
    public static final String PACKAGE = "Package";
    public static final String COMMUNICATION_SPEED = "Communication_speed";
    public static final String CURRENT_CONSUMPTION = "Current_consumption";
    public static final String WORK_FREQ = "Work_frequency";
    public static final String ENCRYPTION = "Encryption";
    public static final String PRICE = "Single_price";
    public static final String ARDUINO_SUPPORT = "Arduino_support";


    public static List<CModuleEntity> loadCSV(Path filePath) throws IOException {
        List<CModuleEntity> cmList = new ArrayList<>();

        Map<String, Integer> headers;
        Stream<String> lines = Files.lines(filePath);
        Iterator<String> iterator = lines.iterator();
        headers = parseHeader(iterator.next());

        iterator.forEachRemaining(s -> {

            String[] fields = fixSplitForStringWithComa(s.split(","));
            CModuleEntity cmEntity = new CModuleEntity(
                    fields[headers.get(NAME)],
                    Integer.valueOf(fields[headers.get(BLUETOOTH)]),
                    Integer.valueOf(fields[headers.get(WIFI)]),
                    Integer.valueOf(fields[headers.get(RADIO)]),
                    Integer.valueOf(fields[headers.get(RANGE)]),
                    Integer.valueOf(fields[headers.get(VOLTAGE_MIN)]),
                    Integer.valueOf(fields[headers.get(VOLTAGE_MAX)]),
                    Integer.valueOf(fields[headers.get(POWER_SAVING)]),
                    Integer.valueOf(fields[headers.get(DIRECTION)]),
                    Integer.valueOf(fields[headers.get(UART)]),
                    Integer.valueOf(fields[headers.get(SPI)]),
                    Integer.valueOf(fields[headers.get(I2C)]),
                    Integer.valueOf(fields[headers.get(USB)]),
                    Integer.valueOf(fields[headers.get(PROGRAMMABLE)]),
                    Integer.valueOf(fields[headers.get(PACKAGE)]),
                    Float.valueOf(fields[headers.get(COMMUNICATION_SPEED)]),
                    Integer.valueOf(fields[headers.get(CURRENT_CONSUMPTION)]),
                    Float.valueOf(fields[headers.get(WORK_FREQ)]),
                    Integer.valueOf(fields[headers.get(ENCRYPTION)]),
                    Float.valueOf(fields[headers.get(PRICE)]),
                    Integer.valueOf(fields[headers.get(ARDUINO_SUPPORT)])
            );
            cmList.add(cmEntity);
        });
        return cmList;
    }

    private static Map<String, Integer> parseHeader(String line) {
        HashMap<String, Integer> headersMap = new HashMap<>();
        String[] headers = line.split(",");
        for (int i = 0; i < headers.length; i++) {
            headersMap.put(headers[i], i);
        }
        return headersMap;
    }

    private static String[] fixSplitForStringWithComa(String[] splited) {
        ArrayList<String> fixed = new ArrayList<>();
        int startString = -1;

        for (int i = 0; i < splited.length; i++) {
            String s = splited[i];
            if (s.startsWith("\"") && !s.endsWith("\"")) {
                startString = i;
            } else if (!s.startsWith("\"") && s.endsWith("\"")) {
                String newString = splited[startString];
                for (int j = startString + 1; j <= i; j++) {
                    newString = String.join(", ", newString, splited[j]);
                }
                fixed.add(newString);
                startString = -1;
                continue;
            }
            if (startString < 0) {
                fixed.add(s);
            }
        }

        return fixed.toArray(new String[0]);
    }


    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get("data/cmBase.csv");
        List<CModuleEntity> cmList = loadCSV(filePath);
        System.out.println(cmList);

    }

}
