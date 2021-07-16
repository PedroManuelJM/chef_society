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
import com.example.chefsociety.actividades.DetalleCatalogoActivity;
import com.example.chefsociety.actividades.DettalleRecetaFavoritaActivity;
import com.example.chefsociety.clases.Favorito;
import com.example.chefsociety.clases.Receta;
import com.loopj.android.http.Base64;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavoritoAdapter extends  RecyclerView.Adapter<FavoritoAdapter.ViewHolder>{

    private List<Favorito> lista_favoritos;

    public FavoritoAdapter(List<Favorito> lista_favoritos) {
        this.lista_favoritos =lista_favoritos;
    }
    @NonNull
    @NotNull
    @Override
    public FavoritoAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorito,parent,false);
        // devolver la vista
        return new FavoritoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoritoAdapter.ViewHolder holder, int position) {

        Favorito favorito= lista_favoritos.get(position);

        holder.nombre_categoria.setText(favorito.getFav_nombre_categoria());
        holder.nombre_receta.setText(favorito.getFav_nombre_receta());
        holder.descripcion_receta.setText(favorito.getFav_descripcion_receta());

        String imagen_categoria = favorito.getFav_imagen_categoria();
        byte[] image_byte = Base64.decode(imagen_categoria, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        holder.Foto_categoria.setImageBitmap(bitmap);

        String imagen_receta = favorito.getFav_imagen_receta();
        byte[] image_byte1 = Base64.decode(imagen_receta, Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(image_byte1,0,image_byte1.length);
        holder.Foto_receta.setImageBitmap(bitmap1);

        holder.item_favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_detalle_receta_favorito = new Intent(v.getContext(), DettalleRecetaFavoritaActivity.class); // Enviando data al Detalle Receta Favorito
                i_detalle_receta_favorito.putExtra("FAV_ID_RECETA",lista_favoritos.get(position).getFav_idreceta());
                i_detalle_receta_favorito.putExtra("FAV_NOMB_RECETA",lista_favoritos.get(position).getFav_nombre_receta());
                i_detalle_receta_favorito.putExtra("FAV_DES_RECETA",lista_favoritos.get(position).getFav_descripcion_receta());
                i_detalle_receta_favorito.putExtra("FAV_IMG_RECETA",lista_favoritos.get(position).getFav_imagen_receta());
                v.getContext().startActivity(i_detalle_receta_favorito);
               // Toast.makeText(v.getContext(),"ID: "+ lista_favoritos.get(position).getFav_idreceta(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_favoritos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_favorito;
        TextView nombre_categoria,nombre_receta,descripcion_receta;
        ImageView Foto_categoria,Foto_receta;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene de item_ingrediente xml
            item_favorito=itemView.findViewById(R.id.cv_item_favoritos);
            nombre_categoria=itemView.findViewById(R.id.fav_nombre_categoria);
            nombre_receta=itemView.findViewById(R.id.lbl_item_nomb_rec_favorito);
            descripcion_receta=itemView.findViewById(R.id.lbl_item_descrip_rec_favorito);
            Foto_categoria=itemView.findViewById(R.id.ivItem_fav_imagencategoria);
            Foto_receta=itemView.findViewById(R.id.ivItemImagenFavorito_receta);

        }
    }
}
