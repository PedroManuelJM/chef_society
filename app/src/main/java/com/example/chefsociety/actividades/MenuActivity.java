package com.example.chefsociety.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.clases.EnlaceMenu;
import com.example.chefsociety.clases.Usuario;
import com.example.chefsociety.fragmentos.CatalogoFragment;
import com.example.chefsociety.fragmentos.FavoritosFragment;
import com.example.chefsociety.fragmentos.OpcionesFragment;
import com.example.chefsociety.fragmentos.RecetaFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class MenuActivity extends AppCompatActivity implements EnlaceMenu {

    Fragment[] fragments;
    private static final int CANTIDAD_FRAGMENTOS=4;
    public String[] jsp_paises ;
    public String[] jsp_tipo_documento ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Usuario usuario=(Usuario) getIntent().getSerializableExtra("usuario");

        inicializar_fragmentos(usuario);
        int id_boton = getIntent().getIntExtra("boton_clicked",-1);
        if(id_boton!=-1)
            menu(id_boton);
    }

    private void inicializar_fragmentos(Usuario usuario) {
        llenar_paises();
        llenar_tipodocumento();
        fragments = new Fragment[CANTIDAD_FRAGMENTOS];
        fragments[0]= new CatalogoFragment();
        fragments[1]= new RecetaFragment();
        fragments[2]= new FavoritosFragment();
        fragments[3]= new OpcionesFragment(); // cuando lleguemos a mapas

    }

    @Override
    public void menu(int id_boton) {
        // gestionar fragmentos
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rl_menu,fragments[id_boton]);
        fragmentTransaction.commit();
    }

    private void llenar_tipodocumento(){
        String[] data;
        AsyncHttpClient async_tipodocumentos = new AsyncHttpClient();
        String s_url = "http://compradw.com/tipodocumentos.php"; //
        async_tipodocumentos.post(s_url, null, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);
                        String[]  s_tipoDocumento = new String[jsonArray.length()+1];
                        s_tipoDocumento[0] = "Seleccione el tipo de documento";
                        for(int i=1;i<=jsonArray.length();i++){
                            s_tipoDocumento[i] = jsonArray.getJSONObject(i-1).getString("descripcion");
                        }

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
                        jsp_paises = s_paises;
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



}