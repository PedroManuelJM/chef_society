package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.adaptadores.IngredienteAdapter;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MostrarIngredientesActivity extends AppCompatActivity {

    RecyclerView jrv_ingrediente;
    ArrayList<Ingrediente> lista;
    IngredienteAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_ingredientes);

        jrv_ingrediente=findViewById(R.id.rvMostrarIngredientes);
        lista= new ArrayList<>();
        // para que funcione --> necesita un manejador de linearlayout
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_ingrediente.setLayoutManager(manager);
        adapter= new IngredienteAdapter(lista);
        jrv_ingrediente.setAdapter(adapter);

        String id_receta = getIntent().getStringExtra("IDRECETA");

        //String nombre_receta = getIntent().getStringExtra("NOMBRERECETA");
        System.out.println( "MOSTRAR-INGREDIENTE: " + id_receta );
        //función para consultar la base de datos
        mostrar_ingredientes();
    }


    private void mostrar_ingredientes() {
        AsyncHttpClient ahc_mostrar_ingredientes = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarIngrediente.php";
        RequestParams params= new RequestParams();
        String id_receta = getIntent().getStringExtra("IDRECETA");
        params.add("idreceta", id_receta);

        System.out.println( "MOSTRAR-INGREDIENTE: " + id_receta);
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
}