package mlearning.grupolink.com.mlearningandroid.Start.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Start.activities.FavoriteCategoryActivity;
import mlearning.grupolink.com.mlearningandroid.Start.adapters.FavCategoryListAdapter;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class favCategoryListFragment extends Fragment  {


    private static  final String TAG = "favCategoryList";
    private Call<ICategories.Category> call_0;
    private ICategories.Category mCategories;
    //INCLUDE
    private LinearLayout linear_loading , lyt_loading;
    private ProgressBar progress;
    private Button retry;

    private RecyclerView recyclerView;
    private FavCategoryListAdapter adapter;
    private CardView card_siguiente , card_atras;
    private String msisdn , CountryCode ;
    private String iniciaDesde= "";

    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fav_cate_list_fragment, container, false);


        lyt_loading    = (LinearLayout) view.findViewById(R.id.lyt_loading);
        linear_loading = (LinearLayout) view.findViewById(R.id.linear_loading);
        progress       = (ProgressBar)  view.findViewById(R.id.progress);
        retry          = (Button)       view.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restartLoading();
            }
        });

        if ( getActivity().getIntent().getStringExtra("iniciaDesde") != null)
            iniciaDesde = getActivity().getIntent().getStringExtra("iniciaDesde");
        if(Utils.getMsisdn(getActivity()) != null)
            msisdn = Utils.getMsisdn(getActivity() );
        if(Utils.getCountryCode(getActivity()) != null)
            CountryCode = Utils.getCountryCode(getActivity());

        restartLoading();
        eventButtons();

       return view;

    }

    public void eventButtons(){
        card_siguiente = (CardView) view.findViewById(R.id.card_siguiente);
        card_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });
        card_atras = (CardView) view.findViewById(R.id.card_atras);
        card_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousFragment();
            }
        });

    }

    public void nextActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        getActivity().finish();
    }

    public void previousFragment(){
        ((FavoriteCategoryActivity)getActivity()).callFragmentGridFavCategory();
    }

    private void restartLoading() {
        showLoading();
        String CountryCode= "";
        if(Utils.getCountryCode(getActivity()) != null)
            CountryCode = Utils.getCountryCode(getActivity());
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
    }


    public  void Postexecute(){

        if(mCategories != null){
            if(mCategories.getCode() == 0){
                if(mCategories.getRows() != null){
                    if(mCategories.getRows().size() > 0){

                        //setear grdiview recicler
                        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());


                        adapter = new FavCategoryListAdapter(getActivity() ,  mCategories.getRows() , mCategories.getSummary().getCdn() );
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
    public void onDestroy() {
        super.onDestroy();
        if(call_0!=null && !call_0.isCanceled()) {
            call_0.cancel();
        }
        Log.e(TAG,"onDestroy.........");
    }


}
