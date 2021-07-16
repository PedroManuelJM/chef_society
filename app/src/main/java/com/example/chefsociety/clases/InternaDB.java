package com.example.chefsociety.clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class InternaDB extends SQLiteOpenHelper {

    private static final String s_nombre_db= "proyecto.db";
    private static final int i_version_db = 1;
    private static final String s_crear_tabla_usuario="CREATE TABLE IF NOT EXISTS USUARIO (CORREO VARCHAR(70), CLAVE VARCHAR(128));";
    private static final String s_eliminar_tabla_usuario="DROP TABLE IF EXISTS USUARIO;";

    public InternaDB(@Nullable Context context) {
        super(context, s_nombre_db, null, i_version_db); // constructor de la DB
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(s_crear_tabla_usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(s_eliminar_tabla_usuario);
        db.execSQL(s_crear_tabla_usuario);
    }

    public boolean agregar_usuario(String s_correo , String s_clave){
        boolean b_agregado = false;
        SQLiteDatabase db= getWritableDatabase();
        // Agregando usuario
        if(db !=null){
            String s_agregar_usuario = "INSERT INTO USUARIO VALUES ('"+s_correo+"','"+s_clave+"');";
            db.execSQL(s_agregar_usuario);
            db.close();
            b_agregado = true;
        }
        return  b_agregado;
    }

    public boolean borrar_sesion(){
        boolean b_borrado = false;
        SQLiteDatabase db= getWritableDatabase();

        if(db !=null){
            String s_borrar_sesion= "DELETE FROM USUARIO;";
            db.execSQL(s_borrar_sesion);
            db.close();
            b_borrado = true;
        }
        return b_borrado;
    }

    public boolean recordo_sesion(){
        boolean b_recordo = false;
        SQLiteDatabase db= getWritableDatabase();

        if(db !=null){
            String s_consulta = "SELECT * FROM USUARIO;";
            Cursor cursor = db.rawQuery(s_consulta,null);
            if(cursor.moveToNext())
                b_recordo= true;
            db.close();
        }
        return b_recordo;

    }

    public String buscar_campo(String s_campo){
        String s_data=null;
        SQLiteDatabase db = getReadableDatabase();
        if(db !=null){
            String s_consulta="SELECT "+s_campo+" FROM USUARIO;";
            Cursor cursor= db.rawQuery(s_consulta,null);
            if(cursor.moveToNext())
                s_data = cursor.getString(0); // el campo
            db.close();
        }
        return s_data;
    }


}
