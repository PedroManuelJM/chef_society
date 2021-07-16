package com.example.chefsociety.clases;

import java.io.Serializable;

public class Ingrediente implements Serializable {

    private  int idingrediente;
    private  int idreceta;
    private  String nombre_ingrediente;
    private  double cantidad_ingrediente;
    private int idunidadmedida;
    private String nombre_unidadmedida;


    public Ingrediente() {
    }

    public Ingrediente(int idingrediente, int idreceta, String nombre_ingrediente, double cantidad_ingrediente, int idunidadmedida) {
        this.idingrediente = idingrediente;
        this.idreceta = idreceta;
        this.nombre_ingrediente = nombre_ingrediente;
        this.cantidad_ingrediente = cantidad_ingrediente;
        this.idunidadmedida = idunidadmedida;
    }

    public Ingrediente(String nombre_ingrediente, double cantidad_ingrediente ,int idunidadmedida ,String nombre_unidadmedida) {
        this.nombre_ingrediente = nombre_ingrediente;
        this.cantidad_ingrediente = cantidad_ingrediente;
        this.idunidadmedida=idunidadmedida;
        this.nombre_unidadmedida = nombre_unidadmedida;
    }

    public int getIdingrediente() {
        return idingrediente;
    }

    public void setIdingrediente(int idingrediente) {
        this.idingrediente = idingrediente;
    }

    public int getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public String getNombre_ingrediente() {
        return nombre_ingrediente;
    }

    public void setNombre_ingrediente(String nombre_ingrediente) {
        this.nombre_ingrediente = nombre_ingrediente;
    }

    public double getCantidad_ingrediente() {
        return cantidad_ingrediente;
    }

    public void setCantidad_ingrediente(double cantidad_ingrediente) {
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    public int getIdunidadmedida() {
        return idunidadmedida;
    }

    public void setIdunidadmedida(int idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
    }

    public String getNombre_unidadmedida() {
        return nombre_unidadmedida;
    }

    public void setNombre_unidadmedida(String nombre_unidadmedida) {
        this.nombre_unidadmedida = nombre_unidadmedida;
    }
}
