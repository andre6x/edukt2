package mlearning.grupolink.com.mlearningandroid.FavoriteCourses;

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

import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.activities.LessonActivity;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.IFavoriteCourses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteCoursesActivity extends AppCompatActivity implements FavoriteCoursesAdapter.ViewHolder.ClickListener{

    private String TAG = "FavoriteCourActivity";
    private String title = "Cursos Favoritos";
    private String CountryCode = "";
    private String msisdn = "";
    private String cdn = "";
    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    private RecyclerView recyclerView;
    private Call<IFavoriteCourses.FavoriteCourses> call_0;
    private IFavoriteCourses.FavoriteCourses mFavorite;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private FavoriteCoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_courses_activity);

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

        IFavoriteCourses courses = MlearningApplicattion.getApplication().getRestAdapter().create(IFavoriteCourses.class);
        call_0 = courses.getNewsFrom(msisdn, CountryCode, Config.limit, Config.offset);
        call_0.enqueue(new Callback<IFavoriteCourses.FavoriteCourses>() {
            @Override
            public void onResponse(Call<IFavoriteCourses.FavoriteCourses> call, Response<IFavoriteCourses.FavoriteCourses> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mFavorite = null;
                    mFavorite = response.body();

                    Postexecute(Refresh);

                } else {
                    showRetry(Refresh);
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<IFavoriteCourses.FavoriteCourses> call, Throwable t) {
                showRetry(Refresh);
                t.printStackTrace();
            }
        });
    }

    private void Postexecute(boolean Refresh)
    {
        if(mFavorite != null){
            if(mFavorite.getCode() == 0){
                PostexecuteRows(Refresh);

            }else if(mFavorite.getCode() ==  119   ){
                Utils.generarAlerta119(this, this.getResources().getString(R.string.text_title_suscriber), mFavorite.getMessage_alert() );
                showLayout(Refresh);
            }else if(mFavorite.getCode() ==  120  ){
                Utils.generarAlertaNoSuscrito(this, this.getResources().getString(R.string.text_title_suscriber), mFavorite.getMessage_alert(), msisdn, CountryCode , false);
                showLayout(Refresh);
            }else if(mFavorite.getCode() ==  121  ){
                validationFreemium(Refresh);
                showLayout(Refresh);
            }else
                showRetry(Refresh);
        }else
            showRetry(Refresh);
    }

    private void PostexecuteRows(boolean Refresh){

        if(mFavorite.getRows() != null){
            if( mFavorite.getRows().size() > 0){

                showLayout(Refresh);
                if(mFavorite.getSummary().getCdn() != null)
                    cdn = mFavorite.getSummary().getCdn();
                //validacion si se a iniciado el adapter
                if (adapter != null){
                    //actuliza la data del apdater
                    Log.e(TAG,"adapter != null-->update data adapter");
                    adapter.updateData(mFavorite.getRows() , cdn);

                }else {//es nulo
                    //crea la lista adapter
                    Log.e(TAG,"adapter  null-->create adapter");
                    adapter = new FavoriteCoursesAdapter(this,mFavorite.getRows(), cdn , this );
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager( this ));
                }

            }else
                showRetry(Refresh);
        }else
            showRetry(Refresh);

    }


    private void validationFreemium(boolean Refresh){
        try{
            Log.e(TAG,mFavorite.getDescription()+"--mLessonById.getStatus()="+mFavorite.getStatus());
            if ( mFavorite.getStatus() == 3 ) {
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
        Log.e(TAG, "URLIMAGEN:" + cdn +"/"+ mFavorite.getRows().get(position).getFolder()+"/" + mFavorite.getRows().get(position).getUrl_cdn_large() ) ;
        String urlImage = cdn +"/"+ mFavorite.getRows().get(position).getFolder()+"/" + mFavorite.getRows().get(position).getUrl_cdn_large() ;
        String title = mFavorite.getRows().get(position).getName()  ;
        String subTitle = mFavorite.getRows().get(position).getDescription() ;
        int idCourse =  mFavorite.getRows().get(position).getIdmed_courses() ;

        Intent intent = new Intent(this, LessonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cdn"     , cdn );
        bundle.putInt("idCourse"   , idCourse);
        bundle.putString("urlImage", urlImage );
        bundle.putString("title"   , title );
        bundle.putString("subTitle", subTitle );
        intent.putExtras(bundle);

        // TRANSITIONS
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            View img_cover     = view.findViewById(R.id.img_cover);
            View txt_title     = view.findViewById(R.id.txt_title);
            View txt_sub_title = view.findViewById(R.id.txt_sub_title);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( this,
                    Pair.create( img_cover,     "element1" ),
                    Pair.create( txt_title,     "element2" ),
                    Pair.create( txt_sub_title, "element3" ));

            startActivity( intent, options.toBundle() );
        } else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClicked(View v, int position) {
        return false;
    }
}
