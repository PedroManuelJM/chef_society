package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.InternaDB;
import com.example.chefsociety.clases.Receta;
import com.example.chefsociety.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class IngredienteActivity extends AppCompatActivity implements View.OnClickListener  {

    public static Receta receta= new Receta();  // instanciando receta
    Button jbtn_agregar_ingrediente,jbtn_mostrar_ingrediente,jbtn_continuar,jbtn_salir ;
    Spinner jsp_unidad_medida ;
    EditText jtxt_nombre_ingrediente,jtxt_cantidad_ingrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediente);

        jbtn_agregar_ingrediente=findViewById(R.id.ing_btn_agregar_ingrediente);
        jbtn_mostrar_ingrediente=findViewById(R.id.ing_btn_mostrar_ingrediente);
        jbtn_continuar=findViewById(R.id.ing_btn_continuar);
        jbtn_salir=findViewById(R.id.ing_btn_salir);

        jsp_unidad_medida=findViewById(R.id.ing_sp_unidad_medida);
        jtxt_nombre_ingrediente=findViewById(R.id.ing_txt_nombre_ingrediente);
        jtxt_cantidad_ingrediente=findViewById(R.id.ing_txt_cantidad_ingrediente);

        jbtn_mostrar_ingrediente.setOnClickListener(this);
        jbtn_agregar_ingrediente.setOnClickListener(this);
        jbtn_continuar.setOnClickListener(this);
        jbtn_salir.setOnClickListener(this);

        consultar_idreceta(LoginActivity.usuario.getIdusuario()); // consultando el ultimo idreceta del usuario logueado

        llenar_unidadmedida();

    }

    private void llenar_unidadmedida() {
        AsyncHttpClient async_unidadmedidas = new AsyncHttpClient();
        String s_url = "http://compradw.com/unidadmedidas.php";
        async_unidadmedidas.post(s_url, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[] s_paises = new String[jsonArray.length()+1];
                        s_paises[0] = "Seleccione la unidad";
                        for(int i=1;i<=jsonArray.length();i++){
                            s_paises[i] = jsonArray.getJSONObject(i-1).getString("nombre_unidadmedida");
                        }
                        jsp_unidad_medida.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_paises));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error cod: " + statusCode,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar las unidad de medida",Toast.LENGTH_LONG).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void consultar_idreceta(int idusuario) {  // IMPORTANTE YA QUE CON ESTO SACO EL ULTIMO IDRECETA
        AsyncHttpClient ahc_consultar = new AsyncHttpClient();
        String s_url = "http://compradw.com/Consulta_receta.php";

        RequestParams params = new RequestParams();
        params.add("idusuario", String.valueOf(idusuario));

        ahc_consultar.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() >0){
                                // Parametros
                                receta.setIdreceta(jsonArray.getJSONObject(0).getInt("idreceta")); //
                                System.out.println("idreceta"+receta.getIdreceta());
                          //      Toast.makeText(getApplicationContext(), " receta id:" + receta.getIdreceta() + " ",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTAR ID_RECETA" + statusCode ,Toast.LENGTH_SHORT).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ing_btn_agregar_ingrediente:
                validarIngrediente();
                break;
            case R.id.ing_btn_continuar:
                vista_instruccion();
                break;
            case R.id.ing_btn_mostrar_ingrediente:
                mostrar_ingrediente();
                break;
            case R.id.ing_btn_salir:
                salir_ingrediente();
                break;

        }
    }

    private void validarIngrediente() {
        if(jtxt_nombre_ingrediente.getText().toString().isEmpty()){
            jtxt_nombre_ingrediente.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese el nombre del ingrediente",Toast.LENGTH_SHORT).show();
        }
        else if(jtxt_cantidad_ingrediente.getText().toString().isEmpty()){
            jtxt_cantidad_ingrediente.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese la cantidad del ingrediente",Toast.LENGTH_SHORT).show();
        }
        else if(jsp_unidad_medida.getSelectedItemPosition()==0)
            Toast.makeText(this,"Ingrese la unidad de medida del ingrediente",Toast.LENGTH_SHORT).show();
        else
            agregar_ingrediente();
    }

    private void mostrar_ingrediente() {
        Intent i_mostrar_ingrediente= new Intent(getApplicationContext(),MostrarIngredientesActivity.class);
        i_mostrar_ingrediente.putExtra("IDRECETA",String.valueOf(receta.getIdreceta())); // PASANDO EL IDRECETA AL ACTIVITY MOSTRAR-INGREDIENTE-ACTIVITY
        startActivity(i_mostrar_ingrediente);
    }

    private void agregar_ingrediente() {

        AsyncHttpClient ahc_agregar_ingrediente = new AsyncHttpClient();
        String s_url = "http://compradw.com/registro_ingrediente.php";
        RequestParams params = new RequestParams();
        params.add("idreceta",String.valueOf(receta.getIdreceta()));
        params.add("nombre_ingrediente",jtxt_nombre_ingrediente.getText().toString().trim());
        params.add("cantidad_ingrediente",jtxt_cantidad_ingrediente.getText().toString().trim());
        params.add("idunidadmedida",String.valueOf(jsp_unidad_medida.getSelectedItemPosition()));

        ahc_agregar_ingrediente.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        jtxt_nombre_ingrediente.setText("");
                        jtxt_cantidad_ingrediente.setText("");
                        jsp_unidad_medida.setSelection(0);
                        Toast.makeText(getApplicationContext(),"Ingrediente registrado!!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR AGREGAR INGREDIENTE" + statusCode ,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void vista_instruccion() {
        Intent i_instruccion= new Intent(getApplicationContext(),InstruccionActivity.class);
        startActivity(i_instruccion);
    }

    private void salir_ingrediente() {
        Intent i_salir= new Intent(getApplicationContext(),PrincipalActivity.class);
        finish();
        startActivity(i_salir);
    }


}