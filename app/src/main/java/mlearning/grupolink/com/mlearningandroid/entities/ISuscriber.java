package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 10/04/2015.
 */
public interface ISuscriber {

    @GET("isSubscribed")
    Call<Suscripcion> isSuscriber(@Query("msisdn") String msisdn,
                                  @Query("cod_servicio") String CountryCode,
                                  @Query("channel") String channel);

    @GET("generatePIN")
    Call<Suscripcion> setSuscriber(@Query("msisdn") String msisdn,
                                   @Query("cod_servicio") String CountryCode,
                                   @Query("channel") String channel);

    @GET("verifyPIN")
    Call<Suscripcion> setPin(@Query("msisdn") String msisdn,
                             @Query("pin") String pin,
                             @Query("cod_servicio") String CountryCode,
                             @Query("channel") String channel);

    @GET("getPIN")
    Call<Suscripcion> getPin(@Query("msisdn") String msisdn);

    @FormUrlEncoded
    @POST("saveInfoSubscriber")
    Call<Suscripcion> saveSuscriptor(@Query("msisdn") String msisdn,
                                     @Query("cod_servicio") String CountryCode,
                                     @Field("name") String name,
                                     @Field("lastname") String lastname,
                                     @Field("email") String email);


    @GET("{id}/{id2}/{id3}/{id4}/{id5}/{id6}")
    Call<Suscripcion> sendDiploma(@Path("id") int course,
                                  @Path("id2") int id2,
                                  @Path("id3") int id3,
                                  @Path("id4") String CountryCode,
                                  @Path("id5") String msisdn,
                                  @Path("id6") int id5);

    public class Suscripcion{
        String description;
        String status;
        String pin;
        String code;
        String message_alert;
        int cant_cursos_free;
        int cant_cursos_free_usados;
        List<Integer> free_taken_courses_IDs;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage_alert() {
            return message_alert;
        }

        public void setMessage_alert(String message_alert) {
            this.message_alert = message_alert;
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
