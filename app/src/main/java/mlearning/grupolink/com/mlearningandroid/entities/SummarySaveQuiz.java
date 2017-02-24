package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

/**
 * Created by GrupoLink on 28/04/2015.
 */
public class SummarySaveQuiz {

    private List<Integer> detailScore;
    private int minToApprove;
    private int correctsAnswers;
    private boolean approved;

    public List<Integer> getDetailScore() {
        return detailScore;
    }

    public void setDetailScore(List<Integer> detailScore) {
        this.detailScore = detailScore;
    }

    public int getMinToApprove() {
        return minToApprove;
    }

    public void setMinToApprove(int minToApprove) {
        this.minToApprove = minToApprove;
    }

    public int getCorrectsAnswers() {
        return correctsAnswers;
    }

    public void setCorrectsAnswers(int correctsAnswers) {
        this.correctsAnswers = correctsAnswers;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
