import CMdatabase.Database;
import CMdatabase.CModuleEntity;
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
    private List<CModuleModelRule> modelRules;

    private CModuleModelGenerator cmModelGenerator;

    public MainFrame(String title) {
        super(title);

        loadKnowledgeBase();

        StartScreen startScreen = new StartScreen();
        startPanel = startScreen.getStartPanel();
        startScreen.addStartListener(this::startDoingThings);
        setContentPane(startPanel);
    }

    private void searchCM(CModuleModel cmModel) {
        System.out.println(cmModel.toString());
        Database db = new Database();
        try {
            db.init();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString(), "Błąd bazy danych", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        CMModelMatcher matcher = new CMModelMatcher(db.getConnection());

        List<CModuleEntity> cmList = matcher.matchCMModel(cmModel);

        System.out.println(cmList);

        resultsScreen = new ResultsScreen(cmList);
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
                "Znaleziono modułów: " + cmList.size(),
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
            modelRules = gson.fromJson(reader, new TypeToken<List<CModuleModelRule>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startDoingThings(ActionEvent event) {
        System.out.println("Start expert's stuff");

        questionScreen = new QuestionScreen();
        cmModelGenerator = new CModuleModelGenerator(questions, modelRules);

        questionScreen.setQuestionListener((nextQuestion, selectedQuestion) -> {
            cmModelGenerator.setAnswerForLastQuestion(selectedQuestion);
            IQuestionModel question;
            if (nextQuestion) {
                question = cmModelGenerator.getNextQuestion();
                if (question != null) {
                    questionScreen.setQuestionModel(question);
                } else {
                    searchCM(cmModelGenerator.generateModel());
                }

            } else {
                question = cmModelGenerator.getPreviousQuestion();
                if (question != null) {
                    questionScreen.setQuestionModel(question);
                }else {
                    setContentPane(startPanel);
                    startPanel.updateUI();
                }
            }
        });

        questionsPanel = questionScreen.getQuestionPanel();
        questionScreen.setQuestionModel(cmModelGenerator.getNextQuestion());
        setContentPane(questionsPanel);
    }
}
