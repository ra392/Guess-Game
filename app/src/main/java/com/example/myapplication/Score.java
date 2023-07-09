package com.example.myapplication;

public class Score {

    private int questionNumber;
    private int result;

    public Score(int questionNumber, int result) {
        this.questionNumber = questionNumber;
        this.result = result;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
