package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by GrupoLink on 09/04/2015.
 */

@DatabaseTable(tableName = "rowsCategoryTable")
public class rowsCategory {

    @DatabaseField
    private int idmed_categories;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String url_cdn_small;
    @DatabaseField
    private String url_cdn_medium;
    @DatabaseField
    private String url_cdn_large;
    @DatabaseField
    private String folder;
    @DatabaseField
    private String date_created;
    @DatabaseField
    private int status_categories;


    public int getIdmed_categories() {
        return idmed_categories;
    }

    public void setIdmed_categories(int idmed_categories) {
        this.idmed_categories = idmed_categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_cdn_small() {
        return url_cdn_small;
    }

    public void setUrl_cdn_small(String url_cdn_small) {
        this.url_cdn_small = url_cdn_small;
    }

    public String getUrl_cdn_medium() {
        return url_cdn_medium;
    }

    public void setUrl_cdn_medium(String url_cdn_medium) {
        this.url_cdn_medium = url_cdn_medium;
    }

    public String getUrl_cdn_large() {
        return url_cdn_large;
    }

    public void setUrl_cdn_large(String url_cdn_large) {
        this.url_cdn_large = url_cdn_large;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public int getStatus_categories() {
        return status_categories;
    }

    public void setStatus_categories(int status_categories) {
        this.status_categories = status_categories;
    }
}
