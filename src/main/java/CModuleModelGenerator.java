import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class CModuleModelGenerator implements IFormController {

    private CModuleModel cmModel;

    //"questions"
    private HashMap<Integer, IQuestionModel> questions;
    private Stack<Integer> usedQuestionIDs;

    private HashMap<Integer, CModuleModelRule> modelRules;


    public CModuleModelGenerator(List<IQuestionModel> questions, List<CModuleModelRule> modelRules){
        cmModel = new CModuleModel();

        this.questions = new HashMap<>();
        for (IQuestionModel question:questions){
            this.questions.put(question.getQuestionID(), question);
        }

        this.usedQuestionIDs = new Stack<>();

        assert validateModelRules(modelRules, cmModel);
        this.modelRules = new HashMap<>();
        for (CModuleModelRule rule:modelRules) {
            this.modelRules.put(rule.getRule_id(), rule);
        }
    }


    private boolean validateModelRules(List<CModuleModelRule> modelRules, CModuleModel model){
        Set<String> flags = model.parametersFlags.keySet();
        Set<String> values = model.parametersValues.keySet();

        for (CModuleModelRule rule:modelRules) {
            Set<String> properties = rule.getRules().keySet();
            for (String property:properties) {
                if (!(flags.contains(property) || values.contains(property))){
                    System.err.println("Property \"" + property + "\" in rule " + rule.getRule_id() + " is unknown");
                    return false;
                }
            }
        }
        return true;
    }


    public CModuleModel generateModel(){

        for (Integer questionID:usedQuestionIDs) {

            CModuleModelRule rule = modelRules.get(questionID);
            if (rule == null) continue;
            IQuestionModel question = questions.get(questionID);

            for (String property : rule.getRules().keySet()) {
                if (rule.getRules().get(property)[question.selectedAnswer()] == null){
                    System.out.println("Nothing to change, skip");
                    continue;
                }

                if (cmModel.parametersValues.keySet().contains(property)) {
                    cmModel.parametersValues.put(property, ((Double) rule.getRules().get(property)[question.selectedAnswer()]).intValue());
                }
                if (cmModel.parametersFlags.keySet().contains(property)) {
                    cmModel.parametersFlags.put(property, (Boolean) rule.getRules().get(property)[question.selectedAnswer()]);
                }
            }
        }
        return cmModel;
    }



    @Override
    public void setAnswerForLastQuestion(int answer){
        questions.get(usedQuestionIDs.get(usedQuestionIDs.size()-1)).setSelectedAnswer(answer);
    }

    @Override
    public IQuestionModel getNextQuestion() {
        IQuestionModel nextQuestion = null;
        if (usedQuestionIDs.isEmpty()){
            usedQuestionIDs.push(0);
            nextQuestion = questions.get(0);
        } else {
            int lastQuestionID = usedQuestionIDs.peek();
            int nextQuestionID = questions.get(lastQuestionID).getNextQuestionID();
            if (nextQuestionID != -1) {
                usedQuestionIDs.push(nextQuestionID);
                nextQuestion = questions.get(nextQuestionID);
            }
        }
        return nextQuestion;
    }

    @Override
    public IQuestionModel getPreviousQuestion() {
        if (usedQuestionIDs.size() < 2){
            return null;
        }
        int lastQuestionID = usedQuestionIDs.pop();
        questions.get(lastQuestionID).setSelectedAnswer(-1);    //remove selection from question
        lastQuestionID = usedQuestionIDs.peek();
        return questions.get(lastQuestionID);
    }
}
