package mlearning.grupolink.com.mlearningandroid.Start.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Start.adapters.Adapter;
import mlearning.grupolink.com.mlearningandroid.Start.adapters.FavoriteCategoryGridAdapter;
import mlearning.grupolink.com.mlearningandroid.Start.fragments.favCategoryGridFragment;
import mlearning.grupolink.com.mlearningandroid.Start.fragments.favCategoryListFragment;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteCategoryActivity extends AppCompatActivity {

    private static  final String TAG = "FavoriteCategory";
    private Call<ICategories.Category> call_0;
    private ICategories.Category mCategories;
    //INCLUDE
    private LinearLayout linear_loading , lyt_loading;
    private ProgressBar progress;
    private Button retry;

    private RecyclerView recyclerView;
    private FavoriteCategoryGridAdapter adapter;
    private CardView card_siguiente , card_omitir;
    private String msisdn , CountryCode ;
    private String iniciaDesde= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_category_activity);

        callFragmentGridFavCategory();

        /*lyt_loading = (LinearLayout) findViewById(R.id.lyt_loading);
        linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
        progress = (ProgressBar) findViewById(R.id.progress);
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restartLoading();
            }
        });

        if ( this.getIntent().getStringExtra("iniciaDesde") != null)
            iniciaDesde = this.getIntent().getStringExtra("iniciaDesde");
        if(Utils.getMsisdn(FavoriteCategoryActivity.this) != null)
            msisdn = Utils.getMsisdn(FavoriteCategoryActivity.this);
        if(Utils.getCountryCode(this) != null)
            CountryCode = Utils.getCountryCode(this);

        restartLoading();
        eventButtons();*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    public void callFragmentGridFavCategory(){
        favCategoryGridFragment mFragment = new favCategoryGridFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mFragment);
        fragmentTransaction.commit();
    }

    public void callFragmentListFavCategory(){
        favCategoryListFragment mFragment = new favCategoryListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mFragment);
        fragmentTransaction.commit();
    }

   /* @Override
    public void onBackPressed() {
       super.onBackPressed();
        Log.e(TAG,"onBackPressed");

        FragmentManager mManager = getSupportFragmentManager();
        Fragment f = mManager.findFragmentById(R.id.fragment_container);
        if(f != null && f instanceof favCategoryListFragment) {
            Log.e(TAG,"1");
            callFragmentGridFavCategory();
        }else{
            Log.e(TAG,"2");
        }
    }*/

    /*public void eventButtons(){
        card_siguiente = (CardView) findViewById(R.id.card_siguiente);
        card_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter != null) {
                    Log.e("adapter--", String.valueOf(adapter.getSelectedItemCount()));
                    if (adapter.getSelectedItemCount() > 0) {
                        Log.e("adapter--", "" + adapter.getSelectedItems());

                        for (int i = 0; i < adapter.getSelectedItems().size() ; i++) {
                            Log.e("adapter--", "--:"+adapter.getSelectedItems().get(i) );
                            int selectPosition = adapter.getSelectedItems().get(i) ;
                            mCategories.getRows().get(selectPosition ).getTitle() ;
                            Log.e("adapter--", "--:"+mCategories.getRows().get(selectPosition ).getTitle() );
                        }

                        if(iniciaDesde.equals("splashActivity")){
                            nextActivity();
                        }else{
                            finish();
                        }

                    }else{
                       snackBar(v, getString(R.string.text_mensaje) );

                    }
                }
            }
        });
        card_omitir = (CardView) findViewById(R.id.card_omitir);
        card_omitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextActivity();
            }
        });

    }

    public void nextActivity(){
        Intent intent = new Intent(FavoriteCategoryActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        finish();
    }


    private void restartLoading() {
        showLoading();
        String CountryCode= "";
        if(Utils.getCountryCode(this) != null)
            CountryCode = Utils.getCountryCode(this);
        restartLoadingCategories(CountryCode );

    }

    private void restartLoadingCategories(String CountryCode ){

        ICategories posiciones = MlearningApplicattion.getApplication().getRestAdapter().create(ICategories.class);
        call_0 = posiciones.getCategoryFrom(CountryCode, Config.limit, Config.offset);
        call_0.enqueue(new Callback<ICategories.Category>() {
            @Override
            public void onResponse(Call<ICategories.Category> call, Response<ICategories.Category> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mCategories = response.body();

                    Postexecute();

                } else {
                    showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ICategories.Category> call, Throwable t) {
                Log.e(TAG, "Error en la petición onFailure");
                showRetry();
                t.printStackTrace();
            }
        });
    }*/

/*
    public  void Postexecute(){

        if(mCategories != null){
            if(mCategories.getCode() == 0){
                if(mCategories.getRows() != null){
                    if(mCategories.getRows().size() > 0){

                        //setear grdiview recicler
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        GridLayoutManager lLayout = new GridLayoutManager(this, 3); //de cuanto va hacer el gridview
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(lLayout);

                        adapter = new FavoriteCategoryGridAdapter(this, mCategories.getRows() , mCategories.getSummary().getCdn() );
                        recyclerView.setAdapter(adapter);

                        showLayout();

                    }else
                        showRetry();
                }else
                    showRetry();
            }else
                showRetry();
        }else
            showRetry();
    }

    public void snackBar(View view ,    String texto){
        Snackbar.make(view, "\n" + texto  , Snackbar.LENGTH_LONG)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            *//*Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(it);*//*
                    }
                })
                .setActionTextColor(this.getResources().getColor(R.color.greenMenu))
                .show();
    }


    private void showLayout( ) {
        if (this != null) {
            lyt_loading.setVisibility(View.GONE);
            linear_loading.setVisibility(View.GONE);
        }
    }

    private void showLoading() {
        if (this != null) {
                lyt_loading.setVisibility(View.VISIBLE);
                linear_loading.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                retry.setVisibility(View.GONE);
            }
    }

    private void showRetry( ) {
        if (this != null){
            lyt_loading.setVisibility(View.VISIBLE);
                linear_loading.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                retry.setVisibility(View.VISIBLE);
            }
    }



    @Override
    public void onItemClicked(int position) {
		toggleSelection(position);
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }


    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

    }*/



}
