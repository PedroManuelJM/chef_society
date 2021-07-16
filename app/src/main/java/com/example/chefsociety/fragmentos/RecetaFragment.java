package com.example.chefsociety.fragmentos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.actividades.IngredienteActivity;
import com.example.chefsociety.actividades.LoginActivity;
import com.example.chefsociety.clases.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecetaFragment extends Fragment implements View.OnClickListener {

    private static final int RESULT_OK = -1 ;
    Button jbtn_continuar_receta, jbtn_cargar_imagen_receta; // variable
    Spinner jsp_categoria_receta;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecetaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecetaFragment newInstance(String param1, String param2) {
        RecetaFragment fragment = new RecetaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_receta, container, false);
        // instanciar variables
        jbtn_continuar_receta = vista.findViewById(R.id.rec_btn_continuar_receta);
        jsp_categoria_receta= vista.findViewById(R.id.rec_sp_categoria_receta);

        //Usuario usuario=(Usuario) getActivity().getIntent().getSerializableExtra("usuario");

        jbtn_continuar_receta.setOnClickListener(this);
        llenar_categoria(); // mostrando datos del spinner categoria
        return vista;
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
                        jsp_categoria_receta.setAdapter(new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item,
                                s_tipodocumento));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(),"Error cod: " + statusCode,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getActivity(),"Error al cargar CATEGORIA RECETA",Toast.LENGTH_LONG).show();
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
            case R.id.rec_btn_continuar_receta:
                vista_ingrediente();
                break;
        }
    }

    private void vista_ingrediente() {
        // LLAMANDO ACTIVITY INGREDIENTE

        Intent i_ingrediente=new Intent(getContext(), IngredienteActivity.class);
        startActivity(i_ingrediente);

    }
}