package mlearning.grupolink.com.mlearningandroid.Settings.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.activities.SettingsActivity;
import mlearning.grupolink.com.mlearningandroid.Utils.SharedPreferencesManager;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;

/**
 * Created by User on 14/04/2015.
 */
public class DesvinculaTelefono extends Fragment {
    String numero= null;
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    String KEY_NUMERO = "numero";
    boolean isVincular = false, isDesvincular = false;


    private View view;
    TextView numeroIngresado;
    CardView card_numero;
    CardView desvincularNumero;
    CardView vincularNumero;
    Dialog customDialog2 = null;
    String KEY_SUSCRIBER = "isSuscriber";
    public static String cdn;
    private ICountries.Countries mCountries;
    Dialog customDialog = null;

    private static String PREFERENCIA_COUNTRYCODE = "Country_Code";
    String KEY_COUNTRYCODE = "isCountry_Code";
    private String CountryCode = "";

    private static String PREFERENCIA_PUSH_NUM = "Push_num_Code";
    private static String KEY_PUSH_NUM = "isPush_num_Code";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.desvincula_telefono, container, false);
        card_numero = (CardView) view.findViewById(R.id.card_numero);
        numeroIngresado = (TextView) view.findViewById(R.id.numeroIngresado);
        desvincularNumero = (CardView) view.findViewById(R.id.desvincularNumero);
        vincularNumero = (CardView) view.findViewById(R.id.vincularNumero);

        numero = SharedPreferencesManager.getValorEsperado(getActivity().getBaseContext(), PREFERENCIA_INICIAL, KEY_NUMERO);
        if (numero == null) {
            card_numero.setVisibility(View.GONE);
            despintarDesvincular();
            pintarVincular();
            isVincular = true;
        } else {
            card_numero.setVisibility(View.VISIBLE);
            despintarVincular();
            isDesvincular = true;
        }
            numeroIngresado.setText(numero);
            desvincularNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDesvincular) {
                        initPopupWindow();
                    }


                }
            });
            vincularNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isVincular) {
                        //System.out.println("Procedemos a llamar a la actividad de Ingreso de Numero");
                        ingresaNumero();

                    }

                }
            });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        numero = SharedPreferencesManager.getValorEsperado(getActivity().getBaseContext(), PREFERENCIA_INICIAL, KEY_NUMERO);
        if (numero != null) {
            pintarDesvincular();
            despintarVincular();
            isVincular = false;
            isDesvincular = true;
        }
        if (isDesvincular){
            card_numero.setVisibility(View.VISIBLE);
            numeroIngresado.setText(numero);
        }
    }

    private void despintarVincular(){
        vincularNumero.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.silver_button_Setting));
    }

    private void despintarDesvincular(){
        desvincularNumero.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.silver_button_Setting));
    }

    private void pintarVincular(){
        vincularNumero.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.greenSetting));
    }

    private void pintarDesvincular(){
        desvincularNumero.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.greenSetting));
    }

    private void eliminarPreferencias(){
        //System.out.println("Proceso existos, se enviara SMS con Pin");
        SharedPreferencesManager.deleteValor(getActivity().getBaseContext(),PREFERENCIA_INICIAL, KEY_NUMERO);
        SharedPreferencesManager.setValorInt(getActivity(), PREFERENCIA_INICIAL, 0, KEY_SUSCRIBER);
        SharedPreferencesManager.setValor(getActivity(), PREFERENCIA_COUNTRYCODE, null, KEY_COUNTRYCODE);
        SharedPreferencesManager.setValor(getActivity(),PREFERENCIA_PUSH_NUM, null, KEY_PUSH_NUM ); //  envio push msis cod a json edukt

        numero = "";
        Utils.generarAlerta(getActivity(), getActivity().getString(R.string.subscribe), getActivity().getString(R.string.text_no_suscribe));
        despintarDesvincular();
        pintarVincular();
        card_numero.setVisibility(View.GONE);
        isDesvincular = false;
        isVincular = true;
    }

    private void ingresaNumero(){
        //llamar ametodo de laactivdad para reemplazar fragmento
        ((SettingsActivity)getActivity()).callFragmentCountries();


    }

    private void initPopupWindow() {
        // TODO Auto-generated method stub

        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        //customDialog.setCancelable(false);
        customDialog.setCancelable(true);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.popup_layout);

        TextView text = (TextView) customDialog.findViewById(R.id.text);
        Button cancelar = (Button) customDialog.findViewById(R.id.btnCancel);
        Button continuar = (Button) customDialog.findViewById(R.id.btnNext);

        text.setText(Html.fromHtml(Config.notification_suscriber));

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                eliminarPreferencias();
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }


}
