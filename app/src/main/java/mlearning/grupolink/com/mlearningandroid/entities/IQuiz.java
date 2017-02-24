package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface IQuiz {


    @GET("getQuiz")
    Call<quiz> getQuizFrom(@Query("lesson") String lesson,
                           @Query("cod_servicio") String CountryCode);

    public class quiz {

        private summaryQuiz summary;
        private int code;
        private List<rowsQuiz> rows;
        private List<List<question>> questions;
        private List<List<List<answer>>> answers;
        private String description;
        String message_alert;

        public summaryQuiz getSummary() {
            return summary;
        }

        public void setSummary(summaryQuiz summary) {
            this.summary = summary;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<rowsQuiz> getRows() {
            return rows;
        }

        public void setRows(List<rowsQuiz> rows) {
            this.rows = rows;
        }

        public List<List<question>> getQuestions() {
            return questions;
        }

        public void setQuestions(List<List<question>> questions) {
            this.questions = questions;
        }

        public List<List<List<answer>>> getAnswers() {
            return answers;
        }

        public void setAnswers(List<List<List<answer>>> answers) {
            this.answers = answers;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMessage_alert() {
            return message_alert;
        }

        public void setMessage_alert(String message_alert) {
            this.message_alert = message_alert;
        }
    }
}
