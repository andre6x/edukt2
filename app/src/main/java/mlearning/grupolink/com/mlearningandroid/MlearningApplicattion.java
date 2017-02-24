package mlearning.grupolink.com.mlearningandroid;

import android.app.Application;

//import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.sql.SQLException;

//import io.fabric.sdk.android.Fabric;
import mlearning.grupolink.com.mlearningandroid.database.Database;
import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.ICursos;
import mlearning.grupolink.com.mlearningandroid.entities.IHomeCursos;
import mlearning.grupolink.com.mlearningandroid.entities.ILesson;
import mlearning.grupolink.com.mlearningandroid.entities.ILessonById;
import mlearning.grupolink.com.mlearningandroid.entities.rows;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;
import mlearning.grupolink.com.mlearningandroid.entities.rowsHome;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonImages;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonVideos;
import mlearning.grupolink.com.mlearningandroid.entities.rowsTopTen;
import mlearning.grupolink.com.mlearningandroid.entities.summaryCategories;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


public class MlearningApplicattion extends Application {

    private static MlearningApplicattion instance;
    private Retrofit mRestAdapter;
    private Retrofit mRestDiplomaAdapter;
    private Retrofit mRestPushAdapter;
    private Database databaseHelper = null;
    private static MlearningApplicattion mInstance;

    //Home
    private Dao<IHomeCursos.HomeCursos, Integer> homeCursos = null;
    private Dao<rowsHome, Integer> rowsHome = null;

    //Home
    private Dao<ICursos.Cursos, Integer> cursos = null;
    private Dao<rows, Integer> rows = null;

    //Lesson (Detalle Curso)
    private Dao<ILesson.Lesson, Integer> lesson = null;
    private Dao<rowsLesson, Integer> rowsLesson = null;


    //LessonByID (Detalle Curso)
    private Dao<ILessonById.LessonById, Integer> lessonByIds = null;
    private Dao<rowsLessonImages, Integer> rowsLessonImages = null;

    private Dao<rowsLessonVideos, Integer> rowsLessonVideos = null;

    //Categories
    private Dao<ICategories.Category, Integer> categories = null;
    private Dao<rowsCategory, Integer> rowsCategory = null;


    //TopTen
    private Dao<rowsTopTen, Integer> topTen = null;
    private Dao<summaryCategories, Integer> summaryTopTen = null;

    //Countries
    private Dao<ICountries.Countries, Integer> countries = null;
    private Dao<rowsCountries, Integer> rowsCountries = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);


        //Fabric.with(this, new Crashlytics());

         //inicio Google Analytics
        mInstance = this;
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        //fin Google Analytics


        mRestAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_path))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestDiplomaAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_path_diploma))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestPushAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_path_push_notification))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageOnFail(R.mipmap.fondo_error)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(config);
        databaseHelper = new Database(this);
        instance=this;
    }

    /**
     * Rest Adapter
     * @return
     */
    public Retrofit getRestAdapter() {
        return this.mRestAdapter;
    }

    public Retrofit getmRestDiplomaAdapter() {
        return this.mRestDiplomaAdapter;
    }

    public Retrofit getmRestPushAdapter() {
        return this.mRestPushAdapter;
    }


    /**
     * IHomeCursos Dao
     * @return
     * @throws SQLException
     */
    public Dao<IHomeCursos.HomeCursos, Integer> getHomeCursosDao() throws SQLException {
        if (homeCursos == null) {
            homeCursos = databaseHelper.getDao(IHomeCursos.HomeCursos.class);
        }
        return homeCursos;
    }

    /**
     * rowsHome Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsHome, Integer> getRowsHomeDao() throws SQLException {
        if (rowsHome == null) {
            rowsHome = databaseHelper.getDao(rowsHome.class);
        }
        return rowsHome;
    }

    /**
     * Cursos Dao
     * @return
     * @throws SQLException
     */
    public Dao<ICursos.Cursos, Integer> getCursosDao() throws SQLException {
        if (cursos == null) {
            cursos = databaseHelper.getDao(ICursos.Cursos.class);
        }
        return cursos;
    }

    /**
     * rows Dao
     * @return
     * @throws SQLException
     */
    public Dao<rows, Integer> getRowsDao() throws SQLException {
        if (rows == null) {
            rows = databaseHelper.getDao(rows.class);
        }
        return rows;
    }

    public  void CleanCursos() throws SQLException {
        databaseHelper.CleanCursos();
    }

    public  void CleanRows() throws SQLException {
        databaseHelper.CleanRows();
    }

    /**
     * Lesson Dao
     * @return
     * @throws SQLException
     */
    public Dao<ILesson.Lesson, Integer> getLessonDao() throws SQLException {
        if (lesson == null) {
            lesson = databaseHelper.getDao(ILesson.Lesson.class);
        }
        return lesson;
    }


    /**
     * LessonById Dao
     * @return
     * @throws SQLException
     */
    public Dao<ILessonById.LessonById, Integer> getLessonByIdDao() throws SQLException {
        if (lessonByIds == null) {
            lessonByIds = databaseHelper.getDao(ILessonById.LessonById.class);
        }
        return lessonByIds;
    }

    /**
     * rowsLesson Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsLesson, Integer> getRowsLessonDao() throws SQLException {
        if (rowsLesson == null) {
            rowsLesson = databaseHelper.getDao(rowsLesson.class);
        }
        return rowsLesson;
    }


    /**
     * rowsLessonImages Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsLessonImages, Integer> getRowsLessonImagesDao() throws SQLException {
        if (rowsLessonImages == null) {
            rowsLessonImages = databaseHelper.getDao(rowsLessonImages.class);
        }
        return rowsLessonImages;
    }

    /**
     * rowsLessonVideos Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsLessonVideos, Integer> getRowsLessonVideosDao() throws SQLException {
        if (rowsLessonVideos == null) {
            rowsLessonVideos = databaseHelper.getDao(rowsLessonVideos.class);
        }
        return rowsLessonVideos;
    }


    public  void CleanLesson() throws SQLException {
        databaseHelper.CleanLesson();
    }

    public  void CleanRowsLesson() throws SQLException {
        databaseHelper.CleanRowsLesson();
    }

    public  void CleanLessonById() throws SQLException {
        databaseHelper.CleanLessonById();
    }

    public  void CleanRowsLessonImages() throws SQLException {
        databaseHelper.CleanRowsLessonImages();
    }

    public  void CleanRowsLessonVideos() throws SQLException {
        databaseHelper.CleanRowsLessonVideos();
    }

    /**
     * Categories Dao
     * @return
     * @throws SQLException
     */
    public Dao<ICategories.Category, Integer> getCategoriesDao() throws SQLException {
        if (categories == null) {
            categories = databaseHelper.getDao(ICategories.Category.class);
        }
        return categories;
    }

    /**
     * rowsCategory Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsCategory, Integer> getRowsCategoryDao() throws SQLException {
        if (rowsCategory == null) {
            rowsCategory = databaseHelper.getDao(rowsCategory.class);
        }
        return rowsCategory;
    }


    /**
     * Datos para la seccion TopTen
     * @return
     * @throws SQLException
     */
    public Dao<rowsTopTen, Integer> getTopTenDao() throws SQLException {
        if (topTen == null){
            topTen = databaseHelper.getDao(rowsTopTen.class);
        }
        return topTen;
    }

    /**
     * Obtener la url para crear la imagen de la seccion de TopTen
     * @return
     * @throws SQLException
     */
    public Dao<summaryCategories, Integer> getSummaryTopTenDao() throws SQLException {
        if(summaryTopTen == null){
            summaryTopTen = databaseHelper.getDao(summaryCategories.class);
        }
        return summaryTopTen;
    }

    public  void CleanCategories() throws SQLException {
        databaseHelper.CleanCategories();
    }

    public  void CleanRowsCategory() throws SQLException {
        databaseHelper.CleanRowsCategory();
    }


    /**
     * Countries Dao
     * @return
     * @throws SQLException
     */
    public Dao<ICountries.Countries, Integer> getCountriesDao() throws SQLException {
        if (countries == null) {
            countries = databaseHelper.getDao(ICountries.Countries.class);
        }
        return countries;
    }



    /**
     * rowsCountries Dao
     * @return
     * @throws SQLException
     */
    public Dao<rowsCountries, Integer> getRowsCountriesDao() throws SQLException {
        if (rowsCountries == null) {
            rowsCountries = databaseHelper.getDao(rowsCountries.class);
        }
        return rowsCountries;
    }

    public  void CleanCountries() throws SQLException {
        databaseHelper.CleanCountries();
    }

    public  void CleanRowsCountries() throws SQLException {
        databaseHelper.CleanRowsCountries();
    }


    /**
     * On finish app
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public static MlearningApplicattion getApplication() {
        return instance;
    }


//inicio Google Analytics
    public static synchronized MlearningApplicattion getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }
    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                            .setDescription(
                                    new StandardExceptionParser(this, null)
                                            .getDescription(Thread.currentThread().getName(), e))
                            .setFatal(false)
                            .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
//fin Google Analytics




}
