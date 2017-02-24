package mlearning.grupolink.com.mlearningandroid.Home.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;


import java.util.Properties;
import java.util.Random;

import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Home.fragments.SlidingMenuFragment;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.Home.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    Properties user;
    private Toolbar mToolbar;
    private String TAG = "MainActivity";
    private Context mContext;
    //private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment mFragment;
    public static boolean flag_first;
    public static boolean FLAG_FEED;
    public static ICategories.Category mCategories;
    public static String cdn;
    private LinearLayout my_course, top_ten, settings;
    public static Intent nextIntent;
    public static boolean isQuizAprobate;
    public static boolean isQuizAprobateViewDiploma, isQuizAprobateViewDiploma_2;
    public static MixpanelAPI mixpanelm ;
    String numeroSuscripcion ;
    String numeroSuscripcion1 = "" ;
    String KEY_NUMERO = "numero";
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    String CountryCode = "";

    public static String countrySelectCLICK ;
    public static Boolean SelectMenuCountry;
    public static Boolean SelectCategory;
    public static Boolean IsFullScreen;
    public static AlertDialog alertNoSuscrito ;
    public static Boolean SelectVideoHD = false;
    public static boolean FLAG_FEED_HOME ;

    Dialog customDialog = null;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String PREFERENCIA_COUNTRYCODE = "Country_Code";
    String KEY_COUNTRYCODE = "isCountry_Code";
    private ICountries.Countries mCountries;

    private static Tweak<Double> gameSpeed = MixpanelAPI.doubleTweak("Game speed",1.0);
    private static Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Shows ads", false);

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String token="";

    SlidingMenu menu;
    Toolbar toolbar;
    int     actionBarMenuState;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        nextIntent = null;
        MainActivity.FLAG_FEED = false;


        // TRANSITIONS
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                /*Explode trans1 = new Explode();
                trans1.setDuration(3000);
                Fade trans2 = new Fade();
                trans2.setDuration(3000);

                getWindow().setExitTransition( trans1 );
                getWindow().setReenterTransition( trans2 );*/

            TransitionInflater inflater = TransitionInflater.from( this );
            Transition transition = inflater.inflateTransition( R.transition.transitions );

            getWindow().setSharedElementExitTransition( transition );
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((AppCompatActivity) this).getSupportActionBar().setDisplayShowTitleEnabled(false);

        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // random state
                actionBarMenuState = generateState(actionBarMenuState);
                getMaterialMenu(toolbar).animateIconState(intToState(actionBarMenuState));
            }
        });*/

        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                //toggleMenu();
                    menu.showMenu();


            }
        });

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //menu.setBehindOffset(400);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        //menu.setShadowWidth(16);
        menu.setFadeDegree(0.35f);
       // menu.setBehindWidth(500);
        menu.setBehindScrollScale(0.0f);
        menu.setMode(SlidingMenu.LEFT); // Use SlidingMenu.RIGHT to start the menu from right
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.fargment_sliding_menu);


        //efecto scale
        SlidingMenu.CanvasTransformer mTransformer = new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                canvas.scale(percentOpen, 1, 0, 0);
            }
        };
        menu.setBehindCanvasTransformer( mTransformer );

        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                getMaterialMenu(toolbar).animateIconState(MaterialMenuDrawable.IconState.ARROW);
            }
        });
        menu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                getMaterialMenu(toolbar).animateIconState(MaterialMenuDrawable.IconState.BURGER);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, SlidingMenuFragment.newInstance())
                .commit();

        //set margen ya que se corta el button debido a libreria
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int navBarHeight = getNavigationBarHeight();
            findViewById(R.id.container_frame).setPadding(0, 0, 0, navBarHeight);
            findViewById(R.id.menu_frame).setPadding(0, 0, 0, navBarHeight);
        }

        callFragmentHome();

    }

    public void toggleMenu() {
        menu.toggle();
    }

    private static MaterialMenuDrawable getMaterialMenu(Toolbar toolbar) {
        return (MaterialMenuDrawable) toolbar.getNavigationIcon();
    }

    private static int generateState(int previous) {
        int generated = new Random().nextInt(4);
        return generated != previous ? generated : generateState(previous);
    }

    private static MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }

    public void callFragmentHome(){

        mFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_frame, mFragment).commit();

    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}