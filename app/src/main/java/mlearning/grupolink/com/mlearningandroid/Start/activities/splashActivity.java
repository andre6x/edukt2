package mlearning.grupolink.com.mlearningandroid.Start.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.AsyncTaskLoader;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.SharedPreferencesManager;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.ISuscriber;
import mlearning.grupolink.com.mlearningandroid.entities.ISuscriberInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GrupoLink on 08/04/2015.
 */
public class splashActivity extends FragmentActivity {

    private final static int TIMEXSPLASH = 3500;
    private String TAG = "splashActivity";
    private Handler handler;
    private Runnable runnable;
    String numeroSuscripcion;
    String KEY_NUMERO = "numero";
    String KEY_SUSCRIBER = "isSuscriber";
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    private static String PREFERENCIA_MENSAJE = "mensajes_alerta";
    String KEY_MESSAGE = "isMessage_alert";

    private static String PREFERENCIA_COUNTRYCODE = "Country_Code";
    String KEY_COUNTRYCODE = "isCountry_Code";
    private AsyncTaskLoader<Void> loader;
    private ISuscriber.Suscripcion suscripcion;
    private ISuscriberInfo.SuscriberInfo infoSuscrito;
    private ICountries.Countries mCountries;

    private final int ID_CATEGORIA_MY_COURSES = -2;
    private MixpanelAPI mixpanel;
    private String CountryCode ;
    private String CountryID = "";

    private Call<ISuscriber.Suscripcion> call_0;
    private Call<ISuscriberInfo.SuscriberInfo> call_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_INICIAL, "593996615765", KEY_NUMERO);
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_COUNTRYCODE, "593", KEY_COUNTRYCODE);//
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_INICIAL, "593984637981", KEY_NUMERO);
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_INICIAL, "593995710471", KEY_NUMERO);
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_INICIAL, "593979357651", KEY_NUMERO);
        //SharedPreferencesManager.setValor(getBaseContext(), PREFERENCIA_INICIAL, "5537844217", KEY_NUMERO);


        CountryCode = Utils.getCountryCode(this);
        //verificaNumeroSuscrito(2);

        numeroSuscripcion = SharedPreferencesManager.getValorEsperado(this, PREFERENCIA_INICIAL, KEY_NUMERO);
        if(numeroSuscripcion != null){
            if(Utils.isNetworkAvailable(this)) {
                verificaNumeroSuscrito(0);
            }
        }else{
            //System.out.println("**--** No existe numero en la preferencia");
            SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 0, KEY_SUSCRIBER);
            //SharedPreferencesManager.setValor(this, PREFERENCIA_MENSAJE, "", KEY_MESSAGE);

        }

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashActivity.this,  /*MainActivity*/FavoriteCategoryActivity.class);
                intent.putExtra("iniciaDesde", "splashActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                finish();
            }
        };

    }


    private void verificaNumeroSuscrito(int index) {
        switch (index ) {
            case 0:
                restartLoadingIsSuscriber();
                break;
            case 1 :
                restartLoadingInfoSuscriber();
                break;
        }
    }

    private void restartLoadingIsSuscriber( ){
        ISuscriber suscriber = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);
        call_0 = suscriber.isSuscriber(numeroSuscripcion, CountryCode , Utils.CHANNEL );
        call_0.enqueue(new Callback<ISuscriber.Suscripcion>() {
            @Override
            public void onResponse(Call<ISuscriber.Suscripcion> call, Response<ISuscriber.Suscripcion> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    suscripcion = null;
                    suscripcion = response.body();

                    validoSuscripcion();
                } else {
                    //showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ISuscriber.Suscripcion> call, Throwable t) {
                /*if (!isDestroyed)
                    showRetry();
                showRetry();*/
                t.printStackTrace();
            }
        });
    }

    private void restartLoadingInfoSuscriber( ){
        ISuscriberInfo topTen = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriberInfo.class);
        call_1 = topTen.getSuscriberInfo(numeroSuscripcion, CountryCode);
        call_1.enqueue(new Callback<ISuscriberInfo.SuscriberInfo>() {
            @Override
            public void onResponse(Call<ISuscriberInfo.SuscriberInfo> call, Response<ISuscriberInfo.SuscriberInfo> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    infoSuscrito = null;
                    infoSuscrito = response.body();

                    postExecutionInfoSuscriber();
                } else {
                    //showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ISuscriberInfo.SuscriberInfo> call, Throwable t) {
                /*if (!isDestroyed)
                    showRetry();
                showRetry();*/
                t.printStackTrace();
            }
        });
    }


    public void postExecutionInfoSuscriber(){
        if (infoSuscrito != null)
            if (infoSuscrito.getCode() == 0)
                envioMixPanel();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call_0!=null && !call_0.isCanceled()) {
            call_0.cancel();
        }
        if(call_1!=null && !call_1.isCanceled()) {
            call_1.cancel();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(handler != null)
            handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(handler != null) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, TIMEXSPLASH);
        }
    }


    private void validoSuscripcion() {
        try {
            if (suscripcion != null) {
                if (suscripcion.getCode().equals("104")) {
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 1, KEY_SUSCRIBER);
                    //System.out.println(getString(R.string.state_suscriber_104) + "");
                } else if (suscripcion.getCode().equals("105")) {
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 1, KEY_SUSCRIBER);
                   // System.out.println(getString(R.string.state_suscriber_105) + "");
                }else if (suscripcion.getStatus().equals("2")) {
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 0, KEY_SUSCRIBER);
                    SharedPreferencesManager.setValor(this, PREFERENCIA_MENSAJE, suscripcion.getMessage_alert(), KEY_MESSAGE);
                    //System.out.println(getString(R.string.state_suscriber_2) + "");
                    //suscripcion.getMessage_alert();
                }
                else if (suscripcion.getStatus().equals("1") /* || (suscripcion.getStatus().equals("0"))*/ )  {
                    //System.out.println("Numero OK");
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 2, KEY_SUSCRIBER);
                    verificaNumeroSuscrito(1);

                 }else if (suscripcion.getStatus().equals("3") )  { //freemium.
                    //System.out.println("Numero OK");
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 2, KEY_SUSCRIBER);
                    //verificaNumeroSuscrito(1);

                } else if (suscripcion.getStatus().equals("0"))  {
                    //System.out.println("Numero OK pero no activo");
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 3, KEY_SUSCRIBER);
                    SharedPreferencesManager.setValor(this, PREFERENCIA_MENSAJE, suscripcion.getMessage_alert(), KEY_MESSAGE);

                }
                else {
                    SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 1, KEY_SUSCRIBER);
                    //System.out.println(getString(R.string.state_suscriber_2) + "--else");

                }
            }else
                SharedPreferencesManager.setValorInt(this, PREFERENCIA_INICIAL, 1, KEY_SUSCRIBER);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void envioMixPanel() {

        mixpanel = Utils.getmixPanel(this);
        mixpanel.getPeople().identify(numeroSuscripcion);
        mixpanel.getPeople().set("$number", "+"+numeroSuscripcion);
        if (infoSuscrito.getInfo().getName_susc() != null  && infoSuscrito.getInfo().getLastname_susc() != null )
             mixpanel.getPeople().set("$name", infoSuscrito.getInfo().getName_susc() +" "+ infoSuscrito.getInfo().getLastname_susc() );
         if (infoSuscrito.getInfo().getLastname_susc() != null  )
             mixpanel.getPeople().set("$lastname",  infoSuscrito.getInfo().getLastname_susc());
         if (infoSuscrito.getInfo().getDate_created() != null  )
             mixpanel.getPeople().set("$created",  infoSuscrito.getInfo().getDate_created());
         if (infoSuscrito.getInfo().getEmail_susc() != null  )
             mixpanel.getPeople().set("$email", infoSuscrito.getInfo().getEmail_susc() );


        MixpanelAPI.People people = mixpanel.getPeople();
        people.identify(numeroSuscripcion);
        people.initPushHandling("460186391963");//numero proy google diego
        //people.initPushHandling("497796584544"); // and pruebas

        /*mixpanel.getPeople().set("$created",  "2011-03-20 16:53:54");
        mixpanel.getPeople().set("$last_login",  "2011-03-21 16:53:54");*/

        //MixpanelAPI mMixpanel = MixpanelAPI.getInstance(this, YOUR_MIXPANEL_PROJECT_ID_TOKEN);

    }


    private void guardarCodigoPais() {
        SharedPreferencesManager.setValor(this, PREFERENCIA_COUNTRYCODE, CountryCode, KEY_COUNTRYCODE);
    }

    public String GetCountryZipCode(){
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();
       /* String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;*/
        return CountryID;

    }


}
