package CMdatabase;

public class MicroControllerEntity {

    private String manufacturer;
    private String product_name;
    private float price;
    private String core;
    private int flash_kb;
    private int sram_bytes;
    private int pin_count;
    private int cpu_speed;
    private int comparators;
    private int ADC_input;
    private int ADC_resolution;
    private int DAC_output;
    private int DAC_resolution;
    private int counters;
    private int UART;
    private int SPI;
    private int I2C;
    private int CAN;
    private int USB;
    private int temp_min;
    private int temp_max;
    private int voltage_min;
    private int voltage_max;
    private float power_consumption;
    private int FPU;
    private int graphics_support;
    private int external_ram_support;
    private String parallel_interfaces;
    private String serial_interfaces;
    private String general_description;
    private String packages;
    private int package_tht;
    private int package_easy;
    private int package_hard;
    private int package_bga;


    public MicroControllerEntity(String manufacturer, String product_name, float price, String core, int flash_kb,
                                 int sram_bytes, int pin_count, int cpu_speed, int comparators, int ADC_input,
                                 int ADC_resolution, int DAC_output, int DAC_resolution, int counters, int UART, int SPI,
                                 int I2C, int CAN, int USB, int temp_min, int temp_max, int voltage_min, int voltage_max,
                                 float power_consumption, int FPU, int graphics_support, int external_ram_support,
                                 String parallel_interfaces, String serial_interfaces, String general_description,
                                 String packages, int package_tht, int package_easy, int package_hard, int package_bga) {

        this.manufacturer = manufacturer;
        this.product_name = product_name;
        this.price = price;
        this.core = core;
        this.flash_kb = flash_kb;
        this.sram_bytes = sram_bytes;
        this.pin_count = pin_count;
        this.cpu_speed = cpu_speed;
        this.comparators = comparators;
        this.ADC_input = ADC_input;
        this.ADC_resolution = ADC_resolution;
        this.DAC_output = DAC_output;
        this.DAC_resolution = DAC_resolution;
        this.counters = counters;
        this.UART = UART;
        this.SPI = SPI;
        this.I2C = I2C;
        this.CAN = CAN;
        this.USB = USB;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.voltage_min = voltage_min;
        this.voltage_max = voltage_max;
        this.power_consumption = power_consumption;
        this.FPU = FPU;
        this.graphics_support = graphics_support;
        this.external_ram_support = external_ram_support;
        this.parallel_interfaces = parallel_interfaces;
        this.serial_interfaces = serial_interfaces;
        this.general_description = general_description;
        this.packages = packages;
        this.package_tht = package_tht;
        this.package_easy = package_easy;
        this.package_hard = package_hard;
        this.package_bga = package_bga;
    }


    @Override
    public String toString() {
        return "MicroControllerEntity{" +
                "manufacturer='" + manufacturer + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", core='" + core + '\'' +
                ", flash_kb=" + flash_kb +
                ", sram_bytes=" + sram_bytes +
                ", pin_count=" + pin_count +
                ", cpu_speed=" + cpu_speed +
                ", comparators=" + comparators +
                ", ADC_input=" + ADC_input +
                ", ADC_resolution=" + ADC_resolution +
                ", DAC_output=" + DAC_output +
                ", DAC_resolution=" + DAC_resolution +
                ", counters=" + counters +
                ", UART=" + UART +
                ", SPI=" + SPI +
                ", I2C=" + I2C +
                ", CAN=" + CAN +
                ", USB=" + USB +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", voltage_min=" + voltage_min +
                ", voltage_max=" + voltage_max +
                ", power_consumption=" + power_consumption +
                ", FPU=" + FPU +
                ", graphics_support=" + graphics_support +
                ", external_ram_support=" + external_ram_support +
                ", parallel_interfaces='" + parallel_interfaces + '\'' +
                ", serial_interfaces='" + serial_interfaces + '\'' +
                ", general_description='" + general_description + '\'' +
                ", packages='" + packages + '\'' +
                ", package_tht=" + package_tht +
                ", package_easy=" + package_easy +
                ", package_hard=" + package_hard +
                ", package_bga=" + package_bga +
                '}';
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getProduct_name() {
        return product_name;
    }

    public float getPrice() {
        return price;
    }

    public String getCore() {
        return core;
    }

    public int getFlash_kb() {
        return flash_kb;
    }

    public int getSram_bytes() {
        return sram_bytes;
    }

    public int getPin_count() {
        return pin_count;
    }

    public int getCpu_speed() {
        return cpu_speed;
    }

    public int getComparators() {
        return comparators;
    }

    public int getADC_input() {
        return ADC_input;
    }

    public int getADC_resolution() {
        return ADC_resolution;
    }

    public int getDAC_output() {
        return DAC_output;
    }

    public int getDAC_resolution() {
        return DAC_resolution;
    }

    public int getCounters() {
        return counters;
    }

    public int getUART() {
        return UART;
    }

    public int getSPI() {
        return SPI;
    }

    public int getI2C() {
        return I2C;
    }

    public int getCAN() {
        return CAN;
    }

    public int getUSB() {
        return USB;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public int getVoltage_min() {
        return voltage_min;
    }

    public int getVoltage_max() {
        return voltage_max;
    }

    public float getPower_consumption() {
        return power_consumption;
    }

    public int getFPU() {
        return FPU;
    }

    public int getGraphics_support() {
        return graphics_support;
    }

    public int getExternal_ram_support() {
        return external_ram_support;
    }

    public String getParallel_interfaces() {
        return parallel_interfaces;
    }

    public String getSerial_interfaces() {
        return serial_interfaces;
    }

    public String getGeneral_description() {
        return general_description;
    }

    public String getPackages() {
        return packages;
    }

    public int getPackage_tht() {
        return package_tht;
    }

    public int getPackage_easy() {
        return package_easy;
    }

    public int getPackage_hard() {
        return package_hard;
    }

    public int getPackage_bga() {
        return package_bga;
    }
}
