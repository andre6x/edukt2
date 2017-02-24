package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 22/07/2015.
 */
public interface ICountries {

    @GET("getCountries.php")
    Call<Countries> getNewsFromByCountries();

    @GET("getCountries.php")
    Call<Countries> getNewsFromByCountries_2(@Query("version") String version);

    @DatabaseTable(tableName = "CountriesTable")
    public class Countries{

        private List<rowsCountries> rows;
        private summaryCountries summary;
        @DatabaseField
        private int code;
        @DatabaseField
        private String cdn;
        @DatabaseField
        private int num_rows;



        public List<rowsCountries> getRows() {
            return rows;
        }

        public void setRows(List<rowsCountries> rows) {
            this.rows = rows;
        }

        public summaryCountries getSummary() {
            return summary;
        }

        public void setSummary(summaryCountries summary) {
            this.summary = summary;
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

        public int getNum_rows() {
            return num_rows;
        }

        public void setNum_rows(int num_rows) {
            this.num_rows = num_rows;
        }



    }
}
