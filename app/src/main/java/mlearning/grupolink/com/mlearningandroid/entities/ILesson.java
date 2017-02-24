package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ILesson {


    @GET("getLesson")
    Call<Lesson> getLessonFrom(@Query("msisdn") String msisdn,
                               @Query("cod_servicio") String CountryCode,
                               @Query("course") String course,
                               @Query("limit") String limit,
                               @Query("offset") String offset);

    @GET("getLesson")
    Call<Lesson> getLessonFrom(@Query("cod_servicio") String CountryCode,
                               @Query("course") String course,
                               @Query("limit") String limit,
                               @Query("offset") String offset);

    @DatabaseTable(tableName = "LessonTable")
    public class Lesson {

        private List<rowsLesson> rows;
        private summaryLesson summary;
        @DatabaseField
        private int code;
        @DatabaseField
        private String cdn;
        @DatabaseField
        private int num_rows;
        @DatabaseField
        private String description;
        @DatabaseField
        private int idCourse;
        @DatabaseField
        String message_alert;

        int status;
        int cant_cursos_free;
        int cant_cursos_free_usados;
        List<Integer> free_taken_courses_IDs;


        public List<rowsLesson> getRows() {
            return rows;
        }

        public void setRows(List<rowsLesson> rows) {
            this.rows = rows;
        }

        public summaryLesson getSummary() {
            return summary;
        }

        public void setSummary(summaryLesson summary) {
            this.summary = summary;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public int getNum_rows() {
            return num_rows;
        }

        public void setNum_rows(int num_rows) {
            this.num_rows = num_rows;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIdCourse() {
            return idCourse;
        }

        public void setIdCourse(int idCourse) {
            this.idCourse = idCourse;
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
