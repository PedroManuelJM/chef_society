package com.example.chefsociety.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chefsociety.R;
import com.example.chefsociety.actividades.LoginActivity;
import com.example.chefsociety.clases.InternaDB;
import com.example.chefsociety.clases.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpcionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpcionesFragment extends Fragment implements View.OnClickListener {

    EditText jtxt_nro_documento,jtxt_nombre,jtxt_apellidos,jtxt_correo;
    Spinner jsp_paises,jsp_tipo_documento ;

    Button jbtn_cerrar_sesion; // variable
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OpcionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpcionesFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static OpcionesFragment newInstance(String param1, String param2) {
        OpcionesFragment fragment = new OpcionesFragment();
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
        View vista= inflater.inflate(R.layout.fragment_opciones, container, false);
        jbtn_cerrar_sesion = vista.findViewById(R.id.opc_btn_cerrar_sesion);
        jbtn_cerrar_sesion.setOnClickListener(this);
        // instanciar variables
        jtxt_nombre=vista.findViewById(R.id.opc_txt_cambiar_nombres);
        jtxt_apellidos=vista.findViewById(R.id.opc_txt_cambiar_apellidos);
        jtxt_correo=vista.findViewById(R.id.opc_txt_cambiar_correo);
        jsp_tipo_documento=vista.findViewById(R.id.opc_sp_cambiar_tipo_documento);
        jtxt_nro_documento=vista.findViewById(R.id.opc_txt_cambiar_nro_documento);
        jsp_paises=vista.findViewById(R.id.opc_sp_cambiar_pais);

        jsp_tipo_documento.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"DNI","PASAPORTE"}));

        jsp_paises.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Perú","Brazil"}));
        return vista;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opc_btn_cerrar_sesion:
                cerrar_sesion();
                break;
        }
    }

    private void cerrar_sesion() {
        // objeto de la db
        InternaDB bd=new InternaDB(getContext());
        bd.borrar_sesion();
        Intent i_login=new Intent(getContext(), LoginActivity.class);
        startActivity(i_login);
    }
}