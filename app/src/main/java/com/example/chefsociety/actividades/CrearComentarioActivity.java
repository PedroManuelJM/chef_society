package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class CrearComentarioActivity extends AppCompatActivity implements View.OnClickListener {

    Receta receta=new Receta();
    String juntar_hora="";
    String fecha="";
    String id_receta="";
    TextView txt_comentario;
    Button jbtn_registrar_comentario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comentario);


        txt_comentario=findViewById(R.id.com_txt_comentario);
        jbtn_registrar_comentario=findViewById(R.id.com_btn_reg_comentario);
        // HORA
        Calendar calendario = Calendar.getInstance();
        int hora, minutos, segundos;
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        juntar_hora= (hora+ ":"+ minutos +":"+ segundos);
        System.out.println(juntar_hora);

        // FECHA
        fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        System.out.println("date " + fecha);

        receta = (Receta) getIntent().getSerializableExtra("RECETA");
        id_receta= String.valueOf(receta.getIdreceta());

        //id_receta= getIntent().getStringExtra("REG_COM_ID_RECETA");

        jbtn_registrar_comentario.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.com_btn_reg_comentario:
                validarComentario();
                break;
        }

    }

    public void validarComentario(){
        if(txt_comentario.getText().toString().isEmpty())
            Toast.makeText(this,"Ingrese el comentario para la receta",Toast.LENGTH_SHORT).show();
        else
        registrar_comentario();
    }

    private void registrar_comentario() {
        AsyncHttpClient ahc_registrar_comentario= new AsyncHttpClient();
        String s_url = "http://compradw.com/registro_comentario.php";

        RequestParams params = new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));
        params.add("idreceta",id_receta);
        params.add("comentario",txt_comentario.getText().toString());
        params.add("fecha",fecha); // imagen convertido en cadena
        params.add("hora",juntar_hora);

        ahc_registrar_comentario.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                         Intent i_mostrar= new Intent(getApplicationContext(),MostrarComentariosActivity.class);
                         i_mostrar.putExtra("COM_ID_RECETA",id_receta);
                         i_mostrar.putExtra("COM_NOM_RECETA",receta.getNombre_receta());
                         i_mostrar.putExtra("COM_DES_RECETA",receta.getDescripcion_receta());
                         i_mostrar.putExtra("COM_IMG_RECETA",receta.getImagen_receta());
                         finish();
                         startActivity(i_mostrar);
                         Toast.makeText(getApplicationContext(),"Comentario registrado!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR DE REGISTRAR USUARIO" + statusCode ,Toast.LENGTH_SHORT).show();
                System.out.println("ERROR DE REGISTRAR USUARIO" + statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }


}