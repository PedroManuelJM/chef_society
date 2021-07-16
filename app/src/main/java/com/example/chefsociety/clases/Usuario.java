package com.example.chefsociety.clases;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int idusuario;
    private String nombres;
    private String apellidos;
    private int idtipodocumento;
    private String nrodocumento;
    private String correo;
    private String clave;
    private String imagen_usuario;
    private int idpais;

    public Usuario() {
    }

    public Usuario(int idusuario, String nombres, String apellidos, int idtipodocumento, String nrodocumento, String correo, String clave, String imagen_usuario, int idpais) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.idtipodocumento = idtipodocumento;
        this.nrodocumento = nrodocumento;
        this.correo = correo;
        this.clave = clave;
        this.imagen_usuario = imagen_usuario;
        this.idpais = idpais;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdtipodocumento() {
        return idtipodocumento;
    }

    public void setIdtipodocumento(int idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getImagen_usuario() {
        return imagen_usuario;
    }

    public void setImagen_usuario(String imagen_usuario) {
        this.imagen_usuario = imagen_usuario;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }
}
