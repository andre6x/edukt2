package mlearning.grupolink.com.mlearningandroid.entities;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GrupoLink on 05/10/2015.
 */
public interface IPushNotification {

    @GET("pushid/{id}/codServ/{id2}/msisdn/{id3}")
    Call<PushNotification> getPushNotification(@Path("id") String push,
                                               @Path("id2") String CountryCode,
                                               @Path("id3") String msisdn);

    @GET("pushid/{id}")
    Call<PushNotification> getPushNotification_2(@Path("id") String push);

    public class PushNotification{

        private int code ;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
