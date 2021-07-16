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
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Hash;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener  {

    EditText jtxt_nro_documento,jtxt_nombres,jtxt_apellidos,jtxt_correo,
             jtxt_clave,jtxt_confirmar_clave;
    Spinner  jsp_paises,jsp_tipo_documento ;
    CheckBox jchk_terminos;
    Button jbtn_registrar,jbtn_cancelar,jbtn_elegir;
    ImageView jiv_foto_auto;
    Boolean bandera_imagen;
    private static final int REQUEST_CODE_GALLERY=1; // foto
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        llenarDatos();

    }

    private void llenarDatos(){
        jtxt_nombres=findViewById(R.id.reg_txt_nombre);
        jtxt_apellidos=findViewById(R.id.reg_txt_apellidos);
        jsp_tipo_documento=findViewById(R.id.reg_sp_tipo_documento);
        jtxt_nro_documento=findViewById(R.id.reg_txt_numero_documento);
        jtxt_correo=findViewById(R.id.reg_txt_correo);
        jtxt_clave=findViewById(R.id.reg_txt_clave);
        jtxt_confirmar_clave=findViewById(R.id.reg_txt_confirmar_clave);
        jiv_foto_auto=findViewById(R.id.imgFotoAuto);
        jsp_paises=findViewById(R.id.reg_sp_pais);

        jchk_terminos=findViewById(R.id.reg_chk_terminos);
        jbtn_registrar=findViewById(R.id.reg_btn_registrar);
        jbtn_cancelar=findViewById(R.id.reg_btn_cancelar);
        jbtn_elegir=findViewById(R.id.btnElegir);
        bandera_imagen=true;
        // comunicacion con el Web-Services -> "tipoDocumento" y "paises"
        llenar_tipodocumento();
        llenar_paises();

        jbtn_elegir.setOnClickListener(this);
        jchk_terminos.setOnClickListener(this);
        jbtn_registrar.setOnClickListener(this);
        jbtn_cancelar.setOnClickListener(this);
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
                        String[]  s_tipodocumento = new String[jsonArray.length()+1];
                        s_tipodocumento[0] = "Seleccione el tipo de documento";
                        for(int i=1;i<=jsonArray.length();i++){
                            s_tipodocumento[i] = jsonArray.getJSONObject(i-1).getString("descripcion");
                        }
                        jsp_tipo_documento.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
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
                Toast.makeText(getApplicationContext(),"Error al cargar los tipo documento",Toast.LENGTH_LONG).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
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
                        String[] s_paises = new String[jsonArray.length()+1];
                        s_paises[0] = "Seleccione País";
                        for(int i=1;i<=jsonArray.length();i++){
                            s_paises[i] = jsonArray.getJSONObject(i-1).getString("nombre_pais");
                        }
                        jsp_paises.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_paises));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnElegir:
                elegir_foto();
                break;
            case R.id.reg_chk_terminos:
                aceptar_terminos();
                break;
            case R.id.reg_btn_registrar:
                registrar_nuevo_usuario();
                break;
            case R.id.reg_btn_cancelar:
                cancelar_registro();
                break;

        }
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
                jiv_foto_auto.setImageURI(uri);
                bandera_imagen=false;
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

    private void aceptar_terminos() {
        jbtn_registrar.setEnabled(jchk_terminos.isChecked()? true : false);
    }
    private void registrar_nuevo_usuario() {
        // llamar al web service para regisstrar
        //Validaciones antes de registrar
        boolean validacion = Campos();
        boolean validarCorreo = false;
        if(validacion){
            validarCorreo = validarEmail(jtxt_correo.getText().toString().trim());
            if(validarCorreo){

            }else{
                jtxt_correo.setError("Ingrese un email válido.");
            }
        }

        if(validarCorreo==true && validacion==true ) {
            Hash hash = new Hash();
            AsyncHttpClient registrar_usuario = new AsyncHttpClient();
            String s_url = "http://compradw.com/registro_usuario.php";

            RequestParams params = new RequestParams();
            params.add("nombres", jtxt_nombres.getText().toString().trim());
            params.add("apellidos", jtxt_apellidos.getText().toString().trim());
            params.add("idtipodocumento", String.valueOf(jsp_tipo_documento.getSelectedItemPosition()));
            params.add("nrodocumento", jtxt_nro_documento.getText().toString().trim());
            params.add("correo", jtxt_correo.getText().toString());
            params.add("clave", hash.StringToHash(jtxt_clave.getText().toString().trim(), "SHA1"));
            params.add("imagen_usuario", image_view_to_base64(jiv_foto_auto)); // imagen convertido en cadena
            params.add("idpais", String.valueOf(jsp_paises.getSelectedItemPosition()));

            registrar_usuario.post(s_url, params, new BaseJsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                    if (statusCode == 200) {
                        int ret_val = rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse);
                        if (ret_val == 1) {
                            jtxt_nombres.setText("");
                            jtxt_apellidos.setText("");
                            jtxt_nro_documento.setText("");
                            jtxt_correo.setText("");
                            jtxt_clave.setText("");
                            jiv_foto_auto.setImageResource(R.mipmap.ic_launcher);
                            Toast.makeText(getApplicationContext(), "Usuario registrado!!", Toast.LENGTH_SHORT).show();
                            Intent i_login = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i_login);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                    Toast.makeText(getApplicationContext(), "ERROR DE REGISTRAR USUARIO" + statusCode, Toast.LENGTH_SHORT).show();
                    System.out.println("ERROR DE REGISTRAR USUARIO" + statusCode);
                }

                @Override
                protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    return null;
                }
            });
        }
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean Campos() {
        boolean validacion=true;
        if(jtxt_nombres.getText().toString().trim().isEmpty()){validacion=false;jtxt_nombres.setError("El campo no puede estar vacío");}
        if(jtxt_apellidos.getText().toString().trim().isEmpty()){validacion=false;jtxt_apellidos.setError("El campo no puede estar vacío");}
        if(jtxt_correo.getText().toString().trim().isEmpty()){validacion=false;jtxt_correo.setError("El campo no puede estar vacío");}
        if(jtxt_clave.getText().toString().trim().isEmpty()){validacion=false;jtxt_clave.setError("El campo no puede estar vacío");}
        if(jtxt_confirmar_clave.getText().toString().trim().isEmpty()){validacion=false;jtxt_confirmar_clave.setError("El campo no puede estar vacío");}
        if(!jtxt_clave.getText().toString().trim().equals(jtxt_confirmar_clave.getText().toString().trim()))
        {validacion=false;jtxt_confirmar_clave.setError("Las claves no son iguales");}
        if(jsp_tipo_documento.getSelectedItemPosition() == 0 ){
            validacion=false;
            Toast.makeText(this,"Seleccione el tipo de documento",Toast.LENGTH_SHORT).show();}
        else if(jsp_paises.getSelectedItemPosition() == 0)
        { validacion=false;
            Toast.makeText(this,"Seleccione el país",Toast.LENGTH_SHORT).show();}
        else if(bandera_imagen)
        {validacion=false;
         Toast.makeText(this,"Suba su imagen de usuario",Toast.LENGTH_SHORT).show();}
        return  validacion;
    }

    private void cancelar_registro() {
        Intent i_login= new Intent(this,LoginActivity.class);
        startActivity(i_login);
        finish();// elimine temporal el form_registro
    }


}