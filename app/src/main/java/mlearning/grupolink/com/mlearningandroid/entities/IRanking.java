package mlearning.grupolink.com.mlearningandroid.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by usuario on 09/06/2015.
 */
public interface IRanking {
    @GET("saveRankCourse")
    Call<Ranking> saveRanking(@Query("msisdn") String msisdn,
                              @Query("cod_servicio") String CountryCode,
                              @Query("course") String course,
                              @Query("ranking") String ranking);

    public class Ranking {
        private String code;
        private String description;
        String message_alert;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
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
    }


}
