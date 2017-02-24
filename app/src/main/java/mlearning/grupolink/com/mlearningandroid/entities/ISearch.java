package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GrupoLink on 09/04/2015.
 */

public interface ISearch {

    @GET("quickSearch")
    Call<Search> getNewsFrom(@Query("word") String word);


    public class Search{

    private summarySearch summary;
    private List<searchCategoryCourse> categories;
    private List<List<searchCategoryCourse>> courses;
    private int code;
    private String description;

        public summarySearch getSummary() {
            return summary;
        }

        public void setSummary(summarySearch summary) {
            this.summary = summary;
        }

        public List<searchCategoryCourse> getCategories() {
            return categories;
        }

        public void setCategories(List<searchCategoryCourse> categories) {
            this.categories = categories;
        }

        public List<List<searchCategoryCourse>> getCourses() {
            return courses;
        }

        public void setCourses(List<List<searchCategoryCourse>> courses) {
            this.courses = courses;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
  }
