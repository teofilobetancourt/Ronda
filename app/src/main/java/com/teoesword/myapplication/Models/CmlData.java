package com.teoesword.myapplication.Models;

public class CmlData {
    private long fecha;
    private String coTarea;
    private String descripcionTarea;
    private String idGrupoRonda;
    private String descripcionGrupoRonda;
    private String secuenciaGrupo;
    private String idCml;
    private String equipo;
    private String tagEquipo;
    private String utSistema;
    private String descripcionCml;
    private String secuenciaCml;
    private String valor;
    private String unit;

    public CmlData(long fecha, String coTarea, String descripcionTarea, String idGrupoRonda,
                   String descripcionGrupoRonda, String secuenciaGrupo, String idCml, String equipo,
                   String tagEquipo, String utSistema, String descripcionCml, String secuenciaCml,
                   String valor, String unit) {
        this.fecha = fecha;
        this.coTarea = coTarea;
        this.descripcionTarea = descripcionTarea;
        this.idGrupoRonda = idGrupoRonda;
        this.descripcionGrupoRonda = descripcionGrupoRonda;
        this.secuenciaGrupo = secuenciaGrupo;
        this.idCml = idCml;
        this.equipo = equipo;
        this.tagEquipo = tagEquipo;
        this.utSistema = utSistema;
        this.descripcionCml = descripcionCml;
        this.secuenciaCml = secuenciaCml;
        this.valor = valor;
        this.unit = unit;
    }

    public long getFecha() {
        return fecha;
    }

    public String getCoTarea() {
        return coTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public String getIdGrupoRonda() {
        return idGrupoRonda;
    }

    public String getDescripcionGrupoRonda() {
        return descripcionGrupoRonda;
    }

    public String getSecuenciaGrupo() {
        return secuenciaGrupo;
    }

    public String getIdCml() {
        return idCml;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getTagEquipo() {
        return tagEquipo;
    }

    public String getUtSistema() {
        return utSistema;
    }

    public String getDescripcionCml() {
        return descripcionCml;
    }

    public String getSecuenciaCml() {
        return secuenciaCml;
    }

    public String getValor() {
        return valor;
    }

    public String getUnit() {
        return unit;
    }
}
