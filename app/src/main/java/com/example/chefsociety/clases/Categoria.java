package com.example.chefsociety.clases;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int idcategoria;
    private String nombre_categoria;
    private String imagen_categoria;


    public Categoria() {

    }

    public Categoria(int idcategoria, String nombre_categoria, String imagen_categoria) {
        this.idcategoria = idcategoria;
        this.nombre_categoria = nombre_categoria;
        this.imagen_categoria = imagen_categoria;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public String getImagen_categoria() {
        return imagen_categoria;
    }

    public void setImagen_categoria(String imagen_categoria) {
        this.imagen_categoria = imagen_categoria;
    }
}
