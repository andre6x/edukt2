package mlearning.grupolink.com.mlearningandroid.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.List;

import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.adapter.LessonAdapter;
import mlearning.grupolink.com.mlearningandroid.constants.Config;
import mlearning.grupolink.com.mlearningandroid.entities.ICursos;
import mlearning.grupolink.com.mlearningandroid.entities.ILesson;
import mlearning.grupolink.com.mlearningandroid.entities.IRanking;
import mlearning.grupolink.com.mlearningandroid.entities.rows;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by GrupoLink on 08/04/2015.
 */
public class LessonActivity extends AppCompatActivity  {


    private String TAG = "PANTALLA LESSON";
    private ListView lista;
    private ILesson.Lesson mLesson;
    private ILesson.Lesson mLesson_Free;
    private int idCourse = 0;
    private String cdn = "";
    private String urlImage = "";
    private String title    = "";
    private String subTitle = "";
    private List<rows> datosCurso;
    private List<rowsCategory> Category;
    private boolean bandera = false;
    private Handler handler = new Handler();
    private final int TIME = 5000;
    private String msisdn = "";
    private IRanking.Ranking mRanking;
    private int idRating = 0;
    private int Progress = 0;
    private float idRanking = 0;
    private float newRanking = 0;
    Button enviarRating;
    private ICursos.Cursos mCursos;
    private ICursos.Cursos RankingmCursos;
    //private ICursos.Cursos mCursosR;
    private int ID_CATEGORIA_MY_COURSES = -2;
    private final int ID_CATEGORIA_HOME = -1;
    private List<rows> lstrows;
    private rows row;
    Boolean aux_ind ;
    private int category = -2;

    private String nameCourse = "";
    private int Idmed_courses = 0 ;
    private String startDate = "";
    private String endDate = "";
    private int totalLesson = 0;

    private String courseName = "";
    private int IdlastLesson = 0 ;
    private Boolean RankeoNotnull = false;
    private MixpanelAPI mixpanel;
    String numeroSuscripcion;
    String KEY_NUMERO = "numero";
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    //public MixpanelAPI mixpanel =   MixpanelAPI.getInstance(this, "7d82149085babc7916828833cdbe2f6f");
    private String RankingFirst = "";
    private String nameCourseFirst = "" ;
    private String CountryCode= "";
    public ProgressDialog Dialog;
    private boolean isDestroyed=false;
    private Call<ILesson.Lesson>   call_0;
    private Call<ICursos.Cursos>   call_1;
    private Call<ICursos.Cursos>   call_2;
    private Call<IRanking.Ranking> call_3;
    private Call<ICursos.Cursos>   call_4;
    private Call<ICursos.Cursos>   call_5;
    private int IdmedCategories = 0;
    private  String titleCategory ="";


    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;

    private ViewGroup mRoot;
    private LinearLayout lyt_contenedor ;

    private RecyclerView recyclerView;
    private LessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TRANSITIONS
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                /*Explode trans1 = new Explode();
                trans1.setDuration(3000);
                Fade trans2 = new Fade();
                trans2.setDuration(3000);
                getWindow().setEnterTransition( trans1 );
                getWindow().setReturnTransition( trans2 );*/
            TransitionInflater inflater = TransitionInflater.from( this );
            Transition transition = inflater.inflateTransition( R.transition.transitions );
            getWindow().setSharedElementEnterTransition(transition);
            Transition transition1 = getWindow().getSharedElementEnterTransition();
            transition1.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }
                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        TransitionManager.beginDelayedTransition(mRoot, new Slide());
                    }
                    lyt_contenedor.setVisibility( View.VISIBLE );
                }
                @Override
                public void onTransitionCancel(Transition transition) {
                }
                @Override
                public void onTransitionPause(Transition transition) {
                }
                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_layout);
        //obtener objeto de la actividad anterior
        /*if(savedInstanceState != null){
            row = (rows) savedInstanceState.getSerializable("car");
        }
        else {
            if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("car") != null) {
                row = (rows) getIntent().getExtras().getSerializable("car");
            } else {
                Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }*/
        //obtener datos de la actividad anterior
        if ( this.getIntent().getExtras().getInt("idCourse") > 0)
            idCourse = this.getIntent().getExtras().getInt("idCourse");
        if ( this.getIntent().getStringExtra("cdn") != null)
            cdn = this.getIntent().getStringExtra("cdn");
        if ( this.getIntent().getStringExtra("urlImage") != null)
            urlImage = this.getIntent().getStringExtra("urlImage");
        if ( this.getIntent().getStringExtra("title") != null)
            title = this.getIntent().getStringExtra("title");
        if ( this.getIntent().getStringExtra("subTitle") != null)
            subTitle = this.getIntent().getStringExtra("subTitle");


        mRoot = (ViewGroup) findViewById(R.id.ll_tv_description);
        lyt_contenedor = (LinearLayout) findViewById(R.id.lyt_contenedor);
        ImageView ivCar = (ImageView) findViewById(R.id.iv_car);
        TextView tvModel = (TextView) findViewById(R.id.tv_model);
        TextView tvBrand = (TextView) findViewById(R.id.tv_brand);

        lyt_contenedor.setVisibility( View.VISIBLE );

        Utils.loadImage( urlImage  ,ivCar );
        tvModel.setText(title );
        tvBrand.setText( subTitle );

        //PORCENTAJE DEL CURSO
       /* ((TextView) findViewById(R.id.txt_porcent_)).setText((int) (mCursos.getRows().get(0).getProgress() * 100) + " %");
        Progress = (int) (mCursos.getRows().get(0).getProgress() * 100 );*/

        //INICIO RANKING CURSO
         try{
            ((RatingBar) findViewById(R.id.ratingBar)).setRating(Float.parseFloat( row.getRanking() ));
            idRanking =  Float.parseFloat( row.getRanking() );
        }catch (Exception e)
        {e.printStackTrace();}

        ((RatingBar) findViewById(R.id.ratingBar)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //System.out.println("---> rating:" + rating );
                if (fromUser)
                    generarPopup((int) rating, ratingBar);
            }
        });
        //FIN RANKING CURSO


        crearLesson();
    }

    @Override
    public void onBackPressed() {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            lyt_contenedor.setVisibility( View.INVISIBLE );
            TransitionManager.beginDelayedTransition(mRoot, new Slide());

        }

        super.onBackPressed();
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

    private void showLoadingDialog(){
        Dialog.setMessage("Espere un Momento..");
        Dialog.setCancelable(false);
        Dialog.show();
    }

    private void showLayoutDialog(){
        if (Dialog != null)
            Dialog.dismiss();
    }

    public void crearLesson(){
        try{
            Utils.sendGoogleAnalyticsTracker("listalecciones", "");
        }catch (Exception e){e.printStackTrace();}

        Dialog = new ProgressDialog(LessonActivity.this);

        linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
        progress = (ProgressBar) findViewById(R.id.progress);
        retry = (Button) findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoading(0);
            }
        });

        if(Utils.getCountryCode(LessonActivity.this ) != null)
            CountryCode = Utils.getCountryCode(LessonActivity.this);


        if (Utils.getMsisdn(LessonActivity.this) != null)
            msisdn = Utils.getMsisdn(LessonActivity.this);


        restartLoading(0);

    }


    private void restartLoading(int index) {
        showLoading();
        switch (index) {
            case 0:
                restartLoadingLesson();
                break;
           /* case 2:
                restartLoadingCurso();
                break;
            case 3:
                restartLoadingCursoNotMsisdn();
                break;
            case 22:
                restartLoadingRanking();

                break;*/

        }
    }

    private void restartLoadingLesson() {
        Log.e(TAG,  getString(R.string.base_path) +"getLesson?msisdn=" + msisdn + "&cod_servicio=" + CountryCode + "&course=" + idCourse + "&limit=" + Config.limit + "&offset=" + Config.offset);
        ILesson lesson = MlearningApplicattion.getApplication().getRestAdapter().create(ILesson.class);
        call_0 = lesson.getLessonFrom(msisdn, CountryCode, "" + idCourse, Config.limit, Config.offset);
        call_0.enqueue(new Callback<ILesson.Lesson>() {
            @Override
            public void onResponse(Call<ILesson.Lesson> call, Response<ILesson.Lesson> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mLesson = null;
                    mLesson = response.body();
                    mLesson_Free = null;
                    mLesson_Free = response.body();

                    try {
                        if (mLesson != null)
                            if (mLesson.getSummary().getCdn() != null)
                                mLesson.setCdn(mLesson.getSummary().getCdn());
                        mLesson.setIdCourse(idCourse);
                        if (mLesson.getRows() != null)
                            if (mLesson.getRows().size() > 0)
                                mLesson.setNum_rows(mLesson.getRows().size());

                        Utils.DeleteLessonById(MlearningApplicattion.getApplication().getLessonDao(), idCourse);
                        Utils.DbsaveLesson(mLesson, MlearningApplicattion.getApplication().getLessonDao());
                        if (mLesson.getRows() != null)
                            if (mLesson.getRows().size() > 0) {
                                Utils.DeleterowsLessonById(MlearningApplicattion.getApplication().getRowsLessonDao(), idCourse);
                                for (rowsLesson row : mLesson.getRows()) {
                                    if (!(MlearningApplicattion.getApplication().getRowsLessonDao().queryBuilder().where().eq("idmed_lesson", row.getIdmed_lesson()).query().size() > 0))
                                        Utils.DbsaveRowsLesson(row, MlearningApplicattion.getApplication().getRowsLessonDao());
                                }
                            }

                        if (Utils.GetLessonFromDatabase(MlearningApplicattion.getApplication().getLessonDao(), idCourse).size() > 0) {
                            mLesson = Utils.GetLessonFromDatabase(MlearningApplicattion.getApplication().getLessonDao(), MlearningApplicattion.getApplication().getRowsLessonDao(), idCourse).get(0);

                            IdlastLesson = Utils.GetlastLessons(idCourse);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    postExecutionLesson();
                } else {
                    showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ILesson.Lesson> call, Throwable t) {
                if (!isDestroyed)
                    showRetry();
                showRetry();
                t.printStackTrace();
            }
        });
    }


    public void postExecutionLesson() {

        if (mLesson_Free != null){
            if (mLesson_Free.getCode() == 119) {
                Utils.generarAlerta119(this, this.getResources().getString(R.string.text_title_suscriber), mLesson_Free.getMessage_alert());
                showRetry();
            } else if (mLesson_Free.getCode() == 120) {
                Utils.generarAlertaNoSuscrito(this, this.getResources().getString(R.string.text_title_suscriber), mLesson_Free.getMessage_alert(),msisdn, CountryCode , false);
                showRetry();
            }else if (mLesson_Free.getCode() == 121) {
                validationFreemium();
            }
            else {
                postExecutionLesson_2();
            }
        }else
            showRetry();



    }
    public void postExecutionLesson_2() {
        if (mLesson != null){
            //restartLoading(2);
            Postexecute();
        }else
            showRetry();
    }

    private void validationFreemium(){
        try{
            Log.e(TAG,mLesson_Free.getDescription()+"--mLessonById.getStatus()="+mLesson_Free.getStatus());
            if ( mLesson_Free.getStatus() == 3 ) {
                if(mLesson_Free.getCant_cursos_free_usados() >= mLesson_Free.getCant_cursos_free() ){
                    Log.e(TAG, "FREE USADOS=" + mLesson_Free.getCant_cursos_free_usados() + "--FREE=" + mLesson_Free.getCant_cursos_free());
                    int sizecourseFree = mLesson_Free.getFree_taken_courses_IDs().size() ;
                    boolean isCourseFree = false;
                    for ( int i=0 ; i < sizecourseFree ; i++){
                        if (idCourse  == mLesson_Free.getFree_taken_courses_IDs().get(i))
                            isCourseFree= true;
                    }
                    if( isCourseFree){
                        postExecutionLesson_2();
                    }else{
                        showRetry();
                        Utils.generarAlertaNoSuscrito(this, getString(R.string.text_title_suscriber), mLesson_Free.getDescription(), msisdn, CountryCode, true);
                    }
                }else
                    postExecutionLesson_2();
            }else
                postExecutionLesson_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Postexecute()
    {
        if(mLesson != null){
            if(mLesson.getCode() == 0){
                if(mLesson.getRows() != null){
                    if(mLesson.getNum_rows() > 0){


                            if(!mLesson.getRows().isEmpty()){
                                Log.e(TAG, "VINIERON DATOS ID LESSON: "+mLesson.getRows().get(0).getIdmed_courses()+" DIMENSION: "+mLesson.getRows().size());



                                if (adapter == null) {
                                    adapter = new LessonAdapter(this ,  mLesson.getRows() , mLesson.getCdn() );
                                }
                                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                                //recyclerView.setLayoutManager(new LinearLayoutManager( this ));
                                LinearLayoutManager llm = new LinearLayoutManager(this);
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(llm);



                            }

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


    private void generarPopup(int rating , View view)
    {

    }






}
