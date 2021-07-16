package com.example.chefsociety.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Hash;
import com.example.chefsociety.clases.InternaDB;
import com.example.chefsociety.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    TextView opc_nombres,opc_apellidos,opc_correo,opc_nrodocumento;
    Spinner jsp_pais,jsp_tipo_documento ;
    Button jbtn_cerrar_sesion, opc_btn_actualizar_datos ,btnActualizarImg ; // variable
    ImageButton btn_regresar_perfil;
    ImageView opc_foto_auto;
    private static final int REQUEST_CODE_GALLERY=1; // foto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        llenar_datos();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opc_btn_cerrar_sesion:
                cerrar_sesion();
                break;
            case R.id.opc_btn_actualizar_datos:
                actualizarDatos();
                break;
            case R.id.btnActualizarImg:
                elegir_foto();
                break;
            case R.id.ib_btn_regresar_perfil:
                principal();
                break;
        }
    }


    private void llenar_datos(){
        // instanciando
        opc_nombres = findViewById(R.id.opc_txt_cambiar_nombres);
        opc_apellidos = findViewById(R.id.opc_txt_cambiar_apellidos);
        opc_correo=findViewById(R.id.opc_txt_cambiar_correo);
        opc_nrodocumento=findViewById(R.id.opc_txt_cambiar_nro_documento);
        jsp_pais=findViewById(R.id.opc_sp_cambiar_pais);
        jsp_tipo_documento=findViewById(R.id.opc_sp_cambiar_tipo_documento);
        opc_foto_auto=findViewById(R.id.imagen_perfil);
        jbtn_cerrar_sesion =findViewById(R.id.opc_btn_cerrar_sesion);
        opc_btn_actualizar_datos = findViewById(R.id.opc_btn_actualizar_datos);
        btnActualizarImg = findViewById(R.id.btnActualizarImg);
        btn_regresar_perfil=findViewById(R.id.ib_btn_regresar_perfil);
        // Seteando Variables
        opc_nombres.setText(LoginActivity.usuario.getNombres());
        opc_apellidos.setText(LoginActivity.usuario.getApellidos());
        opc_correo.setText(LoginActivity.usuario.getCorreo());
        opc_nrodocumento.setText(LoginActivity.usuario.getNrodocumento());
        opc_foto_auto.setImageBitmap(base64_to_image_view(LoginActivity.usuario.getImagen_usuario()));
        jbtn_cerrar_sesion.setOnClickListener(this);
        opc_btn_actualizar_datos.setOnClickListener(this);
        btnActualizarImg.setOnClickListener(this);
        btn_regresar_perfil.setOnClickListener(this);
        llenar_paises();
        llenar_tipodocumento();
    }

    private void llenar_paises() {
        AsyncHttpClient async_paises = new AsyncHttpClient();
        String s_url = "http://compradw.com/paises.php"; // url de la web service para los paises
        async_paises.post(s_url, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[] s_paises = new String[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            s_paises[i] = jsonArray.getJSONObject(i).getString("nombre_pais");
                        }
                        jsp_pais.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_paises));
                        jsp_pais.setSelection(LoginActivity.usuario.getIdpais()-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error cod: " + statusCode,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar los paises",Toast.LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void llenar_tipodocumento(){
        AsyncHttpClient async_tipodocumentos = new AsyncHttpClient();
        String s_url = "http://compradw.com/tipodocumentos.php"; //
        async_tipodocumentos.post(s_url, null, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[]  s_tipodocumento = new String[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            s_tipodocumento[i] = jsonArray.getJSONObject(i).getString("descripcion");
                        }
                        jsp_tipo_documento.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_tipodocumento));
                        jsp_tipo_documento.setSelection(LoginActivity.usuario.getIdtipodocumento()-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error cod: " + statusCode,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(),"Error al cargar los tipo documento",Toast.LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void actualizarDatos() {
        // llamar al web service para regisstrar
        Hash hash = new Hash();
        AsyncHttpClient HttpActualizar = new AsyncHttpClient();
        String s_url = "http://compradw.com/actualizarDatos.php";

        RequestParams params = new RequestParams();
        params.add("idusuario",String.valueOf(LoginActivity.usuario.getIdusuario()));
        params.add("nombres",opc_nombres.getText().toString().trim());
        params.add("apellidos",opc_apellidos.getText().toString().trim());
        params.add("correo",opc_correo.getText().toString());
        params.add("idtipodocumento",String.valueOf(jsp_tipo_documento.getSelectedItemPosition()));
        params.add("nrodocumento",opc_nrodocumento.getText().toString().trim());
        params.add("imagen_usuario",image_view_to_base64(opc_foto_auto));
        params.add("idpais",String.valueOf(jsp_pais.getSelectedItemPosition()));

        HttpActualizar.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if( statusCode==200 ){
                    int ret_val = rawJsonResponse.length() ==0 ? 0 : Integer.parseInt(rawJsonResponse);
                    if(ret_val == 1){
                        LoginActivity.usuario.setNombres(opc_nombres.getText().toString().trim());
                        LoginActivity.usuario.setApellidos(opc_apellidos.getText().toString().trim());
                        LoginActivity.usuario.setIdtipodocumento(jsp_tipo_documento.getSelectedItemPosition());
                        LoginActivity.usuario.setNrodocumento(opc_nrodocumento.getText().toString().trim());
                        LoginActivity.usuario.setCorreo(opc_correo.getText().toString().trim());
                        LoginActivity.usuario.setImagen_usuario(image_view_to_base64(opc_foto_auto));
                        LoginActivity.usuario.setIdpais(jsp_pais.getSelectedItemPosition());
                        Toast.makeText(getApplicationContext(),"Datos Actualizados!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR actualizar datos : " + statusCode ,Toast.LENGTH_SHORT).show();
                System.out.println("ERROR DE REGISTRAR USUARIO" + statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    // PERMISOS PARA SUBIR LA FOTO
    private void elegir_foto() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY);
    }

    @Override
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(resultCode == RESULT_OK && data != null){
                Uri uri= data.getData();
                opc_foto_auto.setImageURI(uri);
            }
            else
                Toast.makeText(this,"Debe elegir una imagen",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private Bitmap base64_to_image_view(String imagenString) {
        byte[] decodeString = Base64.decode(imagenString,Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        return decodeByte;
    }

    private void cerrar_sesion() {
        // objeto de la db
        InternaDB bd=new InternaDB(getApplicationContext());
        bd.borrar_sesion();
        Intent i_login=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i_login);
    }

    private void principal() {
        Intent i_principal=new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i_principal);
    }
}