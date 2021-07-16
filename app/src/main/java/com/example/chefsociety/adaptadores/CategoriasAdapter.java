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
import com.example.chefsociety.actividades.MisRecetasDetalleActivity;
import com.example.chefsociety.clases.Categoria;
import com.example.chefsociety.clases.Ingrediente;
import com.loopj.android.http.Base64;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriasAdapter extends  RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private List<Categoria> lista_categorias;

    public CategoriasAdapter(List<Categoria> lista_categorias) {
        this.lista_categorias =lista_categorias;
    }

    @NonNull
    @NotNull
    @Override
    public CategoriasAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias,parent,false);
        // devolver la vista
        return new CategoriasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoriasAdapter.ViewHolder holder, int position) {
        Categoria categoria = lista_categorias.get(position);
        // recuperando los datos
       // holder.nombre_categoria.setText(categoria.getNombre_ingrediente());
        holder.nombre_categoria.setText(categoria.getNombre_categoria());
        String imagen = categoria.getImagen_categoria();
        byte[] image_byte = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        holder.Foto_categoria.setImageBitmap(bitmap);
        holder.item_categorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_catalogo= new Intent(v.getContext(), CatalogoActivity.class);
                i_catalogo.putExtra("ID_CATEGORIA",lista_categorias.get(position).getIdcategoria());// PASANDO LA DATA  A CATALOGO ACTIVITY
                i_catalogo.putExtra("NOMBRE_CATEGORIA",lista_categorias.get(position).getNombre_categoria()); // PASANDO LA DATA  A CATALOGO ACTIVITY
                i_catalogo.putExtra("IMAGEN_CATEGORIA",lista_categorias.get(position).getImagen_categoria()); // PASANDO LA DATA  A CATALOGO ACTIVITY
                v.getContext().startActivity(i_catalogo);

                //Toast.makeText(v.getContext(),"ID: "+ lista_categorias.get(position).getIdcategoria(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_categorias.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // instanciar
        CardView item_categorias;
        TextView nombre_categoria;
        ImageView Foto_categoria;


        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // enlazando la parte lógica y gráfica
            // viene de item_categoria xml
            item_categorias=itemView.findViewById(R.id.cv_item_miscategorias);
            nombre_categoria=itemView.findViewById(R.id.lbl_item_nombre_categorias);
            Foto_categoria=itemView.findViewById(R.id.ivItemImagenCategorias);

        }
    }
}
