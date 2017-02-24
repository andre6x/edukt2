package mlearning.grupolink.com.mlearningandroid.entities;

/**
 * Created by GrupoLink on 23/04/2015.
 */
public class answer {

    private int id;
    private String answer_option;
    private boolean right_answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer_option() {
        return answer_option;
    }

    public void setAnswer_option(String answer_option) {
        this.answer_option = answer_option;
    }

    public boolean isRight_answer() {
        return right_answer;
    }

    public void setRight_answer(boolean right_answer) {
        this.right_answer = right_answer;
    }
}
