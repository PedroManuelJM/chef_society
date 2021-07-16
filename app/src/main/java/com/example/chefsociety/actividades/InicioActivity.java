package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.chefsociety.R;

public class InicioActivity extends AppCompatActivity {

    // LA PARTE GRAFICA QUE TIENE ID HACER ENLACE PARA PODER MANEJARLO
    ProgressBar jpb_inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        // enlazando en la parte grafica
        jpb_inicio=findViewById(R.id.pb_inicio);
        // HILO
        Thread t_inicio = new Thread(){
            @Override
            public void run() {
                super.run();
                // espera 3 seguros luego carga la siguiente actividad
                try {
                    sleep(4000);
                    //otras rutinas para la pre carga
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // cargamos la siguiente actividad login
                    Intent i_login = new Intent(getApplicationContext(), LoginActivity.class);
                    // LLamar la activity
                    startActivity(i_login);
                    finish(); // Termina el hilo, pasa al sgte Activity
                }
            }
        };
        t_inicio.start(); // el hilo t_inicio comenzara

    }

}