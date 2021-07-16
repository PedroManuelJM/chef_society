package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.EnlaceMenu;
import com.example.chefsociety.clases.Usuario;
import com.example.chefsociety.fragmentos.MenuFragment;
import com.example.chefsociety.fragmentos.OpcionesFragment;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    TextView jlbl_nombre;
    ImageButton ibtn_perfil_usuario,ibtn_crear_receta,ibtn_catalogo,ibtn_favoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        jlbl_nombre=findViewById(R.id.pri_lbl_nombre);

        jlbl_nombre.setText(LoginActivity.usuario.getNombres());
        ibtn_perfil_usuario=findViewById(R.id.ib_men_opciones);
        ibtn_crear_receta=findViewById(R.id.ib_men_recetas);
        ibtn_catalogo=findViewById(R.id.ib_men_catalogo);
        ibtn_favoritos=findViewById(R.id.ib_men_favoritos);

        ibtn_perfil_usuario.setOnClickListener(this);
        ibtn_crear_receta.setOnClickListener(this);
        ibtn_catalogo.setOnClickListener(this);
        ibtn_favoritos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_men_opciones:
                perfil_usuario();
                break;
            case R.id.ib_men_recetas:
                 receta();
                break;
            case R.id.ib_men_catalogo:
                catalogo();
                break;
            case R.id.ib_men_favoritos:
                favoritos();
                break;
        }


    }

    private void favoritos() {
        Intent i_favorito= new Intent(getApplicationContext(),RecetasFavoritasActivity.class);
        startActivity(i_favorito);
    }

    private void catalogo() {
        Intent i_catalogo= new Intent(getApplicationContext(),MostrarCategoriasActivity.class);
        startActivity(i_catalogo);
    }

    private void receta() {
        Intent i_receta= new Intent(getApplicationContext(),CrearRecetaActivity.class);
        startActivity(i_receta);
    }

    private void perfil_usuario() {
        Intent i_perfil = new Intent(getApplicationContext(),PerfilActivity.class);
        startActivity(i_perfil);
    }

}