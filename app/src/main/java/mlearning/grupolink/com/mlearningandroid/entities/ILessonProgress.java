package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ILessonProgress {


    @GET("getCourses")
    Call<LessonProgress> getLessonFrom(@Query("msisdn") String msisdn,
                                       @Query("cod_servicio") String CountryCode,
                                       @Query("course") String course);


    public class LessonProgress{

        private List<rows> rows;
        private summary summary;
        private int code;
        private String description;

    }
}
