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
import com.example.chefsociety.adaptadores.CategoriasAdapter;
import com.example.chefsociety.adaptadores.MisRecetasAdapter;
import com.example.chefsociety.clases.Categoria;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MostrarCategoriasActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView jrv_categorias;
    ArrayList<Categoria> lista;
    CategoriasAdapter adapter = null;
    ImageButton ib_btn_regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_categorias);

        jrv_categorias=findViewById(R.id.rvMostrarCategorias);
        ib_btn_regresar=findViewById(R.id.ib_btn_regresar_principal);
        lista= new ArrayList<>();
        // para que funcione --> necesita un manejador de linearlayout
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_categorias.setLayoutManager(manager);
        adapter= new CategoriasAdapter(lista);
        jrv_categorias.setAdapter(adapter);


        ib_btn_regresar.setOnClickListener(this);
        mostrar_categorias();
    }

    private void mostrar_categorias() {
        AsyncHttpClient ahc_mostrar_categorias= new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarCategoria.php";
        RequestParams params= new RequestParams();
        params.add("ID", "");

        ahc_mostrar_categorias.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Categoria(jsonArray.getJSONObject(i).getInt("idcategoria"),
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
            case R.id.ib_btn_regresar_principal:
                regresar();
                break;
        }

    }

    private void regresar() {
        Intent i_regresar = new Intent(getApplicationContext(),PrincipalActivity.class);
        startActivity(i_regresar);
    }
}