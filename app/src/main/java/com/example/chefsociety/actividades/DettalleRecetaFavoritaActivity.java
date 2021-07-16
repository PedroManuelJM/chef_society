package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.adaptadores.IngredienteAdapter;
import com.example.chefsociety.adaptadores.InstruccionAdapter;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Instruccion;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DettalleRecetaFavoritaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_id_receta,txt_nombre_receta,txt_descripcion_receta;
    ImageView jiv_foto_receta;
    ImageButton ibtn_eliminar_favorito,ib_btn_regresar;

    // Igrediente
    RecyclerView jrv_ingrediente,jrv_instruccion;
    ArrayList<Ingrediente> lista;
    IngredienteAdapter adapter = null;
    // Instruccion
    ArrayList<Instruccion> lista1;
    InstruccionAdapter adapter1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettalle_receta_favorita);

        // INSTANCIAR
        txt_id_receta= findViewById(R.id.idreceta_Mis_Recetas_Favoritas);
        txt_nombre_receta= findViewById(R.id.detalle_Titulo_Mis_Recetas_Favoritas);
        txt_descripcion_receta= findViewById(R.id.detalle_Descripcion_Mis_Recetas_Favoritas);
        jiv_foto_receta = findViewById(R.id.detalle_Imagen_Mis_Recetas_Favoritas);
        jrv_ingrediente=findViewById(R.id.rvMostrarIngredientesDetalleRecetaFavorita); // id de RecyclerView
        jrv_instruccion=findViewById(R.id.rvMostrarInstruccionDetalleRecetaFavorita); // id de RecyclerView
        ibtn_eliminar_favorito=findViewById(R.id.ib_eliminar_favorito); // agregar a favorito
        ib_btn_regresar= findViewById(R.id.ib_btn_regresar_detallerecetafavorita);
        //RECIBIENDO_PARAMETROS
        int id_receta= getIntent().getIntExtra("FAV_ID_RECETA",-1);
        String nombre_receta= getIntent().getStringExtra("FAV_NOMB_RECETA");
        String descripcion_receta = getIntent().getStringExtra("FAV_DES_RECETA");
        String imagen = getIntent().getStringExtra("FAV_IMG_RECETA");
        
        // MOSTRANDO EN EL XML
        txt_id_receta.setText(String.valueOf(id_receta));
        txt_nombre_receta.setText(nombre_receta);
        txt_descripcion_receta.setText(descripcion_receta);
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        jiv_foto_receta.setImageBitmap(bitmap);

        // LISTAR RecyclerView --> INGREDIENTES
        lista= new ArrayList<>();
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_ingrediente.setLayoutManager(manager);
        adapter= new IngredienteAdapter(lista);
        jrv_ingrediente.setAdapter(adapter);
        // LISTAR RecyclerView --> INSTRUCCIÓN
        lista1= new ArrayList<>();
        LinearLayoutManager manager1= new LinearLayoutManager(getApplicationContext());
        jrv_instruccion.setLayoutManager(manager1);
        adapter1= new InstruccionAdapter(lista1);
        jrv_instruccion.setAdapter(adapter1);

        ibtn_eliminar_favorito.setOnClickListener(this);
        ib_btn_regresar.setOnClickListener(this);

        mostrar_ingredientes_detalle_receta_favorita(id_receta);
        mostrar_instrucciones_detalle_receta_favorita(id_receta);

    }

    private void mostrar_instrucciones_detalle_receta_favorita(int id_receta) {
        AsyncHttpClient ahc_mostrar_instrucciones = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarInstruccion.php";
        RequestParams params= new RequestParams();
        params.add("idreceta", String.valueOf(id_receta));
        System.out.println( "MOSTRAR-INSTRUCCIONES MIS RECETAS FAVORITO DETALLE : " + id_receta);
        ahc_mostrar_instrucciones.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista1.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista1.add(new Instruccion(
                                    jsonArray.getJSONObject(i).getInt("idinstruccion"),
                                    jsonArray.getJSONObject(i).getInt("idreceta"),
                                    jsonArray.getJSONObject(i).getString("descripcion_instruccion"),
                                    jsonArray.getJSONObject(i).getInt("nropaso")));
                        }
                        adapter1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar data:" +statusCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void mostrar_ingredientes_detalle_receta_favorita(int id_receta) {
        AsyncHttpClient ahc_mostrar_ingredientes = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarIngrediente.php";
        RequestParams params= new RequestParams();
        params.add("idreceta", String.valueOf(id_receta));

        System.out.println( "MOSTRAR-INGREDIENTE: " + id_receta);
        ahc_mostrar_ingredientes.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Ingrediente(
                                    jsonArray.getJSONObject(i).getString("nombre_ingrediente"),
                                    jsonArray.getJSONObject(i).getDouble("cantidad_ingrediente"),
                                    jsonArray.getJSONObject(i).getInt("idunidadmedida"),
                                    jsonArray.getJSONObject(i).getString("nombre_unidadmedida")));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar data:" +statusCode,Toast.LENGTH_SHORT).show();
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
            case R.id.ib_eliminar_favorito:
                eliminar_receta_favorita(txt_id_receta.getText().toString().trim());
                break;
            case R.id.ib_btn_regresar_detallerecetafavorita:
                regresar();
                break;
        }
    }

    private void eliminar_receta_favorita(String id_receta) {
        AsyncHttpClient ahc_eliminar_receta_favorita= new AsyncHttpClient();
        String s_url = "http://compradw.com/eliminar_recetafavorita.php";

        RequestParams params = new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));
        params.add("idreceta", String.valueOf(id_receta));

        System.out.println("ELIMINAR RECETA : " + LoginActivity.usuario.getIdusuario() + " , ID-RECETA : "+ id_receta);

        ahc_eliminar_receta_favorita.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode ==200){
                  //  int ret_val = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        Toast.makeText(getApplicationContext(),"Receta Favorita eliminado !!!",Toast.LENGTH_SHORT).show();
                        regresar();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al ELIMINAR RECETA FAVORITA"+statusCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void regresar() {
        Intent i_mostrar_principal = new Intent(getApplicationContext(),RecetasFavoritasActivity.class);
        finish();
        startActivity(i_mostrar_principal);
    }
}