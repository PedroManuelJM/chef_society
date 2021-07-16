package com.example.chefsociety.adaptadores;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.chefsociety.actividades.MisRecetasActivity;
import com.example.chefsociety.actividades.MisRecetasDetalleActivity;
import com.example.chefsociety.clases.Ingrediente;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.Base64;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MisRecetasAdapter extends  RecyclerView.Adapter<MisRecetasAdapter.ViewHolder>{

    private List<Receta> lista_misrecetas;

    public MisRecetasAdapter (List<Receta> lista_misrecetas) {
        this.lista_misrecetas =lista_misrecetas;
    }
    @NonNull
    @NotNull
    @Override
    public MisRecetasAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_misrecetas,parent,false);
        // devolver la vista
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MisRecetasAdapter.ViewHolder holder, int position) {
        Receta receta = lista_misrecetas.get(position);
        holder.nombre_receta.setText(receta.getNombre_receta());
        holder.descripcion_receta.setText(receta.getDescripcion_receta());
        String imagen = receta.getImagen_receta();
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        holder.Foto_receta.setImageBitmap(bitmap);
        holder.item_receta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_detalle = new Intent(v.getContext(), MisRecetasDetalleActivity.class);
                i_detalle.putExtra("ID_RECETA",lista_misrecetas.get(position).getIdreceta());// PASANDO LA DATA  A MISDETALLESACTIVITY
                i_detalle.putExtra("NOMBRE_RECETA",lista_misrecetas.get(position).getNombre_receta()); // PASANDO LA DATA  A MISDETALLESACTIVITY
                i_detalle.putExtra("DESCRIPCION_RECETA",lista_misrecetas.get(position).getDescripcion_receta()); // PASANDO LA DATA  A MISDETALLESACTIVITY
                i_detalle.putExtra("IMAGEN_RECETA",lista_misrecetas.get(position).getImagen_receta()); // PASANDO LA DATA  A MISDETALLESACTIVITY
                v.getContext().startActivity(i_detalle);
              //  Toast.makeText(v.getContext(),"ID: "+ lista_misrecetas.get(position).getIdreceta(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_misrecetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_receta;
        TextView nombre_receta,descripcion_receta;
        ImageView Foto_receta;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            item_receta=itemView.findViewById(R.id.cv_item_misrecetas);
            nombre_receta=itemView.findViewById(R.id.lbl_item_nomb_rec_misrecetas);
            descripcion_receta=itemView.findViewById(R.id.lbl_item_descrip_rec_misrecetas);
            Foto_receta=itemView.findViewById(R.id.ivItemMisRecetas);


        }
    }
}
