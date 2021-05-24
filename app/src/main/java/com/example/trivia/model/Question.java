package com.example.trivia.model;

public class Question {
    private String question;
    private Boolean ans;

    public Question() {
    }

    public Question(String question, Boolean ans) {
        this.question = question;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAns() {
        return ans;
    }

    public void setAns(Boolean ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", ans=" + ans +
                '}';
    }
}
