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
import com.example.chefsociety.actividades.CatalogoActivity;
import com.example.chefsociety.actividades.DetalleCatalogoActivity;
import com.example.chefsociety.clases.Categoria;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.Base64;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ViewHolder>{

    private List<Receta> lista_catalogos;

    public CatalogoAdapter(List<Receta> lista_catalogos) {
        this.lista_catalogos =lista_catalogos;
    }

    @NonNull
    @NotNull
    @Override
    public CatalogoAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalogo,parent,false);
        // devolver la vista
        return new CatalogoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogoAdapter.ViewHolder holder, int position) {
        Receta receta= lista_catalogos.get(position);
        holder.nombre_receta.setText(receta.getNombre_receta());
        holder.descripcion_receta.setText(receta.getDescripcion_receta());
        String imagen = receta.getImagen_receta();
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        holder.Foto_receta.setImageBitmap(bitmap);
        holder.item_catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_detallecatalogo= new Intent(v.getContext(), DetalleCatalogoActivity.class);
                i_detallecatalogo.putExtra("ID_CAT_RECETA",lista_catalogos.get(position).getIdreceta());// PASANDO LA DATA A DETALLE CATALOGO ACTIVITY
                i_detallecatalogo.putExtra("DET_CAT_NOMBRECETA",lista_catalogos.get(position).getNombre_receta());// PASANDO LA DATA A DETALLE CATALOGO ACTIVITY
                i_detallecatalogo.putExtra("DET_CAT_DESRECETA",lista_catalogos.get(position).getDescripcion_receta());// PASANDO LA DATA A DETALLE CATALOGO ACTIVITY
                i_detallecatalogo.putExtra("DET_CAT_IMGRECETA",lista_catalogos.get(position).getImagen_receta());// PASANDO LA DATA A DETALLE CATALOGO ACTIVITY
                v.getContext().startActivity(i_detallecatalogo);
              //  Toast.makeText(v.getContext(),"ID: "+ lista_catalogos.get(position).getIdreceta(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_catalogos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_catalogo;
        TextView nombre_receta,descripcion_receta;
        ImageView Foto_receta;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene de item_catalogo xml
            item_catalogo=itemView.findViewById(R.id.cv_item_catalogo);
            nombre_receta=itemView.findViewById(R.id.lbl_item_nomb_rec_catalogo);
            descripcion_receta=itemView.findViewById(R.id.lbl_item_descrip_rec_catalogo);
            Foto_receta=itemView.findViewById(R.id.ivItemCatalogo);

        }
    }
}
