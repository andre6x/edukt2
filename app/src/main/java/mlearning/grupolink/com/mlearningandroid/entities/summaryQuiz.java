package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;


/**
 * Created by GrupoLink on 23/04/2015.
 */
public class summaryQuiz {

    private String cdn;
    private int rows;
    private List<Integer> questions;
    private List<List<Integer>> answers;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }

    public List<List<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(List<List<Integer>> answers) {
        this.answers = answers;
    }
}
