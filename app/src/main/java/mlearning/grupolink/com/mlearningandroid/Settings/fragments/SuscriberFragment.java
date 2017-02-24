package mlearning.grupolink.com.mlearningandroid.Settings.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.activities.SettingsActivity;
import mlearning.grupolink.com.mlearningandroid.Utils.SharedPreferencesManager;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.IPushNotification;
import mlearning.grupolink.com.mlearningandroid.entities.ISuscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SuscriberFragment extends Fragment {

    private View view;
    EditText numeroIngresado;
    EditText codigoPais;
    CardView enviarNumero;
    LinearLayout contentIngresoNumero;
    LinearLayout texto_cabecera1;
    LinearLayout texto_cabecera2;
    LinearLayout texto_cabecera3_mx;
    LinearLayout texto_cabecera4_chile;
    LinearLayout texto_cabecera5_nicaragua;
    LinearLayout texto_cabecera6_salvador;
    LinearLayout texto_pie1;
    LinearLayout texto_pie2;
    private String TAG = "SuscriberActivity";
    private ISuscriber.Suscripcion suscripcion;
    String auxNumero = "";
    String codigoArea = "";
    String CountryCode = "";

    private static String KEY_NUMERO = "numero";
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    private static String KEY_SUSCRIBER = "isSuscriber";
    private static String PREFERENCIA_COUNTRYCODE = "Country_Code";
    private static String KEY_COUNTRYCODE = "isCountry_Code";
    private static String PREFERENCIA_PUSH_NUM = "Push_num_Code";
    private static String KEY_PUSH_NUM = "isPush_num_Code";

    private String linkweb_pais ="";
    private int idCourse = 0;
    private String inicio ="";

    private AsyncTaskLoader<Void> loader;
    public ProgressDialog Dialog;

    private Call<ISuscriber.Suscripcion> call_1;
    private boolean isDestroyed = false;
    private Toolbar mToolbar;

    private Call<IPushNotification.PushNotification> call_2;
    private IPushNotification.PushNotification mNotification;
    private String token="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.suscriber_fragment, container, false);


        CountryCode = ((SettingsActivity)getActivity()).CountryCode ;
        Log.e(TAG,"COUNTRY:"+ CountryCode );


        codigoPais = (EditText) view.findViewById(R.id.codigoPais);
        numeroIngresado = (EditText) view.findViewById(R.id.numeroIngresado);

        enviarNumero = (CardView) view.findViewById(R.id.enviarNumero);
        contentIngresoNumero = (LinearLayout) view.findViewById(R.id.contentIngresoNumero);
        texto_cabecera1 = (LinearLayout) view.findViewById(R.id.texto_cabecera1);
        texto_cabecera2 = (LinearLayout) view.findViewById(R.id.texto_cabecera2);
        texto_cabecera3_mx = (LinearLayout) view.findViewById(R.id.texto_cabecera3_mx);
        texto_cabecera4_chile = (LinearLayout) view.findViewById(R.id.texto_cabecera4_chile);
        texto_cabecera5_nicaragua  = (LinearLayout) view.findViewById(R.id.texto_cabecera5_nicaragua);
        texto_cabecera6_salvador  = (LinearLayout) view.findViewById(R.id.texto_cabecera6_salvador);

        texto_pie1 = (LinearLayout) view.findViewById(R.id.texto_pie1);
        texto_pie2 = (LinearLayout) view.findViewById(R.id.texto_pie2);

        texto_cabecera1.setVisibility(View.GONE);
        texto_cabecera2.setVisibility(View.GONE);
        texto_cabecera3_mx.setVisibility(View.GONE);
        texto_cabecera4_chile.setVisibility(View.GONE);
        texto_cabecera5_nicaragua.setVisibility(View.GONE);
        texto_cabecera6_salvador.setVisibility(View.GONE);
        texto_pie1.setVisibility(View.GONE);
        texto_pie2.setVisibility(View.GONE);

        Dialog = new ProgressDialog(getActivity());



        if (Utils.getCountryCode(getActivity()) != null )
            CountryCode = Utils.getCountryCode(getActivity());
        codigoPais.setText("+" + CountryCode);

        /*if(CountryCode.equals("52")) {
            CountryCode = "593";
        }*/

        if(CountryCode.equals("503")){ //salvador
            int length = 8;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(length);
            numeroIngresado.setFilters(FilterArray);
            texto_cabecera6_salvador.setVisibility(View.VISIBLE);

        }else if(CountryCode.equals("505")){ //nicaragua
            int length = 8;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(length);
            numeroIngresado.setFilters(FilterArray);
            texto_cabecera5_nicaragua.setVisibility(View.VISIBLE);

        }else if(CountryCode.equals("56")){ //chile
            int length = 9;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(length);
            numeroIngresado.setFilters(FilterArray);
            texto_cabecera4_chile.setVisibility(View.VISIBLE);

        }else if(CountryCode.equals("54")) { //argentina
            int length = 10;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(length);
            numeroIngresado.setFilters(FilterArray);

            texto_cabecera2.setVisibility(View.VISIBLE);
            //texto_pie2.setVisibility(View.VISIBLE);

        }else if(CountryCode.equals("52")){ //mexico
            int length = 10;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(length);
            numeroIngresado.setFilters(FilterArray);
            texto_cabecera3_mx.setVisibility(View.VISIBLE);

        }else{ //ecuador 593
            texto_cabecera1.setVisibility(View.VISIBLE);
            //texto_pie1.setVisibility(View.VISIBLE);
        }



        enviarNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado();
                if (camposLlenos()) {
                    if (conexion())
                        LoadingData(1);

                }
            }
        });

        contentIngresoNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado();
            }
        });

        return  view;
    }

    private void showLoadingDialog(){
        Dialog.setMessage("Espere un Momento..");
        Dialog.setCancelable(false);
        Dialog.show();
    }

    private void showLayoutDialog(){
        if (Dialog != null)
            Dialog.dismiss();
    }

    private void LoadingData(int index) {

        switch (index) {
            case 1:
                int numeroLength;
                //Log.d(TAG, "**---** Numero enviado "+numeroIngresado.getText().toString());
                numeroLength = numeroIngresado.getText().toString().length();
                codigoArea = codigoPais.getText().toString().substring(1);

                if (CountryCode.equals("593") ){
                    if (numeroLength >= 10)
                        auxNumero = numeroIngresado.getText().toString().substring(1);
                    else
                        auxNumero = numeroIngresado.getText().toString();
                }
                else {  //505 503 56  54  52
                    auxNumero = numeroIngresado.getText().toString();
                }

                //Log.d(TAG, "**---** auxNumero "+auxNumero );
                auxNumero = codigoArea+""+auxNumero;
                Log.d(TAG, "**---** auxNumerofinal "+auxNumero );


                showLoadingDialog();
                restartLoadingIsSsuscription();
                break;
        }
    }


    private void restartLoadingIsSsuscription(){
        Log.e(TAG,getString(R.string.base_path) +"isSubscribed?msisdn="+auxNumero + "&cod_servicio="+CountryCode +"&channel="+ Utils.CHANNEL );
        ISuscriber suscription = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);
        call_1 = suscription.isSuscriber(auxNumero, CountryCode, Utils.CHANNEL);
        call_1.enqueue(new Callback<ISuscriber.Suscripcion>() {
            @Override
            public void onResponse(Call<ISuscriber.Suscripcion> call, Response<ISuscriber.Suscripcion> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    suscripcion = response.body();
                    postExecutionValidoSuscripcion();
                } else {
                    showLayoutDialog();
                    Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ISuscriber.Suscripcion> call, Throwable t) {
                if (!isDestroyed) {
                    showLayoutDialog();
                }
                showLayoutDialog();
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));
                t.printStackTrace();
            }
        });
    }

    private void restartPushNotification(){


        if(Utils.isOnline(getActivity() )) {
            token = SharedPreferencesManager.getValorEsperado(getActivity() , getString(R.string.preferencias_inicio), "gcmToken");
            if(token==null || token.equalsIgnoreCase("")){
                Log.e(TAG, "token is null");
            }else{
                Log.e(TAG, "token=" + token);
            }
        }else{
            Log.e(TAG, "Verificar conexión de internet");
        }
        Log.e(TAG,getString(R.string.base_path_push_notification) + "pushid/"+ token +"/codServ/"+CountryCode +"/msisdn/"+ auxNumero);

        IPushNotification notifi = MlearningApplicattion.getApplication().getmRestPushAdapter().create(IPushNotification.class);
        call_2 = notifi.getPushNotification(token, CountryCode, auxNumero);
        call_2.enqueue(new Callback<IPushNotification.PushNotification>() {
            @Override
            public void onResponse(Call<IPushNotification.PushNotification> call, Response<IPushNotification.PushNotification> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "PushNotification Respuesta exitosa");
                    mNotification = response.body();
                    if ( mNotification != null ) {
                        if (mNotification.getCode() == 0) {
                            //guardo en preferencia de enviado push y misdni codserv
                            SharedPreferencesManager.setValor(getActivity(), PREFERENCIA_PUSH_NUM, "true", KEY_PUSH_NUM);
                        }
                    }

                    nextMain();

                } else {

                    nextMain();
                    Log.e(TAG, "PushNotification Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<IPushNotification.PushNotification> call, Throwable t) {

                nextMain();
                Log.e(TAG, "PushNotification Error en la petición onFailure");
                t.printStackTrace();
            }
        });
    }


    private boolean camposLlenos() {
        if ( CountryCode.equals("505") || CountryCode.equals("503") ){

            if (numeroIngresado.getText().toString().length() == 8) {
                return true;
            } else if (numeroIngresado.getText().toString().length() > 0) {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number_4));
                return false;
            } else {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number));
                return false;
            }

        }else if ( CountryCode.equals("54") || CountryCode.equals("52") ){

            if (numeroIngresado.getText().toString().length() == 10) {
                return true;
            } else if (numeroIngresado.getText().toString().length() > 0) {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number_3));
                return false;
            } else {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number));
                return false;
            }

        }else { //593 ecuador //chile 56

            if (numeroIngresado.getText().toString().length() == 9) {
                return true;
            } else if (numeroIngresado.getText().toString().length() > 0) {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number_2));
                return false;
            } else {
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_insert_number));
                return false;
            }
        }
    }



    private void postExecutionValidoSuscripcion() {

        showLayoutDialog();
        if(suscripcion != null) {
            if (suscripcion.getDescription() != null) {

                if (suscripcion.getCode().equals("104")){
                    Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.m_state_suscriber_104));
                    //System.out.println(getString(R.string.state_suscriber_104)+"");
                }
                else if (suscripcion.getCode().equals("105")){
                    Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.m_state_suscriber_105));
                    //System.out.println(getString(R.string.state_suscriber_105)+"");
                }
                else if (suscripcion.getCode().equals("0")){

                    if(suscripcion.getStatus().equals("2")){

                        if (suscripcion.getMessage_alert() != null )
                            Utils.generarAlertaNoSuscrito(getActivity(), getString(R.string.text_title_suscriber), suscripcion.getMessage_alert(),auxNumero, CountryCode , false);
                        else
                            Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));

                    }else if (suscripcion.getStatus().equals("0")){
                        if (suscripcion.getMessage_alert() != null )
                            Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), suscripcion.getMessage_alert());
                        else
                            Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));


                    }else if (suscripcion.getStatus().equals("1")) {
                        suscriberOK();

                    }else if (suscripcion.getStatus().equals("3")) { //FREEMIUM
                        suscriberOK();

                    }else
                        Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));
                }else
                    Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));

            }else
                Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));
        }else
            Utils.generarAlerta(getActivity(), getString(R.string.text_title_suscriber), getString(R.string.text_error_metodo));


    }

    public void AlertaFree(String Title, String msm) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("" + Title);
        alert.setMessage("" + msm);
        //alert.setPositiveButton("OK 2", null);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alert, int id) {
                suscriberOK();
            }
        });
        alert.show();
    }


    private void suscriberOK() {

        SharedPreferencesManager.setValor(getActivity().getBaseContext(), PREFERENCIA_INICIAL, auxNumero, KEY_NUMERO);
        SharedPreferencesManager.setValor(getActivity(), PREFERENCIA_COUNTRYCODE, CountryCode, KEY_COUNTRYCODE);
        SharedPreferencesManager.setValorInt(getActivity().getBaseContext(), PREFERENCIA_INICIAL, 2, KEY_SUSCRIBER);

        restartPushNotification(); //guardo push , numero y codservicio
        //nextMain();

    }

    private void nextMain(){
        /*if(MainActivity.nextIntent != null)
            getActivity().startActivity(MainActivity.nextIntent);*/
        getActivity().finish();
    }



    private boolean conexion() {
        if (Utils.isNetworkAvailable(getActivity())){
            return true;
        }else {
            Utils.generarAlerta(getActivity(), getString(R.string.txt_error), getString(R.string.txt_error_conexion));
            return false;
        }
    }
    private boolean errorConexion() {
        if (Utils.isNetworkAvailable(getActivity())) {
            Log.d(TAG, "-->ingreos network ok" );
            return true;
        }else{
            if (Dialog != null) {
                Dialog.dismiss();
            }
            Log.d(TAG, "-->ingreos network NO" );
            Utils.generarAlerta(getActivity(), getString(R.string.txt_error), getString(R.string.txt_error_conexion));
            return false;
        }
    }


    protected void ocultarTeclado() {
        // TODO Auto-generated method stub
        // Lineas para ocultar el teclado virtual (Hide keyboard)
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(numeroIngresado.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Dialog != null) {
            Dialog.dismiss();
            Dialog = null;
        }
        if(call_1!=null && !call_1.isCanceled()) {
            call_1.cancel();
        }

        if(call_2!=null && !call_2.isCanceled()) {
            call_2.cancel();
        }


        isDestroyed = true;

    }

    @Override
    public void onStop() {
        super.onStop();
    }



}
