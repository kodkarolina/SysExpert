import CMdatabase.CModuleEntity;
import CMdatabase.CSVloader;
import CMdatabase.Database;
import CMdatabase.CMatabaseDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    private static String appName = "System Ekspertowy";

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::runGUI);
    }

    private static void runGUI() {

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        PreparingDBInfo preparingDialog = new PreparingDBInfo();
        preparingDialog.setVisible(true);


        JFrame frame = new MainFrame(appName);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeApp();
            }
        });
        frame.setBounds(100, 100, 600, 400);


        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> {
            try {
                Database database = new Database();
                database.init();
                CMatabaseDAO ucDao = new CMatabaseDAO(database.getConnection());
                if (ucDao.tableSize() == 0) {
                    List<CModuleEntity> list = CSVloader.loadCSV(Paths.get("./data/cmBase.csv"));
                    int addedUC = ucDao.insertAll(list);
                    System.out.println("added do db: " + addedUC);
                }
                database.closeDB();
                SwingUtilities.invokeLater(() -> {
                    preparingDialog.setVisible(false);
                    frame.setVisible(true);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    preparingDialog.setVisible(false);
                    JOptionPane.showMessageDialog(null, ex.toString(), "Błąd uruchamiania!", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                });
            }
        });
    }

    private static void closeApp() {
        int selection = JOptionPane.showConfirmDialog(
                null,
                "Zamknąć " + appName + "?",
                "Potwierdź wyjście",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (selection == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }


}
