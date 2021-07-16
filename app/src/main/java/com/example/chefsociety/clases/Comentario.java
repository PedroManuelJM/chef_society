package com.example.chefsociety.clases;

public class Comentario {
    private int c_idusuario;
    private int c_idreceta;
    private String c_nombres;
    private String c_apellidos;
    private String c_imagen_usuario;
    private String c_comentario;
    private String c_fecha;
    private String c_hora;

    public Comentario() {
    }

    public Comentario(int c_idusuario, int c_idreceta, String c_nombres, String c_apellidos, String c_imagen_usuario, String c_comentario, String c_fecha, String c_hora) {
        this.c_idusuario = c_idusuario;
        this.c_idreceta = c_idreceta;
        this.c_nombres = c_nombres;
        this.c_apellidos = c_apellidos;
        this.c_imagen_usuario = c_imagen_usuario;
        this.c_comentario = c_comentario;
        this.c_fecha = c_fecha;
        this.c_hora = c_hora;
    }

    public int getC_idusuario() {
        return c_idusuario;
    }

    public void setC_idusuario(int c_idusuario) {
        this.c_idusuario = c_idusuario;
    }

    public int getC_idreceta() {
        return c_idreceta;
    }

    public void setC_idreceta(int c_idreceta) {
        this.c_idreceta = c_idreceta;
    }

    public String getC_nombres() {
        return c_nombres;
    }

    public void setC_nombres(String c_nombres) {
        this.c_nombres = c_nombres;
    }

    public String getC_apellidos() {
        return c_apellidos;
    }

    public void setC_apellidos(String c_apellidos) {
        this.c_apellidos = c_apellidos;
    }

    public String getC_imagen_usuario() {
        return c_imagen_usuario;
    }

    public void setC_imagen_usuario(String c_imagen_usuario) {
        this.c_imagen_usuario = c_imagen_usuario;
    }

    public String getC_comentario() {
        return c_comentario;
    }

    public void setC_comentario(String c_comentario) {
        this.c_comentario = c_comentario;
    }

    public String getC_fecha() {
        return c_fecha;
    }

    public void setC_fecha(String c_fecha) {
        this.c_fecha = c_fecha;
    }

    public String getC_hora() {
        return c_hora;
    }

    public void setC_hora(String c_hora) {
        this.c_hora = c_hora;
    }
}
