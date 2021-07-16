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
import com.example.chefsociety.clases.Receta;
import com.example.chefsociety.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetalleCatalogoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_id_receta,txt_nombre_receta,txt_descripcion_receta;
    ImageView jiv_foto_receta;
    ImageButton ibtn_agregar_favorito ,ib_btn_regresar,ib_btn_mostrar_comentario;
    // Igrediente
    RecyclerView jrv_ingrediente,jrv_instruccion;
    ArrayList<Ingrediente> lista;
    IngredienteAdapter adapter = null;
    // Instruccion
    ArrayList<Instruccion> lista1;
    InstruccionAdapter adapter1 = null;
    String guardar="";// img

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_catalogo);

        // INSTANCIAR
        txt_id_receta= findViewById(R.id.idreceta_CatalogoDetalle);
        txt_nombre_receta= findViewById(R.id.Titulo_CatalogoDetalle);
        txt_descripcion_receta= findViewById(R.id.Descripcion_CatalogoDetalle);
        jiv_foto_receta = findViewById(R.id.Imagen_CatalogoDetalle);
        jrv_ingrediente=findViewById(R.id.rvMostrarIngredientesCatalogo); // id de RecyclerView
        jrv_instruccion=findViewById(R.id.rvMostrarInstruccionesCatalogo); // id de RecyclerView
        ibtn_agregar_favorito=findViewById(R.id.ib_agregar_favorito); // agregar a favorito
        ib_btn_regresar = findViewById(R.id.ib_btn_regresar_detallecatalogo);
        ib_btn_mostrar_comentario=findViewById(R.id.ib_mostrar_comentario);
       //RECIBIENDO_PARAMETROS
        int id_receta= getIntent().getIntExtra("ID_CAT_RECETA",-1);
        String nombre_receta= getIntent().getStringExtra("DET_CAT_NOMBRECETA");
        String descripcion_receta = getIntent().getStringExtra("DET_CAT_DESRECETA");
        String imagen = getIntent().getStringExtra("DET_CAT_IMGRECETA");
        guardar=imagen;
        System.out.println("IMAGEN-----------:"+guardar);

        // MOSTRANDO EN EL XML
        txt_id_receta.setText(String.valueOf(id_receta));
        txt_nombre_receta.setText(nombre_receta);
        txt_descripcion_receta.setText(descripcion_receta);

        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        jiv_foto_receta.setImageBitmap(bitmap);

        System.out.println("D_CAT_RECETA: " + id_receta);

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

        mostrar_detalle_receta_ingredientes_catalogo(id_receta);
        mostrar_detalle_receta_instrucciones_catalogo(id_receta);

        ibtn_agregar_favorito.setOnClickListener(this);
        ib_btn_regresar.setOnClickListener(this);
        ib_btn_mostrar_comentario.setOnClickListener(this);
    }

    private void mostrar_detalle_receta_instrucciones_catalogo(int id_receta) {
        AsyncHttpClient ahc_mostrar_instrucciones = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarInstruccion.php";
        RequestParams params= new RequestParams();
        params.add("idreceta", String.valueOf(id_receta));

        System.out.println( "MOSTRAR-INSTRUCCIONES MIS RECETAS DETALLE CATALOGO: " + id_receta);
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

    private void mostrar_detalle_receta_ingredientes_catalogo(int id_receta) {
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
            case R.id.ib_agregar_favorito:
                agregar_receta_favorita(txt_id_receta.getText().toString().trim());
                break;
            case R.id.ib_btn_regresar_detallecatalogo:
                regresar();
                break;
            case R.id.ib_mostrar_comentario:
                comentario();
                break;
        }

    }

    private void comentario() {
        Intent i_comentario = new Intent(getApplicationContext(),MostrarComentariosActivity.class);
        i_comentario.putExtra("COM_ID_RECETA",txt_id_receta.getText().toString().trim());
        i_comentario.putExtra("COM_NOM_RECETA",txt_nombre_receta.getText().toString().trim());
        i_comentario.putExtra("COM_DES_RECETA",txt_descripcion_receta.getText().toString().trim());
        i_comentario.putExtra("COM_IMG_RECETA", String.valueOf(guardar));
        startActivity(i_comentario);
    }

    private void regresar() {
        Intent i_regresar = new Intent(getApplicationContext(),MostrarCategoriasActivity.class);
        startActivity(i_regresar);
    }

    private void agregar_receta_favorita(String id_receta) {

        AsyncHttpClient ahc_registrar_receta_favorita= new AsyncHttpClient();
        String s_url = "http://compradw.com/registro_recetafavorita.php";

        RequestParams params = new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));
        params.add("idreceta", String.valueOf(id_receta));

        ahc_registrar_receta_favorita.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        Toast.makeText(getApplicationContext(),"Agregado a mis Favoritos !!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR DE REGISTRAR RECETA FAVORITA" + statusCode ,Toast.LENGTH_SHORT).show();
                System.out.println("ERROR DE REGISTRAR USUARIO" + statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

}