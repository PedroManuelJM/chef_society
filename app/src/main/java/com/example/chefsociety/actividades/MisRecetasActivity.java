package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.adaptadores.IngredienteAdapter;
import com.example.chefsociety.adaptadores.MisRecetasAdapter;

import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MisRecetasActivity extends AppCompatActivity {
    RecyclerView jrv_misrecetas;
    ArrayList<Receta> lista;
    MisRecetasAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recetas);

        jrv_misrecetas=findViewById(R.id.rvMostrarMisRecetas);
        lista= new ArrayList<>();
        // para que funcione --> necesita un manejador de linearlayout
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_misrecetas.setLayoutManager(manager);
        adapter= new MisRecetasAdapter(lista);
        jrv_misrecetas.setAdapter(adapter);

        mostrar_misrecetas();
    }

    private void mostrar_misrecetas() {
        AsyncHttpClient ahc_mostrar_misrecetas = new AsyncHttpClient();
        String s_url ="http://compradw.com/MisRecetasUnicas.php";
        RequestParams params= new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));

        System.out.println( "Mis Recetas: " + LoginActivity.usuario.getIdusuario() );
        ahc_mostrar_misrecetas.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                               lista.add(new Receta(jsonArray.getJSONObject(i).getInt("idreceta"),
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