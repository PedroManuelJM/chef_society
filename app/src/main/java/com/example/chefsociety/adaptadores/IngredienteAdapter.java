package com.example.chefsociety.adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chefsociety.R;
import com.example.chefsociety.actividades.EditarIngredienteActivity;
import com.example.chefsociety.clases.Ingrediente;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IngredienteAdapter extends  RecyclerView.Adapter<IngredienteAdapter.ViewHolder>{

    private List<Ingrediente> lista_ingredientes;

    public IngredienteAdapter(List<Ingrediente> lista_ingredientes) {
        this.lista_ingredientes =lista_ingredientes;
    }
    @NonNull
    @NotNull
    @Override
    public IngredienteAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingrediente,parent,false);
        // devolver la vista
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull IngredienteAdapter.ViewHolder holder, int position) {
        Ingrediente ingrediente = lista_ingredientes.get(position);
        // recuperando los datos
        holder.nombre_ingrediente.setText(ingrediente.getNombre_ingrediente());
        holder.cantidad_ingrediente.setText(String.valueOf(ingrediente.getCantidad_ingrediente()));
        holder.idunidad_medida.setText(String.valueOf(ingrediente.getIdunidadmedida()));
        holder.nombre_unidad_medida.setText(ingrediente.getNombre_unidadmedida());

        holder.item_ingrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"ID: "+ lista_ingredientes.get(position).getIdingrediente(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_ingredientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_ingrediente;
        TextView nombre_ingrediente,cantidad_ingrediente,idunidad_medida,nombre_unidad_medida;
       // ImageView Foto_auto;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene de item_ingrediente xml
            item_ingrediente=itemView.findViewById(R.id.cv_item_mis_ingrediente);
            nombre_ingrediente=itemView.findViewById(R.id.lbl_item_nomb_ingrediente);
            cantidad_ingrediente=itemView.findViewById(R.id.lbl_item_cant_ingrediente);
            idunidad_medida=itemView.findViewById(R.id.lbl_item_idunidadmedida_ingrediente);
            nombre_unidad_medida=itemView.findViewById(R.id.lbl_item_nombreunidadmedida_ingrediente);

        }
    }
}
