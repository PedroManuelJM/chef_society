package com.example.chefsociety.clases;

import java.io.Serializable;

public class Receta extends Ingrediente implements Serializable {
    private int idreceta;
    private String nombre_receta;
    private String descripcion_receta;
    private String imagen_receta;
    private int idcategoria;


    public Receta() {
    }

    public Receta(int idreceta, String nombre_receta, String descripcion_receta, String imagen_receta, int idcategoria) {
        this.idreceta = idreceta;
        this.nombre_receta = nombre_receta;
        this.descripcion_receta = descripcion_receta;
        this.imagen_receta = imagen_receta;
        this.idcategoria = idcategoria;
    }

    public int getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public String getNombre_receta() {
        return nombre_receta;
    }

    public void setNombre_receta(String nombre_receta) {
        this.nombre_receta = nombre_receta;
    }

    public String getDescripcion_receta() {
        return descripcion_receta;
    }

    public void setDescripcion_receta(String descripcion_receta) {
        this.descripcion_receta = descripcion_receta;
    }

    public String getImagen_receta() {
        return imagen_receta;
    }

    public void setImagen_receta(String imagen_receta) {
        this.imagen_receta = imagen_receta;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }
}
