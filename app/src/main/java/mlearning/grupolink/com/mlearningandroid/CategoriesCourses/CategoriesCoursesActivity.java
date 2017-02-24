package mlearning.grupolink.com.mlearningandroid.CategoriesCourses;

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

import mlearning.grupolink.com.mlearningandroid.CategoriesCourses.adapters.CategoryCoursesAdapter;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.activities.LessonActivity;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICategoryCourses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesCoursesActivity extends AppCompatActivity implements CategoryCoursesAdapter.ViewHolder.ClickListener{

    private String TAG = "CategoriesCActivity";
    private String CountryCode = "";
    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private String cdn = "";
    private String titleCategory= "";
    private int idCategory = 0 ;
    private Call<ICategoryCourses.CategoryCourses> call_0;
    private ICategoryCourses.CategoryCourses mCursos;
    private CategoryCoursesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_courses);

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
        //obtener datos bunlde
        if ( this.getIntent().getExtras().getInt("idCategory") >  0)
            idCategory = this.getIntent().getExtras().getInt("idCategory");
        if ( this.getIntent().getStringExtra("titleCategory") != null)
            titleCategory = this.getIntent().getStringExtra("titleCategory");
        //Utils.SetStyleToolbarHome2(this, titleCategory);
        Utils.SetStyleToolbarTitle(this, titleCategory);

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

    public void restartLoading(boolean Refresh){
        showLoading(Refresh);
        restartLoadingCoursesByCategory(Refresh);
    }

    private void restartLoadingCoursesByCategory(final boolean Refresh ){
        Log.e(TAG, getString(R.string.base_path)+"getCourses?category="+ idCategory+"&cod_servicio=" + CountryCode + "&limit=" + Config.limit + "&offset=" + Config.offset);

        ICategoryCourses cursos = MlearningApplicattion.getApplication().getRestAdapter().create(ICategoryCourses.class);
        call_0 = cursos.getNewsFromByCategory( "" + idCategory, CountryCode , Config.limit, Config.offset);
        call_0.enqueue(new Callback<ICategoryCourses.CategoryCourses>() {
            @Override
            public void onResponse(Call<ICategoryCourses.CategoryCourses> call, Response<ICategoryCourses.CategoryCourses> response) {
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
            public void onFailure(Call<ICategoryCourses.CategoryCourses> call, Throwable t) {
                showRetry(Refresh);
                t.printStackTrace();
            }
        });
    }
    private void Postexecute(boolean Refresh)
    {
        if(mCursos != null){
            if(mCursos.getCode() == 0){
                if(mCursos.getRows() != null){
                    if(mCursos.getRows().size() > 0) {

                        showLayout(Refresh);
                        if(mCursos.getSummary().getCdn() != null)
                            cdn = mCursos.getSummary().getCdn();
                        //validacion si se a iniciado el adapter
                        if (adapter != null){
                            //actuliza la data del apdater
                            Log.e(TAG,"adapter != null-->update data adapter");
                            adapter.updateData(mCursos.getRows() , cdn);

                        }else {//es nulo
                            //crea la lista adapter
                            Log.e(TAG,"adapter  null-->create adapter");
                            adapter = new CategoryCoursesAdapter(this,mCursos.getRows(), cdn , this );
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager( this ));

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(call_0!=null && !call_0.isCanceled()) {
            call_0.cancel();
        }
    }


    @Override
    public void onItemClicked(View view, int position) {
        Log.e(TAG, "URLIMAGEN:" + cdn +"/"+ mCursos.getRows().get(position).getFolder()+"/" + mCursos.getRows().get(position).getUrl_cdn_large() ) ;
        String urlImage = cdn +"/"+ mCursos.getRows().get(position).getFolder()+"/" + mCursos.getRows().get(position).getUrl_cdn_large() ;
        String title = mCursos.getRows().get(position).getName()  ;
        String subTitle = mCursos.getRows().get(position).getDescription() ;
        int idCourse = mCursos.getRows().get(position).getIdmed_courses() ;

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
