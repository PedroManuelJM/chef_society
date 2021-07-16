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
import com.example.chefsociety.adaptadores.CatalogoAdapter;
import com.example.chefsociety.adaptadores.ComentarioAdapter;
import com.example.chefsociety.adaptadores.IngredienteAdapter;
import com.example.chefsociety.clases.Comentario;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MostrarComentariosActivity extends AppCompatActivity implements View.OnClickListener {

    public Receta receta= new Receta();
    TextView txt_nombre_receta,txt_descripcion_receta;
    ImageView jiv_foto_receta;
    ImageButton ib_btn_agregar;
    RecyclerView jrv_comentario;
    ArrayList<Comentario> lista;
    ComentarioAdapter adapter = null;
    String id_receta="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_comentarios);

        //enlazando
        txt_nombre_receta=findViewById(R.id.Titulo_ComentarioReceta);
        txt_descripcion_receta=findViewById(R.id.Descripcion_ComentarioReceta);
        jrv_comentario=findViewById(R.id.rvMostrarComentarios); // id de RecyclerView
        jiv_foto_receta=findViewById(R.id.Imagen_ComentarioReceta); //imagen
        ib_btn_agregar=findViewById(R.id.ib_comentar);

        // recibiendo
        id_receta= getIntent().getStringExtra("COM_ID_RECETA");
        String nombre= getIntent().getStringExtra("COM_NOM_RECETA");
        String descripcion= getIntent().getStringExtra("COM_DES_RECETA");
        String imagen= getIntent().getStringExtra("COM_IMG_RECETA");

        receta.setIdreceta(Integer.parseInt(id_receta));
        receta.setNombre_receta(nombre);
        receta.setDescripcion_receta(descripcion);
        receta.setImagen_receta(imagen);

       // guardar=imagen;
       // System.out.println("COMENTARIO - ID REC : "+ imagen);

        // mostrando
        txt_nombre_receta.setText(nombre);
        txt_descripcion_receta.setText(descripcion);
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
        jiv_foto_receta.setImageBitmap(bitmap);


        //Toast.makeText(getApplicationContext(),"COMENTARIO - ID REC : "+ id_receta,Toast.LENGTH_SHORT).show();

        // lista comentario
        lista= new ArrayList<>();
        LinearLayoutManager manager= new LinearLayoutManager(getApplicationContext());
        jrv_comentario.setLayoutManager(manager);
        adapter= new ComentarioAdapter(lista);
        jrv_comentario.setAdapter(adapter);

        ib_btn_agregar.setOnClickListener(this);
        mostrar_comentarios(id_receta);
    }

    private void mostrar_comentarios(String idreceta) {
        AsyncHttpClient ahc_mostrar_comentarios= new AsyncHttpClient();
        String s_url ="http://compradw.com/MostrarComentario.php";
        RequestParams params= new RequestParams();
        params.add("idreceta", idreceta);

        ahc_mostrar_comentarios.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode==200){
                    try {
                        JSONArray jsonArray= new JSONArray(rawJsonResponse);
                        lista.clear();
                        for (int i=0; i < jsonArray.length();i++){
                            lista.add(new Comentario(
                                    jsonArray.getJSONObject(i).getInt("idusuario"),
                                    jsonArray.getJSONObject(i).getInt("idreceta"),
                                    jsonArray.getJSONObject(i).getString("nombres"),
                                    jsonArray.getJSONObject(i).getString("apellidos"),
                                    jsonArray.getJSONObject(i).getString("imagen_usuario"),
                                    jsonArray.getJSONObject(i).getString("comentario"),
                                    jsonArray.getJSONObject(i).getString("fecha"),
                                    jsonArray.getJSONObject(i).getString("hora")));
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
            case R.id.ib_comentar:
                comentario();
                break;
        }
    }

    private void comentario() {
        Intent i_regresar = new Intent(getApplicationContext(),CrearComentarioActivity.class);
       //i_regresar.putExtra("REG_COM_ID_RECETA",id_receta);
        i_regresar.putExtra("RECETA",receta);

        startActivity(i_regresar);
    }

}