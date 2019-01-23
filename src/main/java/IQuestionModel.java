public interface IQuestionModel {

    int getQuestionID();
    String getQuestion();
    String[] getAnswers();
    int selectedAnswer();
    void setSelectedAnswer(int selectedAnswer);
    int getNextQuestionID();
}
