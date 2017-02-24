package mlearning.grupolink.com.mlearningandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import mlearning.grupolink.com.mlearningandroid.Home.activities.MainActivity;
import mlearning.grupolink.com.mlearningandroid.MlearningApplicattion;
import mlearning.grupolink.com.mlearningandroid.R;
import mlearning.grupolink.com.mlearningandroid.Utils.Utils;
import mlearning.grupolink.com.mlearningandroid.entities.ILessonById;
import mlearning.grupolink.com.mlearningandroid.entities.ITraking;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonImages;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonVideos;
import mlearning.grupolink.com.mlearningandroid.viewpagercards.CardFragmentPagerAdapter;
import mlearning.grupolink.com.mlearningandroid.viewpagercards.ShadowTransformer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GrupoLink on 08/04/2015.
 */
public class DetalleLessonActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>{

    private String TAG = "DetalleLessonActivity";
    private int idLeccion;
    private String cdn = "";
    private List<rowsLesson> datosLeccion;
    //private List<rows> datosCurso;
    private ILessonById.LessonById mLessonById;
    private String title = "";
    private LinearLayout linear_container;
    private String contenidoLeccion;
    private List<String> pagineo = null;
    private ViewPager viewPager;
    //private ScreenSlidePagerAdapter pagerAdapet;
    //private LinearLayout linear_indicator;
    private ITraking.Traking mTraking;
    private final int ID_DEFAULT = 387;
    public static Button button_next_lesson;
    public static Button button_view_diploma;
    private String msisdn = "";
    private int Progress = 0;
    private String courseName = "";

    private String nameCourse = "";
    private int Idmed_courses = 0 ;
    private String startDate = "";
    private String endDate = "";
    private int totalLesson = 0;
    private int IdlastLesson = 0;
    private Serializable rowsLesson;
    private int idCourse =  0;
    String numeroSuscripcion;
    String KEY_NUMERO = "numero";
    private static String PREFERENCIA_INICIAL = "codigo_inicial";
    private String CountryCode ="";

    //private WebFragment WebFrag;

    private boolean isDestroyed = false;
    private Call<ILessonById.LessonById> call_0;
    private Call<ITraking.Traking> call_1;
    private Call<ITraking.Traking> call_2;
    private String urlGlobal ;

    //INCLUDE
    private LinearLayout linear_loading;
    private ProgressBar progress;
    private Button retry;
    public static DetalleLessonActivity DetLesA;
    public DetalleLessonActivity() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_lesson_layout);

        contenidoLeccion = "";

        Bundle extras = getIntent().getExtras();
        DetLesA = this;

        if ( this.getIntent().getExtras().getInt("idLeccion") > 0 ) {
            idLeccion = this.getIntent().getExtras().getInt("idLeccion");
        }
        if ( this.getIntent().getExtras().getInt("idCourse") > 0 ) {
            idCourse = this.getIntent().getExtras().getInt("idCourse");
        }





        if (extras != null) {
            idLeccion = extras.getInt("idLeccion");
            cdn = extras.getString("cdn");
            Progress = extras.getInt("Progress");
            courseName = extras.getString("courseName");
            idCourse  = extras.getInt("idCourse");

            nameCourse = extras.getString("nameCourse");
            Idmed_courses = extras.getInt("Idmed_courses");
            startDate = extras.getString("startDate");
            endDate = extras.getString("endDate");
            totalLesson = extras.getInt("totalLesson");

            IdlastLesson = extras.getInt("IdlastLesson");

            //Log.e(TAG, "--variables detalle-leson dilopma nameCourse: "+idCourse +"--"+ nameCourse + " Idmed_courses: " + Idmed_courses + " startDate: " + startDate + " endDate: " + endDate + " totalLesson: " + totalLesson);
            /*rowsLesson = getIntent().getSerializableExtra("rowsLesson");*/
            /*rows = extras.getString("endDate");*/

        }


        //linear_indicator = (LinearLayout) findViewById(R.id.linear_indicator);
        linear_container = (LinearLayout) findViewById(R.id.linear_container);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
        progress = (ProgressBar) findViewById(R.id.progress);
        retry = (Button) findViewById(R.id.retry);

        if(Utils.getCountryCode(this) != null)
            CountryCode = Utils.getCountryCode(this);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartLoading(0);
            }
        });

        restartLoading(0);
        Utils.SetStyleToolbarTitle2(this, title);
        try{
            Utils.sendGoogleAnalyticsTracker("detalleleleccion", "");
        }catch (Exception e){ e.printStackTrace(); }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e("a", "---> ingreso a onresume deTALLLE LESSON SIGUIENTE LECCION");
        if(MainActivity.isQuizAprobate) {
            MainActivity.isQuizAprobate = false;

            if(MainActivity.isQuizAprobateViewDiploma_2){
                MainActivity.isQuizAprobateViewDiploma_2 = false;
                restartLoading(0);
            }else
              nextLesson();
        }
        if(MainActivity.isQuizAprobateViewDiploma){
            MainActivity.isQuizAprobateViewDiploma = false;
            restartLoading(0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void Postexecute()
    {
        try{

        if(datosLeccion != null){
            if(!datosLeccion.isEmpty()){
                 if(datosLeccion.size() > 0){

                 if(datosLeccion != null)
                     if(datosLeccion.size() > 0)
                         if(datosLeccion.get(0).getDescription() != null)
                             title = datosLeccion.get(0).getDescription();
                     if (CountryCode.equals("593"))
                       Utils.SetStyleToolbarTitleShare(this, title, getString(R.string.ingresa_link_parrafo) + " " + courseName + " " + getString(R.string.ingresa_link_edukt));
                     else
                       Utils.SetStyleToolbarTitleShare(this,title, getString(R.string.ingresa_link_parrafo) +" "+ courseName +" " + getString(R.string.ingresa_link_edukt_argentina));

                     //Log.e("TAG", "ID_LESSON " + idLeccion);

                     Utils.MySystemUtils(this);

                    if(mLessonById != null)
                     if(mLessonById.getUrl_data() != null) {
                         //Log.e("TAG", "**---** getUrl_data:" + mLessonById.getUrl_data());
                         if (mLessonById.getImages() != null) {
                             if (mLessonById.getVideos() != null) {
                                 //Log.e("TAG", "**---** 1 " + mLessonById.getImages()+ mLessonById.getVideos());
                                 pagineo = Utils.PaginarString("" + mLessonById.getCdn(), mLessonById.getUrl_data(), mLessonById.getImages(), mLessonById.getVideos());
                             }
                             else {
                                 //Log.e("TAG", "**---** 2 " + mLessonById.getImages() );
                                 pagineo  = Utils.PaginarString("" + mLessonById.getCdn(), mLessonById.getUrl_data(), mLessonById.getImages(), null);
                                 }
                         }else{
                             if (mLessonById.getVideos() != null)  {
                                 //Log.e("TAG", "**---** 3 " + mLessonById.getVideos());
                                 pagineo = Utils.PaginarString("" + mLessonById.getCdn(), mLessonById.getUrl_data(), null, mLessonById.getVideos());
                             }else
                                 pagineo = Utils.PaginarString("" + mLessonById.getCdn(), mLessonById.getUrl_data(), null, null);
                         }

                     }//else
                         //Log.e("TAG", "ESTA NULO LOS DATOS DE PAGIONEO");

                     if(pagineo != null) {


                         CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                                                                                             dpToPixels(2, this),
                                                                                             DetalleLessonActivity.this,
                                                                                             pagineo ,
                                                                                             datosLeccion.get(0).getHas_quiz_mandatory() ,
                                                                                             datosLeccion.get(0).getCurrent_status(),
                                                                                             idLeccion ,
                                                                                             title,
                                                                                             Progress,
                                                                                             IdlastLesson,
                                                                                             idCourse );
                         ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
                         fragmentCardShadowTransformer.enableScaling(true);

                         viewPager.setAdapter(pagerAdapter);
                         viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
                         viewPager.setOffscreenPageLimit(3);


                         /*pagerAdapet = new ScreenSlidePagerAdapter(DetalleLessonActivity.this,getSupportFragmentManager() ,
                                                                   pagineo ,
                                                                   datosLeccion.get(0).getHas_quiz_mandatory() ,
                                                                   datosLeccion.get(0).getCurrent_status(),
                                                                   idLeccion ,
                                                                   title,
                                                                   Progress,
                                                                   IdlastLesson,
                                                                   idCourse);
                         pager.setAdapter(pagerAdapet);
                         pager.setOffscreenPageLimit( pagineo.size());*/

                        if(button_next_lesson != null)
                             button_next_lesson.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     restartLoading(1);
                                 }
                             });

                        /* if(button_view_diploma != null)
                             button_view_diploma.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     Intent actividad = new Intent(DetalleLessonActivity.this, DiplomaActivity.class);
                                     actividad.putExtra("nameCourse", nameCourse);
                                     actividad.putExtra("Idmed_courses", Idmed_courses);
                                     actividad.putExtra("startDate", startDate);
                                     actividad.putExtra("endDate", endDate);
                                     actividad.putExtra("totalLesson", totalLesson);
                                     startActivity(actividad);

                                 }
                             });*/


                         /*pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                             @Override
                             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                             }

                             @Override
                             public void onPageSelected(int position) {
                                 for(int x = 0; x < pagineo.size() ; x++){
                                     if(x == position) {
                                         findViewById(ID_DEFAULT + x).setBackgroundResource(R.drawable.custom_indicator_select);

                                         if (WebFragmentVideo.webChromeClient != null)
                                             if (WebFragmentVideo.webView != null){ //pausar video cuando se cambia de pagina
                                                 WebFragmentVideo.webView.onPause();
                                                 WebFragmentVideo.webView.onResume();
                                             }

                                         if (WebFragment.webView != null){ //pausar video cuando se cambia de pagina
                                             WebFragment.webView.loadUrl("javascript:playPause()");
                                         }

                                     }
                                     else
                                         findViewById(ID_DEFAULT+x).setBackgroundResource(R.drawable.custom_indicator_no_select);
                                 }
                             }

                             @Override
                             public void onPageScrollStateChanged(int state) {

                             }
                         });*/
                     }

                     showLayout();

                }else
                    showRetry();
            }else
                showRetry();
        }else
            showRetry();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void goDiploma(){
        MainActivity.isQuizAprobateViewDiploma = true ;

        /*Intent actividad = new Intent(DetalleLessonActivity.this, DiplomaActivity.class);
        actividad.putExtra("nameCourse", nameCourse);
        actividad.putExtra("Idmed_courses", Idmed_courses);
        actividad.putExtra("startDate", startDate);
        actividad.putExtra("endDate", endDate);
        actividad.putExtra("totalLesson", totalLesson);
        startActivity(actividad);*/
    }

    public void ReLoadVideoHD(){
        Intent refresh = new Intent(DetalleLessonActivity.this, DetalleLessonActivity.class);
        refresh.putExtra("idLeccion", idLeccion);
        refresh.putExtra("cdn", cdn);
        refresh.putExtra("Progress", Progress);
        refresh.putExtra("courseName", courseName);
        refresh.putExtra("idCourse", idCourse);
        refresh.putExtra("nameCourse", nameCourse);
        refresh.putExtra("Idmed_courses", Idmed_courses);
        refresh.putExtra("startDate", startDate);
        refresh.putExtra("endDate", endDate);
        refresh.putExtra("totalLesson", totalLesson);
        refresh.putExtra("IdlastLesson", IdlastLesson);
        this.startActivity(refresh);
        this.finish();


    }


    private void showLayout()
    {
        linear_container.setVisibility(View.VISIBLE);
        linear_loading.setVisibility(View.GONE);
    }

    private void showLoading()
    {
        linear_container.setVisibility(View.GONE);
        linear_loading.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
    }

    private void showRetry()
    {
        linear_container.setVisibility(View.GONE);
        linear_loading.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
    }


    public void nextLesson(){
        try {
            Log.e("a","---> DETALLE LESSON ID_LECCION:"+idLeccion);
            int aux = idLeccion;
              idLeccion = Utils.GetNextLesson(idCourse, idLeccion);
            if(idLeccion >0 && aux != idLeccion) {
                Log.e("a","---> DETALLE LESSON 2 ID_LECCION:"+idLeccion);
                restartLoading(0);
            }
            else
                finish();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void restartLoading(int id) {
        showLoading();
        try{
           if(Utils.getMsisdn(DetalleLessonActivity.this) != null)
                msisdn = Utils.getMsisdn(DetalleLessonActivity.this);
            datosLeccion = null;
            datosLeccion = Utils.GetRowsLessonFromDatabaseById(MlearningApplicattion.getApplication().getRowsLessonDao(), idLeccion);
            if (Utils.GetLessonByIdFromDatabase(MlearningApplicattion.getApplication().getLessonByIdDao(), idLeccion).size() > 0) {
                mLessonById = Utils.GetLessonByIdFromDatabase(MlearningApplicattion.getApplication().getLessonByIdDao(), MlearningApplicattion.getApplication().getRowsLessonImagesDao(), idLeccion).get(0);
                //Log.e("TAG","IMPRIMEINMDO STRING URL SAVE: "+mLessonById.getUrl_data());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch(id){
            case 0:
                restartLoadingLesson();
                break;
            case 1:
                restartLoadingEnviarTracking_1();
                break;
        }
    }


    private void restartLoadingLesson( ){
        Log.e(TAG, getString(R.string.base_path) + "getLesson?msisdn="+msisdn +"&lesson="+idLeccion + "&cod_servicio="+ CountryCode );
        ILessonById lesson = MlearningApplicattion.getApplication().getRestAdapter().create(ILessonById.class);
        call_0 = lesson.getLessonByIdFrom(msisdn, "" + idLeccion, CountryCode);
        call_0.enqueue(new Callback<ILessonById.LessonById>() {
            @Override
            public void onResponse(Call<ILessonById.LessonById> call, Response<ILessonById.LessonById> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mLessonById = null;
                    mLessonById = response.body();

                    try {

                        if (mLessonById != null) {

                            if (mLessonById.getSummary().getCdn() != null)
                                mLessonById.setCdn(mLessonById.getSummary().getCdn());
                            mLessonById.setIdmed_lesson(idLeccion);
                            if (mLessonById.getRows() != null)
                                if (mLessonById.getRows().size() > 0)
                                    mLessonById.setNum_rows(mLessonById.getRows().size());

                            Utils.DeleteLessonIDById(MlearningApplicattion.getApplication().getLessonByIdDao(), idLeccion);
                            Utils.DbsaveLessonById(mLessonById, MlearningApplicattion.getApplication().getLessonByIdDao());
                            if (mLessonById.getImages() != null)
                                if (mLessonById.getImages().size() > 0) {
                                    //System.out.println("**---** imagen"+mLessonById.getImages() );
                                    Utils.DeleterowsLessonImagenesById(MlearningApplicattion.getApplication().getRowsLessonImagesDao(), idLeccion);
                                    for (rowsLessonImages row : mLessonById.getImages()) {
                                        row.setIdmed_lesson(idLeccion);
                                        Utils.DbsaveRowsLessonImagenes(row, MlearningApplicattion.getApplication().getRowsLessonImagesDao());
                                    }
                                }

                            if (mLessonById.getVideos() != null)
                                if (mLessonById.getVideos().size() > 0) {
                                    //System.out.println("**---** video"+mLessonById.getVideos() );
                                    Utils.DeleterowsLessonVideosById(MlearningApplicattion.getApplication().getRowsLessonVideosDao(), idLeccion);
                                    for (rowsLessonVideos row : mLessonById.getVideos()) {
                                        row.setIdmed_lesson(idLeccion);
                                        Utils.DbsaveRowsLessonVideos(row, MlearningApplicattion.getApplication().getRowsLessonVideosDao());
                                    }
                                }

                            /* if (Utils.GetLessonByIdFromDatabase(MlearningApplicattion.getApplication().getLessonByIdDao(), idLeccion).size() > 0)
                                 mLessonById = Utils.GetLessonByIdFromDatabase(MlearningApplicattion.getApplication().getLessonByIdDao(), MlearningApplicattion.getApplication().getRowsLessonImagesDao(), idLeccion).get(0);*/
                        }
                        if (datosLeccion != null) {
                            if (datosLeccion.size() > 0) {
                                //Log.e("TAG", "**----**"+datosLeccion.size() );
                                String url = "";
                                if (mLessonById.getCdn() != null)
                                    url = url + "" + mLessonById.getCdn();

                                System.out.println("**---**url1: " + url);
                                if (datosLeccion.get(0).getFolder() != null) {
                                    url = url + "/" + datosLeccion.get(0).getFolder();
                                    System.out.println("**---**url2: " + url);
                                }
                                if (datosLeccion.get(0).getFile() != null) {
                                    url = url + "/" + datosLeccion.get(0).getFile();
                                    System.out.println("**---**url3: " + url);
                                }
                                System.out.println("**---**url4: " + url);
                                urlGlobal = url;

                                postExecutionWebPage();

                            }
                        }
                        if (datosLeccion != null)
                            if (datosLeccion.size() > 0) {
                                //Log.e("-->>","Current Status: "+datosLeccion.get(0).getCurrent_status());
                                if (datosLeccion.get(0).getCurrent_status() == -1) {

                                    restartLoadingEnviarTracking_0();

                                }
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showRetry();
                    }


                } else {
                    showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ILessonById.LessonById> call, Throwable t) {
                if (!isDestroyed)
                    showRetry();
                showRetry();
                t.printStackTrace();
            }
        });
    }

    private void restartLoadingEnviarTracking_0( ){
        ITraking traking = MlearningApplicattion.getApplication().getRestAdapter().create(ITraking.class);
        call_1 = traking.getTraking(msisdn, CountryCode, "" + idLeccion, Utils.CHANNEL, "0");
        call_1.enqueue(new Callback<ITraking.Traking>() {
            @Override
            public void onResponse(Call<ITraking.Traking> call, Response<ITraking.Traking> response) {
                if (response.isSuccess()) {
                    Log.e(TAG, "Respuesta exitosa");
                    mTraking = null;
                    mTraking = response.body();

                    try {

                        if(mTraking != null) {
                            // Log.e("TRAKING", "Code: " + mTraking.getCode() + " Description: " + mTraking.getDescription());
                            if (mTraking.getCode() == 1)
                                Utils.UpdateRowsLessonFromDatabaseById(MlearningApplicattion.getApplication().getRowsLessonDao(), idLeccion, 0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //showRetry();
                    Log.e(TAG, "Error en la petición");
                }
            }

            @Override
            public void onFailure(Call<ITraking.Traking> call, Throwable t) {
                /*if (!isDestroyed)
                    showRetry();
                showRetry();*/
                t.printStackTrace();
            }
        });
    }

    private void restartLoadingEnviarTracking_1( ){

        if (datosLeccion != null)
            if (datosLeccion.size() > 0) {
                if(datosLeccion.get(0).getCurrent_status() == 0){

                    ITraking traking = MlearningApplicattion.getApplication().getRestAdapter().create(ITraking.class);
                    call_2 = traking.getTraking(msisdn,CountryCode,""+idLeccion, Utils.CHANNEL ,"1");
                    call_2.enqueue(new Callback<ITraking.Traking>() {
                        @Override
                        public void onResponse(Call<ITraking.Traking> call, Response<ITraking.Traking> response) {
                            if (response.isSuccess()) {
                                Log.e(TAG, "Respuesta exitosa");
                                mTraking = null;
                                mTraking = response.body();
                                try {

                                    if(mTraking != null)
                                        if(mTraking.getCode() == 1)
                                            Utils.UpdateRowsLessonFromDatabaseById(MlearningApplicattion.getApplication().getRowsLessonDao(), idLeccion,1);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                postExecutionEnviarTracking();

                            } else {
                                showRetry();
                                Log.e(TAG, "Error en la petición");
                            }
                        }

                        @Override
                        public void onFailure(Call<ITraking.Traking> call, Throwable t) {
                            if (!isDestroyed)
                                showRetry();
                            showRetry();
                            t.printStackTrace();
                        }
                    });

                }
            }
    }



    public void postExecutionLesson(){
        if (mLessonById != null) {
            if (mLessonById.getCode() == 119) {
                Utils.generarAlerta119(this, this.getResources().getString(R.string.text_title_suscriber), mLessonById.getMessage_alert());
                showRetry();
            } else if (mLessonById.getCode() == 120) {
                Utils.generarAlertaNoSuscrito(this, this.getResources().getString(R.string.text_title_suscriber), mLessonById.getMessage_alert(),msisdn, CountryCode , false);
                showRetry();
            } else if (mLessonById.getCode() == 121) {
                validationFreemium();
            }else{
                Postexecute();
            }
        }else
            showRetry();
    }

    private void validationFreemium(){
        try {
            Log.e(TAG, mLessonById.getDescription() + "--mLessonById.getStatus()=" + mLessonById.getStatus());
            if (mLessonById.getStatus() == 3) {
                if (mLessonById.getCant_cursos_free_usados() >= mLessonById.getCant_cursos_free()) {
                    Log.e(TAG, "FREE USADOS=" + mLessonById.getCant_cursos_free_usados() + "--FREE=" + mLessonById.getCant_cursos_free());
                    int sizecourseFree = mLessonById.getFree_taken_courses_IDs().size();
                    boolean isCourseFree = false;
                    for (int i = 0; i < sizecourseFree; i++) {
                        if (idCourse == mLessonById.getFree_taken_courses_IDs().get(i))
                            isCourseFree = true;
                    }
                    if (isCourseFree) {
                        Postexecute();
                    } else {
                        showRetry();
                        Utils.generarAlertaNoSuscrito(this, getString(R.string.text_title_suscriber), mLessonById.getDescription(), msisdn, CountryCode, true);
                    }
                } else
                    Postexecute();
            } else
                Postexecute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postExecutionEnviarTracking (){
        finish();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call_0 != null && !call_0.isCanceled()) {
            call_0.cancel();
        }
        if (call_1 != null && !call_1.isCanceled()) {
            call_1.cancel();
        }
        if (call_2 != null && !call_2.isCanceled()) {
            call_2.cancel();
        }
        getSupportLoaderManager().destroyLoader(0);
    }


    @Override
    public void onBackPressed() {

       /*if (WebFragmentVideo.webChromeClient != null  &&  (WebFragmentVideo.webView != null) ) {
           if (!WebFragmentVideo.webChromeClient.onBackPressed()) {
               if (WebFragmentVideo.webView.canGoBack()) {
                   WebFragmentVideo.webView.goBack();
               } else {   // Standard back button implementation (for example this could close the app)
                   super.onBackPressed();
                   finish();
               }
           }
       }else{
           super.onBackPressed();
           finish();
       }*/

        finish();
    }


    public void postExecutionWebPage(){
        getSupportLoaderManager().destroyLoader(0);
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(DetalleLessonActivity.this) {
            @Override
            public Void loadInBackground() {
                try {
                    contenidoLeccion = Utils.getCadenaFromUrl(urlGlobal);
                    if (contenidoLeccion != null) {
                        //System.out.println("**---**contenidoLeccion: "+contenidoLeccion);
                        mLessonById.setUrl_data(contenidoLeccion);
                        Utils.UpdateRowsLessonURLFromDatabaseById(MlearningApplicattion.getApplication().getLessonByIdDao(), idLeccion, contenidoLeccion);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        loader.forceLoad();
        return loader;

    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        postExecutionLesson();
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }


    public void finishDetalleLesson(){
        finish();
    }

    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
