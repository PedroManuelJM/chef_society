package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.adaptadores.FavoritoAdapter;
import com.example.chefsociety.adaptadores.IngredienteAdapter;
import com.example.chefsociety.clases.Favorito;
import com.example.chefsociety.clases.Ingrediente;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecetasFavoritasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView jrv_favoritos;
    ArrayList<Favorito> lista;
    FavoritoAdapter adapter = null;
    ImageButton ib_btn_regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_favoritas);

        ib_btn_regresar=findViewById(R.id.ib_btn_regresar_receta_favorita);
        // LISTAR RecyclerView --> RECETAS FAVORITOS ADAPTER
        jrv_favoritos=findViewById(R.id.rvMostrarMisRecetasFavoritas);
        lista= new ArrayList<>();
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_favoritos.setLayoutManager(manager);
        adapter= new FavoritoAdapter(lista);
        jrv_favoritos.setAdapter(adapter);

        ib_btn_regresar.setOnClickListener(this);
        mostrar_recetas_favoritas();

    }

    private void mostrar_recetas_favoritas() {

        AsyncHttpClient ahc_mostrar_recetas_favoritas = new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarFavoritoReceta.php";
        RequestParams params= new RequestParams();

        params.add("idusuario", String.valueOf(LoginActivity.usuario.getIdusuario()));

        ahc_mostrar_recetas_favoritas.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){

                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Favorito (
                                    jsonArray.getJSONObject(i).getInt("idusuario"),
                                    jsonArray.getJSONObject(i).getInt("idreceta"),
                                    jsonArray.getJSONObject(i).getString("nombre_receta"),
                                    jsonArray.getJSONObject(i).getString("descripcion_receta"),
                                    jsonArray.getJSONObject(i).getString("imagen_receta"),
                                    jsonArray.getJSONObject(i).getString("nombre_categoria"),
                                    jsonArray.getJSONObject(i).getString("imagen_categoria")));
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
            case R.id.ib_btn_regresar_receta_favorita:
                regresar();
                break;
        }
    }

    private void regresar() {
        Intent i_regresar = new Intent(getApplicationContext(),PrincipalActivity.class);
        startActivity(i_regresar);
    }
}