public interface IFormController {

    IQuestionModel getNextQuestion();
    IQuestionModel getPreviousQuestion();
    void setAnswerForLastQuestion(int answer);

}
