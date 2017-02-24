package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by GrupoLink on 09/04/2015.
 */

@DatabaseTable(tableName = "summaryCategoriesTable")
public class summaryCategories {

    @DatabaseField
    private String cdn;
    private int rows;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
