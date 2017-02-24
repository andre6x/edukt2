package mlearning.grupolink.com.mlearningandroid.Home.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.sql.SQLException;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.Home.adapters.HomeAdapter;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.SharedPreferencesManager;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.activities.LessonActivity;
import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.IHomeCursos;
import mlearning.grupolink.com.mlearningandroid.entities.IPushNotification;
import mlearning.grupolink.com.mlearningandroid.entities.rowsHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by GrupoLink on 08/04/2015.
 */
public class HomeFragment extends Fragment implements HomeAdapter.MyViewHolder.ClickListener {

    private String TAG = "PANTALLA HOME";
    private IPushNotification.PushNotification mNotification;

    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;
    private boolean flag;
    private String cdn = "";

    private IHomeCursos.HomeCursos mCursos;
    private Call<IHomeCursos.HomeCursos> call_1;
    private Call<IPushNotification.PushNotification> call_2;
    private String CountryCode = "";
    private String msisdn = "";
    private String token ="";
    private String SendPush = "";
    private String Send_Push_msisdn = "";

    private static String PREFERENCIA_PUSH = "Push_Code";
    private static String KEY_PUSH = "isPush_Code";
    private static String PREFERENCIA_PUSH_NUM = "Push_num_Code";
    private static String KEY_PUSH_NUM = "isPush_num_Code";

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private HomeAdapter adapter;
    private List<rowsHome> DBrowsHome;
    private IHomeCursos.HomeCursos DBmCursos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View contenView = inflater.inflate(R.layout.home_layout, container, false);

        MainActivity.SelectMenuCountry = true ;
        flag = true;
        linear_loading = (LinearLayout) contenView.findViewById(R.id.linear_loading);
        progress = (ProgressBar) contenView.findViewById(R.id.progress);
        retry = (Button) contenView.findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restartLoading(0, false);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) contenView.findViewById(R.id.srl_swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.greenMenu,
                                                    R.color.yellowMenu,
                                                    R.color.colorPrimary,
                                                    R.color.purpleMenu);
        mRecyclerView = (RecyclerView) contenView.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        try{
            Utils.sendGoogleAnalyticsTracker("home","");
        }catch (Exception e){e.printStackTrace();}
        MainActivity.FLAG_FEED_HOME = false;

        selectDataHomeCoursesDB();
        validacionesEnvioPush();
        swipeRefreshLayout();
        return contenView;
    }

    //obtener datos de la tabla BD
    public  void selectDataHomeCoursesDB(){
        Log.e(TAG,"selectDataRowsHomeDB");
        try {
            //validar si en la tabla ahi datos mayor a 0
            if (Utils.GetRowsHomeFromDatabase(MlearningApplicattion.getApplication().getRowsHomeDao() ).size() > 0 ){
                //asignamos datos de la tabla a la lista de objeto
                DBrowsHome = Utils.GetRowsHomeFromDatabase(MlearningApplicattion.getApplication().getRowsHomeDao() );
                DBmCursos =  Utils.GetHomeCursesFromDatabase(MlearningApplicattion.getApplication().getHomeCursosDao()).get(0);//solo ahi una posicion

                //obtenemos el tamaño de la listaDEobjetos , para recorrerlo y presenta los datos de la tabla bd en el LOG
                int tamaño = DBrowsHome.size();
                for(int i = 0 ; i < tamaño ; i++){
                    Log.e(TAG,i+"-Concentracion:" + DBrowsHome.get(i).getName() +" -idCourse:"+DBrowsHome.get(i).getIdmed_courses() );
                }
                //setear el adaptador con los datos
                callsetAdapter();
                swipeRefreshProgress();
                restartLoading(0, true);

            }else{
                restartLoading(0, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
     }

    public void swipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restartLoading(0 , true);
            }
        });
    }

    public void swipeRefreshProgress(){
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void validacionesEnvioPush(){

        if (Utils.getCountryCode(getActivity()) != null)
            CountryCode = Utils.getCountryCode(getActivity());
        else
            CountryCode= "";

        if (Utils.getMsisdn(getActivity()) != null)
            msisdn = Utils.getMsisdn(getActivity());
        else
            msisdn= "";

        Log.e(TAG, " msisdn:"+ msisdn);

        if(  msisdn.equals("") ) {
            Log.e(TAG, " msisdn vacio");

            if (Utils.getSendPush(getActivity()) != null)
                SendPush = Utils.getSendPush(getActivity());

            if(  SendPush.equals("true")  ) {
                Log.e(TAG, "push ya ah sido enviado a json edukt");
            }else {
                Log.e(TAG, "push no ah sido enviado a json edukt");
                SendPushNotification(1); // 1  push , 2 push msisdn codserv
            }

        }else {
            Log.e(TAG, " msisdn ok");
            if (Utils.getSendPushMsisdn(getActivity()) != null)
                Send_Push_msisdn = Utils.getSendPushMsisdn(getActivity());

            if(  Send_Push_msisdn.equals("true")  ) {
                Log.e(TAG, "push,msisdn,codservicio ya ah sido enviado a json edukt");
            }else {
                Log.e(TAG, "push,msisdn,codservicio no ah sido enviado a json edukt");
                SendPushNotification(2); // 1  push , 2 push msisdn codserv
            }
        }
    }


    private void SendPushNotification(int index){

        if(Utils.isOnline(getActivity() )) {
            token = SharedPreferencesManager.getValorEsperado(getActivity() , getString(R.string.preferencias_inicio), "gcmToken");
            if(token==null || token.equalsIgnoreCase("")){
                Log.e(TAG, "token is null");
            }else{
                Log.e(TAG, "token=" + token);
                restartPushNotification( index);
            }
        }else{
            Log.e(TAG, "Verificar conexión de internet");
        }
    }

    private void Postexecute(boolean Refresh)
    {
        if(mCursos != null){
            if(mCursos.getCode() == 0){
                if(mCursos.getRows()!= null){
                    if(mCursos.getRows().size() > 0 ){

                        showLayout(Refresh);

                        try {
                            //SETEAR DATOS TABLA DE DATABASE
                            Utils.DeleteHomeCursesDB(MlearningApplicattion.getApplication().getHomeCursosDao());
                            Utils.DeleteRowsHomeCursesDB(MlearningApplicattion.getApplication().getRowsHomeDao() );

                            if (mCursos.getSummary().getCdn() != null) {
                                mCursos.setCdn(mCursos.getSummary().getCdn());
                            }
                            Utils.DbsaveHomeCursos(mCursos, MlearningApplicattion.getApplication().getHomeCursosDao());
                            for (rowsHome row : mCursos.getRows()) {
                                 Utils.DbsaveRowsHome(row, MlearningApplicattion.getApplication().getRowsHomeDao());
                            }
                            //OBTENER DATOS TABLA DE DATABASE
                            if (Utils.GetRowsHomeFromDatabase(MlearningApplicattion.getApplication().getRowsHomeDao() ).size() > 0 ){
                                //asignamos datos de la tabla a la lista de objeto
                                DBrowsHome = Utils.GetRowsHomeFromDatabase(MlearningApplicattion.getApplication().getRowsHomeDao() );
                                DBmCursos =  Utils.GetHomeCursesFromDatabase(MlearningApplicattion.getApplication().getHomeCursosDao()).get(0);//solo ahi una posicion

                                //obtenemos el tamaño de la listaDEobjetos , para recorrerlo y presenta los datos de la tabla bd en el LOG
                                int tamaño = DBrowsHome.size();
                                for(int i = 0 ; i < tamaño ; i++){
                                    Log.e(TAG,"Concentracion:" + DBrowsHome.get(i).getName() +" -idCourse:"+DBrowsHome.get(i).getIdmed_courses() );
                                }
                                //setear el adaptador con los datos
                                callsetAdapter();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }else
                        showRetry(Refresh);
                 }else
                    showRetry(Refresh);
            }else
                showRetry(Refresh);
        }else
            showRetry(Refresh);
    }

    //setear datos al adapter
    public void callsetAdapter(){

        if(DBmCursos.getCdn() != null)
            cdn = DBmCursos.getCdn();
        //validacion si se a iniciado el adapter
        if (adapter != null){
            //actuliza la data del apdater
            Log.e(TAG,"adapter != null");
            adapter.updateData(DBrowsHome , cdn);
        }else {//es nulo
            //crea la lista adapter
            Log.e(TAG,"adapter  null");
            adapter = new HomeAdapter(getActivity(), DBrowsHome , cdn, this);
            mRecyclerView.setAdapter(adapter);
        }
        MainActivity.FLAG_FEED_HOME = true;
     }

    private void showLayout(boolean Refresh)
    {  if(getActivity() != null) {
            if (! Refresh) {
                linear_loading.setVisibility(View.GONE);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
       }
    }

    private void showLoading(boolean Refresh)
    {   if(getActivity() != null) {
            if(! Refresh) {
               linear_loading.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                retry.setVisibility(View.GONE);
            }
        }
   }

    private void showRetry(boolean Refresh)
    {
        if(getActivity() != null) {
            if(! Refresh) {
                if (!MainActivity.FLAG_FEED_HOME) {
                    linear_loading.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                } else {
                    linear_loading.setVisibility(View.GONE);
                }
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }
    }

    private void restartLoading(int index , boolean Refresh) {
        showLoading(Refresh);
        switch (index) {
            case 0:
                restartLoadingCourses(Refresh);
                break;
        }
    }


    private void restartLoadingCourses(final boolean Refresh){

        Log.e(TAG, getString(R.string.base_path)+"getCourses?cod_servicio=" + CountryCode +  "&limit=" + Config.limit + "&offset=" + Config.offset);

        IHomeCursos posiciones = MlearningApplicattion.getApplication().getRestAdapter().create(IHomeCursos.class);
        call_1 = posiciones.getNewsFrom(CountryCode, Config.limit, Config.offset);
        call_1.enqueue(new Callback<IHomeCursos.HomeCursos>() {
            @Override
            public void onResponse(Call<IHomeCursos.HomeCursos> call, Response<IHomeCursos.HomeCursos> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mCursos = response.body();

                    Postexecute(Refresh);
                } else {
                    showRetry(Refresh);
                    Log.e(TAG, "Error en la petición");
                }
            }
            @Override
            public void onFailure(Call<IHomeCursos.HomeCursos> call, Throwable t) {
                Log.e(TAG, "Error en la petición onFailure");
                showRetry(Refresh);
                t.printStackTrace();
            }
        });
    }


    private void restartPushNotification(final int index){
        Log.e(TAG, "enviando");
        IPushNotification notifi = MlearningApplicattion.getApplication().getmRestPushAdapter().create(IPushNotification.class);
        if (index == 1)
            call_2 = notifi.getPushNotification_2(token);
        else //2
            call_2 = notifi.getPushNotification(token, CountryCode, msisdn);

        call_2.enqueue(new Callback<IPushNotification.PushNotification>() {
            @Override
            public void onResponse(Call<IPushNotification.PushNotification> call, Response<IPushNotification.PushNotification> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "PushNotification Respuesta exitosa");
                    mNotification = response.body();

                    if ( mNotification != null ) {
                        Log.e(TAG, "mNotification != null");
                        if (mNotification.getCode() == 0) {
                            Log.e(TAG, "mNotification code = 0 INDEX:"+index);
                            if (index == 1)
                                SharedPreferencesManager.setValor(getActivity(), PREFERENCIA_PUSH, "true", KEY_PUSH);
                            else //2
                                SharedPreferencesManager.setValor(getActivity(), PREFERENCIA_PUSH_NUM, "true", KEY_PUSH_NUM);
                        }
                    }


                } else {
                    //showRetry();
                    Log.e(TAG, "PushNotification Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<IPushNotification.PushNotification> call, Throwable t) {
                //showRetry();
                Log.e(TAG, "PushNotification Error en la petición onFailure");
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(call_1!=null && !call_1.isCanceled()) {
            call_1.cancel();
        }
        if(call_2!=null && !call_2.isCanceled()) {
            call_2.cancel();
        }
    }

    @Override
    public void onItemClicked(View view,int position) {
        try {
            //obtener data de DATABASE
            Log.e(TAG, "URLIMAGEN:" + cdn + "/" + DBrowsHome.get(position).getFolder() + "/" + DBrowsHome.get(position).getUrl_cdn_large());
            String urlImage = cdn + "/" + DBrowsHome.get(position).getFolder() + "/" + DBrowsHome.get(position).getUrl_cdn_large();
            String title = DBrowsHome.get(position).getName();
            String subTitle = DBrowsHome.get(position).getDescription();
            int idCourse = DBrowsHome.get(position).getIdmed_courses();

            Intent intent = new Intent(getActivity(), LessonActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putSerializable("car", mCursosDB.getRows().get(position) ) ;
            bundle.putString("cdn", cdn);
            bundle.putInt("idCourse", idCourse);
            bundle.putString("urlImage", urlImage);
            bundle.putString("title", title);
            bundle.putString("subTitle", subTitle);
            intent.putExtras(bundle);
            // TRANSITIONS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                View img_cover = view.findViewById(R.id.img_cover);
                View txt_title = view.findViewById(R.id.txt_title);
                View txt_sub_title = view.findViewById(R.id.txt_sub_title);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(img_cover, "element1"),
                        Pair.create(txt_title, "element2"),
                        Pair.create(txt_sub_title, "element3"));

                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onItemLongClicked(View v,int position) {

        return false;
    }



}
