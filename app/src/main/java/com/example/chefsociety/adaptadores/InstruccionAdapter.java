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
import com.example.chefsociety.actividades.MisRecetasDetalleActivity;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Instruccion;
import com.example.chefsociety.clases.Receta;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InstruccionAdapter extends  RecyclerView.Adapter<InstruccionAdapter.ViewHolder> {

    private List<Instruccion> lista_instrucciones;

    public InstruccionAdapter(List<Instruccion> lista_instrucciones) {
        this.lista_instrucciones=lista_instrucciones;
    }
    @NonNull
    @NotNull
    @Override
    public InstruccionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instruccion,parent,false);
        // devolver la vista
        return new InstruccionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InstruccionAdapter.ViewHolder holder, int position) {
        Instruccion instruccion = lista_instrucciones.get(position);
        holder.descripcion_instruccion.setText(instruccion.getDescripcion_instruccion());
        holder.nropaso.setText(String.valueOf(instruccion.getNropaso()));
        // agregando los items
        holder.item_instruccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(v.getContext(),"ID: "+ lista_instrucciones.get(position).getNropaso(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return lista_instrucciones.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_instruccion;
        TextView descripcion_instruccion, nropaso;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene item instruccion
            item_instruccion=itemView.findViewById(R.id.cv_item_misinstrucciones);
            descripcion_instruccion=itemView.findViewById(R.id.lbl_item_descripcion_instruccion);
            nropaso = itemView.findViewById(R.id.lbl_item_nropaso_instruccion);

        }
    }
}
