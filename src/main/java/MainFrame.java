import UCdatabase.Database;
import UCdatabase.MicroControllerEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.api.scripting.URLReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {


    private JPanel startPanel;
    private JPanel questionsPanel;

    private QuestionScreen questionScreen;
    private ResultsScreen resultsScreen;

    private List<IQuestionModel> questions;
    private List<MicroControllerModelRule> modelRules;

    private UCModelGenerator ucModelGenerator;

    public MainFrame(String title) {
        super(title);

        loadKnowledgeBase();

        StartScreen startScreen = new StartScreen();
        startPanel = startScreen.getStartPanel();
        startScreen.addStartListener(this::startDoingThings);
        setContentPane(startPanel);
    }

    private void searchUC(MicroControllerModel ucModel) {
        System.out.println(ucModel.toString());
        Database db = new Database();
        try {
            db.init();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString(), "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        UCModelMatcher matcher = new UCModelMatcher(db.getConnection());

        List<MicroControllerEntity> ucList = matcher.matchUCModel(ucModel);

        System.out.println(ucList);

        resultsScreen = new ResultsScreen(ucList);
        resultsScreen.setNavigationScreenListener(buttonID -> {
            if (buttonID == ResultsScreen.ScreenNavigationListener.NEXT_BUTTON){
                setContentPane(startPanel);
                startPanel.updateUI();
            }else {
                setContentPane(questionsPanel);
                questionsPanel.updateUI();
            }
        });

        setContentPane(resultsScreen.getResultPanel());
        resultsScreen.getResultPanel().updateUI();

        JOptionPane.showMessageDialog(null,
                "Znaleziono mikrokontrolerów: " + ucList.size(),
                "Wynik wyszukiwania",
                JOptionPane.INFORMATION_MESSAGE);

        db.closeDB();
    }


    private void loadKnowledgeBase() {


        Gson gson = new Gson();

        try (Reader reader = new URLReader(MainFrame.class.getResource("/questions.json"))) {
            questions = gson.fromJson(reader, new TypeToken<List<QuestionModel>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        questions.forEach(questionModel -> questionModel.setSelectedAnswer(-1));    //hack for default values


        try (Reader reader = new URLReader(MainFrame.class.getResource("/model_rules.json"))) {
            modelRules = gson.fromJson(reader, new TypeToken<List<MicroControllerModelRule>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startDoingThings(ActionEvent event) {
        System.out.println("Start expert's stuff");

        questionScreen = new QuestionScreen();
        ucModelGenerator = new UCModelGenerator(questions, modelRules);

        questionScreen.setQuestionListener((nextQuestion, selectedQuestion) -> {
            ucModelGenerator.setAnswerForLastQuestion(selectedQuestion);
            IQuestionModel question;
            if (nextQuestion) {
                question = ucModelGenerator.getNextQuestion();
                if (question != null) {
                    questionScreen.setQuestionModel(question);
                } else {
                    searchUC(ucModelGenerator.generateModel());
                }

            } else {
                question = ucModelGenerator.getPreviousQuestion();
                if (question != null) {
                    questionScreen.setQuestionModel(question);
                }else {
                    setContentPane(startPanel);
                    startPanel.updateUI();
                }
            }
        });

        questionsPanel = questionScreen.getQuestionPanel();
        questionScreen.setQuestionModel(ucModelGenerator.getNextQuestion());
        setContentPane(questionsPanel);
    }
}
