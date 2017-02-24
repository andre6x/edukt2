package mlearning.grupolink.com.mlearningandroid.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ITraking {

    @GET("saveTrackingCourse")
    Call<Traking> getTraking(@Query("msisdn") String msisdn,
                             @Query("cod_servicio") String CountryCode,
                             @Query("lesson") String lesson,
                             @Query("channel") String channel,
                             @Query("status") String status);

    public class Traking {
        private int code;
        private String description;

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
    }
}
