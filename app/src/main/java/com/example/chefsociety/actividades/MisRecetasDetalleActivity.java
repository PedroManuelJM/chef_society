package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class MisRecetasDetalleActivity extends AppCompatActivity {
    TextView txt_nombre_receta,txt_descripcion_receta;
    ImageView jiv_foto_receta;
    RecyclerView jrv_ingrediente , jrv_instruccion;
    ArrayList<Ingrediente> lista;
    ArrayList<Instruccion> lista1;
    IngredienteAdapter adapter = null;
    InstruccionAdapter adapter1= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mis_recetas_detalle);


        txt_nombre_receta = findViewById(R.id.detalle_Titulo_MisRecetas);
        txt_descripcion_receta = findViewById(R.id.detalle_Descripcion_MisRecetas);
        jiv_foto_receta=findViewById(R.id.detalle_Imagen_MisRecetas);

        String nombre_receta = getIntent().getStringExtra("NOMBRE_RECETA");
        String descripcion_receta = getIntent().getStringExtra("DESCRIPCION_RECETA");
        String imagen =getIntent().getStringExtra("IMAGEN_RECETA") ;
        //String nombre_receta = getIntent().getStringExtra("NOMBRERECETA");
        System.out.println( "NOMBRE_RECETA: " + nombre_receta);

        //  RELLANA LOS DATOS AL ACTIVITY MIS RECETAS DETALLE
        txt_nombre_receta.setText(nombre_receta);
        txt_descripcion_receta.setText(descripcion_receta);
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        jiv_foto_receta.setImageBitmap(bitmap);

        // INGREDIENTES
        jrv_ingrediente=findViewById(R.id.rvMostrarIngredientes);
        lista= new ArrayList<>();
        // para que funcione --> necesita un manejador de linearlayout
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_ingrediente.setLayoutManager(manager);
        adapter= new IngredienteAdapter(lista);
        jrv_ingrediente.setAdapter(adapter);

        // INSTRUCCIONES

        jrv_instruccion=findViewById(R.id.rvMostrarInstrucciones);
        lista1= new ArrayList<>();
        // para que funcione --> necesita un manejador de linearlayout
        LinearLayoutManager manager1= new LinearLayoutManager(getApplicationContext());
        jrv_instruccion.setLayoutManager(manager1);
        adapter1= new InstruccionAdapter(lista1);
        jrv_instruccion.setAdapter(adapter1);

        int id_receta = getIntent().getIntExtra("ID_RECETA",-1);

        mostrar_ingredientes(id_receta);

        mostrar_instrucciones(id_receta);

    }

    private void mostrar_ingredientes(int id_receta) {
        AsyncHttpClient ahc_mostrar_ingredientes = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarIngrediente.php";
        RequestParams params= new RequestParams();

        params.add("idreceta", String.valueOf(id_receta));

        System.out.println( "MOSTRAR-INGREDIENTE-MIS RECETAS DETALLE ACTIVITY: " + id_receta);
        ahc_mostrar_ingredientes.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Ingrediente (
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


    private void mostrar_instrucciones(int id_receta){
        AsyncHttpClient ahc_mostrar_instrucciones = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarInstruccion.php";
        RequestParams params= new RequestParams();
        params.add("idreceta", String.valueOf(id_receta));

        System.out.println( "MOSTRAR-INSTRUCCIONES MIS RECETAS DETALLE ACTIVITY: " + id_receta);
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

}