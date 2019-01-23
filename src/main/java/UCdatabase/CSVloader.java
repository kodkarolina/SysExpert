package UCdatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class CSVloader {
    public static final String MANUFACTURER = "manufacturer";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRICE = "price";
    public static final String CORE = "core";
    public static final String FLASH = "flash_kb";
    public static final String SRAM = "sram_bytes";
    public static final String PIN = "pin_count";
    public static final String CPU_SPEED = "cpu_speed";
    public static final String COMPARATORS = "comparators";
    public static final String ADC_INPUT = "ADC_input";
    public static final String ADC_RES = "ADC_resolution";
    public static final String DAC_OUTPUT = "DAC_output";
    public static final String DAC_RES = "DAC_resolution";
    public static final String COUNTERS = "counters";
    public static final String UART = "UART";
    public static final String SPI = "SPI";
    public static final String I2C = "I2C";
    public static final String CAN = "CAN";
    public static final String USB = "USB";
    public static final String TEMP_MIN = "temp_min";
    public static final String TEMP_MAX = "temp_max";
    public static final String VOLTAGE_MIN = "voltage_min";
    public static final String VOLTAGE_MAX = "voltage_max";
    public static final String POWER_CONSUMPTION = "power_consumption";
    public static final String FPU = "FPU";
    public static final String GRAPHICS_SUPPORT = "graphics_support";
    public static final String EXTERNAL_RAM = "external_ram_support";
    public static final String PARALEL_INTERFACES = "parallel_interfaces";
    public static final String SERIAL_INTERFACES = "serial_interfaces";
    public static final String DESCRIPTION = "general_description";
    public static final String PACKAGES = "packages";
    public static final String PACKAGE_THT = "package_tht";
    public static final String PACKAGE_EASY = "package_easy";
    public static final String PACKAGE_HARD = "package_hard";
    public static final String PACKAGE_BGA = "package_bga";


    public static List<MicroControllerEntity> loadCSV(Path filePath) throws IOException {
        List<MicroControllerEntity> ucList = new ArrayList<>();

        Map<String, Integer> headers;
        Stream<String> lines = Files.lines(filePath);
        Iterator<String> iterator = lines.iterator();
        headers = parseHeader(iterator.next());

        iterator.forEachRemaining(s -> {

            String[] fields = fixSplitForStringWithComa(s.split(","));
            MicroControllerEntity ucEntity = new MicroControllerEntity(
                    fields[headers.get(MANUFACTURER)],
                    fields[headers.get(PRODUCT_NAME)],
                    Float.valueOf(fields[headers.get(PRICE)]),
                    fields[headers.get(CORE)],
                    Integer.valueOf(fields[headers.get(FLASH)]),
                    Integer.valueOf(fields[headers.get(SRAM)]),
                    Integer.valueOf(fields[headers.get(PIN)]),
                    Integer.valueOf(fields[headers.get(CPU_SPEED)]),
                    Integer.valueOf(fields[headers.get(COMPARATORS)]),
                    Integer.valueOf(fields[headers.get(ADC_INPUT)]),
                    Integer.valueOf(fields[headers.get(ADC_RES)]),
                    Integer.valueOf(fields[headers.get(DAC_OUTPUT)]),
                    Integer.valueOf(fields[headers.get(DAC_RES)]),
                    Integer.valueOf(fields[headers.get(COUNTERS)]),
                    Integer.valueOf(fields[headers.get(UART)]),
                    Integer.valueOf(fields[headers.get(SPI)]),
                    Integer.valueOf(fields[headers.get(I2C)]),
                    Integer.valueOf(fields[headers.get(CAN)]),
                    Integer.valueOf(fields[headers.get(USB)]),
                    Integer.valueOf(fields[headers.get(TEMP_MIN)]),
                    Integer.valueOf(fields[headers.get(TEMP_MAX)]),
                    Integer.valueOf(fields[headers.get(VOLTAGE_MIN)]),
                    Integer.valueOf(fields[headers.get(VOLTAGE_MAX)]),
                    Float.valueOf(fields[headers.get(POWER_CONSUMPTION)]),
                    Integer.valueOf(fields[headers.get(FPU)]),
                    Integer.valueOf(fields[headers.get(GRAPHICS_SUPPORT)]),
                    Integer.valueOf(fields[headers.get(EXTERNAL_RAM)]),
                    fields[headers.get(PARALEL_INTERFACES)],
                    fields[headers.get(SERIAL_INTERFACES)],
                    fields[headers.get(DESCRIPTION)],
                    fields[headers.get(PACKAGES)],
                    Integer.valueOf(fields[headers.get(PACKAGE_THT)]),
                    Integer.valueOf(fields[headers.get(PACKAGE_EASY)]),
                    Integer.valueOf(fields[headers.get(PACKAGE_HARD)]),
                    Integer.valueOf(fields[headers.get(PACKAGE_BGA)])
            );
            ucList.add(ucEntity);
        });
        return ucList;
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

        Path filePath = Paths.get("pre-data/ucBase.csv");
        List<MicroControllerEntity> ucList = loadCSV(filePath);
        System.out.println(ucList);

    }

}
