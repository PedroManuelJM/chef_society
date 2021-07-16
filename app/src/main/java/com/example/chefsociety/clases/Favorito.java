package com.example.chefsociety.clases;

import java.io.Serializable;

public class Favorito implements Serializable {

    private int fav_idusuario;
    private int fav_idreceta;
    private String fav_nombre_receta;
    private String fav_descripcion_receta;
    private String fav_imagen_receta;
    private String fav_nombre_categoria;
    private String fav_imagen_categoria;

    public Favorito() {
    }

    public Favorito(int fav_idusuario, int fav_idreceta, String fav_nombre_receta, String fav_descripcion_receta, String fav_imagen_receta, String fav_nombre_categoria, String fav_imagen_categoria) {
        this.fav_idusuario = fav_idusuario;
        this.fav_idreceta = fav_idreceta;
        this.fav_nombre_receta = fav_nombre_receta;
        this.fav_descripcion_receta = fav_descripcion_receta;
        this.fav_imagen_receta = fav_imagen_receta;
        this.fav_nombre_categoria = fav_nombre_categoria;
        this.fav_imagen_categoria = fav_imagen_categoria;
    }

    public int getFav_idusuario() {
        return fav_idusuario;
    }

    public void setFav_idusuario(int fav_idusuario) {
        this.fav_idusuario = fav_idusuario;
    }

    public int getFav_idreceta() {
        return fav_idreceta;
    }

    public void setFav_idreceta(int fav_idreceta) {
        this.fav_idreceta = fav_idreceta;
    }

    public String getFav_nombre_receta() {
        return fav_nombre_receta;
    }

    public void setFav_nombre_receta(String fav_nombre_receta) {
        this.fav_nombre_receta = fav_nombre_receta;
    }

    public String getFav_descripcion_receta() {
        return fav_descripcion_receta;
    }

    public void setFav_descripcion_receta(String fav_descripcion_receta) {
        this.fav_descripcion_receta = fav_descripcion_receta;
    }

    public String getFav_imagen_receta() {
        return fav_imagen_receta;
    }

    public void setFav_imagen_receta(String fav_imagen_receta) {
        this.fav_imagen_receta = fav_imagen_receta;
    }

    public String getFav_nombre_categoria() {
        return fav_nombre_categoria;
    }

    public void setFav_nombre_categoria(String fav_nombre_categoria) {
        this.fav_nombre_categoria = fav_nombre_categoria;
    }

    public String getFav_imagen_categoria() {
        return fav_imagen_categoria;
    }

    public void setFav_imagen_categoria(String fav_imagen_categoria) {
        this.fav_imagen_categoria = fav_imagen_categoria;
    }
}
