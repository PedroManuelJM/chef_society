package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Instruccion;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class InstruccionActivity extends AppCompatActivity implements View.OnClickListener {

    Button jbtn_agregar_instruccion,jbtn_salir ;
    EditText jtxt_descripcion_instruccion;
    TextView jtxt_nropaso_instruccion;
    private Instruccion instruccion= new Instruccion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruccion);

        jtxt_descripcion_instruccion=findViewById(R.id.ins_txt_descripcion);
        jtxt_nropaso_instruccion=findViewById(R.id.ins_txt_nropaso);

        jbtn_agregar_instruccion=findViewById(R.id.ing_btn_agregar_instruccion);
        jbtn_salir=findViewById(R.id.ins_btn_salir);

        jbtn_agregar_instruccion.setOnClickListener(this);
        jbtn_salir.setOnClickListener(this);

        consultar_idreceta(LoginActivity.usuario.getIdusuario());
        consultar_idInstruccion(LoginActivity.usuario.getIdusuario(),IngredienteActivity.receta.getIdreceta());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ing_btn_agregar_instruccion:
                validarInstruccion();
                break;

            case R.id.ins_btn_salir:
                salir_instruccion();
                break;
        }
    }
    private void validarInstruccion(){
        if(jtxt_descripcion_instruccion.getText().toString().isEmpty()){
            jtxt_descripcion_instruccion.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese una descripcion del ingrediente",Toast.LENGTH_SHORT).show();
        }
        else
            agregar_instruccion();
    }

    private void consultar_idreceta(int idusuario) { // IMPORTANTE YA QUE CON ESTO SACO EL ULTIMO IDRECETA
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
                            // el id receta viene desde Ingrediente Activity
                            IngredienteActivity.receta.setIdreceta(jsonArray.getJSONObject(0).getInt("idreceta")); //
                            System.out.println("idreceta"+IngredienteActivity.receta.getIdreceta());
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

    private void agregar_instruccion() {
        // webservices --> registra instrucción
        AsyncHttpClient ahc_agregar_ingrediente = new AsyncHttpClient();
        String s_url = "http://compradw.com/registro_instruccion.php";
        RequestParams params = new RequestParams();
        params.add("idreceta",String.valueOf(IngredienteActivity.receta.getIdreceta()));
        params.add("descripcion_instruccion",jtxt_descripcion_instruccion.getText().toString().trim());
        params.add("nropaso",String.valueOf(instruccion.getIdinstruccion()));

        ahc_agregar_ingrediente.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        jtxt_descripcion_instruccion.setText("");
                        consultar_idInstruccion(LoginActivity.usuario.getIdusuario(),IngredienteActivity.receta.getIdreceta());
                        Toast.makeText(getApplicationContext(), "Se agrego la instrucción n°"+instruccion.getIdinstruccion()+1 ,Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR AGREGAR INSTRUCCIÓN DE LA RECETA" + statusCode ,Toast.LENGTH_SHORT).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void consultar_idInstruccion(int idusuario,int idreceta){
        AsyncHttpClient ahc_consultar = new AsyncHttpClient();
        String s_url = "http://compradw.com/Consultar_instruccion.php";

        RequestParams params = new RequestParams();
        params.add("idusuario", String.valueOf(idusuario));
        params.add("idreceta", String.valueOf(idreceta));

        ahc_consultar.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() >0){
                            instruccion.setIdinstruccion(jsonArray.getJSONObject(0).getInt("idInstruccion")+1);
                            jtxt_nropaso_instruccion.setText("Instrucción n°"+instruccion.getIdinstruccion());

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR CONSULTAR ID_INSTRUCCION " + statusCode ,Toast.LENGTH_SHORT).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }
    private void salir_instruccion() {
        Intent i_salir= new Intent(getApplicationContext(),PrincipalActivity.class);
        finish();
        startActivity(i_salir);
    }
}