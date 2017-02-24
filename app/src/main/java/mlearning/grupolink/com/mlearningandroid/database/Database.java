package mlearning.grupolink.com.mlearningandroid.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mlearning.grupolink.com.mlearningandroid.entities.ICategories;
import mlearning.grupolink.com.mlearningandroid.entities.ICategoryCourses;
import mlearning.grupolink.com.mlearningandroid.entities.ICountries;
import mlearning.grupolink.com.mlearningandroid.entities.ICursos;
import mlearning.grupolink.com.mlearningandroid.entities.IFavoriteCourses;
import mlearning.grupolink.com.mlearningandroid.entities.IHomeCursos;
import mlearning.grupolink.com.mlearningandroid.entities.ILesson;
import mlearning.grupolink.com.mlearningandroid.entities.ILessonById;
import mlearning.grupolink.com.mlearningandroid.entities.ITopTen;
import mlearning.grupolink.com.mlearningandroid.entities.rows;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategory;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCategoryCourses;
import mlearning.grupolink.com.mlearningandroid.entities.rowsCountries;
import mlearning.grupolink.com.mlearningandroid.entities.rowsFavoriteCourses;
import mlearning.grupolink.com.mlearningandroid.entities.rowsHome;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLesson;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonImages;
import mlearning.grupolink.com.mlearningandroid.entities.rowsLessonVideos;
import mlearning.grupolink.com.mlearningandroid.entities.rowsTopTen;
import mlearning.grupolink.com.mlearningandroid.entities.summaryCategories;

/** SQLite Adapter
 *
 *
 * Database Structure
 *
 *
 * */
public class Database extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mlearning";
    private static final int DATABASE_VERSION = 3; //msj alert 120
    private  SQLiteDatabase  db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            this.db = db;
            Log.i(Database.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, IHomeCursos.HomeCursos.class);
            TableUtils.createTable(connectionSource, rowsHome.class);
            TableUtils.createTable(connectionSource, ITopTen.TopTen.class);
            TableUtils.createTable(connectionSource, rowsTopTen.class);
            TableUtils.createTable(connectionSource, ICategoryCourses.CategoryCourses.class);
            TableUtils.createTable(connectionSource, rowsCategoryCourses.class);
            TableUtils.createTable(connectionSource, IFavoriteCourses.FavoriteCourses.class);
            TableUtils.createTable(connectionSource, rowsFavoriteCourses.class);

            TableUtils.createTable(connectionSource, ICursos.Cursos.class);
            TableUtils.createTable(connectionSource, rows.class);
            TableUtils.createTable(connectionSource, ILesson.Lesson.class);
            TableUtils.createTable(connectionSource, rowsLesson.class);
            TableUtils.createTable(connectionSource, ILessonById.LessonById.class);
            TableUtils.createTable(connectionSource, rowsLessonImages.class);
            TableUtils.createTable(connectionSource, rowsLessonVideos.class);
            TableUtils.createTable(connectionSource, ICategories.Category.class);
            TableUtils.createTable(connectionSource, rowsCategory.class);
            TableUtils.createTable(connectionSource, summaryCategories.class);
            TableUtils.createTable(connectionSource, ICountries.Countries.class);
            TableUtils.createTable(connectionSource, rowsCountries.class);

        } catch (SQLException e) {
            Log.e(Database.class.getName(), "ERROR AL CREAR LA BASE DE DATOS", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(Database.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, IHomeCursos.HomeCursos.class, true);
            TableUtils.dropTable(connectionSource, rowsHome.class, true);
            TableUtils.dropTable(connectionSource, ITopTen.TopTen.class, true);
            TableUtils.dropTable(connectionSource, rowsTopTen.class, true);
            TableUtils.dropTable(connectionSource, ICategoryCourses.CategoryCourses.class, true);
            TableUtils.dropTable(connectionSource, rowsCategoryCourses.class, true);
            TableUtils.dropTable(connectionSource, IFavoriteCourses.FavoriteCourses.class, true);
            TableUtils.dropTable(connectionSource, rowsFavoriteCourses.class, true);

            TableUtils.dropTable(connectionSource, ICursos.Cursos.class, true);
            TableUtils.dropTable(connectionSource, rows.class, true);
            TableUtils.dropTable(connectionSource, ILesson.Lesson.class, true);
            TableUtils.dropTable(connectionSource, rowsLesson.class, true);
            TableUtils.dropTable(connectionSource, ILessonById.LessonById.class, true);
            TableUtils.dropTable(connectionSource, rowsLessonImages.class, true);
            TableUtils.dropTable(connectionSource, rowsLessonVideos.class, true);
            TableUtils.dropTable(connectionSource, ICategories.Category.class, true);
            TableUtils.dropTable(connectionSource, rowsCategory.class, true);
            TableUtils.dropTable(connectionSource, summaryCategories.class, true);
            TableUtils.dropTable(connectionSource, ICountries.Countries.class, true);
            TableUtils.dropTable(connectionSource, rowsCountries.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(Database.class.getName(), "ERROR AL DROP DE LA BASE DE DATOS", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }

    public void CleanHomeCursos() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), IHomeCursos.HomeCursos.class);
    }
    public void CleanRowsHome() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsHome.class);
    }

    public void CleanTopTen() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ITopTen.TopTen.class);
    }
    public void CleanRowsTopTen() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsTopTen.class);
    }

    public void CleanCategoryCourses() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ICategoryCourses.CategoryCourses.class);
    }
    public void CleanRowsCategoryCourses() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsCategoryCourses.class);
    }

    public void CleanFavoriteCourses() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), IFavoriteCourses.FavoriteCourses.class);
    }
    public void CleanRowsFavoriteCourses() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsFavoriteCourses.class);
    }


    public void CleanCursos() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ICursos.Cursos.class);
    }
    public void CleanRows() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rows.class);
    }


    public void CleanLesson() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ILesson.Lesson.class);
    }
    public void CleanRowsLesson() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsLesson.class);
    }

    public void CleanLessonById() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ILessonById.LessonById.class);
    }

    public void CleanRowsLessonImages() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsLessonImages.class);
    }
    public void CleanRowsLessonVideos() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsLessonVideos.class);
    }

    public void CleanCategories() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ICategories.Category.class);
    }

    public void CleanRowsCategory() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsCategory.class);
    }

    public void CleanCountries() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), ICountries.Countries.class);
    }

    public void CleanRowsCountries() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), rowsCountries.class);
    }

    public void BeginTransaction() {
        db.beginTransaction();
   }

    public void EndTransactionSuccess() {
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public void EndTransaction() {
        db.endTransaction();
    }
}