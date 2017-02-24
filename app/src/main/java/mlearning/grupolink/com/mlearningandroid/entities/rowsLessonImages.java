package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by GrupoLink on 09/04/2015.
 */

@DatabaseTable(tableName = "rowsLessonImageTable")
public class rowsLessonImages {

    @DatabaseField
    private int code;
    @DatabaseField
    private int idtype_record;
    @DatabaseField
    private int idmed_item_file_type;
    @DatabaseField
    private String description;
    @DatabaseField
    private String file;
    @DatabaseField
    private int idmed_lesson;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIdtype_record() {
        return idtype_record;
    }

    public void setIdtype_record(int idtype_record) {
        this.idtype_record = idtype_record;
    }

    public int getIdmed_item_file_type() {
        return idmed_item_file_type;
    }

    public void setIdmed_item_file_type(int idmed_item_file_type) {
        this.idmed_item_file_type = idmed_item_file_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getIdmed_lesson() {
        return idmed_lesson;
    }

    public void setIdmed_lesson(int idmed_lesson) {
        this.idmed_lesson = idmed_lesson;
    }
}
