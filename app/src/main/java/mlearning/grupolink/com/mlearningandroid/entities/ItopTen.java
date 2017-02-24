package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 16/04/2015.
 */
public interface ITopTen {

    @GET("getToptenCourses")
    Call<TopTen> getTopTen(@Query("ranking") String ranking,
                           @Query("cod_servicio") String CountryCode);

    @DatabaseTable(tableName = "TopTenTable")
    public class TopTen{
        private summaryCategories summary;
        private List<rowsTopTen> rows;
        @DatabaseField
        private int code;
        @DatabaseField
        private String cdn;

        public summaryCategories getSummary() {
            return summary;
        }

        public void setSummary(summaryCategories summary) {
            this.summary = summary;
        }

        public List<rowsTopTen> getRows() {
            return rows;
        }

        public void setRows(List<rowsTopTen> rows) {
            this.rows = rows;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }
    }
}
