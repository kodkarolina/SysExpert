package CMdatabase;

public class CModuleEntity {

    private String name;
    private int bluetooth;
    private int wifi;
    private int radio;
    private int range;
    private int voltage_min;
    private int voltage_max;
    private int power_saving;
    private int connection_direction;
    private int UART;
    private int SPI;
    private int I2C;
    private int USB;
    private int programmable;
    private int module_package;
    private float communicationSpeed;
    private int current_consumption;
    private float work_freq;
    private int encryption;
    private float price;
    private int arduino_support;


    public CModuleEntity(String name, int bluetooth, int wifi, int radio, int range, int voltage_min, int voltage_max,
                         int power_saving, int connection_direction, int UART, int SPI, int i2C, int USB,
                         int programmable, int module_package, float communicationSpeed, int current_consumption,
                         float work_freq, int encryption, float price, int arduino_support) {
        this.name = name;
        this.bluetooth = bluetooth;
        this.wifi = wifi;
        this.radio = radio;
        this.range = range;
        this.voltage_min = voltage_min;
        this.voltage_max = voltage_max;
        this.power_saving = power_saving;
        this.connection_direction = connection_direction;
        this.UART = UART;
        this.SPI = SPI;
        this.I2C = i2C;
        this.USB = USB;
        this.programmable = programmable;
        this.module_package = module_package;
        this.communicationSpeed = communicationSpeed;
        this.current_consumption = current_consumption;
        this.work_freq = work_freq;
        this.encryption = encryption;
        this.price = price;
        this.arduino_support = arduino_support;
    }

    @Override
    public String toString() {
        return "CModuleEntity{" +
                "name='" + name + '\'' +
                ", bluetooth=" + bluetooth +
                ", wifi=" + wifi +
                ", radio=" + radio +
                ", range=" + range +
                ", voltage_min=" + voltage_min +
                ", voltage_max=" + voltage_max +
                ", power_saving=" + power_saving +
                ", connection_direction=" + connection_direction +
                ", UART=" + UART +
                ", SPI=" + SPI +
                ", I2C=" + I2C +
                ", USB=" + USB +
                ", programmable=" + programmable +
                ", module_package=" + module_package +
                ", communicationSpeed=" + communicationSpeed +
                ", current_consumption=" + current_consumption +
                ", work_freq=" + work_freq +
                ", encryption=" + encryption +
                ", price=" + price +
                ", arduino_support=" + arduino_support +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getBluetooth() {
        return bluetooth;
    }

    public int getWifi() {
        return wifi;
    }

    public int getRadio() {
        return radio;
    }

    public int getRange() {
        return range;
    }

    public int getVoltage_min() {
        return voltage_min;
    }

    public int getVoltage_max() {
        return voltage_max;
    }

    public int getPower_saving() {
        return power_saving;
    }

    public int getConnection_direction() {
        return connection_direction;
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

    public int getUSB() {
        return USB;
    }

    public int getProgrammable() {
        return programmable;
    }

    public int getModule_package() {
        return module_package;
    }

    public float getCommunicationSpeed() {
        return communicationSpeed;
    }

    public int getCurrent_consumption() {
        return current_consumption;
    }

    public float getWork_freq() {
        return work_freq;
    }

    public int getEncryption() {
        return encryption;
    }

    public float getPrice() {
        return price;
    }

    public int getArduino_support() {
        return arduino_support;
    }
}
