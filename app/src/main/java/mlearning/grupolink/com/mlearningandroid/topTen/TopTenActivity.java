package mlearning.grupolink.com.mlearningandroid.topTen;

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
import mlearning.grupolink.com.mlearningandroid.entities.ITopTen;
import mlearning.grupolink.com.mlearningandroid.topTen.adapters.TopTenAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopTenActivity extends AppCompatActivity implements TopTenAdapter.ViewHolder.ClickListener {

    private String TAG = "TopTenActivity";
    private String title = "Top ten";
    private ITopTen.TopTen mTopTen;
    private String CountryCode = "";
    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    private RecyclerView recyclerView;
    private String cdn = "";
    private Call<ITopTen.TopTen> call_0;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private TopTenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_ten_activity);


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

        try{
            Utils.sendGoogleAnalyticsTracker("top", "");
        }catch (Exception e){e.printStackTrace();}

        if(Utils.getCountryCode(this ) != null)
            CountryCode = Utils.getCountryCode(this );
        swipeRefreshLayout();
        restartLoading(false);
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
        restartLoadingTopTen("true", Refresh);
    }

    private void restartLoadingTopTen(String valor, final boolean Refresh){
        Log.e(TAG, getString(R.string.base_path)+"getToptenCourses?ranking="+ valor+"&cod_servicio=" + CountryCode );
        ITopTen topTen = MlearningApplicattion.getApplication().getRestAdapter().create(ITopTen.class);
        call_0 = topTen.getTopTen(valor, CountryCode);
        call_0.enqueue(new Callback<ITopTen.TopTen>() {
            @Override
            public void onResponse(Call<ITopTen.TopTen> call, Response<ITopTen.TopTen> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mTopTen = null;
                    mTopTen = response.body();

                    postExecutionValidacion(Refresh);
                } else {
                    showRetry(Refresh);
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ITopTen.TopTen> call, Throwable t) {
                showRetry(Refresh);
                t.printStackTrace();
            }
        });
    }

    public void postExecutionValidacion(boolean Refresh){
        if (mTopTen != null) {
            if (mTopTen.getCode() == 104)
                restartLoadingTopTen("false", Refresh);
            else
               Postexecute(Refresh);

        }else
            showRetry(Refresh);
    }

    private void Postexecute(boolean Refresh) {
        if(mTopTen != null){
            if(mTopTen.getCode() == 0){
                if(mTopTen.getRows() != null){
                    if(mTopTen.getRows().size() > 0){

                        showLayout(Refresh);
                        if(mTopTen.getSummary().getCdn() != null)
                            cdn = mTopTen.getSummary().getCdn();
                        //validacion si se a iniciado el adapter
                        if (adapter != null){
                            //actuliza la data del apdater
                            Log.e(TAG,"adapter != null-->update data adapter");
                            adapter.updateData(mTopTen.getRows() , cdn);

                        }else {//es nulo
                            //crea la lista adapter
                            Log.e(TAG,"adapter  null-->create adapter");
                            adapter = new TopTenAdapter(this,mTopTen.getRows(), cdn , this );
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager( this ));
                        }
                    }else
                        showRetry(Refresh);
                }else
                    showRetry(Refresh);
            }else
                showRetry(Refresh);
        }else {
            showRetry(Refresh);

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

        Log.e(TAG, "URLIMAGEN:" + cdn +"/"+ mTopTen.getRows().get(position).getFolder()+"/" + mTopTen.getRows().get(position).getUrl_cdn_large() ) ;
        String urlImage = cdn +"/"+ mTopTen.getRows().get(position).getFolder()+"/" + mTopTen.getRows().get(position).getUrl_cdn_large() ;
        String title = mTopTen.getRows().get(position).getName()  ;
        String subTitle = mTopTen.getRows().get(position).getDescription() ;
        int idCourse = Integer.parseInt( mTopTen.getRows().get(position).getIdmed_courses() );

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
