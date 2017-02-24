package mlearning.grupolink.com.mlearningandroid.Settings.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Settings.activities.SettingsActivity;
import mlearning.grupolink.com.mlearningandroid.Settings.adapters.CountriesListAdapter;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesFragment extends Fragment {

    private View view;
    private String TAG = "CountriesFragment";
    private Call<ICountries.Countries> call_0;
    private String CountryCode = "";
    private ICountries.Countries mCountries;
    private CardView BtnContinuar;
    private RecyclerView recyclerView;


    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.countries_fragment, container, false);

        BtnContinuar = (CardView) view.findViewById(R.id.card_OK);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        linear_loading = (LinearLayout) view.findViewById(R.id.linear_loading);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        retry = (Button) view.findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restartLoading(0);
            }
        });


        restartLoading(0);

        return view;
    }

    private void showLayout()
    {
        linear_loading.setVisibility(View.GONE);

    }

    private void showLoading()
    {
        linear_loading.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
    }

    private void showRetry()
    {
        linear_loading.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
    }

    private void restartLoading(int index) {
        showLoading();
        restartLoadingCountries();

    }


    private void restartLoadingCountries(){

        ICountries countries = MlearningApplicattion.getApplication().getRestAdapter().create(ICountries.class);
        call_0 = countries.getNewsFromByCountries();
        //call_0 = countries.getNewsFromByCountries_2("2.0");
        call_0.enqueue(new Callback<ICountries.Countries>() {
            @Override
            public void onResponse(Call<ICountries.Countries> call, Response<ICountries.Countries> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mCountries = response.body();
                    postExecutionCountries();
                } else {
                    showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ICountries.Countries> call, Throwable t) {
                showRetry();
                t.printStackTrace();
            }
        });
    }

    public void postExecutionCountries(){
        try{

            if (mCountries != null)
                if (mCountries.getSummary().getCdn() != null)
                    mCountries.setCdn(mCountries.getSummary().getCdn());
            if (mCountries.getRows() != null)
                if (mCountries.getRows().size() > 0)
                    mCountries.setNum_rows(mCountries.getRows().size());
            Utils.DeleterowsCountries(MlearningApplicattion.getApplication().getRowsCountriesDao());
            Utils.DeleteCountries(MlearningApplicattion.getApplication().getCountriesDao());

            Utils.DbsaveCountries(mCountries, MlearningApplicattion.getApplication().getCountriesDao());
            if (mCountries.getRows() != null)
                if (mCountries.getRows().size() > 0) {
                    Utils.DeleterowsCountries(MlearningApplicattion.getApplication().getRowsCountriesDao());
                    for (rowsCountries row : mCountries.getRows()) {
                        Utils.DbsaveRowsCountries(row, MlearningApplicattion.getApplication().getRowsCountriesDao());
                    }
                }
            if (Utils.GetCountriesFromDatabaseV2(MlearningApplicattion.getApplication().getCountriesDao()).size() > 0){
                mCountries = Utils.GetCountriesFromDatabase(MlearningApplicattion.getApplication().getCountriesDao(), MlearningApplicattion.getApplication().getRowsCountriesDao()).get(0);
            }

            Postexecute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void Postexecute() {

        String cdn = "";
        MainActivity.countrySelectCLICK = null;


        if(mCountries != null){
            if(mCountries.getCode() == 0){
                if(mCountries.getRows() != null ){
                    if(mCountries.getRows().size() > 0){
                        cdn = mCountries.getCdn();

                        showLayout();


                        CountriesListAdapter adapter = new CountriesListAdapter(getActivity() , mCountries.getRows(), cdn );
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ));



                        BtnContinuar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (MainActivity.countrySelectCLICK != null) {
                                    String CountryCode = MainActivity.countrySelectCLICK;

                                    ((SettingsActivity)getActivity()).CountryCode = CountryCode;

                                    //llamar ametodo de laactivdad para reemplazar fragmento
                                    ((SettingsActivity)getActivity()).callFragmentSuscriber();

                                } else {
                                    CharSequence text = "Debe Seleccionar un País";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getActivity(), text, duration);
                                    toast.show();
                                }
                            }
                        });



                    }else {
                        showRetry();

                    }
                }else {
                    showRetry();

                }
            }else {
                showRetry();

            }
        }else {
            showRetry();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(call_0!=null && !call_0.isCanceled()) {
            call_0.cancel();
        }
    }



}
