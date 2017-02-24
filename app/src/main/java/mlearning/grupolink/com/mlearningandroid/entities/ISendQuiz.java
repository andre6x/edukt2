package mlearning.grupolink.com.mlearningandroid.entities;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ISendQuiz {

    @FormUrlEncoded
    @POST("saveQuiz")
    Call<Sendquiz> setSendQuizFrom(@Query("lesson") String lesson,
                                   @Query("msisdn") String msisdn,
                                   @Query("cod_servicio") String CountryCode,
                                   @Field("answer") JSONArray answer);

    public class Sendquiz {

        private List<detailSaveQuiz> details;
        private SummarySaveQuiz summary;
        private int code;
        private String description;
        private String message_alert;

        int status;
        int cant_cursos_free;
        int cant_cursos_free_usados;
        List<Integer> free_taken_courses_IDs;

        public List<detailSaveQuiz> getDetails() {
            return details;
        }

        public void setDetails(List<detailSaveQuiz> details) {
            this.details = details;
        }

        public SummarySaveQuiz getSummary() {
            return summary;
        }

        public void setSummary(SummarySaveQuiz summary) {
            this.summary = summary;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCant_cursos_free() {
            return cant_cursos_free;
        }

        public void setCant_cursos_free(int cant_cursos_free) {
            this.cant_cursos_free = cant_cursos_free;
        }

        public int getCant_cursos_free_usados() {
            return cant_cursos_free_usados;
        }

        public void setCant_cursos_free_usados(int cant_cursos_free_usados) {
            this.cant_cursos_free_usados = cant_cursos_free_usados;
        }

        public List<Integer> getFree_taken_courses_IDs() {
            return free_taken_courses_IDs;
        }

        public void setFree_taken_courses_IDs(List<Integer> free_taken_courses_IDs) {
            this.free_taken_courses_IDs = free_taken_courses_IDs;
        }
    }
}
