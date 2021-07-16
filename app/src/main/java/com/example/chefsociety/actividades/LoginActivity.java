package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.Hash;
import com.example.chefsociety.clases.InternaDB;
import com.example.chefsociety.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static Usuario usuario = new Usuario();
    EditText jtxt_correo,jtxt_clave;
    CheckBox jchk_recordar;
    Button jbtn_ingresar,jbtn_salir;
    TextView jlbl_registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        llenarDatos();

        // validar si el usuario recordo sesión
        InternaDB bd=new InternaDB(getApplicationContext());// creamos la base de datos

        if(bd.recordo_sesion()){// se verifica si recordo sesión y se extrae los datos
            validar_sesion(bd.buscar_campo("CORREO"),bd.buscar_campo("CLAVE"));
        }
    }

    private void llenarDatos(){
        // instanciar variables
        jtxt_correo=findViewById(R.id.log_txt_correo);
        jtxt_clave=findViewById(R.id.log_txt_clave);
        jchk_recordar=findViewById(R.id.log_chk_recordar);
        jbtn_ingresar=findViewById(R.id.log_btn_ingresar);
        jbtn_salir=findViewById(R.id.log_btn_salir);
        jlbl_registrar=findViewById(R.id.log_lbl_registro);

        jchk_recordar.setOnClickListener(this); // setearlo a todos los botones
        jbtn_ingresar.setOnClickListener(this);
        jbtn_salir.setOnClickListener(this);
        jlbl_registrar.setOnClickListener(this);

        // validar si el usuario recordo sesión
        // creamos la base de datos
        InternaDB bd=new InternaDB(getApplicationContext());
        // se verifica si recordo sesión y se extrae los datos
        if(bd.recordo_sesion()){
            validar_sesion(bd.buscar_campo("CORREO"),bd.buscar_campo("CLAVE"));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_chk_recordar:
                recordar_sesion(); // metodo
                break;
            case R.id.log_btn_ingresar:
                validarData();
                break;
            case R.id.log_btn_salir:
                salir_aplicacion();
                break;
            case R.id.log_lbl_registro:
                registrar_usuario();
                break;
        }
    }

    private void validar_sesion(String s_correo, String s_clave) {
        Hash hash=new Hash();
        InternaDB bd= new InternaDB(getApplicationContext());
        AsyncHttpClient ahc_login = new AsyncHttpClient();
        String s_url = "http://compradw.com/login.php";

        String s_hash_clave= bd.recordo_sesion() ? s_clave : hash.StringToHash(s_clave,"SHA1");

        RequestParams params = new RequestParams();
        params.add("correo",s_correo);

        ahc_login.post(s_url, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        if(jsonArray.length() >0){
                            int i_id_usuario = jsonArray.getJSONObject(0).getInt("idusuario");
                            if(i_id_usuario != -1){
                                // si esta marcado la opcióon recordar sesión
                                if(jchk_recordar.isChecked()){
                                    InternaDB bd= new InternaDB(getApplicationContext()); // la bd
                                    bd.agregar_usuario(s_correo,s_hash_clave); // agregando el usuario a la BD
                                }
                                if(s_hash_clave.equals(jsonArray.getJSONObject(0).getString("clave"))){

                                    usuario.setIdusuario(jsonArray.getJSONObject(0).getInt("idusuario")); // dato 1
                                    usuario.setNombres(jsonArray.getJSONObject(0).getString("nombres"));
                                    usuario.setApellidos(jsonArray.getJSONObject(0).getString("apellidos"));
                                    usuario.setIdtipodocumento(jsonArray.getJSONObject(0).getInt("idtipodocumento"));
                                    usuario.setNrodocumento(jsonArray.getJSONObject(0).getString("nrodocumento"));
                                    usuario.setCorreo(jsonArray.getJSONObject(0).getString("correo"));
                                    usuario.setClave(jsonArray.getJSONObject(0).getString("clave"));
                                    usuario.setImagen_usuario(jsonArray.getJSONObject(0).getString("imagen_usuario"));
                                    usuario.setIdpais(jsonArray.getJSONObject(0).getInt("idpais"));

                                    // enviando el objeto e inicializar la interfaz
                                     Intent i_principal = new Intent(getApplicationContext(),PrincipalActivity.class);
                                   // i_principal.putExtra("usuario",usuario);
                                    startActivity(i_principal);
                                }else{
                                    Toast.makeText(getApplicationContext(), "CLAVE INCORRECTA ",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "CORREO INCORRECTO ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "ERROR LOGIN " + statusCode ,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void recordar_sesion() {
        String s_recordar = jchk_recordar.isChecked() ? "activado":"desactivado";
        System.out.println("Recordar sesión: "+ s_recordar);
    }
    private void salir_aplicacion() {
        System.exit(1);
    }

    private void registrar_usuario() {
        Intent i_registro = new Intent(getApplicationContext(),RegistroActivity.class);
        startActivity(i_registro);
    }

    private void validarData(){
        if(jtxt_correo.getText().toString().trim().isEmpty()){
            jtxt_correo.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese el correo",Toast.LENGTH_SHORT).show();
        }
        else if(jtxt_clave.getText().toString().trim().isEmpty()){
            jtxt_clave.setError("El campo no puede estar vacío");
            Toast.makeText(this,"Ingrese la contraseña",Toast.LENGTH_SHORT).show();
        }
        else
            validar_sesion(jtxt_correo.getText().toString().trim(),jtxt_clave.getText().toString().trim());
    }
}