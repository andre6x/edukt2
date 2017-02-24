package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ICategories {

    @GET("getCategories")
    Call<Category> getCategoryFrom(@Query("msisdn") String msisdn,
                                   @Query("cod_servicio") String CountryCode,
                                   @Query("limit") String limit,
                                   @Query("offset") String offset);

    @GET("getCategories")
    Call<Category> getCategoryFrom(@Query("cod_servicio") String CountryCode,
                                   @Query("limit") String limit,
                                   @Query("offset") String offset);

    @DatabaseTable(tableName = "CategoriesTable")
    public class Category {

        private List<rowsCategory> rows;
        private summaryCategories summary;
        @DatabaseField
        private int code;
        @DatabaseField
        private String cdn;
        @DatabaseField
        private int num_rows;
        @DatabaseField
        private String description;

        public List<rowsCategory> getRows() {
            return rows;
        }

        public void setRows(List<rowsCategory> rows) {
            this.rows = rows;
        }

        public summaryCategories getSummary() {
            return summary;
        }

        public void setSummary(summaryCategories summary) {
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
