package mlearning.grupolink.com.mlearningandroid.entities;

import java.util.List;

/**
 * Created by GrupoLink on 27/04/2015.
 */
public class summarySearch {

    private int categories;
    private List<Integer> courses;

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }
}
