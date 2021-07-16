package com.example.chefsociety.clases;

import java.io.Serializable;

public class Instruccion implements Serializable {

    private int idinstruccion;
    private int idreceta;
    private String descripcion_instruccion;
    private int nropaso;

    public Instruccion() {
    }

    public Instruccion(int idinstruccion, int idreceta, String descripcion_instruccion, int nropaso) {
        this.idinstruccion = idinstruccion;
        this.idreceta = idreceta;
        this.descripcion_instruccion = descripcion_instruccion;
        this.nropaso = nropaso;
    }

    public int getIdinstruccion() {
        return idinstruccion;
    }

    public void setIdinstruccion(int idinstruccion) {
        this.idinstruccion = idinstruccion;
    }

    public int getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public String getDescripcion_instruccion() {
        return descripcion_instruccion;
    }

    public void setDescripcion_instruccion(String descripcion_instruccion) {
        this.descripcion_instruccion = descripcion_instruccion;
    }

    public int getNropaso() {
        return nropaso;
    }

    public void setNropaso(int nropaso) {
        this.nropaso = nropaso;
    }
}
