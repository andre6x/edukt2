package mlearning.grupolink.com.mlearningandroid.Information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.ISuscriber;

/**
 * Created by usuario on 18/09/2015.
 */
public class TermsConditionsActivity extends AppCompatActivity {

    EditText numeroIngresado;
    EditText codigoPais;
    Button enviarNumero;
    LinearLayout contentIngresoNumero;
    LinearLayout texto_cabecera1;
    LinearLayout texto_cabecera2;
    LinearLayout texto_pie1;
    LinearLayout texto_pie2;
    private String TAG = "SuscriberActivity";
    private ISuscriber.Suscripcion suscripcion;
    String auxNumero = "";
    String codigoArea = "";
    String CountryCode = "";
    private String idpais ="";
    private WebView webTextSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions_arg_ecu);
        //Utils.SetStyleActionBar(this, getString(R.string.txt_configuraciones));
        Utils.SetStyleToolbarTitle(this, getString(R.string.txt_configuraciones));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idpais = extras.getString("pais");
        }

        webTextSettings = (WebView) findViewById(R.id.webTextSettings);
        WebSettings webSettings = webTextSettings.getSettings();
        webSettings.setTextZoom(100);
        webTextSettings.setBackgroundColor(0x00000000);
        webTextSettings.getSettings().setJavaScriptEnabled(true);

        webTextSettings.requestFocus(View.FOCUS_DOWN);
        webTextSettings.clearHistory();
        webTextSettings.clearCache(true);
        webTextSettings.getSettings().setDomStorageEnabled(true);
        webTextSettings.getSettings().setUserAgentString("ua");
        webTextSettings.getSettings().setLoadWithOverviewMode(true);
        webTextSettings.getSettings().setAllowFileAccess(true);
        webTextSettings.getSettings().setPluginState(WebSettings.PluginState.ON);
        webTextSettings.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setDomStorageEnabled(true);

        String m_data="";
        if (idpais.equals("ec")) {
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","ECUADOR");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_ecuador.html");
            m_data = getStringFromAssets("bases_condiciones_ecuador.html", getApplicationContext());

        } else if (idpais.equals("mx")) {
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","MÉXICO");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_mexico.html");
            m_data = getStringFromAssets("bases_condiciones_mexico.html", getApplicationContext());

        } else if (idpais.equals("chl")) {
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","CHILE");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_chile.html");
            m_data = getStringFromAssets("bases_condiciones_chile.html", getApplicationContext());

        } else if(idpais.equals("ar")) {
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","ARGENTINA");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_argentina.html");
            m_data = getStringFromAssets("bases_condiciones_argentina.html", getApplicationContext());

        }else if (idpais.equals("ni")){
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","NICARAGUA");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_nicaragua.html");
            m_data = getStringFromAssets("bases_condiciones_nicaragua.html", getApplicationContext());

        }else if (idpais.equals("sv")){
            try{
                Utils.sendGoogleAnalyticsTracker("terminos","SALVADOR");
            }catch (Exception e){e.printStackTrace();}
            //webTextSettings.loadUrl("file:///android_asset/bases_condiciones_salvador.html");
            m_data = getStringFromAssets("bases_condiciones_salvador.html", getApplicationContext());
        }

        webTextSettings.loadDataWithBaseURL("file:///android_asset/", m_data, "text/html", "utf-8", null);
        webTextSettings.setWebChromeClient(new WebChromeClient());
        webTextSettings.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }
            public void onPageFinished(WebView view, String url) {
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion == 14) {
                    webTextSettings.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
                }

            }
        });


    }

    public static String getStringFromAssets(String name, Context p_context)
    {
        try
        {
            InputStream is = p_context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String bufferString = new String(buffer);
            return bufferString;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
            /*FooterMenu.fmt.finish();
            Intent actividad = new Intent(this, FooterMenu.class);
            actividad.putExtra("actividad", 2);
            startActivity(actividad);*/

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
          super.onBackPressed();
            finish();
        /*FooterMenu.fmt.finish();
            Intent actividad = new Intent(this, FooterMenu.class);
            actividad.putExtra("actividad", 2);
            startActivity(actividad);*/
        }


    @Override
    public void onDestroy() {

        super.onDestroy();
        if(webTextSettings != null){
            webTextSettings.onPause();
            webTextSettings.destroy();

        }

    }



}

