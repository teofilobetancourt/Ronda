package com.teoesword.myapplication.Databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ronda")
public class RondaEntity {

    @PrimaryKey
    private long fecha;
    @ColumnInfo(name = "descripcion_cml")
    private String descripcionCml;

    private String coTarea;
    private String descripcionTarea;
    private String idGrupoRonda;
    private String descripcionGrupoRonda;
    private String secuenciaGrupo;
    private String idCml;
    private String equipo;
    private String tagEquipo;
    private String utSistema;
    private String secuenciaCml;
    private String valor;
    private String unit;

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getCoTarea() {
        return coTarea;
    }

    public void setCoTarea(String coTarea) {
        this.coTarea = coTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public String getIdGrupoRonda() {
        return idGrupoRonda;
    }

    public void setIdGrupoRonda(String idGrupoRonda) {
        this.idGrupoRonda = idGrupoRonda;
    }

    public String getDescripcionGrupoRonda() {
        return descripcionGrupoRonda;
    }

    public void setDescripcionGrupoRonda(String descripcionGrupoRonda) {
        this.descripcionGrupoRonda = descripcionGrupoRonda;
    }

    public String getSecuenciaGrupo() {
        return secuenciaGrupo;
    }

    public void setSecuenciaGrupo(String secuenciaGrupo) {
        this.secuenciaGrupo = secuenciaGrupo;
    }

    public String getIdCml() {
        return idCml;
    }

    public void setIdCml(String idCml) {
        this.idCml = idCml;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getTagEquipo() {
        return tagEquipo;
    }

    public void setTagEquipo(String tagEquipo) {
        this.tagEquipo = tagEquipo;
    }

    public String getUtSistema() {
        return utSistema;
    }

    public void setUtSistema(String utSistema) {
        this.utSistema = utSistema;
    }

    public String getDescripcionCml() {
        return descripcionCml;
    }

    public void setDescripcionCml(String descripcionCml) {
        this.descripcionCml = descripcionCml;
    }

    public String getSecuenciaCml() {
        return secuenciaCml;
    }

    public void setSecuenciaCml(String secuenciaCml) {
        this.secuenciaCml = secuenciaCml;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
