package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.adaptadores.CatalogoAdapter;
import com.example.chefsociety.adaptadores.MisRecetasAdapter;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CatalogoActivity extends AppCompatActivity {

    TextView txt_nombre_categoria;
    ImageView jiv_foto_categoria;
    RecyclerView jrv_catalogo;
    ArrayList<Receta> lista;
    CatalogoAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        txt_nombre_categoria= findViewById(R.id.Catalogo_nombre_categoria);
        jiv_foto_categoria = findViewById(R.id.Catalogo_Imagen_categoria);
        jrv_catalogo=findViewById(R.id.rvMostrarCatalogo);

        int id_categoria= getIntent().getIntExtra("ID_CATEGORIA",-1);
        String nombre_categoria= getIntent().getStringExtra("NOMBRE_CATEGORIA");
        String imagen = getIntent().getStringExtra("IMAGEN_CATEGORIA");

        txt_nombre_categoria.setText(nombre_categoria);

        // IMAGEN
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        jiv_foto_categoria.setImageBitmap(bitmap);


        lista= new ArrayList<>();
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_catalogo.setLayoutManager(manager);
        adapter= new CatalogoAdapter(lista);
        jrv_catalogo.setAdapter(adapter);

        mostras_recetas_categorias(id_categoria);
    }

    private void mostras_recetas_categorias(int id_categoria) {
        AsyncHttpClient ahc_mostrar_catalogo = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarCatalogo.php";
        RequestParams params= new RequestParams();
        params.add("idcategoria", String.valueOf(id_categoria));

        System.out.println( "MOSTRAR-CATALOGO ACTIVITY: " + id_categoria);
        ahc_mostrar_catalogo.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Receta(
                                    jsonArray.getJSONObject(i).getInt("idreceta"),
                                    jsonArray.getJSONObject(i).getString("nombre_receta"),
                                    jsonArray.getJSONObject(i).getString("descripcion_receta"),
                                    jsonArray.getJSONObject(i).getString("imagen_receta"),
                                    jsonArray.getJSONObject(i).getInt("idcategoria")));
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
}