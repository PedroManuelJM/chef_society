package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chefsociety.R;

public class EditarIngredienteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText jtxt_ingrediente, jtxt_cantidad, jtxt_unidadmedida;
    Button jbtn_actualizar, jbtn_eliminar, jbtn_regresar;
    int i_ID_INGREDIENTE=-1;
    int i_ID_RECETA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ingrediente);

        jtxt_ingrediente = findViewById(R.id.txtDetalleNombreIngrediente);
        jtxt_cantidad = findViewById(R.id.txtDetalleCantidadIngrediente);
     //   jtxt_unidadmedida = findViewById(R.id.txt);

        jbtn_actualizar = findViewById(R.id.btnActualizarIngrediente);
        jbtn_eliminar = findViewById(R.id.btnEliminarIngrediente);
        jbtn_regresar = findViewById(R.id.btnRegresarIngrediente);

        jbtn_actualizar.setOnClickListener(this);
        jbtn_eliminar.setOnClickListener(this);
        jbtn_regresar.setOnClickListener(this);

        i_ID_INGREDIENTE = getIntent().getIntExtra("ID_INGREDIENTE", -1);
        i_ID_RECETA = getIntent().getIntExtra("ID_RECETA",-1);

        if (i_ID_INGREDIENTE == -1) {
            jbtn_actualizar.setEnabled(false);
            jbtn_eliminar.setEnabled(false);
          //  Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            return;
        }
        mostrar_ingrediente(i_ID_INGREDIENTE,i_ID_RECETA);


    }
    private void mostrar_ingrediente(int i_ID_INGREDIENTE,int i_ID_RECETA) {

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnActualizarIngrediente:
                actualizar_ingrediente();
                break;
            case R.id.btnEliminarIngrediente:
                eliminar_ingrediente();
                break;
            case R.id.btnRegresarIngrediente:
                regresar();
                break;
        }
    }

    private void actualizar_ingrediente() {
    }

    private void eliminar_ingrediente() {
    }

    private void regresar() {
    }
}