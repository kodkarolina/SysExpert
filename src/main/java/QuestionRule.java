public class QuestionRule {

    private int questionID;
    private int[] nextQuestionIds;


    public QuestionRule(int questionID, int[] nextQuestionIds){
        this.questionID = questionID;
        this.nextQuestionIds = nextQuestionIds;
    }

    public int getQuestionID(){
        return questionID;
    }

    public int getNextQuestionID(int questionAnswer){
        if (questionAnswer < nextQuestionIds.length){
            return nextQuestionIds[questionAnswer];
        }else {
            return -1;
        }
    }
}
