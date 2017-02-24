package mlearning.grupolink.com.mlearningandroid.Utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
//import mlearning.grupolink.com.mlearningandroid.activities.SearchActivity;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.ICursos;
import mlearning.grupolink.com.mlearningandroid.entities.IHomeCursos;
import mlearning.grupolink.com.mlearningandroid.entities.ILesson;
import mlearning.grupolink.com.mlearningandroid.entities.ILessonById;
import mlearning.grupolink.com.mlearningandroid.entities.ISuscriber;
import mlearning.grupolink.com.mlearningandroid.entities.ITopTen;
import mlearning.grupolink.com.mlearningandroid.entities.rows;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;
import mlearning.grupolink.com.mlearningandroid.entities.rowsHome;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonImages;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonVideos;
import mlearning.grupolink.com.mlearningandroid.entities.rowsTopTen;
import mlearning.grupolink.com.mlearningandroid.entities.summaryCategories;
import mlearning.grupolink.com.mlearningandroid.entities.summaryCountries;
/*import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.Query;*/


/**
 * Created by Pavilion on 15/03/2015.
 */
public class Utils {

    private static final ImageLoader sImageLoader;
    public static final String CHANNEL = "APP";
    public static String KEY_SUSCRIBER = "isSuscriber";
    public static String KEY_MSISDN = "numero";
    public static String PREFERENCIA_INICIAL = "codigo_inicial";
    public static String PREFERENCIA_COUNTRYCODE = "Country_Code";
    public static String KEY_COUNTRYCODE = "isCountry_Code";

    private static String PREFERENCIA_PUSH = "Push_Code";
    private static String KEY_PUSH = "isPush_Code";

    private static String PREFERENCIA_PUSH_NUM = "Push_num_Code";
    private static String KEY_PUSH_NUM = "isPush_num_Code";

    public static ProgressDialog DialogCountry;

    private static String iDate;
    private static Date jsonDate;

    private static SimpleDateFormat dateJson = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private static SimpleDateFormat dateJson_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateOutput = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateOutputL = new SimpleDateFormat("dd 'de' MMMM, HH:mm");
    private static SimpleDateFormat dateOutputL_HomeLocal = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy - HH:mm");
    private static SimpleDateFormat dateOutputL_HomeLocal_2 = new SimpleDateFormat("dd 'de' MMMM 'del' yyyy");

    static {
        sImageLoader = ImageLoader.getInstance();
    }


    public static String parseDateNotZone(String date) {
        try {
            jsonDate = dateJson_2.parse(date);
            iDate = dateOutputL_HomeLocal_2.format(jsonDate);
            return iDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }


    /**
     * Image cache
     *
     * @param imageUri
     * @param imageView
     */
    public static void loadImage(final String imageUri, final ImageView imageView) {
        if (imageUri != null) {
            ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
            sImageLoader.displayImage(imageUri, imageView, animateFirstListener);
        }
    }

    public static void generateToast(Activity ctx , String message)
    {
        LayoutInflater inflater = ctx.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,null,false);

        if(message != null)
            ((TextView) layout.findViewById(R.id.txt_sms)).setText(message);
        else
            return;

        Toast toast = new Toast(ctx);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    public static void generateToastBOTTOM(Activity ctx , String message)
    {
        LayoutInflater inflater = ctx.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,null,false);

        if(message != null)
            ((TextView) layout.findViewById(R.id.txt_sms)).setText(message);
        else
            return;

        Toast toast = new Toast(ctx);
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    public static void SetStyleActionBarHome(Activity act) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (3 * scale + 0.5f);
        int p_magin = (int) (8 * scale + 0.5f);
        int logp_p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER_VERTICAL);
        layout.setHorizontalGravity(Gravity.RIGHT);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
        params2.gravity = Gravity.CENTER;

        params.setMargins(0, 0, p, 0);
        params2.setMargins(0, 0, p_magin, 0);
        ImageView search = new ImageView(act);
        search.setImageResource(R.mipmap.ic_action_search);
        search.setLayoutParams(params);
        ImageView logo = new ImageView(act);
        logo.setImageResource(R.mipmap.logo);
        logo.setPadding(logp_p, logp_p, logp_p, logp_p);
        logo.setLayoutParams(params2);

        Buscar(search, act);
        layout.addView(logo);
        layout.addView(search);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(false);
        //actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setIcon(android.R.color.transparent);
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);
    }


    public static void SetStyleActionBarMenu(Activity act, String title) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (3 * scale + 0.5f);
        int logp_p = (int) (10 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params2.setMargins(0, 0, p, 0);
        params.setMargins(0, 0, logp_p, 0);
        ImageView search = new ImageView(act);
        search.setImageResource(R.mipmap.ic_action_search);


        ImageView logo = new ImageView(act);
        logo.setImageResource(R.mipmap.logo);
        logo.setPadding(logp_p, logp_p, logp_p, logp_p);
        logo.setLayoutParams(params);
        /*TextView txt = new TextView(act);
        txt.setTextSize(17f);
        txt.setTextColor(Color.WHITE);

        if(title != null)
            txt.setText(title);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.CENTER);*/
        //img.setVisibility(View.INVISIBLE);
        search.setLayoutParams(params2);
        layout.addView(logo);
        Buscar(search, act);
        layout.addView(search);

       /* actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);*/
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);

    }


    public static void SetStyleActionBar2(Activity act, String title) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (3 * scale + 0.5f);
        int p_magin = (int) (8 * scale + 0.5f);
        int logp_p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER_VERTICAL);
        layout.setHorizontalGravity(Gravity.RIGHT);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
        params2.gravity = Gravity.CENTER;

        params.setMargins(0, 0, p, 0);
        params2.setMargins(0, 0, p_magin, 0);
        ImageView search = new ImageView(act);
        search.setImageResource(R.mipmap.ic_action_search);
        search.setLayoutParams(params);
        TextView txt = new TextView(act);
        txt.setTextSize(17f);
        txt.setTextColor(Color.WHITE);
        if(title != null)
            txt.setText(title);
        txt.setPadding(logp_p, logp_p, logp_p, logp_p);
        txt.setLayoutParams(params2);
        txt.setGravity(Gravity.CENTER);

        Buscar(search, act);
        layout.addView(txt);
        layout.addView(search);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(false);
        //actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setIcon(android.R.color.transparent);
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);

    }

    public static void SetStyleActionBar(Activity act, String title) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, 0, p, 0);
        ImageView img = new ImageView(act);
        img.setImageResource(R.mipmap.back);

        TextView txt = new TextView(act);
        txt.setTextSize(17f);
        txt.setTextColor(Color.WHITE);
        if(title != null)
            txt.setText(title);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.CENTER);

        img.setVisibility(View.INVISIBLE);

        layout.addView(txt);
        layout.addView(img);

        /*actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);
    }


    public static void SetStyleActionBarLogoBack(Activity act, String title) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (11 * scale + 0.5f);
        int logp_p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, 0, p, 0);
        ImageView img = new ImageView(act);
        img.setImageResource(R.mipmap.back);


        ImageView logo = new ImageView(act);
        logo.setImageResource(R.mipmap.logo);
        logo.setPadding(logp_p, logp_p, logp_p, logp_p);
        logo.setLayoutParams(params);

        img.setVisibility(View.INVISIBLE);

        layout.addView(logo);
        layout.addView(img);

        /*actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);
    }

    public static void SetStyleActionBarLogoNback(Activity act, String title) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (11 * scale + 0.5f);
        int p_magin = (int) (8 * scale + 0.5f);
        int logp_p = (int) (5 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
        params.setMargins(0, 0, p, 0);

        ImageView logo = new ImageView(act);
        logo.setImageResource(R.mipmap.logo);
        logo.setPadding(logp_p, logp_p, logp_p, logp_p);
        logo.setLayoutParams(params);

        layout.addView(logo);


        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.color.black));
        actionBar.setCustomView(layout);

    }


    public static void SetStyleActionBarShare(Activity act, String title , String share) {

        ActionBar actionBar = act.getActionBar();
        final float scale = act.getResources().getDisplayMetrics().density;
        int p_ = (int) (3 * scale + 0.5f);
        int p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,p,0);
        params2.setMargins(0, 0, p_, 0);
        ImageView img = new ImageView(act);
        img.setImageResource(R.mipmap.ic_action_share);
        img.setLayoutParams(params2);

        if(share != null)
            if(share.length() > 0)
                shareImage(act ,img , share);

        TextView txt = new TextView(act);
        txt.setTextSize(17f);
        txt.setTextColor(Color.WHITE);

        if(title != null)
            txt.setText(title);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.CENTER);
        //img.setVisibility(View.INVISIBLE);
        layout.addView(txt);
        layout.addView(img);

   /*     actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/
        actionBar.setBackgroundDrawable(act.getResources().getDrawable(R.mipmap.btn_fondo_menu));
        actionBar.setCustomView(layout);
    }
    public static void SetStyleToolbarHome(Activity act ) {

        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        ((AppCompatActivity) act).setSupportActionBar( toolbar);
        // Remove default title text
        ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
        imgLogoToolBar.setVisibility(View.VISIBLE);

        TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
        txtTitleToolbar.setVisibility(View.GONE);

        ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
        imgSearchToolbar.setVisibility(View.VISIBLE);
        Buscar(imgSearchToolbar, act);
    }



    public static void SetStyleToolbarHome2(Activity act ,String title  ) {

        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        ((AppCompatActivity) act).setSupportActionBar( toolbar);
        // Remove default title text
        ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
        imgLogoToolBar.setVisibility(View.GONE);

        TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
        txtTitleToolbar.setVisibility(View.VISIBLE);
        txtTitleToolbar.setText(title);

        ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
        imgSearchToolbar.setVisibility(View.VISIBLE);
        Buscar(imgSearchToolbar, act);
    }

    public static void SetStyleToolbarLogo(Activity act  ) {

        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        ((AppCompatActivity) act).setSupportActionBar( toolbar);
        // Remove default title text
        ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);
        // BUTTON BACK
        toolbar.setNavigationIcon(R.mipmap.back);
        //((AppCompatActivity) act).getSupportActionBar().setDisplayUseLogoEnabled(true);

        ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
        imgLogoToolBar.setVisibility(View.VISIBLE);

        TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
        txtTitleToolbar.setVisibility(View.GONE);

        ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
        imgSearchToolbar.setVisibility(View.GONE);

    }

//fragmnt
    public static void SetStyleToolbarTitle(Activity act ,String title ) {

        final float scale = act.getResources().getDisplayMetrics().density;
        int p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, 0, p, 0);
        ImageView img = new ImageView(act);
        img.setImageResource(R.mipmap.back);

        TextView txt = new TextView(act);
        txt.setTextSize(17f);
        txt.setTextColor(Color.WHITE);
        if(title != null)
            txt.setText(title);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.CENTER);

        img.setVisibility(View.INVISIBLE);

        layout.addView(txt);
        layout.addView(img);

        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.addView(layout);

            ((AppCompatActivity) act).setSupportActionBar(toolbar);
            // Remove default title text
            ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);
            // BUTTON BACK
            toolbar.setNavigationIcon(R.mipmap.back);
            //((AppCompatActivity) act).getSupportActionBar().setDisplayUseLogoEnabled(true);

            ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
            imgLogoToolBar.setVisibility(View.GONE);

            TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
            txtTitleToolbar.setVisibility(View.GONE);//VISIBLE
            txtTitleToolbar.setText(title);

            ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
            imgSearchToolbar.setVisibility(View.GONE);
        }
    }

    public static void SetStyleToolbarTitleShare(Activity act ,String title , String share) {


        final float scale = act.getResources().getDisplayMetrics().density;
        int p_ = (int) (3 * scale + 0.5f);
        int p = (int) (11 * scale + 0.5f);

        LinearLayout layout=new LinearLayout(act);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setVerticalGravity(Gravity.CENTER);
        layout.setHorizontalGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,p,0);
        params2.setMargins(0, 0, p_, 0);
        ImageView img = new ImageView(act);
        img.setImageResource(R.mipmap.ic_action_share);
        img.setLayoutParams(params2);

        if(share != null)
            if(share.length() > 0)
                shareImage(act ,img , share);

        TextView txt = new TextView(act);
        txt.setTextSize(15f);
        txt.setTextColor(Color.WHITE);
        txt.setEllipsize(TextUtils.TruncateAt.END);
        txt.setMaxLines(2);

        if(title != null)
            txt.setText(title);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.CENTER);
        layout.addView(txt);
        layout.addView(img);



        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.addView(layout);
            ((AppCompatActivity) act).setSupportActionBar(toolbar);
            // Remove default title text
            ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);
            // BUTTON BACK
            toolbar.setNavigationIcon(R.mipmap.back);
            //((AppCompatActivity) act).getSupportActionBar().setDisplayUseLogoEnabled(true);

            ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
            imgLogoToolBar.setVisibility(View.GONE);

            TextView txtTitleToolbar = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
            txtTitleToolbar.setVisibility(View.GONE);
            txtTitleToolbar.setText(title);

            ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
            imgSearchToolbar.setVisibility(View.GONE);
            imgSearchToolbar.setImageResource(R.mipmap.ic_action_share);

            if (share != null)
                if (share.length() > 0)
                    shareImage(act, imgSearchToolbar, share);
        }
    }

    public static void SetStyleToolbarTitle2 (Activity act ,String title ) {


        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        if (toolbar != null) {



            ((AppCompatActivity) act).setSupportActionBar(toolbar);
            // Remove default title text
            ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);
            // BUTTON BACK
            toolbar.setNavigationIcon(R.mipmap.back);
            //((AppCompatActivity) act).getSupportActionBar().setDisplayUseLogoEnabled(true);

            ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
            imgLogoToolBar.setVisibility(View.GONE);

            TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
            txtTitleToolbar.setVisibility(View.VISIBLE);
            txtTitleToolbar.setText(title);

            ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
            imgSearchToolbar.setVisibility(View.GONE);
        }
    }


    public static void SetStyleToolbarSearch(Activity act ) {

        Toolbar toolbar = (Toolbar) act.findViewById(R.id.toolbar);
        if (toolbar != null) {

            ((AppCompatActivity) act).setSupportActionBar(toolbar);
            // Remove default title text
            ((AppCompatActivity) act).getSupportActionBar().setDisplayShowTitleEnabled(false);
            // BUTTON BACK
            toolbar.setNavigationIcon(R.mipmap.back);
            //((AppCompatActivity) act).getSupportActionBar().setDisplayUseLogoEnabled(true);

            ImageView imgLogoToolBar = (ImageView) toolbar.findViewById(R.id.imgLogoToolBar);
            imgLogoToolBar.setVisibility(View.GONE);

            TextView txtTitleToolbar  = (TextView) toolbar.findViewById(R.id.txtTitleToolbar);
            txtTitleToolbar.setVisibility(View.GONE);


            ImageView imgSearchToolbar = (ImageView) toolbar.findViewById(R.id.imgSearchToolbar);
            imgSearchToolbar.setVisibility(View.GONE);
            imgSearchToolbar.setImageResource(R.mipmap.ic_action_share);
        }

    }

    public static void shareImage(final Activity act,ImageView img ,final String text) {

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                act.startActivity(Intent.createChooser(sharingIntent, act.getResources().getString(R.string.txt_compartir)));
            }
        });
    }


    private static void Buscar(View view ,final Activity act)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(act, SearchActivity.class);
                act.startActivity(intent);
                act.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);*/

            }
        });
    }



    public static void DeleteCursos(Dao<ICursos.Cursos, Integer> cursos) throws SQLException {
        DeleteBuilder<ICursos.Cursos, Integer> deleteBuilder = cursos.deleteBuilder();
        deleteBuilder.delete();
    }

    /**
     * Save Cursos Data
     *
     * @param cursos
     * @param cursosDao
     */
    public static void DbsaveCursos(ICursos.Cursos cursos, Dao<ICursos.Cursos, Integer> cursosDao) {
        try {
            cursosDao.create(cursos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save countries Data
     *
     * @param countries
     * @param countriesDao
     */
    public static void DbsaveCountries(ICountries.Countries countries, Dao<ICountries.Countries, Integer> countriesDao) {
        try {
            countriesDao.create(countries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Curso
     *
     * @param cursosDao
     * @return
     * @throws SQLException
     */
    public static List<ICursos.Cursos> GetCursosFromDatabase(Dao<ICursos.Cursos, Integer> cursosDao , int idCategory) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable WHERE idCategory = "+idCategory;
        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        return cursos;
    }

    /**
     * Get Curso
     *
     * @param cursosDao
     * @return
     * @throws SQLException
     */
    public static List<ICursos.Cursos> GetCursosHomeFromDatabase(Dao<ICursos.Cursos, Integer> cursosDao, int idCategory) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable WHERE idCategory = "+idCategory;
        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        return cursos;
    }


    /**
     * Get Cursos
     *
     * @param cursosDao
     * @param rowsDao
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<ICursos.Cursos> GetCursosFromDatabase(Dao<ICursos.Cursos, Integer> cursosDao, Dao<rows, Integer> rowsDao, int idCategory) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable " + "WHERE idCategory = "+idCategory;

        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        if(cursos != null)
            if(cursos.size() == 1)
                cursos.get(0).setRows(GetRowsFromDatabase(rowsDao, idCategory));

        return cursos;
    }

    /**
     * Get Cursos
     *
     * @param cursosDao
     * @param rowsDao
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<ICursos.Cursos> GetCursosFromDatabase_V2(Dao<ICursos.Cursos, Integer> cursosDao, Dao<rows, Integer> rowsDao, int idCategory) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable " + "WHERE idCategory = "+idCategory;

        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        if(cursos != null)
            if(cursos.size() == 1)
                cursos.get(0).setRows(GetRowsFromDatabase_V2(rowsDao, idCategory));

        return cursos;
    }


    /**
     * Get Rows
     *
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<rows> GetRowsFromDatabase(Dao<rows, Integer> rowsDao, int idmed_categories) throws SQLException {
        List<rows> rows = null;
        String query = "SELECT * FROM rowsTable WHERE idmed_categories = "+idmed_categories;
        //String query = "SELECT * FROM rowsTable WHERE idmed_categories = "+idmed_categories+" and idHomeCategory = -2";
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();

        return rows;
    }

    /**
     * Get Rows
     *
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<rows> GetRowsFromDatabase_V2(Dao<rows, Integer> rowsDao, int idmed_categories) throws SQLException {
        List<rows> rows = null;
        String query = "SELECT  DISTINCT (idmed_courses) idmed_courses , * FROM rowsTable WHERE idmed_categories = " + idmed_categories ;
        //String query = "SELECT * FROM rowsTable WHERE idmed_categories = "+idmed_categories+" and idHomeCategory != -2 ";

        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();

        return rows;
    }


    /**
     * Get Cursos
     *
     * @param cursosDao
     * @param rowsDao
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<ICursos.Cursos> GetCursosHomeFromDatabase(Dao<ICursos.Cursos, Integer> cursosDao, Dao<rows, Integer> rowsDao, int idCategory) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable " +
                "WHERE idCategory = "+idCategory;
        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        if(cursos != null)
            if(cursos.size() == 1)
                cursos.get(0).setRows(GetRowsFromHomeDatabase(rowsDao, idCategory));

        return cursos;
    }


    /**
     * Get Rows
     *
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<rows> GetRowsFromHomeDatabase(Dao<rows, Integer> rowsDao, int idmed_categories) throws SQLException {
        List<rows> rows = null;
        //Log.e("PILAS", "BUSCADO HOME: " + idmed_categories);
        String query = "SELECT * FROM rowsTable WHERE idHomeCategory = "+idmed_categories;
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();

        return rows;
    }

    ///////////////////////////
    public static List<ICountries.Countries> GetCountriesFromDatabase(Dao<ICountries.Countries, Integer> countriesDao, Dao<rowsCountries, Integer> rowsCountriesDao) throws SQLException {
        List<ICountries.Countries> countries = null;
        String query = "SELECT cdn, num_rows  FROM CountriesTable ";
        GenericRawResults<ICountries.Countries> rawResults =countriesDao.queryRaw(query, countriesDao.getRawRowMapper());
        countries = rawResults.getResults();
        if(countries != null)
            if(countries.size() == 1)
                countries.get(0).setRows(GetRowsCountriesFromDatabase(rowsCountriesDao));
        return countries;
    }

    public static List<rowsCountries> GetRowsCountriesFromDatabase(Dao<rowsCountries, Integer> rowsCountriesDao) throws SQLException {
        List<rowsCountries> rowsCountries = null;
        String query = "SELECT * FROM rowsCountriesTable ";
        GenericRawResults<rowsCountries> rawResults = rowsCountriesDao.queryRaw(query, rowsCountriesDao.getRawRowMapper());
        rowsCountries = rawResults.getResults();

        return rowsCountries;
    }

    public static List<ICountries.Countries> GetCountriesFromDatabaseV2(Dao<ICountries.Countries, Integer> countriesDao ) throws SQLException {
        List<ICountries.Countries> countries = null;
        String query = "SELECT cdn, num_rows FROM CountriesTable";
        GenericRawResults<ICountries.Countries> rawResults = countriesDao.queryRaw(query, countriesDao.getRawRowMapper());
        countries = rawResults.getResults();

        return countries;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<ICursos.Cursos> GetCursosHomeFromDatabase2(Dao<ICursos.Cursos, Integer> cursosDao, Dao<rows, Integer> rowsDao, int idCategory, int id_course) throws SQLException {
        List<ICursos.Cursos> cursos = null;
        String query = "SELECT cdn, num_rows FROM CursosTable " +
                "WHERE idCategory = "+idCategory;
        GenericRawResults<ICursos.Cursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        if(cursos != null)
            if(cursos.size() == 1)
                cursos.get(0).setRows(GetRowsFromHomeDatabase2(rowsDao, idCategory, id_course));

        return cursos;
    }


    /**
     * Get Rows
     *
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<rows> GetRowsFromHomeDatabase2(Dao<rows, Integer> rowsDao, int idmed_categories , int id_course) throws SQLException {
        List<rows> rows = null;
        //Log.e("PILAS", "BUSCADO HOME: " + idmed_categories);
        String query = "SELECT * FROM rowsTable WHERE idHomeCategory = "+idmed_categories ;
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();

        return rows;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<String> GetFromCoursesTaken()throws SQLException {
        int i = 0;
        ICursos.Cursos mCursos = null;

        if(Utils.GetCursosHomeFromDatabase(MlearningApplicattion.getApplication().getCursosDao(), MlearningApplicattion.getApplication().getRowsDao(), -2 ) != null)
            if(Utils.GetCursosHomeFromDatabase(MlearningApplicattion.getApplication().getCursosDao(), MlearningApplicattion.getApplication().getRowsDao(), -2 ).size() > 0)
                mCursos = Utils.GetCursosHomeFromDatabase(MlearningApplicattion.getApplication().getCursosDao(), MlearningApplicattion.getApplication().getRowsDao(), -2 ).get(0);

        ArrayList<String> cursostomados = new ArrayList<String>();

        if (mCursos.getRows() != null)
            if (mCursos.getRows().size() > 0)
                for (rows row : mCursos.getRows()) {
                    cursostomados.add( i, Integer.toString( row.getIdmed_courses() ));
                    i++;
                }
        return cursostomados;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Get Row
     *
     * @param rowsDao
     * @return
     * @throws SQLException
     */
    public static List<rows> GetRowsFromDatabaseByIdCourse(Dao<rows, Integer> rowsDao, int idmed_courses ) throws SQLException {
        List<rows> rows = null;
        //String query = "SELECT DISTINCT * FROM rowsTable WHERE idmed_courses = "+idmed_courses;
        String query = "SELECT  * FROM rowsTable WHERE idmed_courses = "+idmed_courses  ;
        //String query = "SELECT DISTINCT * FROM rowsTable WHERE idmed_courses = "+idmed_courses+" and idHomeCategory = -2";
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();
        return rows;
    }



    public static List<rows> GetRowsFromDatabaseByIdCourse2(Dao<rows, Integer> rowsDao, int idmed_courses , int category) throws SQLException {
        List<rows> rows = null;
        //String query = "SELECT DISTINCT * FROM rowsTable WHERE idmed_courses = "+idmed_courses;
        String query = "SELECT  * FROM rowsTable WHERE idmed_courses = "+idmed_courses;
        //String query = "SELECT DISTINCT * FROM rowsTable WHERE idmed_courses = "+idmed_courses+" and idHomeCategory = -2";
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();
        return rows;
    }

    /**
     * Save Rows Data
     *
     * @param rows
     * @param rowsDao
     */
    public static void DbsaveRows(rows rows, Dao<rows, Integer> rowsDao) {
        try {
            rowsDao.create(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save rowsCountries Data
     *
     * @param rowsCountries
     * @param rowsCountriesDao
     */
    public static void DbsaveRowsCountries(rowsCountries rowsCountries, Dao<rowsCountries, Integer> rowsCountriesDao) {
        try {
            rowsCountriesDao.create(rowsCountries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* public static ICursos.Cursos getCursos1(String CountryCode,String limit,String offset)
    {
        ICursos.Cursos cursos = null;
        ICursos resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICursos.class);

        try{
            cursos = resAdapter.getNewsFrom1(CountryCode, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return cursos;
    }*/

   /* public static ICursos.Cursos getCursos(String msisdn ,String CountryCode, String limit,String offset)
    {
        ICursos.Cursos cursos = null;
        ICursos resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICursos.class);

        try{
            cursos = resAdapter.getNewsFrom(msisdn, CountryCode, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return cursos;
    }*/

   /* public static ICursos.Cursos getCursosMsisdn(String msisdn, String  CountryCode ,String course, String limit,String offset)
    {
        ICursos.Cursos cursos = null;
        ICursos resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICursos.class);

        try{
            cursos = resAdapter.getNewsFromByCourse(msisdn, CountryCode, course, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return cursos;
    }*/

   /* public static ICursos.Cursos getCursosByCategory(String category ,String CountryCode, String limit,String offset)
    {
        ICursos.Cursos cursos = null;
        ICursos resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICursos.class);

        try{
            cursos = resAdapter.getNewsFromByCategory(category, CountryCode, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return cursos;
    }*/


    public static void DeleteLesson(Dao<ILesson.Lesson, Integer> lesson) throws SQLException {
        DeleteBuilder<ILesson.Lesson, Integer> deleteBuilder = lesson.deleteBuilder();
        deleteBuilder.delete();
    }


    public static void DeleteLessonById(Dao<ILessonById.LessonById, Integer> lesson) throws SQLException {
        DeleteBuilder<ILessonById.LessonById, Integer> deleteBuilder = lesson.deleteBuilder();
        deleteBuilder.delete();
    }

    /**
     * Save Lesson Data
     *
     * @param lesson
     * @param lessonDao
     */
    public static void DbsaveLesson(ILesson.Lesson lesson, Dao<ILesson.Lesson, Integer> lessonDao) {
        try {
            lessonDao.create(lesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void DbsaveLessonById(ILessonById.LessonById lesson, Dao<ILessonById.LessonById, Integer> lessonDao) {
        try {
            lessonDao.create(lesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static int GetNextLesson(int idCourse ,int IdcurrentLesson/*, int IdlastLesson*/ )throws SQLException {

        int  IdnextLessson =-1 , i = 0;
        Boolean  indicator = false;
        ILesson.Lesson mLesson = Utils.GetLessonFromDatabase(MlearningApplicattion.getApplication().getLessonDao(), MlearningApplicattion.getApplication().getRowsLessonDao(), idCourse).get(0);

        if (mLesson.getRows() != null)
            if (mLesson.getRows().size() > 0) {
                for (rowsLesson row : mLesson.getRows()) {
                   // Log.e("TAG", "-----> row.getIdmed_lesson= "+row.getIdmed_lesson() );
                    if (indicator) {
                        IdnextLessson = row.getIdmed_lesson();
                        //Log.e("TAG", "-----> siguiente leccion row.getIdmed_lesson= "+row.getIdmed_lesson() );
                        break;
                    }
                    if ( IdcurrentLesson == row.getIdmed_lesson() )
                        indicator = true;
                    i++;
                }
            }
        //Log.e("TAG", "-----> leccion a enviar row.getIdmed_lesson= "+IdnextLessson );
        return IdnextLessson;
    }

    public static int GetlastLessons(int idCourse)throws SQLException {

        int IdlastLesson = 0 , i = 0;
        ILesson.Lesson mLesson = Utils.GetLessonFromDatabase(MlearningApplicattion.getApplication().getLessonDao(), MlearningApplicattion.getApplication().getRowsLessonDao(), idCourse).get(0);

        if (mLesson.getRows() != null)
            if (mLesson.getRows().size() > 0) {
                for (rowsLesson row : mLesson.getRows()) {
                    IdlastLesson = row.getIdmed_lesson();
                    i++;
                }
            }

        return IdlastLesson;
    }


    /**
     * Get Lesson
     *
     * @param lessonDao
     * @return
     * @throws SQLException
     */
    public static List<ILesson.Lesson> GetLessonFromDatabase(Dao<ILesson.Lesson, Integer> lessonDao, int idCourse) throws SQLException {
        List<ILesson.Lesson> lesson = null;
        String query = "SELECT cdn, num_rows , idCourse FROM LessonTable " +
                "WHERE idCourse = "+idCourse;
        GenericRawResults<ILesson.Lesson> rawResults = lessonDao.queryRaw(query, lessonDao.getRawRowMapper());
        lesson = rawResults.getResults();

        return lesson;
    }





    /**
     * Get Lesson
     *
     * @param lessonDao
     * @return
     * @throws SQLException
     */
    public static List<ILessonById.LessonById> GetLessonByIdFromDatabase(Dao<ILessonById.LessonById, Integer> lessonDao, int idmed_lesson) throws SQLException {
        List<ILessonById.LessonById> lesson = null;
        String query = "SELECT cdn, num_rows , idmed_lesson FROM LessonByIdTable " +
                "WHERE idmed_lesson = "+idmed_lesson;
        GenericRawResults<ILessonById.LessonById> rawResults = lessonDao.queryRaw(query, lessonDao.getRawRowMapper());
        lesson = rawResults.getResults();

        return lesson;
    }





    public static List<ILesson.Lesson> GetLessonFromDatabase(Dao<ILesson.Lesson, Integer> lessonDao, Dao<rowsLesson, Integer> rowsLessonDao, int id_course) throws SQLException {
        List<ILesson.Lesson> lesson = null;
        String query = "SELECT cdn, num_rows ,idCourse FROM LessonTable " +
                "WHERE idCourse = "+id_course;
        GenericRawResults<ILesson.Lesson> rawResults = lessonDao.queryRaw(query, lessonDao.getRawRowMapper());
        lesson = rawResults.getResults();

        if(lesson != null)
            if(lesson.size() == 1)
                lesson.get(0).setRows(GetRowsLessonFromDatabase(rowsLessonDao , id_course));

        return lesson;
    }


    public static List<rowsLesson> GetRowsLessonFromDatabaseById(Dao<rowsLesson, Integer> rowsLessonDao, int idmed_lesson) throws SQLException {
        List<rowsLesson> rowsLesson = null;
        String query = "SELECT * FROM rowsLessonTable " +
                "WHERE idmed_lesson = "+idmed_lesson;
        GenericRawResults<rowsLesson> rawResults = rowsLessonDao.queryRaw(query, rowsLessonDao.getRawRowMapper());
        rowsLesson = rawResults.getResults();

        return rowsLesson;
    }


    public static List<ILessonById.LessonById> GetLessonByIdFromDatabase(Dao<ILessonById.LessonById, Integer> lessonDao, Dao<rowsLessonImages, Integer> rowsLessonDao, int idmed_lesson ) throws SQLException {
        List<ILessonById.LessonById> lesson = null;
        String query = "SELECT cdn, num_rows ,idmed_lesson , url_data FROM LessonByIdTable " +
                "WHERE idmed_lesson = "+idmed_lesson;
        GenericRawResults<ILessonById.LessonById> rawResults = lessonDao.queryRaw(query, lessonDao.getRawRowMapper());
        lesson = rawResults.getResults();

        if(lesson != null)
            if(lesson.size() == 1)
                lesson.get(0).setImages(GetRowsLessonImagesFromDatabaseById(rowsLessonDao, idmed_lesson));

        return lesson;
    }


    public static List<rowsLessonImages> GetRowsLessonImagesFromDatabaseById(Dao<rowsLessonImages, Integer> rowsLessonDao, int idmed_lesson) throws SQLException {
        List<rowsLessonImages> rowsLesson = null;
        String query = "SELECT * FROM rowsLessonImageTable " +
                "WHERE idmed_lesson = "+idmed_lesson;
        GenericRawResults<rowsLessonImages> rawResults = rowsLessonDao.queryRaw(query, rowsLessonDao.getRawRowMapper());
        rowsLesson = rawResults.getResults();

        return rowsLesson;
    }


    public static void UpdateRowsLessonFromDatabaseById(Dao<rowsLesson, Integer> rowsLessonDao, int idmed_lesson, int status) throws SQLException {
        UpdateBuilder<rowsLesson, Integer> updateBuilder = rowsLessonDao.updateBuilder();
        updateBuilder.where().eq("idmed_lesson", idmed_lesson);
        updateBuilder.updateColumnValue("current_status", status);
        updateBuilder.update();
    }



    public static void UpdateRowsLessonURLFromDatabaseById(Dao<ILessonById.LessonById, Integer> rowsLessonDao, int idmed_lesson, String  url_data) throws SQLException {
        UpdateBuilder<ILessonById.LessonById, Integer> updateBuilder = rowsLessonDao.updateBuilder();
        updateBuilder.where().eq("idmed_lesson", idmed_lesson);
        updateBuilder.updateColumnValue("url_data", url_data);
        updateBuilder.update();
    }


    public static List<rowsLesson> GetRowsLessonFromDatabase(Dao<rowsLesson, Integer> rowsLessonDao, int id_course) throws SQLException {
        List<rowsLesson> rowsLesson = null;
        String query = "SELECT * FROM rowsLessonTable " +
                "WHERE idmed_courses = "+id_course;
        GenericRawResults<rowsLesson> rawResults = rowsLessonDao.queryRaw(query, rowsLessonDao.getRawRowMapper());
        rowsLesson = rawResults.getResults();

        return rowsLesson;
    }


    public static void DbsaveRowsLesson(rowsLesson rowsLesson, Dao<rowsLesson, Integer> rowsLessonDao) {
        try {
            rowsLessonDao.create(rowsLesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void DbsaveRowsLessonImagenes(rowsLessonImages rowsLesson, Dao<rowsLessonImages, Integer> rowsLessonDao) {
        try {
            rowsLessonDao.create(rowsLesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void DbsaveRowsLessonVideos(rowsLessonVideos rowsLesson, Dao<rowsLessonVideos, Integer> rowsLessonDao) {
        try {
            rowsLessonDao.create(rowsLesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


   /* public static ILesson.Lesson getLesson(String CountryCode, String idCourse,String limit,String offset)
    {
        ILesson.Lesson lesson = null;
        ILesson resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ILesson.class);

        try{
            lesson = resAdapter.getLessonFrom(CountryCode, idCourse, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return lesson;
    }*/


   /* public static ILesson.Lesson getLesson(String msisdn , String CountryCode ,String idCourse,String limit,String offset)
    {
        ILesson.Lesson lesson = null;
        ILesson resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ILesson.class);

        try{
            lesson = resAdapter.getLessonFrom(msisdn, CountryCode, idCourse, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return lesson;
    }*/

   /* public static ICountries.Countries getCountries()
    {  String version = "2.0";
        ICountries.Countries countries = null;
        ICountries resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICountries.class);

        try{
            countries = resAdapter.getNewsFromByCountries_2(version);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return countries;
    }*/

    public static void DeleteCursoById(Dao<ICursos.Cursos, Integer> cursoDao, int idCategory) throws SQLException {
        DeleteBuilder<ICursos.Cursos, Integer> deleteBuilder = cursoDao.deleteBuilder();
        deleteBuilder.where().eq("idCategory", idCategory);
        deleteBuilder.delete();
    }

    public static void DeleterowsCursoById(Dao<rows, Integer> rowDao, int idCategory) throws SQLException {
        DeleteBuilder<rows, Integer> deleteBuilder = rowDao.deleteBuilder();
        deleteBuilder.where().eq("idmed_categories", idCategory).and().eq("idHomeCategory", 0);
        deleteBuilder.delete();
    }


    public static void DeleterowsCursoByIdHome(Dao<rows, Integer> rowDao, int idCategory) throws SQLException {
        DeleteBuilder<rows, Integer> deleteBuilder = rowDao.deleteBuilder();
        deleteBuilder.where().eq("idHomeCategory", idCategory);
        deleteBuilder.delete();
    }

    public static void DeleteLessonById(Dao<ILesson.Lesson, Integer> lessonDao, int idCourse) throws SQLException {
        DeleteBuilder<ILesson.Lesson, Integer> deleteBuilder = lessonDao.deleteBuilder();
        deleteBuilder.where().eq("idCourse", idCourse);
        deleteBuilder.delete();
    }

    public static void DeleterowsLessonById(Dao<rowsLesson, Integer> rowLessonDao, int idCourse) throws SQLException {
        DeleteBuilder<rowsLesson, Integer> deleteBuilder = rowLessonDao.deleteBuilder();
        deleteBuilder.where().eq("idmed_courses", idCourse);
        deleteBuilder.delete();
    }



    public static void DeleteLessonIDById(Dao<ILessonById.LessonById, Integer> lessonDao, int idmed_lesson) throws SQLException {
        DeleteBuilder<ILessonById.LessonById, Integer> deleteBuilder = lessonDao.deleteBuilder();
        deleteBuilder.where().eq("idmed_lesson", idmed_lesson);
        deleteBuilder.delete();
    }

    public static void DeleterowsLessonImagenesById(Dao<rowsLessonImages, Integer> rowLessonDao, int idmed_lesson) throws SQLException {
        DeleteBuilder<rowsLessonImages, Integer> deleteBuilder = rowLessonDao.deleteBuilder();
        deleteBuilder.where().eq("idmed_lesson", idmed_lesson);
        deleteBuilder.delete();
    }

    public static void DeleterowsLessonVideosById(Dao<rowsLessonVideos, Integer> rowLessonDao, int idmed_lesson) throws SQLException {
        DeleteBuilder<rowsLessonVideos, Integer> deleteBuilder = rowLessonDao.deleteBuilder();
        deleteBuilder.where().eq("idmed_lesson", idmed_lesson);
        deleteBuilder.delete();
    }


    public static void DeleteCategories(Dao<ICategories.Category, Integer> categories) throws SQLException {
        DeleteBuilder<ICategories.Category, Integer> deleteBuilder = categories.deleteBuilder();
        deleteBuilder.delete();
    }

    /**
     * Save Categories Data
     *
     * @param categories
     * @param categoriesDao
     */
    public static void DbsaveCategories(ICategories.Category categories, Dao<ICategories.Category, Integer> categoriesDao) {
        try {
            categoriesDao.create(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Categories
     *
     * @param categoriesDao
     * @return
     * @throws SQLException
     */
    public static List<ICategories.Category> GetCategoriesFromDatabase(Dao<ICategories.Category, Integer> categoriesDao) throws SQLException {
        List<ICategories.Category> categories = null;
        String query = "SELECT cdn, num_rows FROM CategoriesTable";
        GenericRawResults<ICategories.Category> rawResults = categoriesDao.queryRaw(query, categoriesDao.getRawRowMapper());
        categories = rawResults.getResults();

        return categories;
    }


    /**
     * Get Categories
     *
     * @param categoriesDao
     * @param rowsCategoryDao
     * @return
     * @throws SQLException
     */
    public static List<ICategories.Category> GetCategoriesFromDatabase(Dao<ICategories.Category, Integer> categoriesDao, Dao<rowsCategory, Integer> rowsCategoryDao) throws SQLException {
        List<ICategories.Category> categories = null;
        String query = "SELECT cdn, num_rows FROM CategoriesTable";
        GenericRawResults<ICategories.Category> rawResults = categoriesDao.queryRaw(query, categoriesDao.getRawRowMapper());
        categories = rawResults.getResults();

        if(categories != null)
            if(categories.size() == 1)
                categories.get(0).setRows(GetRowsCategoryFromDatabase(rowsCategoryDao));

        return categories;
    }


    /**
     * Get RowsCategory
     *
     * @param rowsCategoryDao
     * @return
     * @throws SQLException
     */
    public static List<rowsCategory> GetRowsCategoryFromDatabase(Dao<rowsCategory, Integer> rowsCategoryDao) throws SQLException {
        List<rowsCategory> rowsCategory = null;
        String query = "SELECT * FROM rowsCategoryTable";
        GenericRawResults<rowsCategory> rawResults = rowsCategoryDao.queryRaw(query, rowsCategoryDao.getRawRowMapper());
        rowsCategory = rawResults.getResults();

        return rowsCategory;
    }


    /**
     * Get RowCategory
     *
     * @param rowsCategoryDao
     * @return
     * @throws SQLException
     */
    public static List<rowsCategory> GetRowsCategoryFromDatabaseByIdCategory(Dao<rowsCategory, Integer> rowsCategoryDao, int idmed_categories) throws SQLException {
        List<rowsCategory> rowsCategory = null;
        String query = "SELECT DISTINCT * FROM rowsCategoryTable WHERE idmed_categories = "+idmed_categories;
        GenericRawResults<rowsCategory> rawResults = rowsCategoryDao.queryRaw(query, rowsCategoryDao.getRawRowMapper());
        rowsCategory = rawResults.getResults();
        return rowsCategory;
    }

    /**
     * Save RowsCategory Data
     *
     * @param rowsCategory
     * @param rowsCategoryDao
     */
    public static void DbsaveRowsCategoryCategory(rowsCategory rowsCategory, Dao<rowsCategory, Integer> rowsCategoryDao) {
        try {
            rowsCategoryDao.create(rowsCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //INICIO HOME

    //Save HomeCursos Data
    public static void DbsaveHomeCursos(IHomeCursos.HomeCursos homeCursos, Dao<IHomeCursos.HomeCursos, Integer> homeCursosDao) {
        try {
            homeCursosDao.create(homeCursos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Save rowsHome Data
    public static void DbsaveRowsHome(rowsHome rows, Dao<rowsHome, Integer> rowsDao) {
        try {
            rowsDao.create(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /*OBTEBER REGISTROS DE LA TABLA BD*/
    public static List<IHomeCursos.HomeCursos> GetHomeCursesFromDatabase(Dao<IHomeCursos.HomeCursos, Integer> cursosDao) throws SQLException {
        List<IHomeCursos.HomeCursos> cursos = null;
        String query = "SELECT cdn FROM HomeTable ";
        GenericRawResults<IHomeCursos.HomeCursos> rawResults = cursosDao.queryRaw(query, cursosDao.getRawRowMapper());
        cursos = rawResults.getResults();

        return cursos;
    }
    /*OBTEBER REGISTROS DE LA TABLA BD*/
    public static List<rowsHome> GetRowsHomeFromDatabase(Dao<rowsHome, Integer> rowsHomeDao) throws android.database.SQLException, java.sql.SQLException {
        List<rowsHome> rowsHome = null;
        String query = "SELECT * FROM rowsHomeTable";
        GenericRawResults<rowsHome> rawResults = rowsHomeDao.queryRaw(query, rowsHomeDao.getRawRowMapper());
        rowsHome = rawResults.getResults();
        return rowsHome;
    }
    //ELIMINAR REGISTROS DE TABLA
    public static void DeleteHomeCursesDB(Dao<IHomeCursos.HomeCursos, Integer> rowDao) throws SQLException {
        DeleteBuilder<IHomeCursos.HomeCursos, Integer> deleteBuilder = rowDao.deleteBuilder();
        deleteBuilder.delete();
    }
    //ELIMINAR REGISTROS DE TABLA
    public static void DeleteRowsHomeCursesDB(Dao<rowsHome, Integer> rowDao) throws SQLException {
        DeleteBuilder<rowsHome, Integer> deleteBuilder = rowDao.deleteBuilder();
        deleteBuilder.delete();
    }

    //FIN HOME


    /*public static ICategories.Category getCategories(String CountryCode,String limit,String offset)
    {
        ICategories.Category categories = null;
        ICategories resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICategories.class);

        try{
            categories = resAdapter.getCategoryFrom(CountryCode, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return categories;
    }*/


/*    public static ICategories.Category getCategories(String msisdn ,String CountryCode, String limit,String offset)
    {
        ICategories.Category categories = null;
        ICategories resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ICategories.class);

        try{
            categories = resAdapter.getCategoryFrom(msisdn, CountryCode, limit, offset);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return categories;
    }*/


   /* public static IQuiz.quiz getQuiz(String lesson ,String CountryCode)
    {
        IQuiz.quiz quiz = null;
        IQuiz resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(IQuiz.class);
       // Log.i("^.^",lesson);
        try{
            quiz = resAdapter.getQuizFrom(lesson, CountryCode);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return quiz;
    }*/

    public static String getCadenaFromUrl(String url){

        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            BufferedHttpEntity buf = new BufferedHttpEntity(entity);

            InputStream is = buf.getContent();

            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line + "\n");
            }
            String result = total.toString();
            //Log.e("Get URL", "Downloaded string: " + result);
            return result;

        } catch (Exception e) {
            //Log.e("Get Url", "Error in downloading: " + e.toString());
        }

        return null;
    }

    public static void MySystemUtils(Context appContext) { // called once from within onCreate
        Boolean  indicator = false;
        ActivityManager activityManager = (ActivityManager)appContext.getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        long totalMemory  = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            indicator = true;
            totalMemory  = mi.totalMem / 1048576L;
        }
        if(indicator)
        { if (totalMemory >= 1100)
            MainActivity.SelectVideoHD = true;
        else
            MainActivity.SelectVideoHD = false;
        }else{
            MainActivity.SelectVideoHD = false;
        }
        //Log.e("TAG","**--MEMORY:"+totalMemory +"selechd:"+MainActivity.SelectVideoHD );
    }

    public static List<String> PaginarString(String cdn ,String cadena, List<rowsLessonImages> lista_images , List<rowsLessonVideos> lista_videos)
    {
        BufferedReader br = null;
        boolean isFirst = true;
        boolean isTitle = false;
        boolean afterImage= true;
        int auxcont = 0;
        String aux= "[";
        String aux2= "]";
        String expressionRegular = ".*[{][0-9]+[}].*";
        //String expressionRegular = "^[<][p][>].*[{][0-9]+[}].*[<][/][p][>]$";
        String expressionRegularTitulo = "^[<][h][2].*";
        //String expressionRegularTitulo = "^[<][h][2][>].*";
        String expressionRegularVideo = ".*[\\[][0-9]+[\\]].*";
        String expressionRegularVideoGIF = ".*[#][0-9]+[#].*";
        //String expressionRegularVideo = "^[<][p][>].*[\\[][0-9]+[\\]].*[<][/][p][>]$";
        //String expressionRegularVideo = ".*[\\[][0-9]+[\\]].*";
        List<String> paginar = new ArrayList<String>();
        StringBuilder pagina = new StringBuilder();
        String url_image = "";
        String url_video = "";
        String url_video2WAP= "";
        String url_thumbnail_M = "";
        String titulos = "";
        int code;
        try {
            String sCurrentLine;
            String auxsCurrentLine;
            String auxsCurrentLine2;
            br = new BufferedReader(new StringReader(cadena));
            while ((sCurrentLine = br.readLine()) != null) {

                auxcont = auxcont + 1;

                if(sCurrentLine.trim().matches(expressionRegularTitulo)) {
                    titulos = sCurrentLine;
                    isTitle = true;
                }



                if(sCurrentLine.trim().matches(expressionRegularVideo)) {
                    if (lista_videos != null) {
                        code = extractDigits2(sCurrentLine);

                        //Log.e("TAG", "**---** video code: "+ code);
                        for (rowsLessonVideos row : lista_videos){
                           // Log.e("TAG", "**---** video code: "+ code + " row.getCode: "+row.getCode() +" row.getIdmed_item_file_type:"+ row.getIdmed_item_file_type() );
                            if (code == row.getCode() && row.getIdmed_item_file_type() == 14  ) {

                               // Log.e("TAG", "**---** row.getFile: "+ cdn+ row.getFile() );
                                url_video = "" + cdn + row.getFile();
                                url_thumbnail_M = ""+ row.getThumbnail_M();
                                //break;
                            }
                            if (code == row.getCode() && row.getIdmed_item_file_type() == 11  ) {

                                // Log.e("TAG", "**---** row.getFile: "+ cdn+ row.getFile() );
                                url_video2WAP = "" + cdn + row.getFile();
                                //break;
                            }
                        }


                       /// Log.e("TAG", "**--- ESTA FORMANDO LA CADENA: " +" --url_video: "+ url_video);
                        //poster="http://docs.brightcove.com/en/video-cloud/smart-player-api/samples/assets/dolphins.png"


                        String html = "";
                        Log.e("tag","--ingreso video norma---------------------");
                        if (MainActivity.SelectVideoHD ) {

                            html += "<video  id=\"video\"   width=\"100%\"  preload=\"none\"  poster=\"" + url_thumbnail_M + "\" controls>  <source src=\"" + url_video + "\" >   </video>";
                        }
                        else {
                            //html += "<video id=\"video\"   width=\"100%\"   poster=\"" + url_thumbnail_M + "\"   loop webkit-playsinline onclick=\" if (this.paused) { this.play();} else { this.pause();}\">  <source src=\"" + url_video2WAP + "\" >   </video>";
                            html += "<video id=\"video\"   width=\"100%\"  preload=\"none\"  poster=\"" + url_thumbnail_M + "\" controls>  <source src=\"" + url_video2WAP + "\" >   </video>";

                            /*html += "<style> .button-secondary {width:100%@; height:100%@; color:fff; font-size:15px; border-radius: 8px; text-shadow: 0 4px 5px rgba(0, 0, 0, 0.2);}.button-secondary {background: rgb(10, 98, 133); } </style><div id = 'jefe' style = 'width:180px; margin:auto; height:40px' align=center> \n" +
                                    "<a  onClick=\"ok.performClick()\"><input type = 'text' readonly = 'true' style='text-align: center' value = 'Ver Video en HD' class = 'button-secundary button-secondary'/></a></div>";*/
                        }
                        //html += "<video id=\"video\"   width=\"100%\"  preload=\"none\"  poster=\""+ url_thumbnail_M + "\" controls>  <source src=\""+  "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4" + "\" > </video>";
                        //html += "<video id=\"video\"  width=\"100%\" preload=\"none\" poster=\"http://docs.brightcove.com/en/video-cloud/smart-player-api/samples/assets/dolphins.png\"  controls>  <source src=\" http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4 \" > </video>";
                        //html += "<embed width=\"100%\"  src=\"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4\">";
                        /*html = html + " <input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />\n" +
                                "\n" +
                                "<script type=\"text/javascript\">\n" +
                                "    function showAndroidToast(toast) {\n" +
                                "        Android.showToast(toast);\n" +
                                "    }\n" +
                                "</script>  <img name=\"imagen1\" width=\"100%\" src=\""+ url_thumbnail_M + "\" onClick=\" ok.performClick() \" > "; //alert('gracias por pulsarme')
                        */


                        //Log.e("TAG", "**--- ESTA FORMANDO LA CADENA: " +" --url_video: "+ url_video);
                        sCurrentLine = html ;
                    }
                }

                //para video tipo GIF 15
                if(sCurrentLine.trim().matches(expressionRegularVideoGIF)) {
                    if (lista_videos != null) {
                        code = extractDigits3GIF(sCurrentLine);

                        for (rowsLessonVideos row : lista_videos){
                            if (code == row.getCode() && row.getIdmed_item_file_type() == 15  ) {
                                // Log.e("TAG", "**---** row.getFile: "+ cdn+ row.getFile() );
                                url_video = "" + cdn + row.getFile();
                                url_thumbnail_M = ""+ row.getThumbnail_M();
                            }
                        }
                        //url_video = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
                        String html = "";
                        //Log.e("tag","--ingreso video gif---------------------");
                        html +=" <div width=\"100%\" style=\"position: relative; left: 0; top: 0;\" style=\"background-color:red;\"> ";   //onclick=" if (this.paused) { this.play();} else { this.pause();}"
                        html += "<video id=\"video1\"   width=\"100%\"   poster=\"" + url_thumbnail_M + "\"   loop webkit-playsinline  onclick=\"playPause(this);\" >  <source src=\"" + url_video + "\"  style=\"position: relative; top: 0; left: 0;\" >   </video>";
                        html += " <img id=\"images1\"  src=gif_image.png     width = \"50\"; heigth= \"25\";  onclick=\"playPauseIMG(this);\" style=\"position: absolute; top: 40%; left: 43%;\"> ";
                        html += "</div> ";

                        //html += " <input type=\"button\" value=\"Say hello\" onClick=\"playPause()\" />\n" ;


                                html =  html +  "\n" +
                                "<script type=\"text/javascript\">\n" +

                                " function playPause(param){                    \n"+
                                " var vid = param;                              \n"+
                                " var im1 = document.getElementById('images1'); \n"+
                                " if (vid.paused){                      \n"+
                                "     vid.play();                       \n"+
                                "     im1.style.visibility = 'hidden';  \n"+
                                " }else{                                \n"+
                                "     vid.pause();                      \n"+
                                "     im1.style.visibility = 'visible'; \n"+
                                " }                                     \n"+
                                "}                                      \n"+
                                " function playPauseIMG(param1){        \n" +
                                " var vid2 = document.getElementById('video1');  \n" +
                                " var im2 = param1;                              \n" +
                                " if (vid2.paused){                              \n"+
                                "    vid2.play();                                \n"+
                                "    im2.style.visibility = 'hidden';            \n"+
                                " }else{                                         \n"+
                                "    vid2.pause();                               \n"+
                                "    im2.style.visibility = 'visible';           \n"+
                                " }                                              \n"+
                                "}                                               \n"+


                                        " function playPause(){                    \n"+
                                        " var vid = document.getElementById('video1');  \n"+
                                        " var im1 = document.getElementById('images1'); \n"+
                                        /*" if (vid.paused){                      \n"+
                                        "     vid.play();                       \n"+
                                        "     im1.style.visibility = 'hidden';  \n"+
                                        " }else{                                \n"+*/
                                        "     vid.pause();                      \n"+
                                        "     im1.style.visibility = 'visible'; \n"+
                                        /*" }                                     \n"+*/
                                        "}                                      \n"+


                                "    function showAndroidToast(toast) {  \n" +
                                "        Android.showToast(toast);       \n" +
                                "    }                                   \n" +
                                "</script> ";

                        sCurrentLine = html ;
                    }
                }



                if(sCurrentLine.trim().matches(expressionRegular))
                {
                   /* if(!isFirst) {


                        paginar.add(pagina.toString());
                        pagina = new StringBuilder();

                        if (lista_images != null){
                            code = extractDigits(sCurrentLine);
                            Log.e("TAG", "**---** code: "+ code);
                            for (rowsLessonImages row : lista_images)
                                if (code == row.getCode() && row.getIdmed_item_file_type() == 1) {
                                    Log.e("TAG", "**---** code: "+ code + " row.getCode: "+row.getCode() +" row.getIdmed_item_file_type:"+ row.getIdmed_item_file_type() );
                                    Log.e("TAG", "**---** row.getFile: "+  row.getFile() );

                                    url_image = "" + cdn+row.getFile();
                                    break;
                                }

                            if(titulos.length() > 0) {
                                pagina.append(titulos + "\n");
                                titulos = "";
                            }

                            sCurrentLine = "<img src=\"" + url_image + "\" width=\"100%\">";
                            Log.e("TAG","ESTA FORMANDO LA CADENA: "+sCurrentLine);
                            pagina.append(sCurrentLine + "\n");
                        }



                    }else {
                        isFirst = false;
*/
                    if (lista_images != null) {
                        code = extractDigits(sCurrentLine);
                        for (rowsLessonImages row : lista_images)
                            if (code == row.getCode() && row.getIdmed_item_file_type() == 1) {
                                url_image = "" + cdn + row.getFile();
                                break;
                            }

                           /* if(titulos.length() > 0) {
                                pagina.append(titulos + "\n");
                                titulos = "";
                            }
*/
                        sCurrentLine = "<img src=\"" + url_image + "\" width=\"100%\">";
                        //Log.e("TAG","ESTA FORMANDO LA CADENA: "+sCurrentLine);
                          /*  pagina.append(sCurrentLine + "\n");*/
                    }



                    /*}
                afterImage = false;
                auxcont = 0;*/
                }

               /* else
                {   if( (isTitle) &&  (auxcont == 1) )
                    {pagina.append(titulos+"\n");
                    afterImage=true ; }

                    if ( isTitle )
                    {  isTitle = false;


                        pagina.append("\n");
                    }
                    else {
                        if ( (sCurrentLine == "") || (sCurrentLine == null) || (sCurrentLine == " ") || (sCurrentLine.trim().length() <= 0))
                            auxcont = auxcont + 1 ;

                        pagina.append(sCurrentLine + "\n");

                    }
                }*/

               /* if(  ((sCurrentLine.trim().matches(expressionRegular))&& (auxcont > 3) ) || ( (sCurrentLine.trim().matches(expressionRegularTitulo))&& (auxcont > 3) ) ){*/
              /* if ( auxcont >= 14 ) {
                   if (auxcont == 14){

                       if ((sCurrentLine.trim().matches(expressionRegularTitulo))) {
                           paginar.add(pagina.toString());
                           pagina = new StringBuilder();
                           pagina.append(sCurrentLine + "\n");
                           auxcont = 0;
                       }else{
                           pagina.append(sCurrentLine + "\n");
                           }
                   } else if (auxcont == 15) {
                       auxcont = 0;
                       if ((sCurrentLine.trim().matches(expressionRegularTitulo))) {
                           paginar.add(pagina.toString());
                           pagina = new StringBuilder();
                           pagina.append(sCurrentLine + "\n");

                       }else{
                           paginar.add(pagina.toString());
                           pagina = new StringBuilder();
                           pagina.append(sCurrentLine + "\n");
                       }
                   }
                }*/

                if ((sCurrentLine.trim().matches(expressionRegularTitulo)) && (auxcont >= 5 ) ) {

                    paginar.add(pagina.toString());
                    pagina = new StringBuilder();
                    pagina.append(sCurrentLine + "\n");
                    //auxcont = 0;

                }
                else {
                    //auxcont++;
                    pagina.append(sCurrentLine + "\n");

               }

            }

            //last pagination
            if(pagina != null)
                paginar.add(pagina.toString());

            return  paginar;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    public static int extractDigits(String src) {

        try {

            boolean flag = false;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < src.length(); i++) {
                char c = src.charAt(i);

                if (flag && Character.isDigit(c)) {
                    builder.append(c);
                }

                if(c == '{')
                    flag = true;
            }


            return Integer.parseInt(builder.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static int extractDigits2(String src) {

        try {

            boolean flag = false;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < src.length(); i++) {
                char c = src.charAt(i);

                if (flag && Character.isDigit(c)) {
                    builder.append(c);
                }

                if(c == '[')
                    flag = true;
            }


            return Integer.parseInt(builder.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static int extractDigits3GIF(String src) {

        try {

            boolean flag = false;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < src.length(); i++) {
                char c = src.charAt(i);

                if (flag && Character.isDigit(c)) {
                    builder.append(c);
                }

                if(c == '#')
                    flag = true;
            }


            return Integer.parseInt(builder.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static Boolean CadenaVideo(String cadena )
    {   //recoore y devulve true si la cadena contiene etiqueta video

        String expressionRegularVideo =  "^[<][v][i][d][e][o].*";
        String expressionRegularTitulo = "^[<][h][2].*";
        BufferedReader br = null;
        String sCurrentLine;
        Boolean FlagVideo = null;
        try {


            br = new BufferedReader(new StringReader(cadena));
            FlagVideo = false ;
            while ((sCurrentLine = br.readLine()) != null) {
                if(sCurrentLine.trim().matches(expressionRegularVideo))
                     FlagVideo= true ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FlagVideo;
    }

    /*public static ISearch.Search getSearch(String word)
    {
        ISearch.Search search = null;
        ISearch resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISearch.class);

        try{
            search = resAdapter.getNewsFrom(word);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return search;
    }*/



   /* public static ILessonById.LessonById getLessonById(String lesson_id , String msisdn, String CountryCode)
    {
        ILessonById.LessonById lesson = null;
        ILessonById resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ILessonById.class);

        try {
            lesson = resAdapter.getLessonByIdFrom(msisdn, lesson_id,  CountryCode);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return lesson;
    }*/

    /*public static IRanking.Ranking setRanking(String msisdn ,String CountryCode, String course , String ranking )
    {   //System.out.println("^.^"+msisdn+"--"+course+"--"+ranking);
        IRanking.Ranking rankin = null;
        IRanking resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(IRanking.class);

        try {
            rankin = resAdapter.saveRanking(msisdn, CountryCode, course, ranking);
            //Log.e("^.^", rankin.getDescription()+" ** "+rankin.getCode());
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return rankin;
    }*/

   /* public static ITraking.Traking setTraking(String msisdn ,String CountryCode, String lesson , String channel , String status)
    {
        ITraking.Traking traking = null;
        ITraking resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ITraking.class);

        try {
            traking = resAdapter.getTraking(msisdn, CountryCode, lesson, channel, status);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }
        return traking;
    }*/


    /*public static ISendQuiz.Sendquiz sendQuiz(String lesson,ArrayPostQuiz answer, String msisdn , String CountryCode)
    {
        ISendQuiz.Sendquiz sendQuiz = null;
        ISendQuiz resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISendQuiz.class);

       // Log.w("^.^", " lesson " + lesson + " msidn " + msisdn);

        try{

            JSONArray answers = new JSONArray();
            JSONObject jo;

            for(postParamsQuiz datos : answer.getAnswer()) {
                jo = new JSONObject();
                jo.put("quizID", datos.getQuizID());
                jo.put("questionID", datos.getQuestionID());
                jo.put("answerID", datos.getAnswerID());
                answers.put(jo);
            }

           // Log.w("^.^"," json armado "+answers.toString());

            sendQuiz = resAdapter.setSendQuizFrom(lesson, msisdn, CountryCode, answers);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sendQuiz;
    }*/



    /**
     * Método para crear un AlertDialog en la aplicación
     * @param act
     * @param Title
     * @param msm
     */
    public static  void generarAlerta(Activity act , String Title , String msm)
    {
        if(!((Activity) act).isFinishing())
        {
            try{
                //show dialog
                AlertDialog.Builder alert = new AlertDialog.Builder(act);
                alert.setTitle(""+Title);
                alert.setMessage(""+msm);
                alert.setPositiveButton("OK",null);
                alert.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * Método para crear un AlertDialog en la aplicación
     * @param act
     * @param Title
     * @param msm
     */
    public static  void generarAlerta(Context act , String Title , String msm)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setTitle("" + Title);
        alert.setMessage("" + msm);
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    /**
     * Método para crear un AlertDialog en la aplicación
     * @param act
     * @param Title
     * @param msm
     */
    public static void generarAlertaNoSuscrito(final Activity act ,
                                               String Title ,
                                               String msm ,
                                               final String msisdn,
                                               final String CountryCode ,
                                               final boolean isFinish ) {

        try{

            AlertDialog.Builder alert = new AlertDialog.Builder(act);
            alert.setTitle("" + Title);
            alert.setMessage("" + msm);
            alert.setCancelable(false);
            //alert.setPositiveButton("OK 2", null);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface alert, int id) {

                    if (CountryCode.equals("56")) {  //chile
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.link_pag_chl)));
                        act.startActivity(browserIntent);

                    }else if (CountryCode.equals("54")) {  //argentina
                        String urlARG_2 = "&ikhp=116158&idLanding=251&sec=bf0feb&user=terraweb&pass=wqhdsdke278&url=aHR0cDovL3d3dy5lZHVrdGUubmV0";
                        String urlARG = act.getString(R.string.link_pag_arg_new_1) + msisdn + urlARG_2 ;

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( urlARG /*act.getString(R.string.link_pag_arg)*/ ));
                        act.startActivity(browserIntent);

                    }else if (CountryCode.equals("52")){ //mexico
                        Intent intent = new Intent(act , MainActivity.class);  // envia al main
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //para borrar pila de actividades
                        act.startActivity(intent);

                    }else  if (CountryCode.equals("593")){ //ecuador
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(   act.getString(R.string.link_pag_ecu) ));
                        act.startActivity(browserIntent);

                    }else  if (CountryCode.equals("505")){ //nicaragua
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(   act.getString(R.string.link_pag_nica) ));
                        act.startActivity(browserIntent);
                    }else  if (CountryCode.equals("503")){ //salvador
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(   act.getString(R.string.link_pag_sv) ));
                        act.startActivity(browserIntent);
                    }

                    if ( isFinish ){
                         act.finish();
                    }


                }
            });
            //alert.show();
            MainActivity.alertNoSuscrito = alert.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método para crear un AlertDialog en la aplicación
     * @param act
     * @param Title
     * @param msm
     */
    /*public static void generarAlertaNoSuscrito(final Activity act, String Title, String msm,  final String CountryCode)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setTitle("" + Title);
        alert.setMessage("" + msm);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface alert, int id) {

                if (CountryCode.equals("56")) {  //chile
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.link_pag_chl)));
                    act.startActivity(browserIntent);
                } else if (CountryCode.equals("54")) {  //argentina
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.link_pag_arg)));
                    act.startActivity(browserIntent);
                } else if (CountryCode.equals("52")) {    //mexico
                    Intent intent = new Intent(act, MainActivity.class);  // envia al main
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //para borrar pila de actividades
                    act.startActivity(intent);
                } else if (CountryCode.equals("593")) { // ecuador
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(act.getString(R.string.link_pag_ecu)));
                    act.startActivity(browserIntent);
                }else  if (CountryCode.equals("505")){ //nicaragua
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(   act.getString(R.string.link_pag_nica) ));
                    act.startActivity(browserIntent);

                }else  if (CountryCode.equals("503")){ //salvador
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(   act.getString(R.string.link_pag_sv) ));
                    act.startActivity(browserIntent);
                }

            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });
        MainActivity.alertNoSuscrito = alert.show();

    }*/

    public static  void generarAlerta119(Context act , String Title , String msm)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setTitle("" + Title);
        alert.setMessage("" + msm);
        alert.setPositiveButton("OK", null);
        MainActivity.alertNoSuscrito = alert.show();
    }



    private static void showLoadingDialog(){
        DialogCountry.setMessage("Espere un Momento..");
        DialogCountry.setCancelable(false);
        DialogCountry.show();
    }

    private static void showLayoutDialog(){
        if (DialogCountry != null)
            DialogCountry.dismiss();
    }



    /*public static ISuscriber.Suscripcion setSuscriber(String msisdn , String CountryCode)
    {   ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);

        try {
            suscripcion = resAdapter.setSuscriber(msisdn, CountryCode, CHANNEL);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return suscripcion;
    }*/

    public static ISuscriber.Suscripcion setPin(String msisdn, String pin , String CountryCode)
    {   ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);

        /*try {
            suscripcion = resAdapter.setPin(msisdn, pin, CountryCode, CHANNEL);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }*/

        return suscripcion;
    }

    public static ISuscriber.Suscripcion getPin(String numero) {
        ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);

        /*try {
            suscripcion = resAdapter.getPin(numero);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }*/

        return suscripcion;
    }

    /*public static ISuscriber.Suscripcion isSuscriber(String numero , String CountryCode) {
        ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);

        try {
            suscripcion = resAdapter.isSuscriber(numero, CountryCode, CHANNEL);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return suscripcion;
    }*/

    /****************************************************************
     Top Ten
     ***************************************************************/
   /* public static ITopTen.TopTen getTopTen(String valor, String CountryCode) {
        ITopTen.TopTen topTen = null;
        ITopTen resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ITopTen.class);

        try{
            topTen = resAdapter.getTopTen(valor, CountryCode);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return topTen;
    }*/

    public static ITopTen.TopTen getTopTenFromDatabase(Dao<rowsTopTen, Integer> topTensDao, Dao<summaryCategories, Integer> summaryCategorieseDao) throws SQLException {
        ITopTen.TopTen topTens = new ITopTen.TopTen();
        List<rowsTopTen> rowsTopTens = null;
        String query = "SELECT folder, idmed_categories, title, name, ranking, subscribers, url_cdn_small, idmed_courses FROM TopTenTable ";
        GenericRawResults<rowsTopTen> rawResults = topTensDao.queryRaw(query, topTensDao.getRawRowMapper());
        rowsTopTens = rawResults.getResults();

        if(rowsTopTens != null)
            topTens.setSummary(GetSummaryCategoriesFromDatabase(summaryCategorieseDao));
        topTens.setRows(rowsTopTens);

        return topTens;
    }

    public static summaryCategories GetSummaryCategoriesFromDatabase(Dao<summaryCategories, Integer> summaryCategorieseDao) throws SQLException {
        summaryCategories record = null;
        String query = "SELECT cdn FROM summaryCategoriesTable";
        GenericRawResults<summaryCategories> rawResults = summaryCategorieseDao.queryRaw(query, summaryCategorieseDao.getRawRowMapper());
        record = rawResults.getFirstResult();

        return record;
    }

    public static void DeleteTopTen(Dao<rowsTopTen, Integer> topTenDao, Dao<summaryCategories, Integer> summaryTopTen) throws SQLException {
        DeleteBuilder<rowsTopTen, Integer> deleteBuilder = topTenDao.deleteBuilder();
        deleteBuilder.delete();
        DeleteBuilder<summaryCategories, Integer> deleteBuilder1 = summaryTopTen.deleteBuilder();
        deleteBuilder1.delete();
    }

    public static void DbSaveSummaryCategories(summaryCategories summary, Dao<summaryCategories, Integer> summaryTopTenDao) {
        try {
            summaryTopTenDao.create(summary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DbSaveTopTen(rowsTopTen records, Dao<rowsTopTen, Integer> topTenDao) {
        try {
            topTenDao.create(records);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void DeleteCountries(Dao<rowsCountries, Integer> countriesDao, Dao<summaryCountries, Integer> summaryCountries) throws SQLException {
        DeleteBuilder<rowsCountries, Integer> deleteBuilder = countriesDao.deleteBuilder();
        deleteBuilder.delete();
        DeleteBuilder<summaryCountries, Integer> deleteBuilder1 = summaryCountries.deleteBuilder();
        deleteBuilder1.delete();
    }


    public static void DeleteCountries(Dao<ICountries.Countries, Integer> countriesDao ) throws SQLException {
        DeleteBuilder<ICountries.Countries, Integer> deleteBuilder = countriesDao.deleteBuilder();
        deleteBuilder.delete();
    }



    public static void DeleterowsCountries(Dao<rowsCountries, Integer> countriesDao ) throws SQLException {
        DeleteBuilder<rowsCountries, Integer> deleteBuilder = countriesDao.deleteBuilder();
        deleteBuilder.delete();

    }


    /**
     * Check internet Connection
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }



    public static boolean isOnline(Activity act)
    {
        ConnectivityManager cm =
                (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /****************************************************************
     Top Ten
     ***************************************************************/

    /**validaciones
     * case  0 : flujo de sucripcion
     * case 1 : enviar alerta telefono no suscripto
     * case 2 : Paso a detalle curso
     **/

    public static int isSuscrito(Context ctx){
        return SharedPreferencesManager.getValorEsperadoInt(ctx, PREFERENCIA_INICIAL, KEY_SUSCRIBER);
    }
    /**
     *
     * @param ctx
     * @return codigo de pais que se tiene en la preferencia
     */

    public static String getCountryCode(Context ctx){
        return SharedPreferencesManager.getValorEsperado(ctx, PREFERENCIA_COUNTRYCODE, KEY_COUNTRYCODE);
    }

    /**
     *
     * @param ctx
     * @return numero que se tiene en la preferencia
     */
    public static String getMsisdn(Context ctx){
        return SharedPreferencesManager.getValorEsperado(ctx, PREFERENCIA_INICIAL, KEY_MSISDN);
    }


    /**
     *
     * @param ctx
     * @return numero que se tiene en la preferencia
     */
    public static String getSendPush(Context ctx){
        return SharedPreferencesManager.getValorEsperado(ctx, PREFERENCIA_PUSH, KEY_PUSH);
    }

    /**
     *
     * @param ctx
     * @return numero que se tiene en la preferencia
     */
    public static String getSendPushMsisdn(Context ctx){
        return SharedPreferencesManager.getValorEsperado(ctx, PREFERENCIA_PUSH_NUM, KEY_PUSH_NUM);
    }

/*    public static ISuscriber.Suscripcion setSuscriber(String msisdn ,String CountryCode, String first_name, String last_name, String email) {
        ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriber.class);

        try{
            suscripcion = resAdapter.saveSuscriptor(msisdn, CountryCode, first_name, last_name, email);
        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return suscripcion;
    }*/


  /*  public static ISuscriber.Suscripcion setGeneradiploma(String msisdn,String CountryCode, int course) {
        int id2 = 0 , id3 = 1, id5 =1 ;
        ISuscriber.Suscripcion suscripcion = null;
        ISuscriber resAdapter = MlearningApplicattion.getApplication().getmRestDiplomaAdapter().create(ISuscriber.class);

        try {
            suscripcion = resAdapter.sendDiploma(course, id2, id3, CountryCode, msisdn, id5);

        } catch (RetrofitError rec){
            rec.printStackTrace();
            return null;
        }

        return suscripcion;
    }*/

   /* public static ISuscriberInfo.SuscriberInfo getSuscriber(String msisdn , String CountryCode){
        ISuscriberInfo.SuscriberInfo suscriberInfo = null;
        ISuscriberInfo restAdapter = MlearningApplicattion.getApplication().getRestAdapter().create(ISuscriberInfo.class);
        try {
            suscriberInfo = restAdapter.getSuscriberInfo(msisdn, CountryCode);
        } catch (RetrofitError er){
            er.printStackTrace();
            return null;
        }
        return suscriberInfo;
    }*/


    public static MixpanelAPI getmixPanel(Activity act){
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(act, "3b8a21b24688318a68d898af7d60cdda");
        return mixpanel;

    }

    public static MixpanelAPI getmixPanelCont(Context ctx){
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(ctx, "3b8a21b24688318a68d898af7d60cdda");
        return mixpanel;

    }


    public static List<rows> GetRowsCourseDatabase(Dao<rows, Integer> rowsDao, int idmed_course) throws SQLException {
        List<rows> rows = null;

        String query = "SELECT   distinct(idmed_courses ), name  ,idmed_categories  FROM rowsTable where idmed_courses = "+idmed_course;
        GenericRawResults<rows> rawResults = rowsDao.queryRaw(query, rowsDao.getRawRowMapper());
        rows = rawResults.getResults();

        return rows;
    }


   /* public static MixpanelAPI getmixPanel2(Context ctx){
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(ctx, "7d82149085babc7916828833cdbe2f6f" "7e28774e4c58d9d3ce898dc64e330833"  ); //PRUEBAS
        return mixpanel;
        //return SharedPreferencesManager.getValorEsperado(ctx, PREFERENCIA_INICIAL, KEY_MSISDN);
    }*/

    public  static  void sendMixpanelQuiz(String nameCourse, int idCourse ,String nameLesson, int idLesson , int idEvento, Activity act) {
        String evento = "Termino la lectura";
        String evento2 = "Realizo el  test";
        String evento3 = "Entrega del certificado";
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";
        List<rows> lstrows;

        try {
            MixpanelAPI mixpanel = Utils.getmixPanel(act);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            if ( idEvento == 1 )
                props.put("Actividad",evento);
            else if (idEvento == 2)
                props.put("Actividad",evento2);
            else  if(idEvento == 3)
                props.put("Actividad",evento3);

            props.put("idCourse", idCourse);

            if (nameCourse.equals(null) || nameCourse.equals("") ) {
                lstrows = Utils.GetRowsCourseDatabase(MlearningApplicattion.getApplication().getRowsDao(), idCourse);
                if (lstrows.get(0).getName() != null)
                    props.put("nameCourse", lstrows.get(0).getName());
            }else
                props.put("nameCourse", nameCourse);

            if(idEvento != 3) {
                props.put("idLesson", idLesson);
                props.put("nameLesson", nameLesson);
            }
            mixpanel.track("Recepción del Certificado", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  static  void sendMixpanelQuizAdvance(String nameCourse, int idCourse  , int Progress, Activity act) {
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";
        List<rows> lstrows;

        try {
            MixpanelAPI mixpanel = Utils.getmixPanel(act);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            props.put("Progress", Progress+ " %");

            props.put("idCourse", idCourse);

            if (nameCourse.equals(null) || nameCourse.equals("") ) {
                lstrows = Utils.GetRowsCourseDatabase(MlearningApplicattion.getApplication().getRowsDao(), idCourse);
                if (lstrows.get(0).getName() != null)
                    props.put("nameCourse", lstrows.get(0).getName());
            }else
                props.put("nameCourse", nameCourse);

            mixpanel.track("Avances de los cursos", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static  void sendMixpanelChannel( int idcourse ,int idEvento , Context context ) {
        List<rows> lstrows;
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";
        String evento1 = "Home";
        String evento2 = "Mis cursos";
        String evento3 = "TopTen";

        try {
            MixpanelAPI mixpanel = Utils.getmixPanelCont(context);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(context, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            if (idEvento == 1)
                props.put("Inicio", evento1);
            else if (idEvento == 2)
                props.put("Inicio", evento2);
            else if (idEvento == 3)
                props.put("Inicio", evento3);

            props.put("idCourse", idcourse);

            lstrows = Utils.GetRowsCourseDatabase(MlearningApplicattion.getApplication().getRowsDao(), idcourse);
            if ( lstrows.get(0).getName() != null)
                props.put("nameCourse", lstrows.get(0).getName() );

            mixpanel.track("Canales de consumo de un curso", props);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static  void sendMixpanelChannelACT(int idcourse ,int idEvento ,String title2 , int id_category , Activity act){
        List<rows> lstrows;
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";
        String evento4 = "Categorias";
        String evento5 = "Buscador";

        try {
            MixpanelAPI mixpanel = Utils.getmixPanelCont(act);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            if (idEvento == 4)
                props.put("Inicio", evento4);
            else if (idEvento == 5)
                props.put("Inicio", evento5);

            if (idEvento == 4) {
                props.put("Panel de categorias", title2);
                props.put("id_category", id_category);
            }

            props.put("idCourse", idcourse);


            lstrows = Utils.GetRowsCourseDatabase(MlearningApplicattion.getApplication().getRowsDao(), idcourse);
            if ( lstrows.get(0).getName() != null)
                props.put("nameCourse", lstrows.get(0).getName() );

            mixpanel.track("Canales de consumo de un curso", props);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void sendMixpanelRanking(int  idCourse , String nameCourse , int idRating ,  Activity act) {
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";

        try {

            MixpanelAPI mixpanel = Utils.getmixPanel(act);
            JSONObject props = new JSONObject();

            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");
            props.put("idCourse", idCourse);
            props.put("nameCourse", nameCourse);
            props.put("Estrella", idRating);
            mixpanel.track("Valoración de los cursos", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static  void sendMixpanelInfoSuscriber(String numeroIngresado , String email,  Activity act ) {
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";

        try {
            MixpanelAPI mixpanel = Utils.getmixPanel(act);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            props.put("Correo electrónico", email);
            mixpanel.track("Información de los usuarios", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static  void sendMixpanelPopularword( String word,  Activity act ) {
        String numeroSuscripcion ;
        String KEY_NUMERO = "numero";

        try {
            MixpanelAPI mixpanel = Utils.getmixPanel(act);
            JSONObject props = new JSONObject();
            numeroSuscripcion = SharedPreferencesManager.getValorEsperado(act, PREFERENCIA_INICIAL, KEY_NUMERO);
            if(numeroSuscripcion != null)
                props.put("idNumber", numeroSuscripcion);
            else
                props.put("idNumber", "Free");

            props.put("Palabras claves", word);
            mixpanel.track("Palabras claves mas populares", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static  void sendMixpanelStartSubscription(String inicio , String numero , int idCourse ,String pin,  Activity act ){
        List<rows> lstrows;
        try {
            MixpanelAPI mixpanel = Utils.getmixPanelCont(act);
            JSONObject props = new JSONObject();
            props.put("Inicio", inicio);
            if (idCourse != 0 ) {
                props.put("idCourse", idCourse);
                lstrows = Utils.GetRowsCourseDatabase(MlearningApplicattion.getApplication().getRowsDao(), idCourse);
                if (lstrows.get(0).getName() != null)
                    props.put("nameCourse", lstrows.get(0).getName());
            }
            props.put("idNumber", numero);
            props.put("Pin", pin);
            mixpanel.track("Iniciar un curso y Suscripción", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tracking the screen view  --Google Analytics trackers
    public static  void sendGoogleAnalyticsTracker (String inicio , String mensaje){
        try {

            String GoogleAnalyticsScreen  =  "";
            if (inicio.equals("home")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA HOME";

            } else if (inicio.equals("cursos")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA MIS CURSOS";

            } else if (inicio.equals("top")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA TOP TEN";

            } else if (inicio.equals("configuraciones")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA CONFIGURACIÓN";

            } else if (inicio.equals("terminos")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA TÉRMINOS CONDICIONES DE " + mensaje;

            } else if (inicio.equals("buscar")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA BUSCAR";

            } else if (inicio.equals("categoria")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA CATEGORÍA " +mensaje;

            } else if (inicio.equals("suscripcion")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA SUSCRIPCIÓN";

            } else if (inicio.equals("listalecciones")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA LISTA DE LECCIONES";

            } else if (inicio.equals("detalleleleccion")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA DETALLE LECCIÓN";

            } else if (inicio.equals("examen")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA EXAMEN";

            } else if (inicio.equals("diploma")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA DIPLOMA";

            } else if (inicio.equals("enviarinformacion")) {
                GoogleAnalyticsScreen = "EDUKT PANTALLA ENVIAR INFORMACION USUARIO";

            }

            // Tracking the screen view
            MlearningApplicattion.getInstance().trackScreenView(   GoogleAnalyticsScreen );
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
