package mlearning.grupolink.com.mlearningandroid.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by GrupoLink on 22/07/2015.
 */
@DatabaseTable(tableName = "rowsCountriesTable")
public class rowsCountries {

    @DatabaseField
    private int cod_servicio;
    @DatabaseField
    private String cod_pais;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String url_flag;

    public int getCod_servicio() {
        return cod_servicio;
    }

    public void setCod_servicio(int cod_servicio) {
        this.cod_servicio = cod_servicio;
    }

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_flag() {
        return url_flag;
    }

    public void setUrl_flag(String url_flag) {
        this.url_flag = url_flag;
    }
}
