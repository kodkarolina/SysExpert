import UCdatabase.MicroControllerEntity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UCTableModel extends AbstractTableModel {

    private List<MicroControllerEntity> ucList;
    private String[] tableHeader = {"Producent", "Produkt", "Cena", "Rdzeń", "Obudowa", "Pamięć Flash [kB]",
            "Pamięć Ram [B]", "Ilość pinów", "Taktowanie [MHz]", "Komparatory", "Wejścia ADC", "Rozdzielczość ADC",
            "Wyjścia DAC", "Rozdzielczość DAC", "Liczniki", "UART", "SPI", "I2C", "CAN", "USB", "Temperatura min [°C]",
            "Temperatura max [°C]", "Napięcie min [V]", "Napięcie max [V]", "Zużycie energii [uA]", "FPU", "Wsparcie dla grafiki",
            "Zewnętrzna pamięć RAM", "Interfejsy równoległe", "Interfejsy szeregowe", "Opis"
    };

    public UCTableModel(List<MicroControllerEntity> ucList) {
        this.ucList = ucList;
    }

    @Override
    public String getColumnName(int i) {
        return tableHeader[i];
    }

    @Override
    public int getRowCount() {
        return ucList.size();
    }

    @Override
    public int getColumnCount() {
        return tableHeader.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        MicroControllerEntity uc = ucList.get(row);

        switch (column) {
            case 0:
                return uc.getManufacturer();
            case 1:
                return uc.getProduct_name();
            case 2:
                return uc.getPrice();
            case 3:
                return uc.getCore();
            case 4:
                return uc.getPackages();
            case 5:
                return uc.getFlash_kb();
            case 6:
                return uc.getSram_bytes();
            case 7:
                return uc.getPin_count();
            case 8:
                return uc.getCpu_speed();
            case 9:
                return uc.getComparators();
            case 10:
                return uc.getADC_input();
            case 11:
                return uc.getADC_resolution();
            case 12:
                return uc.getDAC_output();
            case 13:
                return uc.getDAC_resolution();
            case 14:
                return uc.getCounters();
            case 15:
                return uc.getUART();
            case 16:
                return uc.getSPI();
            case 17:
                return uc.getI2C();
            case 18:
                return uc.getCAN();
            case 19:
                return uc.getUSB() > 0 ? "jest" : "brak";
            case 20:
                return uc.getTemp_min();
            case 21:
                return uc.getTemp_max();
            case 22:
                return (uc.getVoltage_min() / 1000f);
            case 23:
                return (uc.getVoltage_max() / 1000f);
            case 24:
                return uc.getPower_consumption();
            case 25:
                return uc.getFPU() > 0 ? "jest" : "brak";
            case 26:
                return uc.getGraphics_support() > 0 ? "jest" : "brak";
            case 27:
                return uc.getExternal_ram_support() > 0 ? "jest" : "brak";
            case 28:
                return uc.getParallel_interfaces();
            case 29:
                return uc.getSerial_interfaces();
            case 30:
                return uc.getGeneral_description();
            default:
                return "";
        }
    }
}
