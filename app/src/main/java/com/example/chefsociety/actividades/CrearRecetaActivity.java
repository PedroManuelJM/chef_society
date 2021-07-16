package com.example.chefsociety.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class CrearRecetaActivity extends AppCompatActivity implements View.OnClickListener {

    EditText jtxt_nombre_receta,jtxt_descripcion_receta;
    Button jbtn_continuar_receta, jbtn_cargar_imagen_receta ,jbtn_mis_recetas; // variable
    Spinner jsp_categoria_receta;
    ImageView jiv_foto_receta , i_btn_regresar;
    ImageView imagendefault;
    Boolean bandera;
    private static final int REQUEST_CODE_GALLERY=1; // foto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_receta);

        jiv_foto_receta=findViewById(R.id.imgFotoReceta);
        jbtn_continuar_receta = findViewById(R.id.rec_btn_continuar_receta);
        jbtn_cargar_imagen_receta=findViewById(R.id.rec_btn_imagen_receta);
        jbtn_mis_recetas=findViewById(R.id.rec_btn_mis_recetas);
        jsp_categoria_receta= findViewById(R.id.rec_sp_categoria_receta);
        jtxt_nombre_receta=findViewById(R.id.rec_txt_nombre_receta);
        jtxt_descripcion_receta=findViewById(R.id.rec_txt_descripcion_receta);
        i_btn_regresar=findViewById(R.id.ib_btn_regresar_crear_receta);
        //
        bandera=true;
        imagendefault = jiv_foto_receta;
        jbtn_mis_recetas.setOnClickListener(this);
        jbtn_cargar_imagen_receta.setOnClickListener(this);
        jbtn_continuar_receta.setOnClickListener(this);
        i_btn_regresar.setOnClickListener(this);

        llenar_categoria();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rec_btn_continuar_receta:
                validarDataReceta();
                break;
            case R.id.rec_btn_imagen_receta:
                elegir_foto();
                break;
            case R.id.rec_btn_mis_recetas:
                mis_recetas();
                break;
            case R.id.ib_btn_regresar_crear_receta:
                principal();
                break;
        }
    }

    private void validarDataReceta() {

        if(jtxt_nombre_receta.getText().toString().isEmpty()){
            jtxt_nombre_receta.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese el nombre de la Receta",Toast.LENGTH_SHORT).show();
        }
        else if(jtxt_descripcion_receta.getText().toString().isEmpty()){
            jtxt_descripcion_receta.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese la descripcion de la Receta",Toast.LENGTH_SHORT).show();
        }
        else if(bandera)
          Toast.makeText(this,"Ingrese la imagen de la Receta",Toast.LENGTH_SHORT).show();
        else if(jsp_categoria_receta.getSelectedItemPosition()==0)
            Toast.makeText(this,"Ingrese la categoria de la Receta",Toast.LENGTH_SHORT).show();
        else
            registrar_receta();
    }

    @Override
    // PERMISOS PARA SUBIR LA FOTO
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i_file_chooser = new Intent(Intent.ACTION_PICK);
                i_file_chooser.setType("image/*");
                startActivityForResult(i_file_chooser,REQUEST_CODE_GALLERY);
            }
            else
                Toast.makeText(this,"No se puede acceder al almacenamiento externo",Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void elegir_foto() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(resultCode == RESULT_OK && data != null){
                Uri uri= data.getData();
                jiv_foto_receta.setImageURI(uri);
                bandera=false;
            }
            else
                Toast.makeText(this,"Debe elegir una imagen",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void llenar_categoria() {
        AsyncHttpClient async_tipodocumentos = new AsyncHttpClient();
        String s_url = "http://compradw.com/categorias.php"; //
        async_tipodocumentos.post(s_url, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[]  s_tipodocumento = new String[jsonArray.length()+1];
                        s_tipodocumento[0] = "Seleccione la categoria";
                        for(int i=1;i<=jsonArray.length();i++){
                            s_tipodocumento[i] = jsonArray.getJSONObject(i-1).getString("nombre_categoria");
                        }
                        jsp_categoria_receta.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_tipodocumento));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error cod: " + statusCode,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar CATEGORIA RECETA",Toast.LENGTH_LONG).show();
            }
            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    // imagen en basse64
    private String image_view_to_base64(ImageView jiv_foto_auto) {
        Bitmap bitmap = ((BitmapDrawable) jiv_foto_auto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        String imagen = Base64.encodeToString(byteArray,Base64.DEFAULT);
        return imagen;
    }

    private void mis_recetas(){
        Intent i_mis_recetas = new Intent(getApplicationContext(),MisRecetasActivity.class);
        startActivity(i_mis_recetas);
    }

    private void registrar_receta() {
        AsyncHttpClient ahc_registrar_receta= new AsyncHttpClient();
        String s_url = "http://compradw.com/registro_receta.php";

        RequestParams params = new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));
        params.add("nombre_receta",jtxt_nombre_receta.getText().toString());
        params.add("descripcion_receta",jtxt_descripcion_receta.getText().toString());
        params.add("imagen_receta",image_view_to_base64(jiv_foto_receta));
        params.add("idcategoria",String.valueOf(jsp_categoria_receta.getSelectedItemPosition()));

        ahc_registrar_receta.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        Toast.makeText(getApplicationContext(),"Receta registrada!!",Toast.LENGTH_SHORT).show();
                        Intent i_ingrediente = new Intent(getApplicationContext(),IngredienteActivity.class);
                        finish();
                        startActivity(i_ingrediente);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR DE REGISTRAR RECETA" + statusCode ,Toast.LENGTH_SHORT).show();
                System.out.println("ERROR DE REGISTRAR USUARIO" + statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });

    }

    private void principal() {
        Intent i_principal=new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i_principal);
    }
}