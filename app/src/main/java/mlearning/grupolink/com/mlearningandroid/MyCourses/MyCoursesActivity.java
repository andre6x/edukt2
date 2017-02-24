package mlearning.grupolink.com.mlearningandroid.MyCourses;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.Comparator;

import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.activities.LessonActivity;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.IMyCourses;
import mlearning.grupolink.com.mlearningandroid.entities.rowsMyCourses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCoursesActivity extends AppCompatActivity implements MyCoursesAdapter.ViewHolder.ClickListener{

    private String TAG = "MyCoursesActivity";
    private String title = "Mis Cursos";
    private String CountryCode = "";
    private String msisdn = "";
    private String cdn = "";
    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    private RecyclerView recyclerView;
    private Call<IMyCourses.MyCourses> call_0;
    private IMyCourses.MyCourses mMycourse;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private MyCoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_courses_activity);

        linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
        progress = (ProgressBar) findViewById(R.id.progress);
        retry = (Button) findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoading(false);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);

        Utils.SetStyleToolbarTitle(this, title);

        getDataMsisdnCountry();
        swipeRefreshLayout();
        restartLoading(false);
    }

    public void getDataMsisdnCountry(){
        if(Utils.getMsisdn(this) != null)
            msisdn = Utils.getMsisdn(this);
        if(Utils.getCountryCode(this) != null)
            CountryCode = Utils.getCountryCode(this);
    }

    public void swipeRefreshLayout(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restartLoading(true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void showLayout(boolean Refresh)
    {  if(this != null) {
        if (!Refresh) {
            linear_loading.setVisibility(View.GONE);
        }else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    }

    private void showLoading(boolean Refresh)
    {   if(this != null) {
        if (!Refresh) {
            linear_loading.setVisibility(View.VISIBLE);
            progress.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }
    }
    }

    private void showRetry(boolean Refresh)
    {  if(this != null) {
            if (!Refresh) {
                linear_loading.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                retry.setVisibility(View.VISIBLE);
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void restartLoading(boolean Refresh) {
        showLoading(Refresh);
        restartLoadingMyCourses(Refresh);
    }

    private void restartLoadingMyCourses(final boolean Refresh){
        Log.v(TAG, getString(R.string.base_path) +"getCourses?msisdn="+msisdn + "&cod_servicio="+ CountryCode + "&limit=" + Config.limit + "&offset=" + Config.offset);

        IMyCourses courses = MlearningApplicattion.getApplication().getRestAdapter().create(IMyCourses.class);
        call_0 = courses.getNewsFrom(msisdn, CountryCode, Config.limit, Config.offset);
        call_0.enqueue(new Callback<IMyCourses.MyCourses>() {
            @Override
            public void onResponse(Call<IMyCourses.MyCourses> call, Response<IMyCourses.MyCourses> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mMycourse = null;
                    mMycourse = response.body();

                    Postexecute(Refresh);

                } else {
                    showRetry(Refresh);
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<IMyCourses.MyCourses> call, Throwable t) {
                showRetry(Refresh);
                t.printStackTrace();
            }
        });
    }

    private void Postexecute(boolean Refresh)
    {
        if(mMycourse != null){
            if(mMycourse.getCode() == 0){
                PostexecuteRows(Refresh);

            }else if(mMycourse.getCode() ==  119   ){
                Utils.generarAlerta119(this, this.getResources().getString(R.string.text_title_suscriber), mMycourse.getMessage_alert() );
                showLayout(Refresh);
            }else if(mMycourse.getCode() ==  120  ){
                Utils.generarAlertaNoSuscrito(this, this.getResources().getString(R.string.text_title_suscriber), mMycourse.getMessage_alert(), msisdn, CountryCode , false);
                showLayout(Refresh);
            }else if(mMycourse.getCode() ==  121  ){
                validationFreemium(Refresh);
                showLayout(Refresh);
            }else
                showRetry(Refresh);
        }else
            showRetry(Refresh);
    }

    private void PostexecuteRows(boolean Refresh){

        if(mMycourse.getRows() != null){
            if( mMycourse.getRows().size() > 0){


                showLayout(Refresh);
                if(mMycourse.getSummary().getCdn() != null)
                    cdn = mMycourse.getSummary().getCdn();
                //validacion si se a iniciado el adapter
                if (adapter != null){
                    //actuliza la data del apdater
                    Log.e(TAG,"adapter != null-->update data adapter");
                    adapter.updateData(mMycourse.getRows() , cdn);

                }else {//es nulo
                    //crea la lista adapter
                    Log.e(TAG,"adapter  null-->create adapter");
                    adapter = new MyCoursesAdapter(this,mMycourse.getRows(), cdn , this );
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager( this ));

                    RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.rv_list_2);
                    adapter = new MyCoursesAdapter(this,mMycourse.getRows(), cdn , this );
                    recyclerView2.setAdapter(adapter);
                    recyclerView2.setLayoutManager(new LinearLayoutManager( this ));


                }

            }else
                showRetry(Refresh);
        }else
            showRetry(Refresh);

    }


    private void validationFreemium(boolean Refresh){
        try{
            Log.e(TAG,mMycourse.getDescription()+"--mLessonById.getStatus()="+mMycourse.getStatus());
            if ( mMycourse.getStatus() == 3 ) {
                PostexecuteRows(Refresh);
            }else
                PostexecuteRows(Refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(call_0!=null && !call_0.isCanceled()) {
            call_0.cancel();
        }
    }


    @Override
    public void onItemClicked(View view, int position) {
        Log.e(TAG, "URLIMAGEN:" + cdn +"/"+ mMycourse.getRows().get(position).getFolder()+"/" + mMycourse.getRows().get(position).getUrl_cdn_large() ) ;
        String urlImage = cdn +"/"+ mMycourse.getRows().get(position).getFolder()+"/" + mMycourse.getRows().get(position).getUrl_cdn_large() ;
        String title = mMycourse.getRows().get(position).getName()  ;
        String subTitle = mMycourse.getRows().get(position).getDescription() ;
        int idCourse =  mMycourse.getRows().get(position).getIdmed_courses() ;

        Intent intent = new Intent(this, LessonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cdn"     , cdn );
        bundle.putInt("idCourse"   , idCourse);
        bundle.putString("urlImage", urlImage );
        bundle.putString("title"   , title );
        bundle.putString("subTitle", subTitle );
        intent.putExtras(bundle);

        // TRANSITIONS
       /* if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            View img_cover     = view.findViewById(R.id.img_cover);
            View txt_title     = view.findViewById(R.id.txt_title);
            View txt_sub_title = view.findViewById(R.id.txt_sub_title);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( this,
                    Pair.create( img_cover,     "element1" ),
                    Pair.create( txt_title,     "element2" ),
                    Pair.create( txt_sub_title, "element3" ));

            startActivity( intent, options.toBundle() );
        } else {*/
            startActivity(intent);
        //}
    }

    @Override
    public boolean onItemLongClicked(View v, int position) {
        return false;
    }
}
